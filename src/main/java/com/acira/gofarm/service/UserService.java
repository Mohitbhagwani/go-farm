package com.acira.gofarm.service;


import com.acira.gofarm.constant.Status;
import com.acira.gofarm.dto.AuthRequest;
import com.acira.gofarm.dto.UserRegistrationDTO;
import com.acira.gofarm.entity.Address;
import com.acira.gofarm.entity.User;
import com.acira.gofarm.entity.UserInfo;
import com.acira.gofarm.exception.CustomException;
import com.acira.gofarm.repository.AddressRepository;
import com.acira.gofarm.repository.UserInfoRepository;
import com.acira.gofarm.repository.UserRepository;
import com.acira.gofarm.security.service.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private JwtService jwtService;


    public User getUserByContactNumber(Long contactNumber) {
        return userRepository.findByContactNumber(contactNumber);
    }

    public Map<String, Object> authenticate(AuthRequest authRequest) {
        User user = getUserByContactNumber(authRequest.getContactNumber());
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> userMap = new HashMap<>();
        if (user == null) {
            user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setContactNumber(authRequest.getContactNumber());
            user.setRoles("none");
            user.setStatus(Status.INACTIVE);
            userRepository.save(user);
        }
        if (user.getStatus()!=null && user.getStatus().equals(Status.ACTIVE)) {
            userMap.put("status", Status.ACTIVE);
            userMap.put("contactNumber", authRequest.getContactNumber());
            userMap.put("id", user.getId());
            response.put("user", userMap);
            response.put("token", jwtService.generateToken(userMap));
            return response;
        } else {
            userMap.put("status", Status.INACTIVE);
            userMap.put("contactNumber", authRequest.getContactNumber());
            userMap.put("id", user.getId());
            response.put("user", userMap);
            response.put("token", jwtService.generateToken(userMap));
            return response;
        }
    }

    @Transactional
    public UserRegistrationDTO registerUser(UserRegistrationDTO userRegistrationDTO) {
        Claims claims = jwtService.userData();
        User user = userRepository.findById(claims.get("id").toString()).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), String.format("User not found with %s", claims.get("id").toString())));
        if (user.getStatus().equals(Status.ACTIVE)) {
            throw new CustomException(HttpStatus.CONFLICT.value(), String.format("User with contact number %s is already enabled", user.getContactNumber().toString()));
        }
        var userDTO = userRegistrationDTO.getUser();
        var addressDTO = userRegistrationDTO.getAddressDetail();
        user.setEmail(userDTO.getEmail());
        user.setRoles(userDTO.getRole());
        user.setStatus(Status.ACTIVE); // You can set the initial enabled status

        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setFirstName(userDTO.getFirstName());
        userInfo.setLastName(userDTO.getLastName());
        userInfo.setAge(userDTO.getAge());
        userInfo.setGender(userDTO.getGender());
        userInfo.setAlternateContactNumber(userDTO.getAlternateContactNumber());


        // Link UserInfo to User
        userInfo.setUser(user);

        // Create Address entity
        Address address = new Address();
        address.setId(UUID.randomUUID().toString());
        address.setAddress1(addressDTO.getAddress1());
        address.setAddress2(addressDTO.getAddress2());
        address.setDistrict(addressDTO.getDistrict());
        address.setTaluk(addressDTO.getTaluk());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setPostalCode(addressDTO.getPostalCode());

        userInfo.setAddress(address.getId());
        // Link Address to UserInfo
        address.setUserInfo(userInfo);

        // Save entities to the database
        userRepository.save(user);
        userInfoRepository.save(userInfo);
        addressRepository.save(address);

        return userRegistrationDTO;
    }
}

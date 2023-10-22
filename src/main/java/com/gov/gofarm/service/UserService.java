package com.gov.gofarm.service;


import com.gov.gofarm.dto.AuthRequest;
import com.gov.gofarm.dto.UserRegistrationDTO;
import com.gov.gofarm.entity.Address;
import com.gov.gofarm.entity.User;
import com.gov.gofarm.entity.UserInfo;
import com.gov.gofarm.exception.CustomException;
import com.gov.gofarm.repository.AddressRepository;
import com.gov.gofarm.repository.UserInfoRepository;
import com.gov.gofarm.repository.UserRepository;
import com.gov.gofarm.security.service.JwtService;
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
            user.setEnabled(false);
            userRepository.save(user);
        }
        if (user.getEnabled()) {
            userMap.put("enabled", true);
            userMap.put("contactNumber", authRequest.getContactNumber());
            userMap.put("id", user.getId());
            response.put("user", userMap);
            response.put("token", jwtService.generateToken(userMap));
            return response;
        } else {
            userMap.put("enabled", false);
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
        if (user.getEnabled()) {
            throw new CustomException(HttpStatus.CONFLICT.value(), String.format("User with contact number %s is already enabled", user.getContactNumber().toString()));
        }
        var userDTO = userRegistrationDTO.getUser();
        var addressDTO = userRegistrationDTO.getAddressDetail();
        user.setEmail(userDTO.getEmail());
        user.setRoles(userDTO.getRole());
        user.setEnabled(true); // You can set the initial enabled status

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

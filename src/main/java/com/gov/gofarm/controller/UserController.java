package com.gov.gofarm.controller;


import com.gov.gofarm.dto.AuthRequest;
import com.gov.gofarm.dto.UserRegistrationDTO;
import com.gov.gofarm.dto.http.ApiResponse;
import com.gov.gofarm.security.service.JwtService;
import com.gov.gofarm.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;


    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public UserRegistrationDTO register(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) {
        return userService.registerUser(userRegistrationDTO);
    }


    @PostMapping("/authenticate")
    public ApiResponse authenticateUser(@RequestBody @Valid AuthRequest authRequest) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), ""));
        return new ApiResponse(true, userService.authenticate(authRequest), null);
    }


}

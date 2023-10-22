package com.gov.gofarm.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {
    @Valid
    private UserDTO user;
    @Valid
    private Address addressDetail;
}

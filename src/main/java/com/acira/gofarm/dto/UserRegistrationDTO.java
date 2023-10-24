package com.acira.gofarm.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.prefs.Preferences;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {
    @Valid
    private UserDTO user;
    @Valid
    private Address addressDetail;

    private Preference preference;
}

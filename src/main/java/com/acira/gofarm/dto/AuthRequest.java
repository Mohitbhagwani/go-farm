package com.acira.gofarm.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @Max(value = 9999999999L, message = "Contact number should be 10 digits long.")
    @Min(value = 1000000000L, message = "Contact number should be 10 digits long.")
    private Long contactNumber;
    @NotBlank(message = "otp cannot be blank.")
    private String otp;
}

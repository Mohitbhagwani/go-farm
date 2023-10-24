package com.acira.gofarm.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDTO {
    @NotBlank(message = "firstName cannot be left null, empty or blank")
    private String firstName;
    private String middleName;
    private String lastName;
    @NotBlank(message = "registrationNumber cannot be left null, empty or blank")
    private String registrationNumber;
    @Max(value = 9999999999L, message = "Maximum Age should be 68 yrs")
    @Min(value = 18L, message = "Minimum Age should be 18 yrs")
    private int age;
    @Pattern(regexp = "^(male|female|trans)$", message = "Invalid gender. Allowed values: male, female, trans")
    @NotBlank(message = "gender cannot be left null, empty or blank")
    private String gender;
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid email, kindly enter valid email.")
    @NotBlank(message = "email cannot be left null, empty or blank")
    private String email;
    @Pattern(regexp = "^(buyer|fpo)$", message = "Invalid role. Allowed values: buyer, fpo")
    @NotBlank(message = "role cannot be left null, empty or blank")
    private String role;
    private Long AlternateContactNumber;
    private Long totalShareMembers;

}

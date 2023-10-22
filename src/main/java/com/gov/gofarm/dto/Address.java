package com.gov.gofarm.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Address {
    @NotBlank(message = "address1 cannot be left null, empty or blank")
    private String address1;
    private String address2;
    @NotBlank(message = "town cannot be left null, empty or blank")
    private String town;
    @NotBlank(message = "taluk cannot be left null, empty or blank")
    private String taluk;
    @NotBlank(message = "district cannot be left null, empty or blank")
    private String district;
    @NotBlank(message = "city cannot be left null, empty or blank")
    private String city;
    @NotBlank(message = "state cannot be left null, empty or blank")
    private String state ="karnataka";
    @Max(value = 999999L, message = "postalCode number should be 6 digits long.")
    @Min(value = 100000L, message = "postalCode number should be 6 digits long.")
    private Integer postalCode;
}

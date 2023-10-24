package com.acira.gofarm.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CropDTO {
    private String id;
    @Pattern(regexp = "^(local|hybrid|others)$", message = "Invalid variety. Allowed values: local, hybrid, others")
    @NotBlank(message = "role cannot be left null, empty or blank")
    private String variety;
    private String otherVariety;
    private BigDecimal quantity;
    private String lotId;
    private String distanceRange;
    private UUID masterCropId;
    private BigDecimal price;
    private String uom;
}

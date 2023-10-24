package com.acira.gofarm.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterCropDetail {
    @Id
    private String id;
    private String cropName;
    private String kannadaCropName;
    private String uom; // This is the column with comma-separated UOMs

    @Transient
    private List<String> uomList; // Transient field to hold UOMs as a list

    // Constructor, Getters, and Setters

    @PostLoad
    private void onLoad() {
        if (uom != null && !uom.isEmpty()) {
            uomList = Arrays.stream(uom.split(","))
                    .map(String::trim) // Trim each element
                    .collect(Collectors.toList());
        } else {
            uomList = new ArrayList<>();
        }
    }


    @PrePersist
    @PreUpdate
    private void onSave() {
        if (uomList != null && !uomList.isEmpty()) {
            uom = String.join(",", uomList);
        } else {
            uom = null;
        }
    }

    @JsonIgnore
    public String getUom() {
        return uom;
    }

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;
}



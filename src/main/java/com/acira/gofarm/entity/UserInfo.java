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
public class UserInfo {
    @Id
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private int age;
    private String gender;
    private Long alternateContactNumber;
    private String address;
    private String registrationNumber;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    private String farmerMajorCrops;

//    @Transient
//    private List<String> farmerMajorCropList;

    private String buyerPreferredLocations;

//    @Transient
//    private List<String> buyerPreferredLocationList;

    private String buyerPreferredCrops;
//
//    @Transient
//    private List<String> buyerPreferredCropList;

//    @PostLoad
//    private void onLoad() {
//        if (farmerMajorCrops != null && !farmerMajorCrops.isEmpty()) {
//            farmerMajorCropList = Arrays.stream(farmerMajorCrops.split(","))
//                    .map(String::trim) // Trim each element
//                    .collect(Collectors.toList());
//        } else {
//            farmerMajorCropList = new ArrayList<>();
//        }
//
//
//        if (buyerPreferredLocations != null && !buyerPreferredLocations.isEmpty()) {
//            buyerPreferredLocationList = Arrays.stream(buyerPreferredLocations.split(","))
//                    .map(String::trim) // Trim each element
//                    .collect(Collectors.toList());
//        } else {
//            buyerPreferredLocationList = new ArrayList<>();
//        }
//
//        if (buyerPreferredCrops != null && !buyerPreferredCrops.isEmpty()) {
//            buyerPreferredCropList = Arrays.stream(buyerPreferredCrops.split(","))
//                    .map(String::trim)
//                    .collect(Collectors.toList());
//        } else {
//            buyerPreferredCropList = new ArrayList<>();
//        }
//
//    }
//
//
//    @PrePersist
//    @PreUpdate
//    private void onSave() {
//        if (farmerMajorCropList != null && !farmerMajorCropList.isEmpty()) {
//            farmerMajorCrops = String.join(",", farmerMajorCropList);
//        } else {
//            farmerMajorCrops = null;
//        }
//
//        if (buyerPreferredLocationList != null && !buyerPreferredLocationList.isEmpty()) {
//            buyerPreferredLocations = String.join(",", buyerPreferredLocationList);
//        } else {
//            buyerPreferredLocations = null;
//        }
//
//        if (buyerPreferredCropList != null && !buyerPreferredCropList.isEmpty()) {
//            buyerPreferredCrops = String.join(",", buyerPreferredCropList);
//        } else {
//            buyerPreferredCrops = null;
//        }
//
//    }

//    @JsonIgnore
    public String getFarmerMajorCrops() {
        return farmerMajorCrops;
    }

//    @JsonIgnore
    public String getBuyerPreferredLocation() {
        return buyerPreferredLocations;
    }


//    @JsonIgnore
    public String getBuyerPreferredCrops() {
        return buyerPreferredCrops;
    }

    @OneToOne
    @MapsId
    private User user;

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private List<Address> addresses;

    @ManyToOne
    @JoinColumn(name = "media_id")
    private Media media;
}


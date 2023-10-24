package com.acira.gofarm.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Crop {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo user;

    private String variety;
    private String otherVariety;

    private String cropDescription;
    private Timestamp plantingDate;
    private Timestamp harvestDate;
    private String cropLocation;
    private BigDecimal quantityHarvested;
    private String cropCondition;
    private String lotId;
    private String distanceRange;
    private String uom;

    @ManyToOne
    @JoinColumn(name = "master_crop_id")
    private MasterCropDetail masterCrop;

    @ManyToOne
    @JoinColumn(name = "media_id")
    private Media media;

    private BigDecimal price;

    public void setPrice(BigDecimal price) {
        if (price != null) {
            this.price = price.setScale(2, RoundingMode.DOWN);
        }
    }

    public void setQuantityHarvested(BigDecimal quantityHarvested) {
        if (quantityHarvested != null) {
            this.quantityHarvested = quantityHarvested.setScale(2, RoundingMode.DOWN);
        }
    }

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updatedAt;

    private Timestamp deletedAt;
}

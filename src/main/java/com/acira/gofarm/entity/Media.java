package com.acira.gofarm.entity;

import com.acira.gofarm.constant.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Media {
    @Id
    private String id;

    private String mediaDescription;
    private String fileName;
    private String mediaType;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Lob
    private String mediaData;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updatedAt;

    private Timestamp deletedAt;
}

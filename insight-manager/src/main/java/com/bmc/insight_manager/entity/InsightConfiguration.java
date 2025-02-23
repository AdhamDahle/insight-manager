package com.bmc.insight_manager.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "insight_configurations",
        indexes = {
                @Index(name = "idx_insight_type_name", columnList = "insight_type_name"),
                @Index(name = "idx_created_date", columnList = "created_date"),
                @Index(name = "idx_last_updated_date", columnList = "last_updated_date")
        })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsightConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "insight_type_name", unique = true, nullable = false)
    private String insightTypeName;

    @Column(name = "insight_consumed", nullable = false)
    private int insightConsumed = 0;

    @Column(name = "time_saved", nullable = false)
    private int timeSaved = 0;

    @Column(name = "created_date", updatable = false, nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastUpdatedDate;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdDate = now;
        this.lastUpdatedDate = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdatedDate = LocalDateTime.now();
    }
}

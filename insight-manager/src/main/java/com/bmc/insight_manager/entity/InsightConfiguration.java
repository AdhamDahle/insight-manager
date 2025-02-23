package com.bmc.insight_manager.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Entity representing an Insight Configuration")
public class InsightConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the configuration", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "insight_type_name", unique = true, nullable = false)
    @Schema(description = "Name of the insight type", example = "error", required = true)
    private String insightTypeName;

    @Column(name = "insight_consumed", nullable = false)
    @Schema(description = "Number of insights consumed", example = "5", required = true)
    private int insightConsumed = 0;

    @Column(name = "time_saved", nullable = false)
    @Schema(description = "Time saved in minutes", example = "10", required = true)
    private int timeSaved = 0;

    @Column(name = "created_date", updatable = false, nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Creation date of the configuration", example = "2023-10-12T08:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Last update date of the configuration", example = "2023-10-12T08:30:00", accessMode = Schema.AccessMode.READ_ONLY)
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
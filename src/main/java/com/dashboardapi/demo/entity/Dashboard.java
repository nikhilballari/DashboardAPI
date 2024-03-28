package com.dashboardapi.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entity class that represents the table from the database
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(
            name = "created_date",
            columnDefinition = "DATE",
            nullable = false
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate createdAt;
    @Column(
            name = "updated_date",
            columnDefinition = "DATE",
            nullable = false
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate updatedAt;
    private String title;
}

package com.evotektask.inventory_app.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String namaBarang;

    @Column(nullable = false)
    private Integer jumlahStokBarang;

    @Column(nullable = false, unique = true)
    private String nomorSeriBarang;

    @Column(columnDefinition = "jsonb")
    private JsonNode additionalInfo;

    private String gambarBarang;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
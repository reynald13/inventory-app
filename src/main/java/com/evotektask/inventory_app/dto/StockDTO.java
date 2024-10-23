package com.evotektask.inventory_app.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StockDTO {
    @NotNull
    @Size(min = 1, max = 100)
    private String namaBarang;

    @NotNull
    private Integer jumlahStokBarang;

    @NotNull
    @Size(min = 1, max = 50)
    private String nomorSeriBarang;

    private String additionalInfo;
    private String gambarBarang;
}

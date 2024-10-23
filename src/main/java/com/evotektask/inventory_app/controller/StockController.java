package com.evotektask.inventory_app.controller;

import com.evotektask.inventory_app.dto.StockDTO;
import com.evotektask.inventory_app.model.Stock;
import com.evotektask.inventory_app.service.StockService;
import com.evotektask.inventory_app.utils.FileValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;
    private final FileValidator fileValidator;

    public StockController(StockService stockService, FileValidator fileValidator) {
        this.stockService = stockService;
        this.fileValidator = fileValidator;
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
        }

        String mimeTypeError = fileValidator.validateMimeType(file);
        if (mimeTypeError != null) {
            return new ResponseEntity<>(mimeTypeError, HttpStatus.BAD_REQUEST);
        }

        String fileSizeError = fileValidator.validateFileSize(file, 5 * 1024 * 1024);  // 5MB limit
        if (fileSizeError != null) {
            return new ResponseEntity<>(fileSizeError, HttpStatus.BAD_REQUEST);
        }

        try {
            file.transferTo(new java.io.File("uploads/" + file.getOriginalFilename()));  // Save file to 'uploads' folder
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createStock(@RequestBody StockDTO stockDTO) {
        try {
            Stock stock = stockService.fromDTO(stockDTO);  // Convert from DTO to entity
            return ResponseEntity.ok(stockService.createStock(stock));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create stock: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Stock> listStocks() {
        return stockService.listAllStocks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Optional<Stock> stock = stockService.getStockById(id);
        return stock.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStock(@PathVariable Long id) {
        Optional<Stock> stock = stockService.getStockById(id);
        if (stock.isPresent()) {
            stockService.deleteStock(id);
            return ResponseEntity.ok("Stock deleted successfully");
        } else {
            return new ResponseEntity<>("Stock not found", HttpStatus.NOT_FOUND);
        }
    }
}
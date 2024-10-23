package com.evotektask.inventory_app.service;

import com.evotektask.inventory_app.dto.StockDTO;
import com.evotektask.inventory_app.model.Stock;
import com.evotektask.inventory_app.repository.StockRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private static final Logger logger = LogManager.getLogger(StockService.class);
    private final StockRepository stockRepository;
    private final ObjectMapper objectMapper;

    public StockService(StockRepository stockRepository, ObjectMapper objectMapper) {
        this.stockRepository = stockRepository;
        this.objectMapper = objectMapper;
    }

    public Stock createStock(Stock stock) {
        logger.info("Creating new stock: {}", stock.getNamaBarang());
        return stockRepository.save(stock);
    }

    public List<Stock> listAllStocks() {
        logger.info("Fetching all stocks from the database.");
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockById(Long id) {
        logger.info("Fetching stock with ID: {}", id);
        return stockRepository.findById(id);
    }

    public Stock fromDTO(StockDTO stockDTO) throws Exception {
        Stock stock = new Stock();
        stock.setNamaBarang(stockDTO.getNamaBarang());
        stock.setJumlahStokBarang(stockDTO.getJumlahStokBarang());
        stock.setNomorSeriBarang(stockDTO.getNomorSeriBarang());

        if (stockDTO.getAdditionalInfo() != null) {
            JsonNode additionalInfoNode = objectMapper.readTree(stockDTO.getAdditionalInfo());
            stock.setAdditionalInfo(additionalInfoNode);
        }

        stock.setGambarBarang(stockDTO.getGambarBarang());
        return stock;
    }

    public StockDTO toDTO(Stock stock) {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setNamaBarang(stock.getNamaBarang());
        stockDTO.setJumlahStokBarang(stock.getJumlahStokBarang());
        stockDTO.setNomorSeriBarang(stock.getNomorSeriBarang());

        if (stock.getAdditionalInfo() != null) {
            stockDTO.setAdditionalInfo(stock.getAdditionalInfo().toString());
        }

        stockDTO.setGambarBarang(stock.getGambarBarang());
        return stockDTO;
    }

    public void deleteStock(Long id) {
        logger.info("Deleting stock with ID: {}", id);
        stockRepository.deleteById(id);
    }

    public Stock updateStock(Long id, StockDTO stockDTO) throws Exception {
        Optional<Stock> stockOptional = stockRepository.findById(id);
        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            stock.setNamaBarang(stockDTO.getNamaBarang());
            stock.setJumlahStokBarang(stockDTO.getJumlahStokBarang());
            stock.setNomorSeriBarang(stockDTO.getNomorSeriBarang());

            JsonNode additionalInfoJson = objectMapper.readTree(stockDTO.getAdditionalInfo());
            stock.setAdditionalInfo(additionalInfoJson);

            stock.setGambarBarang(stockDTO.getGambarBarang());
            return stockRepository.save(stock);
        }
        return null;
    }
}
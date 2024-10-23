package com.evotektask.inventory_app.repository;

import com.evotektask.inventory_app.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}

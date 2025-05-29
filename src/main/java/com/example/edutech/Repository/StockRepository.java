package com.example.edutech.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.edutech.Model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findByIdProducto(Long idProducto);
    Stock findByNombreProducto(String nombreProducto);
}
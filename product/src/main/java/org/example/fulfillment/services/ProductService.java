package org.example.fulfillment.services;

import org.example.fulfillment.dto.ProductDTO;
import org.example.fulfillment.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    void loadCsvData();

    Product create(ProductDTO dto);

    List<Product> readAll();

    Product update(Product product);

    void delete(Long id);
    List<Product> readFilter(String status);
    BigDecimal sumValue();
    Optional<Product> findById(Long id);
}

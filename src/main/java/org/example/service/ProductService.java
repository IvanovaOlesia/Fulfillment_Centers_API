package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.ProductDTO;
import org.example.entity.Product;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public Product create(ProductDTO dto){
        return productRepository.save(Product.builder()
                .productId(dto.getProductId())
                .status(dto.getStatus())
                .fulfillmentCenter(dto.getFulfillmentCenter())
                .quantity(dto.getQuantity())
                .value(dto.getValue())
                .build());

    }
    public List<Product> readAll(){
        return productRepository.findAll();
    }
    public Product update(Product product){
        return productRepository.save(product);
    }
    public void delete(Long id){
        productRepository.deleteById(id);
    }
}

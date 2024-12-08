package org.example.controller;

import org.example.dto.ProductDTO;
import org.example.entity.Product;
import org.example.repository.ProductRepository;
import org.example.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest

class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;


    @Test
    void create() {
        ProductDTO productDTO = ProductDTO.builder()
                .productId("p45")
                .status("Sellable")
                .fulfillmentCenter("fc5")
                .quantity(6)
                .value(349)
                .build();
        Product product = Product.builder()
                .productId(productDTO.getProductId())
                .status(productDTO.getStatus())
                .fulfillmentCenter(productDTO.getFulfillmentCenter())
                .quantity(productDTO.getQuantity())
                .value(productDTO.getValue())
                .build();
        when(productRepository.save(productDTO)).thenReturn(product)
    }

    @Test
    void readAll() {
    }

    @Test
    void findAllByStatus() {
    }

    @Test
    void sumValue() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void loadCsv() {
    }
}
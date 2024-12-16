package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.example.dto.ProductDTO;
import org.example.entity.Product;
import org.example.service.ProductService;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProductService productService;
    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProductController productController;

    private final ProductDTO productDTO = ProductDTO.builder()
            .productId("p45")
            .status("Sellable")
            .fulfillmentCenter("fc5")
            .quantity(6)
            .value(349)
            .build();
    private final Product product1 = Product.builder()
            .productId("p45")
            .status("Sellable")
            .fulfillmentCenter("fc5")
            .quantity(6)
            .value(349)
            .build();
    private final Product product2 = Product.builder()
            .productId("p46")
            .status("Sellable")
            .fulfillmentCenter("fc4")
            .quantity(8)
            .value(525)
            .build();


    @Test
    void createProductTest() throws Exception {
        when(productService.create(productDTO)).thenReturn(product1);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(product1.getProductId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(product1.getStatus()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fulfillmentCenter").value(product1.getFulfillmentCenter()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(product1.getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.value").value(product1.getValue()))
                .andDo(print());
    }
    @Test
    void readAllTest() throws Exception {
        List<Product> products = Arrays.asList(product1,product2);
        when(productService.readAll()).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productId").value(product1.getProductId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value(product1.getStatus()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fulfillmentCenter").value(product1.getFulfillmentCenter()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantity").value(product1.getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].value").value(product1.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].productId").value(product1.getProductId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].status").value(product1.getStatus()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].fulfillmentCenter").value(product1.getFulfillmentCenter()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].quantity").value(product1.getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].value").value(product1.getValue()))
                .andDo(print());
    }
    @Test
     void findAllByStatusTest() throws Exception {
        List<Product> products = Arrays.asList(product1,product2);
        when(productService.readFilter("Sellable")).thenReturn(products);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/{status}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString("Sellable")))
                .andExpect()

    }
}
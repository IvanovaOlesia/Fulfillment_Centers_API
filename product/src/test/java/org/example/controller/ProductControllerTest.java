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
    private final Product product = Product.builder()
            .productId(productDTO.getProductId())
            .status(productDTO.getStatus())
            .fulfillmentCenter(productDTO.getFulfillmentCenter())
            .quantity(productDTO.getQuantity())
            .value(productDTO.getValue())
            .build();


    @Test
    void createProduct() throws Exception {
        when(productService.create(productDTO)).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(product.getProductId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(product.getStatus()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fulfillmentCenter").value(product.getFulfillmentCenter()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(product.getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.value").value(product.getValue()))
                .andDo(print());
    }

}
package org.example.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private String productId;
    private String status;
    private String fulfillmentCenter;
    private int quantity;
    private double value;
}

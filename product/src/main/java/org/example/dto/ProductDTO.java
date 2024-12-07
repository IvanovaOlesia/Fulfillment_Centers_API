package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductDTO {
    @NotNull
    @NotBlank(message = "Product id is required")
    private String productId;
    @NotNull
    @NotBlank(message = "Status is required")
    @Pattern(regexp = "Sellable|Unfulfillable|Inbound", message = "Status must be one of: Sellable, Unfulfillable, Inbound")
    private String status;
    @NotNull
    @NotBlank(message = "Fulfillment center is required")
    private String fulfillmentCenter;
    @NotNull
    @Positive(message = "Quantity cannot be a negative number" )
    private int quantity;
    @NotNull
    @Positive(message = "Value cannot be a negative number" )
    private double value;
}

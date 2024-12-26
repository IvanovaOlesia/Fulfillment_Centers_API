package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "##0.00")
    private BigDecimal value;
}

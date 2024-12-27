package org.example.fulfillment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
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
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
    @NotNull
    @Positive(message = "Value cannot be a negative number" )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "##0.00")
    @DecimalMin(value = "0.01", message = "Value must be greater than or equal to 0.01")
    private BigDecimal value;
}

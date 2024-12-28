package org.example.fulfillment.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.example.fulfillment.dto.ProductDTO;
import org.example.fulfillment.entity.Product;
import org.example.fulfillment.services.ProductService;
import org.example.fulfillment.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "main_methods")
@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
@Validated
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create a new product",
            description = "Creates a new product based on the provided ProductDTO.")
    public ResponseEntity<?> create(@RequestBody @Validated ProductDTO dto) {
        return new ResponseEntity<>(productService.create(dto), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all products",
            description = "Retrieves a list of all products.")
    public ResponseEntity<List<Product>> readAll() {
        return new ResponseEntity<>(productService.readAll(), HttpStatus.OK);
    }
    @GetMapping("/status/{status}")
    @Operation(summary = "Find products by status",
            description = "Retrieves a list of products filtered by the specified status.")
    public ResponseEntity<List<Product>> findAllByStatus(@PathVariable  @Pattern(regexp = "Sellable|Unfulfillable|Inbound", message = "Status must be one of: Sellable, Unfulfillable, Inbound")  String status) {
        return new ResponseEntity<>(productService.readFilter(status), HttpStatus.OK);
    }
    @GetMapping("/value")
    @Operation(summary = "Calculate total value of products",
            description = "Calculates and returns the total value of all products.")
    public ResponseEntity<BigDecimal> sumValue(){
        return new ResponseEntity<>(productService.sumValue(), HttpStatus.OK);
    }

    @PutMapping
    @Operation(summary = "Update an existing product",
            description = "Updates the details of an existing product based on the provided Product object.")
    public ResponseEntity<Product> update(@Validated @RequestBody Product product) {
        return new ResponseEntity<>(productService.update(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product",
            description = "Deletes the product with the specified ID.")
    public ResponseEntity<?> delete(@PathVariable @Min(1) Long id) {
        Product product = productService.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
        productService.delete(id);
        return new ResponseEntity<>("Product " + product.getId() + " deleted successfully",HttpStatus.OK );
    }

    @PutMapping("/load-csv")
    @Operation(summary = "Load products from CSV",
            description = "Loads product data from a CSV file.")
    public HttpStatus loadCsv() {
        productService.loadCsvData();
        return HttpStatus.OK;
    }


}

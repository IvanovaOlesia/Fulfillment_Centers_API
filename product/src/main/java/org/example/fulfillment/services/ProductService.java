package org.example.fulfillment.services;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.fulfillment.dto.ProductDTO;
import org.example.fulfillment.entity.Product;
import org.example.fulfillment.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.util.List;

@Service
public class ProductService {
    private final Resource resource;
    private final ProductRepository productRepository;
    @Autowired
    public ProductService(@Value("${csv.file.path}") Resource resource, ProductRepository productRepository) {
        this.resource = resource;
        this.productRepository = productRepository;
    }

    public void loadCsvData() {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            csvReader.skip(1);
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                productRepository.save(Product.builder()
                        .productId(values[0])
                        .status(values[1])
                        .fulfillmentCenter(values[2])
                        .quantity(Integer.parseInt(values[3].trim()))
                        .value(Double.parseDouble(values[4].trim()))
                        .build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
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
    public List<Product> readFilter(String status){
       return productRepository.findAllByStatus(status);
    }
    public Double sumValue(){
        return productRepository.sumValueByStatus();
    }
}

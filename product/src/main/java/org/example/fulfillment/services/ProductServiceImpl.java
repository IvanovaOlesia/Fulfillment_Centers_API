package org.example.fulfillment.services;

import com.opencsv.CSVReader;
import org.example.fulfillment.dto.ProductDTO;
import org.example.fulfillment.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.fulfillment.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final Resource resource;
    private final ProductRepository productRepository;
    @Autowired
    public ProductServiceImpl(@Value("${csv.file.path}") Resource resource, ProductRepository productRepository) {
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
                        .value(new BigDecimal(values[4].trim()))
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
    public BigDecimal sumValue(){
        return productRepository.sumValueByStatus();
    }
    public Optional<Product> findById(Long id){
        return productRepository.findById(id);
    }
}

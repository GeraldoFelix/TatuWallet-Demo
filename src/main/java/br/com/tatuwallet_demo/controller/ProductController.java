package br.com.tatuwallet_demo.controller;

import br.com.tatuwallet_demo.dto.ProductRecordDto;
import br.com.tatuwallet_demo.model.ProductModel;
import br.com.tatuwallet_demo.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/produtos")
    public ResponseEntity<ProductModel> save (@RequestBody @Valid ProductRecordDto productRecordDto) {
        var product_model = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, product_model);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(product_model));

    }

    @GetMapping("/produtos")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(productRepository.findAll());
    }

    @GetMapping("/produtos/{id}")
    public ResponseEntity<ProductModel> getProduct (@PathVariable (value = "id") UUID id) {
        Optional<ProductModel> productModel = productRepository.findById(id);
        if (productModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(productModel.get());
    }

    @PutMapping ("/produtos/{id}")
    public ResponseEntity<ProductModel> UpdateProduct (@PathVariable (value = "id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> productModel = productRepository.findById(id);
        if (productModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        var product_model = productModel.get();
        BeanUtils.copyProperties(productRecordDto, product_model);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(product_model));
    }

    @DeleteMapping ("/produtos/{id}")
    public ResponseEntity<Object> deleteProduct (@PathVariable (value = "id") UUID id) {
        Optional<ProductModel> productModel = productRepository.findById(id);
        if (productModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        productRepository.delete(productModel.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted");
    }

}

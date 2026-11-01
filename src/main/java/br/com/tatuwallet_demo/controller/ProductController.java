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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}

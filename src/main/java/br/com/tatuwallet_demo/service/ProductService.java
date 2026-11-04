package br.com.tatuwallet_demo.service;

import br.com.tatuwallet_demo.dto.ProductRecordDto;
import br.com.tatuwallet_demo.model.ProductModel;
import br.com.tatuwallet_demo.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductModel save(ProductRecordDto dto) {
        var product = new ProductModel();
        BeanUtils.copyProperties(dto, product);
        return productRepository.save(product);
    }

    public List<ProductModel> findAll() {
        return productRepository.findAll();
    }

    public Optional<ProductModel> findById(UUID id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Optional<ProductModel> update(UUID id, ProductRecordDto dto) {
        return productRepository.findById(id).map(product -> {
            BeanUtils.copyProperties(dto, product);
            return productRepository.save(product);
        });
    }

    @Transactional
    public boolean delete(UUID id) {
        return productRepository.findById(id).map(product -> {
            productRepository.delete(product);
            return true;
        }).orElse(false);
    }
}
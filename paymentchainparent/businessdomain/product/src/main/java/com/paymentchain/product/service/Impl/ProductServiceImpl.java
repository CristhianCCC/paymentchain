package com.paymentchain.product.service.Impl;
import com.paymentchain.product.common.StandardizedExceptionResponse;
import com.paymentchain.product.entities.Product;
import com.paymentchain.product.exceptions.ApiExceptionHandler;
import com.paymentchain.product.exceptions.BusinessRuleException;
import com.paymentchain.product.repositories.ProductRepository;
import com.paymentchain.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product postProduct(Product product) throws BusinessRuleException {
        String nombre = product.getName();
        String code = product.getCode();
        if(nombre.isBlank() || code.isBlank()) {
            //aplicando excepcion de tipo businessRuleException
            BusinessRuleException businessRuleException = new BusinessRuleException("601", HttpStatus.PRECONDITION_FAILED, product.getId(), "uno o ambos campos es vacio, por favor verificar");
            throw businessRuleException;
        }
        return productRepository.save(product);
    }

    @Override
    public Product putProduct(Long id, Product product) {
        return productRepository.findById(id).map(productFound -> {
            productFound.setCode(product.getCode());
            productFound.setName(product.getName());
            return productRepository.save(productFound);
        }).orElseThrow(RuntimeException::new);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

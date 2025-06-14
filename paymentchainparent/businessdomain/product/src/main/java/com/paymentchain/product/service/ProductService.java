package com.paymentchain.product.service;
import com.paymentchain.product.entities.Product;
import com.paymentchain.product.exceptions.BusinessRuleException;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Optional<Product> findProductById(Long id);

    Product postProduct(Product product) throws BusinessRuleException;

    Product putProduct(Long id, Product product);

    void deleteProduct(Long id);
}

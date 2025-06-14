package com.paymentchain.product.controllers;
import com.paymentchain.product.entities.Product;
import com.paymentchain.product.exceptions.BusinessRuleException;
import com.paymentchain.product.repositories.ProductRepository;
import com.paymentchain.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")// /v1
public class ProductController {

@Autowired
private ProductService productService;

@GetMapping
public ResponseEntity<List<Product>> findAll(){
    List<Product> productList = productService.findAll();
    return ResponseEntity.status(HttpStatus.OK).body(productList);
}

@GetMapping("/{id}")
public ResponseEntity<Optional<Product>> findProduct(@PathVariable Long id) {
    Optional<Product> productOptional = productService.findProductById(id);
    return ResponseEntity.status(HttpStatus.OK).body(productOptional);
}

@PostMapping
    public ResponseEntity<Product> postProduct (@RequestBody Product product) throws BusinessRuleException {
    Product productCreated = productService.postProduct(product);
    return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
}

@PutMapping("/{id}")
public ResponseEntity<Product> putProduct (@PathVariable Long id, @RequestBody Product product){
    Product productEdited = productService.putProduct(id, product);
    return ResponseEntity.ok(productEdited);
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteProduct (@PathVariable Long id){
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
}

}

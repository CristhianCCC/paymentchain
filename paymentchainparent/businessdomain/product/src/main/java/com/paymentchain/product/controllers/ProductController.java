package com.paymentchain.product.controllers;
import com.paymentchain.product.entities.Product;
import com.paymentchain.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

@Autowired
private ProductRepository productRepository;

@GetMapping
public List<Product> findAll(){
    return productRepository.findAll();
}

@GetMapping("/{id}")
public ResponseEntity<Product> findProduct(@PathVariable Long id) {
    Optional<Product> productOptional = productRepository.findById(id);
    return productOptional.map(customer -> ResponseEntity.ok().body(customer)).orElseGet(() -> ResponseEntity.notFound().build());
}

@PostMapping
    public ResponseEntity<?> postProduct (@RequestBody Product product){
    Product productCreated = productRepository.save(product);
    return ResponseEntity.ok(productCreated);
}

@PutMapping("/{id}")
public ResponseEntity<Product> putProduct (@PathVariable Long id, @RequestBody Product product){
    Optional<Product> productOptional = productRepository.findById(id);
    if (productOptional.isPresent()){
        Product productEdited = productOptional.get();
        productEdited.setName(product.getName());
        productEdited.setCode(product.getCode());
        productRepository.save(productEdited);
        return ResponseEntity.ok().body(productEdited);
    }else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteProduct (@PathVariable Long id){
    productRepository.deleteById(id);
    return ResponseEntity.noContent().build();
}

}

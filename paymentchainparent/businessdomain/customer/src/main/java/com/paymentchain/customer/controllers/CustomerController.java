package com.paymentchain.customer.controllers;
import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.exceptions.BusinessRuleException;
import com.paymentchain.customer.repositories.CustomerRepository;
import com.paymentchain.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;





    //inyectando y usando Environment para mostrar si se esta mostrando el ambiente de desarrollador o local
    @Autowired
    private Environment env;

    @GetMapping("/check")
    public String profileName (){
        return "El perfil activo es : " + env.getProperty ("custom.activeprofileName");
    }



    @GetMapping
    public ResponseEntity<List<Customer>> findAll(){
        List<Customer> listCustomer =customerRepository.findAll();
        return ResponseEntity.status(HttpStatus.FOUND).body(listCustomer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findCustomer(@PathVariable("id") Long id) { //("id") se hace asi para que swagger la pueda leer
        Optional<Customer> customerOptional = customerRepository.findById(id);
        return customerOptional.map(customer -> ResponseEntity.ok().body(customer)).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<?> postCustomer (@RequestBody Customer customer) throws BusinessRuleException, UnknownHostException {
        Customer customerCreated = customerService.postCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> putCustomer (@PathVariable("id") Long id, @RequestBody Customer customer){
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()){
            Customer customerEdited = customerOptional.get();
            customerEdited.setNombre(customer.getNombre());
            customerEdited.setPhone(customer.getPhone());
            customerEdited.setAddress(customer.getAddress());
            customerEdited.setCode(customer.getCode());
            customerEdited.setIban(customer.getIban());
            customerEdited.setTransactions(customer.getTransactions());
            customerRepository.save(customerEdited);
            return ResponseEntity.ok().body(customerEdited);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long id) {
        customerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/full")
    public Customer getByCode (@RequestParam(name = "code") String code){
        //buscando al cliente por codigo
        Customer customer =  customerService.getByCodeService(code);

        return customer;
    }




}
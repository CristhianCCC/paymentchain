package com.paymentchain.customer.entities;

import jakarta.persistence.*;
import jakarta.transaction.Transaction;

import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String nombre;
    private String phone;
    private String Iban;
    private String  surname;
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerProduct> products;

    @Transient // it means that it won't save in the database
    private List<?> transactions; //the <?> allows to add elements of any type could be String, int etc


    public Customer() {
    }

    public Customer(Long id, String code, String nombre, String phone, String iban, String surname, String address, List<CustomerProduct> products, List<?> transactions) {
        this.id = id;
        this.code = code;
        this.nombre = nombre;
        this.phone = phone;
        this.Iban = iban;
        this.surname = surname;
        this.address = address;
        this.products = products;
        this.transactions = transactions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIban() {
        return Iban;
    }

    public void setIban(String iban) {
        Iban = iban;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<CustomerProduct> getProducts() {
        return products;
    }

    public void setProducts(List<CustomerProduct> products) {
        this.products = products;
    }

    public List<?> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<?> transactions) {
        this.transactions = transactions;
    }
}
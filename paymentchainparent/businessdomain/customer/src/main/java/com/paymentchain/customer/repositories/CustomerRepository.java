package com.paymentchain.customer.repositories;
import com.paymentchain.customer.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository <Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.code = ?1")
    Customer findByCode(String code);

}

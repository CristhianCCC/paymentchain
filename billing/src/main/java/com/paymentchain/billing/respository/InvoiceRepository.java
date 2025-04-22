
package com.paymentchain.billing.respository;

import com.paymentchain.billing.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    
}

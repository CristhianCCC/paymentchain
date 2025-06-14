
package com.paymentchain.billing.controller;

import com.paymentchain.billing.common.InvoiceRequestMapper;
import com.paymentchain.billing.common.InvoiceResponseMapper;
import com.paymentchain.billing.dto.InvoiceRequest;
import com.paymentchain.billing.dto.InvoiceResponse;
import com.paymentchain.billing.entities.Invoice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.paymentchain.billing.respository.InvoiceRepository;
import java.util.Optional;

/* documentando a nivel de clase que hara esta api */
@Tag(name = "Billing Api", description = "This Api serve all functionality for management invoices")
@RestController
@RequestMapping("/billing")
public class InvoiceRestController {
    
    @Autowired
    InvoiceRepository invoiceRepository;

    /*se inyectan los dto*/
    @Autowired
    InvoiceResponseMapper invoiceResponseMapper;

    @Autowired
    InvoiceRequestMapper invoiceRequestMapper;

    /* documentando a nivel de metodo que hara este metodo */
    @Operation(description = "return all invoices", summary = "return 204 if not found")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "exito"), @ApiResponse(responseCode = "500", description = "internal error")})
    @GetMapping()
    public ResponseEntity<List<InvoiceResponse>> listInvoices(){
        List<Invoice> invoices = invoiceRepository.findAll();
        /*se implementa el metodo que pasa de invoices a invoicesresponse y se pasa como parametro el listado de invoices que devolvio el repositorio*/
        return ResponseEntity.ok(invoiceResponseMapper.InvoiceListToInvoiceResponseList(invoices));
    }
    
    @GetMapping("/{id}")
    public Optional<Invoice> get(@PathVariable long id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        return invoice;
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable String id, @RequestBody Invoice input) {
        return null;
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody InvoiceRequest invoiceRequest) {
        /*se trae el metodo de invoicerequest y se pasa como parametro el body*/
        Invoice InvoiceRequestToInvoice = invoiceRequestMapper.InvoiceRequestToInvoice(invoiceRequest);
        /*se guarda el invoice request utilizando el repositorio*/
        Invoice invoiceSaved = invoiceRepository.save(InvoiceRequestToInvoice);
        /*se toma el objeto guardado para devolverlo en pantalla*/
        InvoiceResponse invoiceResponse = invoiceResponseMapper.InvoiceToInvoiceResponse(invoiceSaved);
        /*se devuelve el objeto en pantalla que se habia guardado*/
        return ResponseEntity.ok(invoiceResponse);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
         Optional<Invoice> dto = invoiceRepository.findById(Long.valueOf(id));
        if (!dto.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        invoiceRepository.delete(dto.get());
        return ResponseEntity.ok().build();
    }
    
}

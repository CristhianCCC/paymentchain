package com.paymentchain.billing.common;

import com.paymentchain.billing.dto.InvoiceResponse;
import com.paymentchain.billing.entities.Invoice;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-14T12:43:34-0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class InvoiceResponseMapperImpl implements InvoiceResponseMapper {

    @Override
    public InvoiceResponse InvoiceToInvoiceResponse(Invoice source) {
        if ( source == null ) {
            return null;
        }

        InvoiceResponse invoiceResponse = new InvoiceResponse();

        invoiceResponse.setCustomerId( source.getCustomerId() );
        invoiceResponse.setId( source.getId() );
        invoiceResponse.setNumber( source.getNumber() );
        invoiceResponse.setDetail( source.getDetail() );
        invoiceResponse.setAmount( source.getAmount() );

        return invoiceResponse;
    }

    @Override
    public List<InvoiceResponse> InvoiceListToInvoiceResponseList(List<Invoice> source) {
        if ( source == null ) {
            return null;
        }

        List<InvoiceResponse> list = new ArrayList<InvoiceResponse>( source.size() );
        for ( Invoice invoice : source ) {
            list.add( InvoiceToInvoiceResponse( invoice ) );
        }

        return list;
    }

    @Override
    public Invoice InvoiceResponseToInvoice(InvoiceResponse source) {
        if ( source == null ) {
            return null;
        }

        Invoice invoice = new Invoice();

        invoice.setCustomerId( source.getCustomerId() );
        invoice.setId( source.getId() );
        invoice.setNumber( source.getNumber() );
        invoice.setDetail( source.getDetail() );
        invoice.setAmount( source.getAmount() );

        return invoice;
    }

    @Override
    public List<Invoice> InvoiceResponseToInvoiceList(List<InvoiceResponse> source) {
        if ( source == null ) {
            return null;
        }

        List<Invoice> list = new ArrayList<Invoice>( source.size() );
        for ( InvoiceResponse invoiceResponse : source ) {
            list.add( InvoiceResponseToInvoice( invoiceResponse ) );
        }

        return list;
    }
}

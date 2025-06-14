package com.paymentchain.billing.common;

import com.paymentchain.billing.dto.InvoiceResponse;
import com.paymentchain.billing.entities.Invoice;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/*anotacion para indicar que esta interfaz va a contener el mapeo para los dto*/
@Mapper(componentModel = "spring")
public interface InvoiceResponseMapper {

    @Mappings({
            @Mapping(source="customerId", target = "customerId")
    })
    InvoiceResponse InvoiceToInvoiceResponse(Invoice source);

    List<InvoiceResponse> InvoiceListToInvoiceResponseList(List<Invoice> source);

    /*Inversa del primer metodo*/
    @InheritInverseConfiguration
    Invoice InvoiceResponseToInvoice(InvoiceResponse source);

    /*Inversa del segundo metodo*/
    @InheritInverseConfiguration
    List<Invoice> InvoiceResponseToInvoiceList(List<InvoiceResponse> source);
}

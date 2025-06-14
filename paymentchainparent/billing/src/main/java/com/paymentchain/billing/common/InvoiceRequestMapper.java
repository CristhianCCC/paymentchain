package com.paymentchain.billing.common;

import com.paymentchain.billing.dto.InvoiceRequest;
import com.paymentchain.billing.entities.Invoice;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/*
 * Interfaz de mapeo para convertir entre el DTO InvoiceRequest
 * y la entidad Invoice, usando MapStruct.
 */
@Mapper(componentModel = "spring")
public interface InvoiceRequestMapper {

    /*
     * Método principal de mapeo: convierte un InvoiceRequest (DTO) en una entidad Invoice.
     * @param source objeto DTO con los datos de la factura.
     * @return entidad Invoice preparada para persistencia.
     */
    @Mappings({
            @Mapping(source = "customerId", target = "customerId")
            // Puedes agregar más @Mapping si los nombres de propiedad difieren.
    })
    Invoice InvoiceRequestToInvoice(InvoiceRequest source);

    /*
     * Convierte una lista de DTOs InvoiceRequest en una lista de entidades Invoice.
     * Útil para procesar múltiples facturas en lote.
     * @param source lista de DTOs.
     * @return lista de entidades Invoice.
     */
    List<Invoice> InvoiceRequestListToInvoiceList(List<InvoiceRequest> source);

    /*
     * Mapeo inverso: convierte una entidad Invoice de nuevo a un DTO InvoiceRequest.
     * @InheritInverseConfiguration reutiliza la configuración de InvoiceRequestToInvoice.
     * @param source entidad Invoice.
     * @return DTO InvoiceRequest con los mismos datos.
     */
    @InheritInverseConfiguration
    InvoiceRequest InvoiceToInvoiceRequest(Invoice source);

    /*
     * Mapeo inverso para listas: convierte una lista de entidades Invoice
     * a una lista de DTOs InvoiceRequest.
     * @param source lista de Invoice.
     * @return lista de InvoiceRequest.
     */
    @InheritInverseConfiguration
    List<InvoiceRequest> InvoiceListToInvoiceRequestList(List<Invoice> source);
}

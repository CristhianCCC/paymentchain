package com.paymentchain.billing.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;


@Entity
@Data
public class Invoice {
   @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
   private long id;
   /* documentacion con swagger*/
   @Schema(name = "customerId", requiredMode = REQUIRED, example = "2", defaultValue = "1", description = "unique value id customer that represents the owner of the invoice")
   private long customerId;
   private String number;
   private String detail;
   private double amount;  
}

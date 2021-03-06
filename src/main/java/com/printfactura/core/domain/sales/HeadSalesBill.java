package com.printfactura.core.domain.sales;


import com.printfactura.core.domain.customer.Customer;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class HeadSalesBill {

    private int Id;
    private int IdCustomer;
    private int IdCustomerType;
    private String Amount;
    private String Date;
    private String BillNumber;
    private BigDecimal Global_dto;
    private String State;

    // Customer information
    private Customer customer;

    // Direct debit information (optional)
    private DirectDebits directDebits;
}

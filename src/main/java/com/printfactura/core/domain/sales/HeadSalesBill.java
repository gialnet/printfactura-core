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

    @Builder.Default
    private BigDecimal Global_dto = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal base = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal vat = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal total = BigDecimal.ZERO;

    @Builder.Default
    private String State="pending";

    // Direct debit information (optional)
    private DirectDebits directDebits;
}

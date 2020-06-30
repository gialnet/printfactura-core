package com.printfactura.core.domain.sales.ui;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InvoiceSalesUI {

    private int InvoiceID;
    private String Customer;
    private String DateInvoice;
    private String NumberInvoice;
    private String TotalAmount;
    private String VAT;
    private String State;
}

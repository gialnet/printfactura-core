package com.printfactura.core.domain.sales.ui;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InvoiceNumber {


    @Builder.Default
    private String invoiceNumber="";
    @Builder.Default
    private String invoiceDate="";
    @Builder.Default
    private String customerName="";
    @Builder.Default
    private String FromDate="";
    @Builder.Default
    private String ToDate="";

}

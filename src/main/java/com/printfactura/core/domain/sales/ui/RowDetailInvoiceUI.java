package com.printfactura.core.domain.sales.ui;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RowDetailInvoiceUI {

    private String Concept;
    private BigDecimal Unit;
    private BigDecimal Price;
    private BigDecimal Total;
}

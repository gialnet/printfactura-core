package com.printfactura.core.domain.sales.ui;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    private BigDecimal VAT;
    private BigDecimal Total;
    @Builder.Default
    private List<RowDetail> rowDetails= new ArrayList<>();

    public void CalcTotal(){

        Total=Unit.multiply(Price);
    }
}

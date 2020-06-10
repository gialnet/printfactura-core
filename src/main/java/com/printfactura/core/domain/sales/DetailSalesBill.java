package com.printfactura.core.domain.sales;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DetailSalesBill {

    private int Id;
    private String Id_bill;
    private String Id_item;
    private String Id_store;
    private String Concept;
    private BigDecimal Unit;
    private BigDecimal Price;
    private BigDecimal Amount;
    private BigDecimal PorVAT;
    private BigDecimal PorDiscount;
    // Only available in Spain Tax
    private BigDecimal RecargoEquivalencia;

    public BigDecimal getTotalAmount(){

        // Price * PorDiscount / 100 = Final Price
        if (PorDiscount != null)
            if (PorDiscount.compareTo(BigDecimal.ZERO) > 0)
                Price.multiply(PorDiscount).divide(BigDecimal.valueOf(100));

        // Unit * Price = Amount
        Amount = Unit.multiply(Price);

        return Amount;
    }

    public BigDecimal getAmountVAT(){
        return Amount.multiply(PorVAT).divide(BigDecimal.valueOf(100));
    }

}

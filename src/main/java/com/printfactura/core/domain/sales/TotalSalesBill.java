package com.printfactura.core.domain.sales;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TotalSalesBill {

    private  BigDecimal BaseEuros;
    private  BigDecimal VATEuros;
    private  BigDecimal TotalEuros;

    private  BigDecimal BasePound;
    private  BigDecimal VATPound;
    private  BigDecimal TotalPound;
    // https://www.gov.uk/government/publications/hmrc-exchange-rates-for-2020-monthly
    private  BigDecimal HMRC_ExchangeRates = BigDecimal.ZERO;
    private int Month;
    private int Hours;
    private int PorVAT;
    private BigDecimal PriceHour;

    public void Calculate(){

        this.BaseEuros = BigDecimal.valueOf(Hours).multiply(PriceHour);
        this.VATEuros = BigDecimal.valueOf(PorVAT).multiply(BaseEuros).divide(BigDecimal.valueOf(100));
        this.TotalEuros = BaseEuros.add(VATEuros);
    }

    public void ApplyCurrencyExchange(){

        if (HMRC_ExchangeRates != null) {

            if (HMRC_ExchangeRates.compareTo(BigDecimal.ZERO) > 0)
            {

                this.BasePound=this.BaseEuros.multiply(HMRC_ExchangeRates);
                this.VATPound=this.VATEuros.multiply(HMRC_ExchangeRates);
                this.TotalPound=this.TotalEuros.multiply(HMRC_ExchangeRates);
            }

        }

    }
}

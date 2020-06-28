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

    @Builder.Default
    private  BigDecimal BaseEuros=BigDecimal.ZERO;
    @Builder.Default
    private  BigDecimal VATEuros=BigDecimal.ZERO;
    @Builder.Default
    private  BigDecimal TotalEuros=BigDecimal.ZERO;

    @Builder.Default
    private  BigDecimal BasePound=BigDecimal.ZERO;
    @Builder.Default
    private  BigDecimal VATPound=BigDecimal.ZERO;
    @Builder.Default
    private  BigDecimal TotalPound=BigDecimal.ZERO;

    // https://www.gov.uk/government/publications/hmrc-exchange-rates-for-2020-monthly
    @Builder.Default
    private  BigDecimal HMRC_ExchangeRates = BigDecimal.ZERO;
    private int Month;
    private int Hours;
    private int PorVAT;
    @Builder.Default
    private BigDecimal PriceHour = BigDecimal.ZERO;

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

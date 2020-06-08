package com.printfactura.core.domain;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TotalsBill {

    private  BigDecimal BaseEuros;
    private  BigDecimal VATEuros;
    private  BigDecimal TotalEuros;

    private  BigDecimal BasePound;
    private  BigDecimal VATPound;
    private  BigDecimal TotalPound;
    // https://www.gov.uk/government/publications/hmrc-exchange-rates-for-2020-monthly
    private  BigDecimal HMRC_ExchangeRates;
    private String month;

}

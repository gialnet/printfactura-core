package com.printfactura.core.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Locale;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MySelf {

    //private final String version="v0.1.0";
    private String Identification ="VAT number: GB 292 4881 67";
    private String CompanyName = "Vivaldi-Spring LTD";
    private String Address = "Suite 38, Temple Chambers, 3-7 Temple Avenue";
    private String City ="London";
    private String PostCode ="EC4Y 0HP";
    private String Country ="United Kingdom";

    private String TaxPeriod="Q1";
    private String fiscal_year="2020";

    private BigDecimal Vat = BigDecimal.valueOf(20);
    private String IBAN ="GB50 ABBY 0901 2938 4122 40";
    private String BankAccount ="SORT CODE 09-01-29 ACCOUNT NUMBER 38412240";
    private String BankName ="Santander United Kingdom";

}

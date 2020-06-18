package com.printfactura.core.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Locale;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MySelf {

    @Builder.Default
    private String version="v0.1.0";

    private String IdUser=""; // email address

    private String Identification ="";
    private String CompanyName = "";
    private String Address = "";
    private String City ="";
    private String PostCode ="";
    private String Country ="";

    private String TaxPeriod="";

    @Builder.Default
    private String fiscal_year= "";

    @Builder.Default
    private BigDecimal Vat = BigDecimal.valueOf(20);
    private String IBAN ="";
    private String BankAccount ="";
    private String BankName ="";

}

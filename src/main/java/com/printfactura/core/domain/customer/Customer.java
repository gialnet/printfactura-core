package com.printfactura.core.domain.customer;

import com.printfactura.core.domain.SupportedCurrencies;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Customer {

    private int IdCode;
    private String Identification ="";
    private String CompanyName = "";
    private String Address = "";
    private String City ="";
    private String PostCode ="";
    private String Country ="";
    private SupportedCurrencies currencies;
    @Builder.Default
    private BigDecimal Vat = BigDecimal.valueOf(0);
    private List<CustomerElectronicAddress> customerElectronicAddresses;
}

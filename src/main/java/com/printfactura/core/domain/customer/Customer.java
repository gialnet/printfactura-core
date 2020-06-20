package com.printfactura.core.domain.customer;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Customer {

    /*
    Trilateral-IT Ltd.
    Onega House, 112 Main Road
    DA14 6NE Sidcup Kent
    United Kingdom
    Company registration Number 6473406
    VAT Registration Number 928465196
     */
    private String IdCode;
    private String Identification ="VAT Registration Number 928465196";
    private String CompanyName = "Trilateral-IT LTD";
    private String Address = "Onega House, 112 Main Road";
    private String City =" Kent";
    private String PostCode ="DA14 6NE Sidcup";
    private String Country ="United Kingdom";
    private List<CustomerElectronicAddress> customerElectronicAddresses;
}

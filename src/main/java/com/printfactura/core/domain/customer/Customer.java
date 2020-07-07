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

    private int IdCode;
    private String Identification ="";
    private String CompanyName = "";
    private String Address = "";
    private String City ="";
    private String PostCode ="";
    private String Country ="";
    private List<CustomerElectronicAddress> customerElectronicAddresses;
}

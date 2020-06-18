package com.printfactura.core.testutils;

import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.domain.customer.CustomerElectronicAddress;

import java.util.ArrayList;
import java.util.List;

public class BuildCustomerObject {



    public static Customer BuilderCustomer(){

        List<CustomerElectronicAddress> customerElectronicAddresses = new ArrayList<>();

        customerElectronicAddresses.add(CustomerElectronicAddress.builder().
                Reference("James Brennan, sales").
                Type("email").
                Value("James@trilateral-it.com").build());

        customerElectronicAddresses.add(CustomerElectronicAddress.builder().
                Reference("Pamela Perfitt, payments").
                Type("email").
                Value("Pamela@trilateral-it.com").build());

        Customer customer = Customer.builder().
                IdCode("1").
                CompanyName("Trilateral-IT LTD").
                Address("Onega House, 112 Main Road").
                City("Kent").
                PostCode("DA14 6NE Sidcup").
                Country("United Kingdom").
                Identification("VAT Registration Number 928465196").
                //customerElectronicAddresses(customerElectronicAddresses).
                build();

        return customer;
    }
}
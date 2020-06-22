package com.printfactura.core.ui.grid;

import com.printfactura.core.domain.customer.Customer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerGrid {


    static List<Customer> customers = new ArrayList<>();

    public static List<Customer> MakeListCustomers(){

        // Add customer
        customers.add(Customer.builder().
                IdCode(1).
                Identification("VAT Registration Number 928465196").
                CompanyName("Trilateral IT").
                Address("Onega House, 112 Main Road").
                City("Kent").
                PostCode("DA14 6NE Sidcup").
                Country("United Kingdom").
                build()
        );

        customers.add(Customer.builder().
                IdCode(2).
                Identification("VAT 2").
                CompanyName("May Business Consulting Ltd").
                Address("The Pinnacle, 170 Midsummer Boulevard").
                City("Milton Keynes").
                PostCode("MK9 1FE").
                Country("United Kingdom").
                build()
        );

        customers.add(Customer.builder().
                IdCode(3).
                Identification("VAT number: GB 292 4881 67").
                CompanyName("Vivaldi-Spring LTD").
                Address("Suite 38, Temple Chambers, 3-7 Temple Avenue").
                City("London").
                PostCode("EC4Y 0HP").
                Country("United Kingdom").
                build()
        );

        customers.add(Customer.builder().
                IdCode(4).Identification("VAT 4").
                CompanyName("CodeControl LTD").
                Address(" Kollwitzstraße 91").
                City("Berlin").
                PostCode("10435").
                Country("Germany").
                build()
        );

        customers.add(Customer.builder().
                IdCode(5).
                Identification("VAT Registration Number 928465196").
                CompanyName("Tripateral IT").
                Address("Onega House, 112 Main Road").
                City("Kent").
                PostCode("DA14 6NE Sidcup").
                Country("United Kingdom").
                build()
        );

        customers.add(Customer.builder().
                IdCode(6).
                Identification("VAT Registration Number 928465196").
                CompanyName("Tricornio IT").
                Address("Onega House, 112 Main Road").
                City("Kent").
                PostCode("DA14 6NE Sidcup").
                Country("United Kingdom").
                build()
        );

        customers.add(Customer.builder().
                IdCode(7).
                Identification("VAT Registration Number 928465196").
                CompanyName("Towueno IT").
                Address("Onega House, 112 Main Road").
                City("Kent").
                PostCode("DA14 6NE Sidcup").
                Country("United Kingdom").
                build()
        );

        customers.add(Customer.builder().
                IdCode(8).
                Identification("VAT 2").
                CompanyName("May Sevilla Consulting Ltd").
                Address("The Pinnacle, 170 Midsummer Boulevard").
                City("Milton Keynes").
                PostCode("MK9 1FE").
                Country("United Kingdom").
                build()
        );

        customers.add(Customer.builder().
                IdCode(9).
                Identification("VAT 2").
                CompanyName("May Madrid Consulting Ltd").
                Address("The Pinnacle, 170 Midsummer Boulevard").
                City("Milton Keynes").
                PostCode("MK9 1FE").
                Country("United Kingdom").
                build()
        );
        customers.add(Customer.builder().
                IdCode(10).
                Identification("VAT 2").
                CompanyName("May Milton Consulting Ltd").
                Address("The Pinnacle, 170 Midsummer Boulevard").
                City("Milton Keynes").
                PostCode("MK9 1FE").
                Country("United Kingdom").
                build()
        );

        customers.add(Customer.builder().
                IdCode(11).
                Identification("VAT 4").
                CompanyName("CodeMunich LTD").
                Address(" Kollwitzstraße 91").
                City("Berlin").
                PostCode("10435").
                Country("Germany").
                build()
        );

        customers.add(Customer.builder().
                IdCode(12).Identification("VAT 4").
                CompanyName("Code Madrid LTD").
                Address(" Kollwitzstraße 91").
                City("Berlin").
                PostCode("10435").
                Country("Germany").
                build()
        );

        return customers;
    }

    @Test
    public void CheckMakeListCustomers(){

        assertEquals(MakeListCustomers().size(), 3);
    }
}

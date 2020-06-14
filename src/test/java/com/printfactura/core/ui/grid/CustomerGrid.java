package com.printfactura.core.ui.grid;

import com.printfactura.core.domain.customer.Customer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerGrid {


    List<Customer> customers = new ArrayList<>();

    public List<Customer> MakeListCustomers(){

        // Add customer
        customers.add( Customer.builder().
                CompanyName("Customer 1").
                Address("Street 1 number 1").
                City("Milton Keynes").
                PostCode("MK5 6LG").
                Country("United Kingdom").
                build()
        );

        customers.add( Customer.builder().
                CompanyName("Vivaldi-Spring LTD").
                Address("Suite 38, Temple Chambers, 3-7 Temple Avenue").
                City("London").
                PostCode("EC4Y 0HP").
                Country("United Kingdom").
                build()
        );

        return customers;
    }

    @Test
    public void CheckMakeListCustomers(){

        assertEquals(MakeListCustomers().size(), 2);
    }
}

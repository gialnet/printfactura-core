package com.printfactura.core.services.rocksdb;

import com.google.gson.Gson;
import com.printfactura.core.domain.customer.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicesCustomer {

    private Gson gson = new Gson();
    private List<Customer> customers = new ArrayList<>();

    public List<Customer> MakeListCustomers() {

        customers.clear();
        // Add customer
        customers.add(Customer.builder().
                CompanyName("Trilateral IT").
                Address("Street 1 number 1").
                City("Milton Keynes").
                PostCode("MK5 6LG").
                Country("United Kingdom").
                build()
        );

        customers.add(Customer.builder().
                CompanyName("May Bussines Company").
                Address("Somerset street").
                City("Milton Keynes").
                PostCode("MK5 6LG").
                Country("United Kingdom").
                build()
        );

        customers.add(Customer.builder().
                CompanyName("Vivaldi-Spring LTD").
                Address("Suite 38, Temple Chambers, 3-7 Temple Avenue").
                City("London").
                PostCode("EC4Y 0HP").
                Country("United Kingdom").
                build()
        );
        return customers;
    }

}

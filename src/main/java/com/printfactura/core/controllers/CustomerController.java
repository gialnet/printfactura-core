package com.printfactura.core.controllers;

import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.services.rocksdb.ServicesCustomer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
public class CustomerController {

    private final ServicesCustomer servicesCustomer;

    public CustomerController(ServicesCustomer servicesCustomer) {
        this.servicesCustomer = servicesCustomer;
    }

    @GetMapping("/customer/grid")
    public String showForm(Model model) {

        List<Customer> lc = servicesCustomer.MakeListCustomers();
        log.info("' company name{}'",lc.get(0).getCompanyName());
        model.addAttribute("customers", lc);

        return "customergrid";
    }
}

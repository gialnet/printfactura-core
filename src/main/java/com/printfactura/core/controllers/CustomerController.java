package com.printfactura.core.controllers;

import com.printfactura.core.domain.MySelf;
import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.services.rocksdb.ServicesCustomer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class CustomerController {

    private final ServicesCustomer servicesCustomer;
    private Customer customer;

    public CustomerController(ServicesCustomer servicesCustomer) {
        this.servicesCustomer = servicesCustomer;
    }

    @GetMapping("/customer/grid")
    public String showForm(Model model) {

        List<Customer> lc = servicesCustomer.MakeListCustomers();
        log.info("company name '{}'",lc.get(0).getCompanyName());
        log.info("number of records '{}'",lc.size());
        model.addAttribute("customers", lc);

        return "customergrid";
    }

    @GetMapping("/customer/new")
    public String UpdateCustomer(Model model, Authentication a){

        if (a!=null) {
            log.info(" /customer/new -> authenticate user '{}'", a.getName());
            customer = Customer.builder().build();

            log.info("/customer/new GET object Customer '{}'", customer);

            model.addAttribute("Customer", customer);
        }
        else return "/login";

        return "customer";

    }

    @PostMapping("/customer/new")
    public String SaveCustomer(@ModelAttribute("Customer") Customer myCustomerform, Authentication a){

        log.info("Post /customer/new");
        log.info("/customer/new-> Authentication user '{}'",a.getName());
        log.info("V-> myCustomerform object user '{}'", myCustomerform);
        //log.info("mySelfController object user '{}'", mySelfController);

        if (myCustomerform==null)
            return "customer";

        if (servicesCustomer.SaveCustomer(myCustomerform,a.getName()) )
        {
            log.info("/customer/new-> update data '{}'", myCustomerform);
        }
        else {
            log.info("Post error doesn't was possible to save data in RocksDB");
            return "AlertMessage";
        }
        return "customer_success";
    }
}

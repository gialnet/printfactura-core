package com.printfactura.core.controllers;

import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.services.lucene.LuceneServiceCustomer;
import com.printfactura.core.services.rocksdb.ServicesCustomer;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class CustomerController {

    private final ServicesCustomer servicesCustomer;
    private final LuceneServiceCustomer luceneServiceCustomer;
    private Customer customer;

    public CustomerController(ServicesCustomer servicesCustomer, LuceneServiceCustomer luceneServiceCustomer) {
        this.servicesCustomer = servicesCustomer;
        this.luceneServiceCustomer = luceneServiceCustomer;
    }

    @GetMapping("/customer/grid")
    public String showForm(Model model, HttpSession session) throws IOException, ParseException {

        List<Customer> lc = luceneServiceCustomer.
                CustomerByPages(1,5,(String)session.getAttribute("uuid"));

        //log.info("company name '{}'",lc.get(0).getCompanyName());
        log.info("number of records '{}'",lc.size());
        model.addAttribute("customers", lc);

        return "customergrid";
    }

    @GetMapping("/customer/new")
    public String UpdateCustomer(Model model, Authentication a, HttpSession session){

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
    public String SaveCustomer(@ModelAttribute("Customer") Customer myCustomerform,
                               Authentication a, HttpSession session) throws IOException {

        log.info("Post /customer/new");
        log.info("/customer/new-> Authentication user '{}'",a.getName());
        log.info("V-> myCustomerform object user '{}'", myCustomerform);
        //log.info("mySelfController object user '{}'", mySelfController);
        //session.getAttribute("uuid")

        if (myCustomerform==null)
            return "customer";

        if (servicesCustomer.SaveCustomer(myCustomerform, a.getName(), (String) session.getAttribute("uuid") ))
        {
            log.info("/customer/new-> update data '{}'", myCustomerform);
        }
        else {
            log.info("Post error doesn't was possible to save data in RocksDB");
            return "AlertMessage";
        }
        return "customer_success";
    }

    @GetMapping("/customer/search")
    public String SearchCustomer(Model model,
                                 Authentication a, HttpSession session){

        String searchName="";
        List<Customer> customers = new ArrayList<>();
        model.addAttribute("SearchName", searchName);
        model.addAttribute("Customer",customers);

        return "searchbyname";
    }

    @PostMapping("/customer/search")
    public String SearchCustomer(@ModelAttribute("Customer") Customer myCustomerform,
                                 @ModelAttribute("searchName") String searchName,
                                 Model model,
                                 Authentication a, HttpSession session) throws Exception {


         model.addAttribute("Customer", luceneServiceCustomer.
                 CompanyNamePrefixQuery(searchName, (String) session.getAttribute("uuid")));

        return "searchbyname";

    }
}

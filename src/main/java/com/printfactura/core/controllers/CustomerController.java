package com.printfactura.core.controllers;

import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.domain.customer.FieldsForSearchCustomers;
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
import javax.websocket.server.PathParam;
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
    private String showForm(Model model, HttpSession session) throws IOException, ParseException {

        List<Customer> lc = luceneServiceCustomer.
                CustomerByPages(1,5,(String)session.getAttribute("uuid"));

        //log.info("company name '{}'",lc.get(0).getCompanyName());
        log.info("number of records '{}'",lc.size());
        model.addAttribute("customers", lc);

        return "customergrid";
    }

    @GetMapping("/customer/update")
    private String UpdateCustomer(@PathParam("id") int id, Model model, Authentication a, HttpSession session){

            // find the customer
           customer = servicesCustomer.FindCustomerByID(a.getName(), id);
           log.info("GET Customer id ->{}", customer.getIdCode());
           customer.setIdCode(id);
           model.addAttribute("Customer", customer);

           return "customer_update";

    }

    @PostMapping("/customer/update")
    private String UpdateCustomerAction(@ModelAttribute("Customer") Customer upd_customer,
                                        Authentication a, HttpSession session) throws IOException {

        log.info("POST Customer id ->{}", upd_customer.getIdCode());

        servicesCustomer.UpdateCustomer(upd_customer, a.getName(), (String)session.getAttribute("uuid"));

        return "redirect:search";

    }


    @GetMapping("/customer/new")
    private String ShowCustomerForm(Model model,
                               Authentication a, HttpSession session) throws IOException {

        customer = Customer.builder().build();
        /*log.info("Post /customer/new");
        log.info("/customer/new-> Authentication user '{}'",a.getName());*/

        model.addAttribute("Customer", customer);

        return "customer_add";
    }

    @PostMapping("/customer/new")
    private String SaveCustomer(@ModelAttribute("Customer") Customer myCustomerform,
                                Authentication a, HttpSession session) throws IOException {

        log.info("Post /customer/new");
        log.info("/customer/new-> Authentication user '{}'",a.getName());
        log.info("V-> myCustomerform object user '{}'", myCustomerform);

        if (myCustomerform==null)
            return "customer_add";

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
    private String SearchCustomer(Model model,
                                 Authentication a, HttpSession session){

        FieldsForSearchCustomers searchName = FieldsForSearchCustomers.builder().build();
        List<Customer> customers = new ArrayList<>();
        model.addAttribute("SearchName", searchName);
        model.addAttribute("Customer",customers);

        return "customer_grid_searchbyname";
    }

    @PostMapping("/customer/search")
    private String SearchCustomer(@ModelAttribute("Customer") Customer myCustomerform,
                                 @ModelAttribute("SearchName") FieldsForSearchCustomers searchName,
                                 Model model,
                                 Authentication a, HttpSession session) throws Exception {


         model.addAttribute("Customer", luceneServiceCustomer.
                 CompanyNamePrefixQuery(searchName.getSearchName(), (String) session.getAttribute("uuid")));

        return "customer_grid_searchbyname";

    }
}

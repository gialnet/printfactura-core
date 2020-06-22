package com.printfactura.core.controllers;

import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.domain.sales.ui.InvoiceSalesUI;
import com.printfactura.core.domain.sales.ui.RowDetailInvoiceUI;
import com.printfactura.core.services.lucene.LuceneServiceCustomer;
import com.printfactura.core.services.rocksdb.ServicesInvoice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class InvoiceSalesController {

    private final ServicesInvoice servicesInvoice;
    private final LuceneServiceCustomer luceneServiceCustomer;
    private List<RowDetailInvoiceUI> rowsInvoice= new ArrayList<>();

    List<InvoiceSalesUI> lisales = new ArrayList<>();

    public InvoiceSalesController(ServicesInvoice servicesInvoice, LuceneServiceCustomer luceneServiceCustomer) {
        this.servicesInvoice = servicesInvoice;
        this.luceneServiceCustomer = luceneServiceCustomer;
    }


    // List<Customer> CompanyNamePrefixQuery(String name, String uuid)
    @RequestMapping(value = "/invoice", method = RequestMethod.GET)
    public String showStudentBySurname(@RequestParam(value = "name", required = false) String name,
                                       Model model,
                                       HttpSession session ) throws Exception {



        model.addAttribute("customer",
                luceneServiceCustomer.CompanyNamePrefixQuery(name,(String) session.getAttribute("uuid")));

        return "invoice";
    }

    /**
     * Send one row and then add to the list of rows
     *
     * @param rows
     * @param model
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/invoice/detail", method = RequestMethod.GET)
    public String showDetailRow(@ModelAttribute(value = "rowobject") RowDetailInvoiceUI rows,
                                       Model model,
                                       HttpSession session ) throws Exception {

        rowsInvoice.add(rows);

        model.addAttribute("rowlist", rowsInvoice );

        return "invoice";
    }

    @GetMapping("/invoice/grid")
    private String showForm(Model model) {

        lisales = servicesInvoice.MakeListSalesInvoice();
        log.info("List of sales '{}'",lisales.get(0).getCustomer());
        model.addAttribute("invoices", lisales);

        return "invoicegrid";
    }

    @GetMapping("/invoice/new")
    private String InvoiceNew(Model model, Authentication a, HttpSession session) {

        Customer customer = Customer.builder().CompanyName("La mia").build();
        log.info("Invoice new '{}'",session.getAttribute("uuid"));
        model.addAttribute("Customer", customer);

        return "invoice";
    }
}

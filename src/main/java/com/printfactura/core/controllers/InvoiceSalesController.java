package com.printfactura.core.controllers;

import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.domain.sales.ui.InvoiceSalesUI;
import com.printfactura.core.services.rocksdb.ServicesInvoice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class InvoiceSalesController {

    private final ServicesInvoice servicesInvoice;

    List<InvoiceSalesUI> lisales = new ArrayList<>();

    public InvoiceSalesController(ServicesInvoice servicesInvoice) {
        this.servicesInvoice = servicesInvoice;
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

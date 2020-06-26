package com.printfactura.core.controllers;

import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.domain.customer.FieldsForSearchCustomers;
import com.printfactura.core.domain.sales.ui.*;
import com.printfactura.core.services.lucene.LuceneServiceCustomer;
import com.printfactura.core.services.rocksdb.ServicesInvoice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class InvoiceSalesController {

    private final ServicesInvoice servicesInvoice;
    private final LuceneServiceCustomer luceneServiceCustomer;
    //private Hashtable <String, List<RowDetail>> rowDetailMap = new Hashtable<>();


    List<InvoiceSalesUI> lisales = new ArrayList<>();

    public InvoiceSalesController(ServicesInvoice servicesInvoice, LuceneServiceCustomer luceneServiceCustomer) {
        this.servicesInvoice = servicesInvoice;
        this.luceneServiceCustomer = luceneServiceCustomer;
    }


    // List<Customer> CompanyNamePrefixQuery(String name, String uuid)
   /* @RequestMapping(value = "/invoice", method = RequestMethod.GET)
    public String showStudentBySurname(@RequestParam(value = "name", required = false) String name,
                                       Model model,
                                       HttpSession session ) throws Exception {



        model.addAttribute("rowlist", rowsInvoice );
        model.addAttribute("invoice", rowDetailInvoiceUI);
        model.addAttribute("customer",
                luceneServiceCustomer.CompanyNamePrefixQuery(name,(String) session.getAttribute("uuid")));

        return "invoice";
    }*/


    @GetMapping("/invoice/number")
    private String invoiceNumber(Model model, Authentication a, HttpSession session){

        InvoiceNumber invoiceNumber = InvoiceNumber.builder().build();
        model.addAttribute("invoiceAttr", invoiceNumber);

        return "invoice_number";
    }

    @PostMapping("/invoice/number")
    private String invoiceNumberCheck(@ModelAttribute("invoiceAttr") InvoiceNumber invoiceNumber,
                                      Authentication a, HttpSession session){

        log.info("Invoice date '{}'",invoiceNumber.getInvoiceDate());
        log.info("Invoice number '{}'",invoiceNumber.getInvoiceNumber());
        session.setAttribute("InvoiceNumber", invoiceNumber);

        return "redirect:customersearch";
    }

    /* ******************* Customer *************************************** */

    @GetMapping("/invoice/customersearch")
    public String SearchCustomer(Model model,
                                 Authentication a, HttpSession session){

        FieldsForSearchCustomers searchName = FieldsForSearchCustomers.builder().build();
        List<Customer> customers = new ArrayList<>();
        model.addAttribute("SearchName", searchName);
        model.addAttribute("Customer",customers);

        return "invoice_searchcustomer";
    }

    @PostMapping("/invoice/customersearch")
    public String SearchCustomer(@ModelAttribute("Customer") Customer myCustomerform,
                                 @ModelAttribute("SearchName") FieldsForSearchCustomers searchName,
                                 Model model,
                                 Authentication a, HttpSession session) throws Exception {


        model.addAttribute("Customer", luceneServiceCustomer.
                CompanyNamePrefixQuery(searchName.getSearchName(), (String) session.getAttribute("uuid")));

        return "invoice_searchcustomer";

    }

    @GetMapping("/invoice/selected")
    private String SelectFromGrid(@PathParam("id") int id,
                                  @PathParam("name") String name,
                                  Model model,
                                  Authentication a, HttpSession session){

        // get the record selected in the grid
        log.info("id -> {} {}", id, name);


        FieldsForSearchCustomers searchName = FieldsForSearchCustomers.builder().
                searchName(name).id(id)
                .build();

        List<Customer> customers = new ArrayList<>();
        model.addAttribute("SearchName", searchName);
        model.addAttribute("Customer",customers);


        return "invoice_searchcustomer";
    }

    /* **************************** Details invoice ***************************************** */

    @GetMapping("/invoice/new")
    private String InvoiceNew(Model model, Authentication a, HttpSession session) {

        List<RowDetail> rowDetails = new ArrayList<>();

        RowDetailInvoiceUI rowDetailInvoiceUI = RowDetailInvoiceUI.builder().build();

        //rowDetailMap.put((String) session.getAttribute("uuid"), rowDetails);

        session.setAttribute("ListRows", rowDetails);

        Customer customer = Customer.builder().CompanyName("La mia").build();
        log.info("Invoice new '{}'",session.getAttribute("uuid"));

        model.addAttribute("Customer", customer);
        //model.addAttribute("rowlist", rowsInvoice );
        model.addAttribute("invoice", rowDetailInvoiceUI);

        return "invoice_details";
    }

    @PostMapping("/invoice/new")
    public String AddRowDetail(@ModelAttribute("invoice") RowDetailInvoiceUI rows,
                               Model model,
                               HttpSession session){

       //List<RowDetail> lrowDetail = rowDetailMap.get((String) session.getAttribute("uuid"));
       List<RowDetail> lrowDetail = (List<RowDetail>) session.getAttribute("ListRows");

       log.info("uuid for HasTable '{}'", (String) session.getAttribute("uuid"));

        rows.CalcTotal();
        RowDetail rowDetail = RowDetail.builder().
                Concept(rows.getConcept()).
                Unit(rows.getUnit()).
                Price(rows.getPrice()).
                Total(rows.getTotal()).
                build();

        log.info("Before Size of details '{}'", lrowDetail.size());

        lrowDetail.add(rowDetail);

        rows.setRowDetails(lrowDetail);

        log.info("Size of details '{}'", lrowDetail.size());

        Customer customer = Customer.builder().CompanyName("La mia").build();
        model.addAttribute("invoice", rows);
        model.addAttribute("Customer", customer);

        return "invoice_details";

    }

    /* invoice_final*/

    @GetMapping("/invoice/final")
    private String LastStep(Model model, Authentication a, HttpSession session){

        InvoiceFinal invoiceFinal = InvoiceFinal.builder().build();
        model.addAttribute("invoiceFinalStep", invoiceFinal);

        return "invoice_final";
    }



    @GetMapping("/invoice/grid")
    private String showForm(Model model) {

        lisales = servicesInvoice.MakeListSalesInvoice();
        log.info("List of sales '{}'",lisales.get(0).getCustomer());
        model.addAttribute("invoices", lisales);

        return "invoicegrid";
    }


}

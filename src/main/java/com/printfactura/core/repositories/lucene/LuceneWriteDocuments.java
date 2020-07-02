package com.printfactura.core.repositories.lucene;

import com.printfactura.core.domain.appusers.AppUser;
import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.domain.sales.SalesBill;
import com.printfactura.core.domain.sales.ui.InvoiceSalesUI;

import java.io.IOException;
import java.text.ParseException;

public interface LuceneWriteDocuments {

    boolean WriteCustomerDocument(Customer customer, String suuid) throws IOException;

    boolean WriteInvoiceDocument(SalesBill salesBill, String suuid) throws IOException, ParseException;

    boolean WriteAppUserDocument(AppUser appUser) throws IOException;
}

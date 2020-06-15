package com.printfactura.core.repositories.lucene;

import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.domain.sales.ui.InvoiceSalesUI;

import java.io.IOException;

public interface LuceneWriteDocuments {

    boolean WriteCustomerDocument(Customer customer, String suuid) throws IOException;

    boolean WriteInvoiceDocument(InvoiceSalesUI invoiceSalesUI, String suuid) throws IOException;
}

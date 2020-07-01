package com.printfactura.core.services.rocksdb;

import com.printfactura.core.domain.sales.ui.InvoiceSalesUI;
import com.printfactura.core.services.lucene.LuceneServiceSalesInvoice;
import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static com.printfactura.core.testutils.InvoiceStuff.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ServicesInvoiceTest {

    @Autowired
    ServicesInvoice servicesInvoice;

    @Autowired
    LuceneServiceSalesInvoice luceneServiceSalesInvoice;

    private final String uuid="6bfeb78f-3301-4985-9f73-8dab2f64194a";
    private final String email="e1";

    @Test
    void getSeqInvoice() {

        int seq = servicesInvoice.GetSeqInvoice(email);

        System.out.println(seq);

        assertTrue( seq > 0);
    }

    @Test
    void saveSalesBill() throws IOException {

        boolean isOK;
        isOK = servicesInvoice.SaveSalesBill(createSalesBill(),email,uuid);
        isOK = servicesInvoice.SaveSalesBill(createSalesBill2(),email,uuid);
        isOK = servicesInvoice.SaveSalesBill(createSalesBill3(),email,uuid);
        isOK = servicesInvoice.SaveSalesBill(createSalesBill4(),email,uuid);
        isOK = servicesInvoice.SaveSalesBill(createSalesBill5(),email,uuid);

        assertTrue(isOK);
    }

    @Test void InvoiceByPage() throws IOException, ParseException {

        var invoices = luceneServiceSalesInvoice.InvoiceByPage(1,3,uuid);
        System.out.println(invoices.size());

        for (InvoiceSalesUI li: invoices){

            System.out.println(li.getInvoiceID());

        }
    }
}
package com.printfactura.core.services.rocksdb;

import com.printfactura.core.domain.sales.ui.InvoiceSalesUI;
import com.printfactura.core.services.lucene.LuceneServiceSalesInvoice;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldDocs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.printfactura.core.testutils.InvoiceStuff.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ServicesInvoiceTest {

    @Autowired
    ServicesInvoice servicesInvoice;

    @Autowired
    LuceneServiceSalesInvoice luceneServiceSalesInvoice;

    private final String uuid="5b88ca6d-7f71-4cd9-a5fc-0771c84d972b";
    private final String email="l1";

    @Test
    void getSeqInvoice() {

        int seq = servicesInvoice.GetSeqInvoice(email);

        System.out.println(seq);

        assertTrue( seq > 0);
    }

    @Test
    void saveSalesBill() throws IOException, java.text.ParseException {

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

    @Test
    void betweenDates() throws java.text.ParseException, IOException, ParseException {

        var FromDate =  new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-02");
        var ToDate =  new SimpleDateFormat("yyyy-MM-dd").parse("2020-03-02");

        List<InvoiceSalesUI> invoiceSalesUIList = luceneServiceSalesInvoice.
                SalesBetweenDates("2020-01-02", "2020-06-02", uuid, false);

        for (InvoiceSalesUI is: invoiceSalesUIList){

            System.out.println(is.getInvoiceID()+" "+is.getCustomer()+" "+is.getDateInvoice());
            /*System.out.print(" "+is.getCustomer());
            System.out.print(" "+is.getDateInvoice());*/

        }

        //System.out.println(topDocs.totalHits);
    }
}
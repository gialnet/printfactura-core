package com.printfactura.core.services.lucene;

import com.printfactura.core.domain.sales.ui.InvoiceSalesUI;
import com.printfactura.core.repositories.lucene.SalesInvoiceRepository;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LuceneServiceSalesInvoice {

    private final SalesInvoiceRepository invoiceRepository;
    private List<InvoiceSalesUI> invoiceSalesUI = new ArrayList<>();

    public LuceneServiceSalesInvoice(SalesInvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }


    public TopDocs orderByIdCodeFromTo(int FromInvoiceID, int ToInvoiceID, String uuid) throws IOException, ParseException {

        IndexSearcher searcher = invoiceRepository.OpenSearcher(uuid);

        return invoiceRepository.orderByIdCodeFromTo(searcher, FromInvoiceID, ToInvoiceID);
    }

    public List<InvoiceSalesUI> InvoiceByPage(int page, int size, String uuid) throws IOException, ParseException {

        IndexSearcher searcher = invoiceRepository.OpenSearcher(uuid);

        int from = size * (page -1) + page;
        int to = page * size;
        return ListOfInvoices(invoiceRepository.
                        orderByIdCodeFromTo(searcher, from, to),
                        searcher);
    }

    public List<InvoiceSalesUI> ListOfInvoices(TopDocs hits, IndexSearcher searcher) throws IOException {

        invoiceSalesUI.clear();

        for (ScoreDoc sd : hits.scoreDocs)
        {
            Document d = searcher.doc(sd.doc);
            // System.out.println(String.format(d.get("Customer")));

            invoiceSalesUI.add(InvoiceSalesUI.builder().
                    InvoiceID((Integer) d.getField("InvoiceID").numericValue()).
                    Customer(d.get("Customer")).    // name of customer
                    DateInvoice(d.get("DateInvoice")).
                    NumberInvoice(d.get("NumberInvoice")).
                    TotalAmount(d.get("TotalAmount")).
                    VAT(d.get("VAT")).
                    build()
            );
        }

        return invoiceSalesUI;

    }
}

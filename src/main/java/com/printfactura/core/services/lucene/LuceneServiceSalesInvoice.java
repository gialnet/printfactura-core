package com.printfactura.core.services.lucene;

import com.printfactura.core.domain.sales.ui.InvoiceSalesUI;
import com.printfactura.core.repositories.lucene.SalesInvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexNotFoundException;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldDocs;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
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

    public List<InvoiceSalesUI> InvoiceByID(int id, String uuid) throws IOException, ParseException {

        try {

            IndexSearcher searcher = invoiceRepository.OpenSearcher(uuid);

            return ListOfInvoices(invoiceRepository.
                            searchByIdCode(searcher, id),
                    searcher);
        }
        catch (Exception e){

            log.info(" LuceneServicesSalesInvoice InvoiceByIdCode error {}", e.getMessage());

        }

        invoiceSalesUI.clear();

        return invoiceSalesUI;

    }

    public List<InvoiceSalesUI> InvoiceByPage(int page, int size, String uuid) throws IOException, ParseException {

        try {
            IndexSearcher searcher = invoiceRepository.OpenSearcher(uuid);

            int from = size * (page -1) + page;
            int to = page * size;
            return ListOfInvoices(invoiceRepository.
                            orderByIdCodeFromTo(searcher, from, to),
                    searcher);
        }
        catch (IndexNotFoundException e){
            log.info("{}", e.getMessage());
        }

        invoiceSalesUI.clear();

        return invoiceSalesUI;

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
                    DateInvoice(d.get("DateInvoiceString")).
                    NumberInvoice(d.get("NumberInvoice")).
                    TotalAmount(d.get("TotalAmount")).
                    VAT(d.get("VAT")).
                    build()
            );
        }

        return invoiceSalesUI;

    }

    public TopDocs BetweenDates(String FromDate, String ToDate, String uuid, boolean reverse) throws IOException, ParseException, java.text.ParseException {

        IndexSearcher searcher = invoiceRepository.OpenSearcher(uuid);

        TopDocs topDocs = invoiceRepository.BetweenDates(searcher, FromDate, ToDate, reverse);

        return topDocs;
    }

    public List<InvoiceSalesUI> SalesBetweenDates(String FromDate, String ToDate, String uuid, boolean reverse) throws IOException, ParseException, java.text.ParseException {

        invoiceSalesUI.clear();

        try {

            IndexSearcher searcher = invoiceRepository.OpenSearcher(uuid);

            TopDocs topDocs = invoiceRepository.BetweenDates(searcher, FromDate, ToDate, reverse);

            for (ScoreDoc sd : topDocs.scoreDocs) {
                Document d = searcher.doc(sd.doc);
                invoiceSalesUI.add(InvoiceSalesUI.builder().
                        InvoiceID(Integer.parseInt(d.get("InvoiceID"))).
                        Customer(d.get("Customer")).
                        DateInvoice(d.get("DateInvoiceString")).
                        NumberInvoice(d.get("NumberInvoice")).
                        TotalAmount(d.get("TotalAmount")).
                        VAT(d.get("VAT")).
                        State(d.get("State")).
                        build());
            }
        }
        catch (Exception e){
            log.info("{}", e.getMessage());
        }

        return invoiceSalesUI;
    }

    public List<InvoiceSalesUI> SalesBetweenDates(TopDocs topDocs, IndexSearcher searcher) throws IOException {

        invoiceSalesUI.clear();

        for (ScoreDoc sd : topDocs.scoreDocs) {
            Document d = searcher.doc(sd.doc);
            invoiceSalesUI.add(InvoiceSalesUI.builder().
                    InvoiceID(Integer.parseInt(d.get("InvoiceID"))).
                    Customer(d.get("Customer")).
                    DateInvoice(d.get("DateInvoiceString")).
                    NumberInvoice(d.get("NumberInvoice")).
                    TotalAmount(d.get("TotalAmount")).
                    VAT(d.get("VAT")).
                    State(d.get("State")).
                    build());
        }

        return invoiceSalesUI;
    }

    public List<InvoiceSalesUI> CompanyNamePrefixQuery(String name, String uuid)
            throws Exception {


        try{

            IndexSearcher searcher = invoiceRepository.OpenSearcher(uuid);

            return ListOfInvoices(invoiceRepository.
                            CompanyNamePrefixQuery(searcher, name),
                            searcher);
        }
        catch (Exception e){

            System.out.println("Exception occurred, Lucene index folder empty or not able to access");
            return invoiceSalesUI;
        }

    }
}

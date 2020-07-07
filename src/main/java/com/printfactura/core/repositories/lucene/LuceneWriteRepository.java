package com.printfactura.core.repositories.lucene;

import com.printfactura.core.domain.appusers.AppUser;
import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.domain.sales.SalesBill;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

import org.apache.lucene.util.BytesRef;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.apache.lucene.document.LongPoint.newExactQuery;

@Slf4j
@Repository
public class LuceneWriteRepository implements LuceneWriteDocuments {

    @Value("${lucene-path}")
    private String lucene_path;

    public LuceneWriteRepository() {
    }

    private IndexWriter createWriter(String userPath) throws IOException
    {
        FSDirectory dir = FSDirectory.open(Paths.get(lucene_path + userPath));
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        // Create index in Memory RAM
        // MemoryIndex index = new MemoryIndex();
        IndexWriter writer = new IndexWriter(dir, config);
        return writer;
    }

    /**
     *
     * @param customer
     * @param suuid
     * @return
     * @throws IOException
     */
    public boolean UpdateCustomer(Customer customer, String suuid) throws IOException {

        IndexWriter writer = createWriter("customer/" + suuid);

        // first delete and then insert new values
        writer.deleteDocuments(
                IntPoint.newExactQuery("IdCode",
                        customer.getIdCode()) );

        Document customerDoc = CreateCustomerDocument(customer);

        writer.addDocument(customerDoc);

        writer.commit();
        writer.close();

        return true;
    }

    @Override
    public boolean WriteCustomerDocument(Customer customer, String suuid) throws IOException {

        IndexWriter writer = createWriter("customer/" + suuid);

        Document customerDoc = CreateCustomerDocument(customer);

        writer.addDocument(customerDoc);

        // List<Document>
        //writer.addDocuments(documents);
        writer.commit();
        writer.close();

        return true;
    }



    private Document CreateCustomerDocument(Customer customer){

        Document document = new Document();

        //document.add(new StringField("IdCode", customer.getIdCode() , Field.Store.YES));
        document.add(new IntPoint("IdCode", customer.getIdCode()));
        document.add(new StoredField("IdCode", customer.getIdCode()) );
        document.add(new SortedNumericDocValuesField("IdCode", customer.getIdCode()) );


        document.add(new TextField("Identification", customer.getIdentification() , Field.Store.YES));

        //document.add(new TextField("CompanyName", customer.getCompanyName() , Field.Store.YES));
        document.add(new StringField("CompanyName", customer.getCompanyName() , Field.Store.YES));
        document.add(new SortedDocValuesField("CompanyName", new BytesRef(customer.getCompanyName()) ));


        document.add(new TextField("Address", customer.getAddress() , Field.Store.YES));
        document.add(new TextField("City", customer.getCity() , Field.Store.YES));
        document.add(new TextField("PostCode", customer.getPostCode() , Field.Store.YES));
        document.add(new TextField("Country", customer.getCountry() , Field.Store.YES));

        return document;
    }



    @Override
    public boolean WriteInvoiceDocument(SalesBill salesBill, String suuid) throws IOException, ParseException {

        IndexWriter writer = createWriter("invoice/" + suuid);

        Document invoiceDoc = CreateInvoiceDocument(salesBill);

        writer.addDocument(invoiceDoc);

        // List<Document>
        //writer.addDocuments(documents);
        writer.commit();
        writer.close();

        return true;
    }


    private Document CreateInvoiceDocument(SalesBill salesBill) throws ParseException {

        Document document = new Document();

        document.add(new IntPoint("InvoiceID", salesBill.getHeadSalesBill().getId()) );
        document.add(new StoredField("InvoiceID", salesBill.getHeadSalesBill().getId()) );
        document.add(new SortedNumericDocValuesField("InvoiceID", salesBill.getHeadSalesBill().getId()) );

        document.add(new StringField("Customer", salesBill.getCustomer().getCompanyName() , Field.Store.YES));
        document.add(new SortedDocValuesField("Customer", new BytesRef(salesBill.getCustomer().getCompanyName()) ));

        // Date are store like Long values fro fast search and order
        LocalDate localDate = LocalDate.parse(salesBill.getHeadSalesBill().getDate());
        document.add(new LongPoint("DateInvoice", localDate.toEpochDay() ));
        document.add(new StoredField("DateInvoiceString", salesBill.getHeadSalesBill().getDate() ));
        document.add(new StoredField("DateInvoice", localDate.toEpochDay() ));


        // https://lucene.apache.org/core/8_5_2/core/index.html?org/apache/lucene/document/DateTools.html
        // Another approach is LongPoint, which indexes the values in sorted order. For indexing a Date or Calendar, just
        // get the unix timestamp as long using Date.getTime() or Calendar.getTimeInMillis() and index this as a numeric value
        // with LongPoint and use PointRangeQuery to query it.
        document.add(new SortedNumericDocValuesField("DateInvoice",localDate.toEpochDay() ));

        document.add(new StringField("NumberInvoice", salesBill.getHeadSalesBill().getBillNumber() , Field.Store.YES));
        document.add(new StringField("TotalAmount", salesBill.getHeadSalesBill().getTotal().toString() , Field.Store.YES));
        document.add(new StringField("VAT", salesBill.getHeadSalesBill().getVat().toString() , Field.Store.YES));
        document.add(new StringField("State", salesBill.getHeadSalesBill().getState() , Field.Store.YES));

        return document;
    }

    /* *************  AppUser  ******************* */
    @Override
    public boolean WriteAppUserDocument(AppUser appUser) throws IOException {

        IndexWriter writer = createWriter("AppUsers");

        Document customerDoc = CreateAppUserDocument(appUser);

        writer.addDocument(customerDoc);

        // List<Document>
        //writer.addDocuments(documents);
        writer.commit();
        writer.close();

        return true;
    }

    private Document CreateAppUserDocument(AppUser appUser) {
        Document document = new Document();


        document.add(new StringField("IdUser", appUser.getIdUser() , Field.Store.YES));
        document.add(new StringField("UserUUID", appUser.getUserUUID() , Field.Store.YES));
        document.add(new StringField("Status", appUser.getStatus() , Field.Store.YES));

        return document;
    }
}

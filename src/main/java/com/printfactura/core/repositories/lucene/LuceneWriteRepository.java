package com.printfactura.core.repositories.lucene;

import com.printfactura.core.domain.appusers.AppUser;
import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.domain.sales.ui.InvoiceSalesUI;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.memory.MemoryIndex;
import org.apache.lucene.store.FSDirectory;

import org.apache.lucene.util.BytesRef;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Paths;

@Slf4j
@Repository
public class LuceneWriteRepository implements LuceneWriteDocuments {

    private final String INDEX_DIR = "c:/temp/lucene8index/";
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
    public boolean WriteInvoiceDocument(InvoiceSalesUI invoiceSalesUI, String suuid) throws IOException {

        IndexWriter writer = createWriter("invoice/" + suuid);

        Document invoiceDoc = CreateInvoiceDocument(invoiceSalesUI);

        writer.addDocument(invoiceDoc);

        // List<Document>
        //writer.addDocuments(documents);
        writer.commit();
        writer.close();

        return true;
    }


    private Document CreateInvoiceDocument(InvoiceSalesUI invoiceSalesUI){

        Document document = new Document();

        document.add(new StringField("InvoiceID", invoiceSalesUI.getInvoiceID() , Field.Store.YES));
        document.add(new TextField("Customer", invoiceSalesUI.getCustomer() , Field.Store.YES));
        document.add(new StringField("DateInvoice", invoiceSalesUI.getDateInvoice() , Field.Store.YES));
        document.add(new StringField("NumberInvoice", invoiceSalesUI.getNumberInvoice() , Field.Store.YES));
        document.add(new StringField("TotalAmount", invoiceSalesUI.getTotalAmount() , Field.Store.YES));
        document.add(new StringField("VAT", invoiceSalesUI.getVAT() , Field.Store.YES));

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

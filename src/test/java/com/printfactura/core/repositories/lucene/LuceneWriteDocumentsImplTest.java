package com.printfactura.core.repositories.lucene;

import com.printfactura.core.domain.appusers.AppUser;
import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.services.lucene.LuceneServiceAppUser;
import com.printfactura.core.services.lucene.LuceneServiceCustomer;
import com.printfactura.core.testutils.BuildCustomerObject;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LuceneWriteDocumentsImplTest {


    @Autowired
    private LuceneServiceAppUser luceneServiceAppUser;

    // max docs 2.147.483.519
    LuceneWriteDocumentsImpl luceneWriteDocuments = new LuceneWriteDocumentsImpl();
    //LuceneSearchDocumentsImpl luceneSearchDocuments = new LuceneSearchDocumentsImpl();

    @Test
    void writeAppUserDocument() throws IOException {

        AppUser appUser = AppUser.builder().
                IdUser("antonio.gialnet@gmail.com").
                Password("a1").
                build();

        luceneWriteDocuments.WriteAppUserDocument(appUser);

    }

    @Test
    public void ServiceSearchAppUser() throws Exception {
        AppUser appUser = luceneServiceAppUser.searchByIdUser("antonio.gialnet@gmail.com");
        System.out.println(appUser.getUserUUID());
        System.out.println(appUser.getIdUser());
    }

    @Test
    void writeCustomerDocument() throws IOException {

        Customer customer = BuildCustomerObject.BuilderCustomer();

        System.out.println(customer.getCompanyName());
        // Create an Index Document
        luceneWriteDocuments.WriteCustomerDocument(customer, UUID.randomUUID().toString());
        //assertTrue(luceneWriteDocuments.WriteCustomerDocument(customer));

    }

    @Test
    void searchCustomerDocument() throws Exception {

        //luceneSearchDocuments.
        /*IndexSearcher searcher = luceneSearchDocuments.createSearcher();

        //Search by ID
        TopDocs foundDocs = luceneSearchDocuments.searchById("1", searcher);

        System.out.println("Total Results :: " + foundDocs.totalHits);

        for (ScoreDoc sd : foundDocs.scoreDocs)
        {
            Document d = searcher.doc(sd.doc);
            System.out.println(String.format(d.get("CompanyName")));
        }*/
    }

    @Test
    void searchCustomerByName() throws Exception {

        /*IndexSearcher searcher = luceneSearchDocuments.createSearcher();
        //Search by firstName
        TopDocs foundDocs2 = luceneSearchDocuments.searchByFirstName("Tri*", searcher);

        System.out.println("Total Results :: " + foundDocs2.totalHits);

        for (ScoreDoc sd : foundDocs2.scoreDocs)
        {
            Document d = searcher.doc(sd.doc);
            System.out.println(String.format(d.get("IdCode")));
        }*/
    }

}
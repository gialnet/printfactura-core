package com.printfactura.core.services.lucene;

import com.printfactura.core.domain.appusers.AppUser;
import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.repositories.lucene.document.CustomerSearch;
import com.printfactura.core.repositories.lucene.LuceneSearchDocuments;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LuceneServiceCustomer {

    private List<Customer> customers = new ArrayList<>();
    private final LuceneSearchDocuments<CustomerSearch> customerSearch;

    public LuceneServiceCustomer(LuceneSearchDocuments<CustomerSearch> customerSearch) {
        this.customerSearch = customerSearch;
    }

    public List<Customer> searchByIdCode(String IdCode, AppUser appUser) throws Exception {

        // Select Index by AppUser UserUUID
        IndexSearcher searcher = customerSearch.CustomerSearch().CreateSearcherCustomer(appUser);
        customers.clear();

        // search by customer code
        TopDocs docFound = customerSearch.CustomerSearch().searchByIdCode(IdCode);

        for (ScoreDoc sd : docFound.scoreDocs)
        {
            Document d = searcher.doc(sd.doc);
            // System.out.println(String.format(d.get("CompanyName")));

            // Add customer
            customers.add(Customer.builder().
                    IdCode(d.get("IdCode")).
                    Identification(d.get("Identification")).
                    CompanyName(d.get("CompanyName")).
                    Address(d.get("Address")).
                    City(d.get("City")).
                    PostCode(d.get("PostCode")).
                    Country(d.get("Country")).
                    build()
            );
        }

        return customers;
    }
}

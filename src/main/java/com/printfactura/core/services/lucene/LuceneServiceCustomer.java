package com.printfactura.core.services.lucene;

import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.repositories.lucene.CustomerLucene;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LuceneServiceCustomer {

    private List<Customer> customers = new ArrayList<>();
    private final CustomerLucene customerSearch;

    public LuceneServiceCustomer(CustomerLucene customerSearch) {
        this.customerSearch = customerSearch;
    }


    public TopDocs orderByIdCodeFromTo(int FromIdCode, int ToIdCode, String uuid) throws IOException, ParseException {

        IndexSearcher searcher = customerSearch.OpenSearcher(uuid);
        // Select Index by AppUser UserUUID

        return customerSearch.orderByIdCodeFromTo(searcher, FromIdCode, ToIdCode);
    }

    /**
     *
     * @param page page of data 1,2,3,...
     * @param size amount of rows per page 10,22, whatever
     * @param uuid
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public List<Customer> CustomerByPages(int page, int size, String uuid) throws IOException, ParseException {

        IndexSearcher searcher = customerSearch.OpenSearcher(uuid);

        int from = size * (page -1) + page;
        int to = page * size;
        return ListOfCustomer(customerSearch.
                        orderByIdCodeFromTo(searcher, from, to),
                        searcher);
    }
    /**
     *
     * @param hits
     * @param searcher
     * @return
     * @throws IOException
     */
    public List<Customer> ListOfCustomer(TopDocs hits, IndexSearcher searcher) throws IOException {

        customers.clear();
        
        for (ScoreDoc sd : hits.scoreDocs)
        {
            Document d = searcher.doc(sd.doc);
            // System.out.println(String.format(d.get("CompanyName")));

           /* IndexableField field = d.getField("IdCode");
            String sv= field.stringValue();*/
            // Add customer
            customers.add(Customer.builder().
                    IdCode((Integer) d.getField("IdCode").numericValue()).
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


    /**
     *
     * @param name
     * @param uuid
     * @return
     * @throws Exception
     */
    public List<Customer> searchByCompanyName(String name, String uuid) throws Exception {

        IndexSearcher searcher = customerSearch.OpenSearcher(uuid);
        customers.clear();

        return ListOfCustomer(customerSearch.
                        searchByCompanyName(searcher, name),
                searcher);

    }

    /**
     *
     * @param name
     * @param uuid
     * @return
     * @throws Exception
     */
    public List<Customer> CompanyNamePrefixQuery(String name, String uuid) throws Exception {
        IndexSearcher searcher = customerSearch.OpenSearcher(uuid);
        customers.clear();

        return ListOfCustomer(customerSearch.
                        CompanyNamePrefixQuery(searcher, name),
                searcher);
    }

    public List<Customer> searchByName(String name, String uuid) throws IOException, ParseException {

        IndexSearcher searcher = customerSearch.OpenSearcher(uuid);
        customers.clear();

        QueryParser qp = new QueryParser("CompanyName", new StandardAnalyzer());
        Query idQuery = qp.parse(name);
        TopDocs hits = searcher.search(idQuery, 10);

        for (ScoreDoc sd : hits.scoreDocs)
        {
            Document d = searcher.doc(sd.doc);
            // System.out.println(String.format(d.get("CompanyName")));

            // Add customer
            customers.add(Customer.builder().
                    IdCode(Integer.parseInt(d.get("IdCode"))).
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

    public List<Customer> searchByIdCode(int IdCode, String uuid) throws Exception {

        // Select Index by AppUser UserUUID
        IndexSearcher searcher = customerSearch.OpenSearcher(uuid);
        customers.clear();

        // search by customer code
        TopDocs docFound = customerSearch.searchByIdCode(searcher, IdCode);

        for (ScoreDoc sd : docFound.scoreDocs)
        {
            Document d = searcher.doc(sd.doc);
            // System.out.println(String.format(d.get("CompanyName")));

            // Add customer
            customers.add(Customer.builder().
                    IdCode(Integer.parseInt(d.get("IdCode"))).
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

package com.printfactura.core.services.rocksdb;

import com.printfactura.core.domain.appusers.AppUser;
import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.repositories.lucene.document.CustomerSearch;
import com.printfactura.core.ui.grid.CustomerGrid;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@PropertySource(value = "classpath:application.properties")
@SpringBootTest
class ServicesCustomerTest {

    @Autowired
    ServicesCustomer servicesCustomer;

    @Test
    void saveCustomer() throws IOException {

        int i=0;
        boolean saveOK=false;

        for (Customer  cl: CustomerGrid.MakeListCustomers())
        {
            saveOK=servicesCustomer.SaveCustomer(cl,"diego@gmail.com","2168839c-3d08-4411-8127-db678f89d055");
            if (saveOK)
                i++;
        }

        assertEquals(i, 3);
    }

    @Test
    @DisplayName("index customer order by id")
    void orderByIdCodeFromTo() throws IOException, ParseException {

        CustomerSearch customerSearch = new CustomerSearch();

        AppUser appUser = AppUser.builder().
                IdUser("diego@gmail.com").
                Password("a1").
                UserUUID("2168839c-3d08-4411-8127-db678f89d055").
                build();

        customerSearch.CreateSearcherCustomer(appUser);
        IndexSearcher searcher = customerSearch.getIndexSearcher();

        TopDocs topDocs = customerSearch.orderByIdCodeFromTo("2","4");

        System.out.println(topDocs.totalHits);

        for (ScoreDoc sd : topDocs.scoreDocs) {

            Document d = searcher.doc(sd.doc);
            //System.out.println(d.get("IdCode"));
            System.out.println(d.get("CompanyName"));
        }

    }
}
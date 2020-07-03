package com.printfactura.core.services.rocksdb;

import com.printfactura.core.domain.appusers.AppUser;
import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.services.lucene.LuceneServiceCustomer;
import com.printfactura.core.ui.grid.CustomerGrid;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.TopDocs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*@TestPropertySource(locations="classpath:application-test.properties")
@EnableConfigurationProperties*/
@SpringBootTest
class ServicesCustomerTest {

    @Autowired
    ServicesCustomer servicesCustomer;

    @Autowired
    LuceneServiceCustomer luceneServiceCustomer;

    private final String uuid="b463b875-cc23-46ff-ba2a-fbe7c5497ade";
    private final String email="m1";

    @Test
    void saveCustomer() throws IOException {

        int i=0;
        boolean saveOK=false;

        for (Customer  cl: CustomerGrid.MakeListCustomers())
        {
            saveOK=servicesCustomer.SaveCustomer(cl,email,uuid);
            if (saveOK)
                i++;
        }

        assertEquals(i, 12);
    }

    @Test
    @DisplayName("index customer order by id")
    void orderByIdCodeFromTo() throws IOException, ParseException {

        AppUser appUser = AppUser.builder().
                IdUser(email).
                Password("a1").
                UserUUID(uuid).
                build();


        TopDocs topDocs = luceneServiceCustomer.orderByIdCodeFromTo(1,12,uuid);

        System.out.println(topDocs.totalHits);

        // IndexSearcher searcher =
        /*for (ScoreDoc sd : topDocs.scoreDocs) {

            Document d = searcher.doc(sd.doc);
            //System.out.println(d.get("IdCode"));
            System.out.println(d.get("CompanyName"));
        }*/

    }

    @Test
    public void searchByName() throws Exception {

       List<Customer> lc = luceneServiceCustomer.searchByCompanyName("CodeControl LTD",uuid);
       System.out.println(lc.size());
       for (Customer cm: lc) {
           System.out.println(cm.getCompanyName());
           System.out.println(cm.getIdCode());
       }
    }

    @Test
    public void CompanyNamePrefixQuery() throws Exception {

        List<Customer> lc = luceneServiceCustomer.CompanyNamePrefixQuery("TR",uuid);
        System.out.println(lc.size());
        for (Customer cm: lc) {
            System.out.println(cm.getCompanyName());
            System.out.println(cm.getIdCode());
        }
    }

    @Test
    public void searchByIdCode() throws Exception {
        List<Customer> lc = luceneServiceCustomer.searchByIdCode(19,uuid);
        System.out.println(lc.size());
    }
}
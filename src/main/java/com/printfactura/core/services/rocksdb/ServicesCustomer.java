package com.printfactura.core.services.rocksdb;

import com.google.gson.Gson;
import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.repositories.lucene.LuceneWriteRepository;
import com.printfactura.core.repositories.rocksdb.KVRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ServicesCustomer {

    private final KVRepository<String, Object> repository;
    private final LuceneWriteRepository luceneWriteRepository;
    private Gson gson = new Gson();
    private List<Customer> customers = new ArrayList<>();
    private Customer customer;

    public ServicesCustomer(KVRepository<String, Object> repository, LuceneWriteRepository luceneWriteRepository) {
        this.repository = repository;
        this.luceneWriteRepository = luceneWriteRepository;
    }

    public List<Customer> MakeListCustomers() {

        customers.clear();
        // Add customer
        customers.add(Customer.builder().
                CompanyName("Trilateral IT").
                Address("Street 1 number 1").
                City("Milton Keynes").
                PostCode("MK5 6LG").
                Country("United Kingdom").
                build()
        );

        customers.add(Customer.builder().
                CompanyName("May Bussines Company").
                Address("Somerset street").
                City("Milton Keynes").
                PostCode("MK5 6LG").
                Country("United Kingdom").
                build()
        );

        customers.add(Customer.builder().
                CompanyName("Vivaldi-Spring LTD").
                Address("Suite 38, Temple Chambers, 3-7 Temple Avenue").
                City("London").
                PostCode("EC4Y 0HP").
                Country("United Kingdom").
                build()
        );
        return customers;
    }

    private Optional<Object> Find(String key){

        return repository.find(key);
    }

    public Customer FindCustomerByID(String AppUserEmail, int CustomerID)
    {

       var myCustomer = Find("customer." +
                AppUserEmail.toLowerCase() +
                "."+CustomerID);

        // found value
        if (myCustomer.isPresent()){

            String js_myCustomer = (String) myCustomer.get();
            customer = gson.fromJson(js_myCustomer, Customer.class);

        }
        else {
            customer = Customer.builder().IdCode(CustomerID).build();

        }

        return customer;

    }

    private int IncreaseOneSeqCustomer(String email)
    {

        // find sequence
        var sequence = repository.find("sequence.customer."+email);
        int seq = (int) sequence.get();
        // increment sequence
        seq++;
        // save sequence
        repository.save("sequence.customer."+email, seq);

        return seq;
    }

    public boolean SaveCustomer(Customer customer, String email, String uuid) throws IOException {

        log.info("SaveCustomer-> customer object '{}'",customer);
        boolean saveOK=false;

        customer.setIdCode(IncreaseOneSeqCustomer(email));
        /*String customer_key ="customer." + email.toLowerCase() + "."+ customer.getIdCode();
        log.info("SaveCustomer-> key customer '{}'",customer_key);*/
        if (repository.save("customer." +
                email.toLowerCase() +
                "."+
                customer.getIdCode(), gson.toJson(customer)) )
            saveOK = luceneWriteRepository.WriteCustomerDocument(customer,uuid);

        return saveOK;
    }

    public boolean UpdateCustomer(Customer customer, String email, String uuid) throws IOException {

        // luceneWriteRepository.UpdateCustomer
        log.info("SaveCustomer-> customer object '{}'",customer);
        boolean saveOK=false;


        if (repository.save("customer." +
                email.toLowerCase() +
                "."+
                customer.getIdCode(), gson.toJson(customer)) )
            saveOK = luceneWriteRepository.UpdateCustomer(customer,uuid);

        return saveOK;
    }
}

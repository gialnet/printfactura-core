package com.printfactura.core.services.rocksdb;

import com.google.gson.Gson;
import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.repositories.rocksdb.KVRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ServicesCustomer {

    private final KVRepository<String, Object> repository;
    private Gson gson = new Gson();
    private List<Customer> customers = new ArrayList<>();

    public ServicesCustomer(KVRepository<String, Object> repository) {
        this.repository = repository;
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

    public Customer Find(){
        return null;
    }

    private String IncreaseOneSeqCustomer(String email)
    {

        // find sequence
        var sequence = repository.find("sequence.customer."+email);
        int seq = (int) sequence.get();
        // increment sequence
        seq++;
        // save sequence
        repository.save("sequence.customer."+email, seq);

        return String.valueOf(seq);
    }

    public boolean SaveCustomer(Customer customer, String email){

        log.info("SaveCustomer-> customer object '{}'",customer);
        customer.setIdCode(IncreaseOneSeqCustomer(email));
        /*String customer_key ="customer." + email.toLowerCase() + "."+ customer.getIdCode();
        log.info("SaveCustomer-> key customer '{}'",customer_key);*/
        repository.save("customer." +
                email.toLowerCase() +
                "."+
                customer.getIdCode(), gson.toJson(customer));

        return true;
    }

}

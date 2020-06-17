package com.printfactura.core.services.rocksdb;

import com.google.gson.Gson;
import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.repositories.rocksdb.KVRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicesRocksDB {

    private Gson gson = new Gson();
    private final KVRepository<String, Object> repository;

    public ServicesRocksDB(KVRepository<String, Object> repository) {
        this.repository = repository;
    }

    public void SaveCustomer(){

        var customer = Customer.builder().
                CompanyName("Super Company").
                Identification("La madre superiora").
                Address("His home").
                build();

        repository.save("customer1", gson.toJson(customer));
    }

    public Optional<Object> FindCustomer(){

       return repository.find("customer1");
    }


}

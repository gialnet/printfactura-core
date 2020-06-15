package com.printfactura.core.services.rocksdb;

import com.google.gson.Gson;
import com.printfactura.core.domain.appusers.AppUser;
import com.printfactura.core.repositories.rocksdb.KVRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ServicesUsers {

    private Gson gson = new Gson();
    private final KVRepository<String, Object> repository;

    public ServicesUsers(KVRepository<String, Object> repository) {
        this.repository = repository;
    }

    /**
     *  Save IdUser usually email address antonio@gmial.com
     *  the value the object AppUser in JSON
     *
     *  Create two fields for customer sequence and invoice sequence
     *  sequence.customer.antonio@gmial.com    => 0
     *  sequence.invoice.antonio@gmial.com     => 0
     *
     *  Create an UUID version 4 for each user this number it is use for Lucene Index
     *  to create a different folder to contain the index
     *  /lucene8/customers/880d6dd3-9e6e-4c6a-8791-8c0d682e6d19
     *  /lucene8/invoices/880d6dd3-9e6e-4c6a-8791-8c0d682e6d19
     *
     * @param appUser
     */
    public boolean SaveUser(AppUser appUser){

        // Create two fields for customer sequence and invoice sequence
        repository.save("sequence.customer." + appUser.getIdUser(), 0);
        repository.save("sequence.invoice." +  appUser.getIdUser(), 0);

        // Create an UUID version 4 for each user
        repository.save("user.uuid." +  appUser.getIdUser(), UUID.randomUUID());

        // save IdUser usually email address antonio@gmial.com
        return repository.save(appUser.getIdUser(), gson.toJson(appUser));

    }

    /**
     * Search for an user
     * @param IdUser
     */
    public Optional<Object> FindUser(String IdUser){

        return repository.find(IdUser);

    }
}

package com.printfactura.core.services.rocksdb;

import com.google.gson.Gson;
import com.printfactura.core.domain.appusers.AppUser;
import com.printfactura.core.repositories.rocksdb.KVRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
     * @param appUser
     */
    public boolean SaveUser(AppUser appUser){

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

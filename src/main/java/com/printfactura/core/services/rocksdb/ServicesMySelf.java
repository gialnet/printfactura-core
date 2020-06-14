package com.printfactura.core.services.rocksdb;

import com.google.gson.Gson;
import com.printfactura.core.domain.MySelf;
import com.printfactura.core.repositories.rocksdb.KVRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicesMySelf {

    private Gson gson = new Gson();
    private final KVRepository<String, Object> repository;

    public ServicesMySelf(KVRepository<String, Object> repository) {
        this.repository = repository;
    }

    /**
     *
     * @param mySelf
     * @return
     */
    public boolean SaveMySelf(MySelf mySelf){

        return repository.save(mySelf.getIdentification(), gson.toJson(mySelf));
    }

    /**
     *
     * @param IdMySelf
     * @return
     */
    public Optional<Object> FindMySelf(String IdMySelf){

        return repository.find(IdMySelf);
    }
}

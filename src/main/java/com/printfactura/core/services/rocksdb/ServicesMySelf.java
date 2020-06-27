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
    private MySelf mySelf;
    Optional<MySelf> oms;

    public ServicesMySelf(KVRepository<String, Object> repository) {
        this.repository = repository;
    }

    /**
     * K -> mySelf. + email user account
     * K -> mySelf.antonio@gmail.com
     * @param mySelf
     * @return
     */
    public boolean SaveMySelf(MySelf mySelf){

        return repository.save("mySelf."+mySelf.getIdUser().toLowerCase(), gson.toJson(mySelf));
    }

    /**
     *
     * @param email
     * @return
     */
    private Optional<Object> Find(String email){

        return repository.find("mySelf."+email.toLowerCase());
    }

    /**
     * If found populate object mySelf otherwise an empty object
     * @param email
     * @return
     */
    public MySelf FindMySelf(String email){

        var myselfdb = Find(email);

        // found value
        if (myselfdb.isPresent()){

            String js_myself = (String) myselfdb.get();
            mySelf = gson.fromJson(js_myself, MySelf.class);
            //oms = Optional.ofNullable(mySelf);
        }
        else {
            mySelf = MySelf.builder().IdUser(email).build();
            //oms = Optional.ofNullable(mySelf);
        }

        return mySelf;
    }
}

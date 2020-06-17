package com.printfactura.core.custom;

import com.printfactura.core.services.rocksdb.ServicesUsers;
import org.springframework.stereotype.Component;

@Component
public class ShouldAuthenticateAgainstThirdPartySystem {

    private final ServicesUsers servicesUsers;

    public ShouldAuthenticateAgainstThirdPartySystem(ServicesUsers servicesUsers) {
        this.servicesUsers = servicesUsers;
    }

    public boolean AuthenticateAgainstThirdPartySystem(String name, String password){

        return servicesUsers.UserAuthByUserAndPassword(name, password);

    }

}

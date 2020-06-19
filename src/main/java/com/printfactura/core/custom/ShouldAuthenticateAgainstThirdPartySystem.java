package com.printfactura.core.custom;

import com.printfactura.core.services.rocksdb.ServicesUsers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class ShouldAuthenticateAgainstThirdPartySystem {

    private final ServicesUsers servicesUsers;
    @Autowired
    private HttpSession httpSession;

    public ShouldAuthenticateAgainstThirdPartySystem(ServicesUsers servicesUsers) {
        this.servicesUsers = servicesUsers;
    }

    public boolean AuthenticateAgainstThirdPartySystem(String name, String password){

        boolean user_auth = servicesUsers.UserAuthByUserAndPassword(name, password);
        if (user_auth){
            log.info("uuid-> '{}'", servicesUsers.getAppUser().getUserUUID());
            httpSession.setAttribute("uuid",servicesUsers.getAppUser().getUserUUID());
        }
        
        return true;

    }

}

package com.printfactura.core.controllersrest;

import com.printfactura.core.domain.appusers.AppUser;
import com.printfactura.core.services.rocksdb.ServicesUsers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class UserLogin {

    private final ServicesUsers servicesUsers;
    Map<String, Object> response = new HashMap<String, Object>();
    private AppUser appUser;

    public UserLogin(ServicesUsers servicesUsers) {
        this.servicesUsers = servicesUsers;
    }

    @RequestMapping(value = "v1/login", method = RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity<?> checkLogin(@RequestParam String user, @RequestParam String password){

        response.clear();
        response.put("version", "0.1.0");

        if (servicesUsers.UserAuthByUserAndPassword(user, password)){

            response.put("result", true);
            appUser = servicesUsers.getAppUser();
            response.put("uuid", appUser.getUserUUID());
            response.put("IdUser", appUser.getIdUser());
            response.put("Status", appUser.getStatus());
            response.put("Country", appUser.getCountryISO3166());
            response.put("Language", appUser.getLanguageISO3166());
        }
        else
            response.put("result", false);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

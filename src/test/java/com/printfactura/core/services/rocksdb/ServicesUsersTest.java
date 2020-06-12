package com.printfactura.core.services.rocksdb;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.printfactura.core.domain.appusers.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServicesUsersTest {

    @Autowired
    ServicesUsers servicesUsers;

    Gson gson= new Gson();

    @Test
    void saveUser() {

        AppUser appUser = AppUser.builder().
                IdUser("antonio@gmail.com".toLowerCase()).
                Password("a1").
                SignDate(new Date()).
                Status("newUser").
                build();

        assertEquals(servicesUsers.SaveUser(appUser), true);
    }

    @Test
    void findUser() {

       var user = servicesUsers.FindUser("antonio@gmail.com");

       System.err.println(user.toString());
       String value = (String) user.get();
       System.out.println(value);

        // From JSON to Object
        AppUser appUser;

        // Don't matter if value it is String or JSON
        appUser=gson.fromJson(value, AppUser.class);
        System.out.println(appUser.getIdUser());
        System.out.println(appUser.getPassword());

    }
}
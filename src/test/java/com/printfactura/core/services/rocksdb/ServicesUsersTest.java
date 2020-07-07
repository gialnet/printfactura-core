package com.printfactura.core.services.rocksdb;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.printfactura.core.domain.appusers.AppUser;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Disabled("for demonstration purposes")
class ServicesUsersTest {

    @Autowired
    ServicesUsers servicesUsers;

    Gson gson= new Gson();

    AppUser appUser;

    String email ="l1";

    @Test
    void saveUser() throws IOException {

        appUser = AppUser.builder().
                IdUser(email.toLowerCase()).
                Password("a1").
                build();

        assertEquals(servicesUsers.SaveUser(appUser), true);
    }

    @Test
    public void GetSequences(){

       var seqObj = servicesUsers.GetSequences(email);
       System.out.println(seqObj.getSeqCustomer());
       System.out.println(seqObj.getSeqInvoice());

       assertNotNull(seqObj);
    }

    @Test
    void findUser() {

       var user = servicesUsers.FindUser(email);

       if (user.isPresent()){
           System.err.println(user.toString());
           String value = (String) user.get();
           System.out.println(value);

            // From JSON to Object

            // Don't matter if value it is String or JSON
            appUser=gson.fromJson(value, AppUser.class);
            System.out.println(appUser.getIdUser());
            System.out.println(appUser.getPassword());

       }
       assertNotEquals(user, Optional.empty());
    }

    @Test
    void NotFindUser() {

        var user = servicesUsers.FindUser("antonioXX@gmail.com");
        assertEquals(user, Optional.empty());
    }

    @Test
    void registerUserExist() {

        var user = servicesUsers.FindUser("antonio@gmail.com");
        if (user.isPresent())
            System.out.println("Send alert signal");

        assertNotEquals(user, Optional.empty());
    }

    @Test
    void registerUserNotExist() throws IOException {

        appUser = AppUser.builder().
                IdUser("d1@gmail.com".toLowerCase()).
                Password("a1").
                build();

        var user = servicesUsers.FindUser(appUser.getIdUser());

        if (user.isEmpty()){
            servicesUsers.SaveUser(appUser);
            var valueFind = servicesUsers.FindKey("sequence.customer."+appUser.getIdUser());
            Integer value = (Integer) valueFind.get();
            System.out.println(value);
        }


    }

    @Test
    public void UserAuthByUserAndPassword(){

        String user = "antonio@gmail.comh";
        String pass = "a1b";
        var userdata = servicesUsers.FindUser(user);
        if (userdata.isEmpty()){

            System.out.println("User doesn't exist");
        }
        else {
            String value = (String) userdata.get();
            appUser=gson.fromJson(value, AppUser.class);
            if (appUser.getIdUser().equals(user)) {
                if (appUser.getPassword().equals(pass))
                    System.out.println("User and password match");
                else
                    System.out.println("Password doesn't match match");
            }
            else {
                System.out.println("User doesn't match match");
            }

        }

    }
}
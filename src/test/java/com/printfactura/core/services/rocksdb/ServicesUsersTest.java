package com.printfactura.core.services.rocksdb;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.printfactura.core.domain.appusers.AppUser;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@Disabled("for demonstration purposes")
class ServicesUsersTest {

    @Autowired
    ServicesUsers servicesUsers;

    Gson gson= new Gson();

    AppUser appUser;

    static String email;
    static String pass;

    @BeforeAll
    @Test
    public static void RandomEmailValues(){

        email = RandomStringUtils.randomAlphabetic(10);
        pass =  RandomStringUtils.randomAlphabetic(5);
    }

    @Test
    @Order(1)
    @DisplayName("Create a user object with random values")
    void saveUser() throws IOException {

        appUser = AppUser.builder().
                IdUser(email.toLowerCase()).
                Password(pass).
                build();

        assertEquals(servicesUsers.SaveUser(appUser), true);
    }

    @Test
    @Order(2)
    @DisplayName("check if sequences for customer and invoice has been create")
    public void GetSequences(){

       var seqObj = servicesUsers.GetSequences(email.toLowerCase());
       System.out.println(seqObj.getSeqCustomer());
       System.out.println(seqObj.getSeqInvoice());

       assertNotNull(seqObj);
    }

    @Test
    @Order(3)
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
    @Order(4)
    void NotFindUser() {

        var user = servicesUsers.FindUser("antonioXX@gmail.com");
        assertEquals(user, Optional.empty());
    }

    @Test
    @Order(5)
    void registerUserExist() {

        var user = servicesUsers.FindUser(email);
        if (user.isPresent())
            System.out.println("Send alert signal");

        assertNotEquals(user, Optional.empty());
    }

    @Test
    @Order(6)
    void registerUserNotExist() throws IOException {

        var user = servicesUsers.FindUser(email.toLowerCase());

        if (user.isEmpty()){
            servicesUsers.SaveUser(appUser);
            var valueFind = servicesUsers.FindKey("sequence.customer."+email.toLowerCase());
            Integer value = (Integer) valueFind.get();
            System.out.println(value);
            assertNotNull(value);
        }
        else {
            var valueFind = servicesUsers.FindKey("sequence.customer."+email.toLowerCase());
            Integer value = (Integer) valueFind.get();
            System.out.println(value);
            assertNotNull(value);
        }

    }

    @Test
    @Order(7)
    public void UserAuthByUserAndPassword(){

//        String user = "antonio@gmail.comh";
//        String pass = "a1b";
        var userdata = servicesUsers.FindUser(email);
        if (userdata.isEmpty()){

            System.out.println("User doesn't exist");
        }
        else {
            String value = (String) userdata.get();
            appUser=gson.fromJson(value, AppUser.class);
            if (appUser.getIdUser().equals(email)) {
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

    @Test
    public void givenUsingApache_whenGeneratingRandomStringBounded_thenCorrect() {

        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        System.out.println(generatedString);
    }

    @Test
    public void givenUsingApache_whenGeneratingRandomAlphabeticString_thenCorrect() {
        String generatedString = RandomStringUtils.randomAlphabetic(10);

        System.out.println(generatedString);
    }
}
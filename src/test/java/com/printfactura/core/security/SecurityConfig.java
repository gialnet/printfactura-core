package com.printfactura.core.security;

import com.printfactura.core.controllers.MySelfController;
import com.printfactura.core.custom.CustomAuthenticationProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SecurityConfig {

    @Autowired
    private TestRestTemplate restTemplate;

    /*@Autowired
    CustomAuthenticationProvider customAuthenticationProvider;

    SecurityConfig securityConfig = new SecurityConfig();

    @Autowired
    MySelfController mySelfController;

    @Test
    public void givenMemUsers_whenGetPingWithValidUser_thenOk() {
        ResponseEntity<String> result
                = makeRestCallToGetPing("pepe", "pass");

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo("OK");
    }*/

    private ResponseEntity<String>
    makeRestCallToGetPing(String username, String password) {
        return restTemplate.withBasicAuth(username, password)
                .getForEntity("/menu", String.class, Collections.emptyMap());
    }

    private ResponseEntity<String> makeRestCallToGetPing() {
        return restTemplate
                .getForEntity("/menu", String.class, Collections.emptyMap());
    }
}

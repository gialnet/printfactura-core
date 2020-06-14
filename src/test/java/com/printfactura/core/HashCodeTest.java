package com.printfactura.core;

import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class HashCodeTest {

    @Test
    public void Test1() throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        String stringToEncrypt="Hola mundo, maravilloso esto no para de crecer un poco mas mamita que rico";
        messageDigest.update(stringToEncrypt.getBytes());
        String encryptedString = new String(messageDigest.digest());

        System.out.println(encryptedString.length());
        String fm = String.format("%h", encryptedString);
        System.err.println(fm);
        System.out.printf("%h",encryptedString);
    }

    @Test
    public void RamdonUUID(){
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
        System.out.println(uuid.version());
        System.out.println(uuid.variant());
    }
}


package com.printfactura.core.custom;

public class ShouldAuthenticateAgainstThirdPartySystem {

    public static boolean shouldAuthenticateAgainstThirdPartySystem(String name, String password){

        if (name.equals("pepe"))
        return true;
        else
            return false;
    }
}

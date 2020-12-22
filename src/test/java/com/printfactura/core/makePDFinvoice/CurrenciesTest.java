package com.printfactura.core.makePDFinvoice;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.printfactura.core.makePDFinvoice.Currencies.SUPORTED_CURRENCIES;
import static org.junit.jupiter.api.Assertions.*;

class CurrenciesTest {

    @Test
    void values() {

        Currencies listCurrencies[] = Currencies.values();
        Arrays.stream(listCurrencies).forEach(System.out::println);
    }

    @Test
    void valueOf() {

        // get country
        SUPORTED_CURRENCIES.keySet().stream().forEach(e -> {System.out.println( e ); });

        // get language
        SUPORTED_CURRENCIES.values().stream().forEach(e -> {System.out.println( e ); });
    }
}
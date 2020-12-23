package com.printfactura.core.makePDFinvoice;

import org.junit.jupiter.api.Test;

import java.text.NumberFormat;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SupportedCurrenciesTest {

    @Test
    public void Test1(){

        SupportedCurrencies listCurrencies[] = SupportedCurrencies.values();
        Arrays.stream(listCurrencies).forEach(System.out::println);

        assertEquals(4, listCurrencies.length);
    }

    @Test
    public void PrintSymbols(){

        SupportedCurrencies listCurrencies[] = SupportedCurrencies.values();
        Arrays.stream(listCurrencies).forEach(e -> { System.out.println(NumberFormat.getCurrencyInstance(e.getLocale(e)).format(1520));});

        //NumberFormat.getCurrencyInstance(e.getLocale(e)).format(1520);
    }

}
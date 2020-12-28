package com.printfactura.core.makePDFinvoice;

import com.printfactura.core.domain.SupportedCurrencies;
import org.junit.jupiter.api.Test;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SupportedCurrenciesTest {

    @Test
    public void ListOfSupportedCurrencies(){

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

    @Test
    public void OneCurrency(){

        SupportedCurrencies supportedCurrencies = SupportedCurrencies.EURO;

        // Locale locale= supportedCurrencies.getLocale(supportedCurrencies);
        // Currency currency = Currency.getInstance(supportedCurrencies.getLocale(supportedCurrencies));

        // System.out.println(currency.getSymbol());

        System.out.println((Currency.getInstance(supportedCurrencies.getLocale(supportedCurrencies))).getSymbol());

        assertEquals("€", (Currency.getInstance(supportedCurrencies.getLocale(supportedCurrencies))).getSymbol());
    }

    @Test
    public void ConstantOneCurrency(){

        SupportedCurrencies supportedCurrencies = SupportedCurrencies.EURO;

        // Locale locale= supportedCurrencies.getLocale(supportedCurrencies);
        // Currency currency = Currency.getInstance(supportedCurrencies.getLocale(supportedCurrencies));

        // System.out.println(currency.getSymbol());

        System.out.println(supportedCurrencies);

        //assertEquals("€", (Currency.getInstance(supportedCurrencies.getLocale(supportedCurrencies))).getSymbol());
    }
}
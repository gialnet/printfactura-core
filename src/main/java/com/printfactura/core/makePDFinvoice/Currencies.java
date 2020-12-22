package com.printfactura.core.makePDFinvoice;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public enum Currencies {

    // country first because primary key for Map
    UK("UK", "en"), EURO("EU", "en"), SEK("SE", "sv"), BRL("BR", "pt");

    private final String language;
    private final String country;

    static final Map<String, String> SUPORTED_CURRENCIES =
            Arrays.stream(values()).collect(Collectors.toMap(Currencies::getCountry, Currencies::getLanguage));

    Currencies(String country, String language) {
        this.language = language;
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry(){
        return country;
    }
}

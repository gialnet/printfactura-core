package com.printfactura.core.domain;

import java.util.Locale;

public enum SupportedCurrencies {

    GBP(new Locale("en","GB")),
    EURO(new Locale("es","ES")),
    SEK(new Locale("sv","SE")),
    BRL(new Locale("pt","BR"));


    private final Locale locale;
    public static final SupportedCurrencies[] LIST_OF_CURRENCIES = SupportedCurrencies.values();

    SupportedCurrencies(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale(SupportedCurrencies currencies){
        return  currencies.locale;
    }
}

package com.printfactura.core.makePDFinvoice;

import java.util.Locale;

public enum SupportedCurrencies {

    GBP(new Locale("en","GB")),
    EURO(new Locale("es","ES")),
    SEK(new Locale("sv","SE")),
    BRL(new Locale("pt","BR"));


    private final Locale locale;

    SupportedCurrencies(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale(SupportedCurrencies currencies){
        return  currencies.locale;
    }
}

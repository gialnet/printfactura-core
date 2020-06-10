package com.printfactura.core.domain;

import com.printfactura.core.domain.sales.TotalSalesBill;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TotalSalesBillTest {
    static TotalSalesBill totalSalesBill;

    @BeforeAll
    public static void setup(){
        totalSalesBill = TotalSalesBill.builder().Hours(20*8).
                PriceHour(BigDecimal.valueOf(60)).
                PorVAT(20).
                HMRC_ExchangeRates(BigDecimal.valueOf(1.1385)).
                build();
    }

    @Test
    @DisplayName("Calc import from hours works by hour price and amount of VAT")
    public void CalculateTest()
    {

        totalSalesBill.Calculate();

        assertEquals(totalSalesBill.getBaseEuros(), BigDecimal.valueOf(9600));
        assertEquals(totalSalesBill.getVATEuros(), BigDecimal.valueOf(1920));
        assertEquals(totalSalesBill.getTotalEuros(), BigDecimal.valueOf(11520));

    }

    @Test
    @DisplayName("Convert from Euros to Pound by official exchange rate")
    public void ApplyCurrencyExchangeTest()
    {

        totalSalesBill.Calculate();
        totalSalesBill.ApplyCurrencyExchange();

        System.err.println(totalSalesBill.getBasePound());
        System.err.println(totalSalesBill.getVATPound());
        System.err.println(totalSalesBill.getTotalPound());

        assertEquals(totalSalesBill.getBasePound().setScale(2), BigDecimal.valueOf(10929.60).setScale(2));
        assertEquals(totalSalesBill.getVATPound().setScale(2), BigDecimal.valueOf(2185.92).setScale(2));
        assertEquals(totalSalesBill.getTotalPound().setScale(2), BigDecimal.valueOf(13115.52).setScale(2));

    }

}

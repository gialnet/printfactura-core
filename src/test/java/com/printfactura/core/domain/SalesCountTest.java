package com.printfactura.core.domain;

import com.printfactura.core.domain.sales.SalesCount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SalesCountTest {

    @Test
    @DisplayName("Format a bill number from year month and bill sequential number")
    public void getFormatBillNumberTest(){
        SalesCount salesCount = SalesCount.builder().
                Month("01").
                Year("2020").
                Number(2).
                build();

        System.err.println(salesCount.getFormatBillNumber());
        assertEquals(salesCount.getFormatBillNumber(), "2020/01/2");
    }
}

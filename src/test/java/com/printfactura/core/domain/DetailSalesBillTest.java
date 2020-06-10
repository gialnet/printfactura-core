package com.printfactura.core.domain;

import com.printfactura.core.domain.sales.DetailSalesBill;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DetailSalesBillTest {

    static DetailSalesBill detailSalesBill;

    @Test
    public void DetailSalesBillOneRowTest(){
        
        detailSalesBill = DetailSalesBill.builder().Concept("Hours work March").
                Unit(BigDecimal.valueOf(160)).
                Price(BigDecimal.valueOf(60)).
                PorVAT(BigDecimal.valueOf(20)).build();

        System.err.println(detailSalesBill.getTotalAmount());

        //assertEquals(detailSalesBill.getAmount(), 0);
    }

    @Test
    public void ListDetailSalesBillTest(){

        List<DetailSalesBill> detailSalesBills = new ArrayList<>();
        detailSalesBills.add(
                DetailSalesBill.builder().Concept("Hours work March").
                        Unit(BigDecimal.valueOf(160)).
                        Price(BigDecimal.valueOf(60)).
                        PorVAT(BigDecimal.valueOf(20)).build());

        assertEquals(detailSalesBills.size(), 1);
    }

}

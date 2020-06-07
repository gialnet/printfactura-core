package com.printfactura.core;

import com.printfactura.core.domain.MySelf;
import com.printfactura.core.domain.SalesCount;
import com.printfactura.core.repositories.GetDataSellBill;
import com.printfactura.core.repositories.GetDataDatosPerRepoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DatosPerTest {

    @Autowired
    GetDataDatosPerRepoImpl datosPerRepo;

    @Autowired
    GetDataSellBill getDataSellBill;

    @Test
    public void getDatosPerTest(){

        List<MySelf> mySelfList = datosPerRepo.getMe();
        assertTrue(mySelfList.size()>0 );

    }

    @Test
    public void getSaleCountTest(){
       SalesCount salesCount = getDataSellBill.getSaleCount();
       System.out.println(salesCount.getYear());
    }
}
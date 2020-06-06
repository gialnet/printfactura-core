package com.printfactura.core;

import com.printfactura.core.domain.MySelf;
import com.printfactura.core.domain.SalesCount;
import com.printfactura.core.repositories.Bill;
import com.printfactura.core.repositories.DatosPerRepoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DatosPerTest {

    @Autowired
    DatosPerRepoImpl datosPerRepo;

    @Autowired
    Bill bill;

    @Test
    public void getDatosPerTest(){

        List<MySelf> mySelfList = datosPerRepo.getMe();
        assertTrue(mySelfList.size()>0 );

    }

    @Test
    public void getSaleCountTest(){
       SalesCount salesCount = bill.getSaleCount();
       System.out.println(salesCount.getYear());
    }
}
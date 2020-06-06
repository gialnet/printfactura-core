package com.printfactura.core;

import com.printfactura.core.domain.MySelf;
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

    @Test
    public void getDatosPerTest(){

        List<MySelf> mySelfList = datosPerRepo.getMe();
        assertTrue(mySelfList.size()>0 );

    }
}
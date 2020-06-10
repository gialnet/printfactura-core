package com.printfactura.core.rocksdb;

import com.printfactura.core.services.rocksdb.ServicesRocksDB;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServicesRocksDBTest {

    @Autowired
    ServicesRocksDB servicesRocksDB;

    @Test
    public void SaveCustomerTest(){

        servicesRocksDB.SaveCustomer();
    }

    @Test
    public void FindCustomerTest(){

       var customer = servicesRocksDB.FindCustomer();

       System.err.println(customer.toString());
    }

}

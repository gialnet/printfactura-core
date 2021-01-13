package com.printfactura.core.retry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MyRetryServiceTest {

    @Autowired
    MyRetryService myRetryService;

    @Test
    void retry() {
        String result = myRetryService.retry("error");
        Assertions.assertEquals("Retry Recovery OK!", result);
    }
}
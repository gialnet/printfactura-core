package com.printfactura.core.retry;

import com.printfactura.core.error.ApiRetryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@Slf4j
public class MyRetryService {


    @Retryable(value = {RuntimeException.class}, maxAttempts = 4, backoff = @Backoff(1000))
    public String retry(String s){
        log.info("MyRetry in witch second {}", LocalDateTime.now().getSecond());
        if (s.equals("error"))
            throw new ApiRetryException("Error in MyRetryService");
        else return "Hi";
    }

    @Recover
    public String retryRecover(RuntimeException t, String s){

        log.info("MyRetry recovered {}", t.getMessage());
        return "Retry Recovery OK!";
    }
}

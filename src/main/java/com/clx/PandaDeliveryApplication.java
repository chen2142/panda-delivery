package com.clx;

import com.clx.config.WebMvcConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@ServletComponentScan
@Import(WebMvcConfig.class)
@EnableTransactionManagement
@EnableCaching
public class PandaDeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(PandaDeliveryApplication.class, args);
        log.info("Starting successful!");
    }

}

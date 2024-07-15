package org.example.houseKeeping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HouseKeepingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouseKeepingApplication.class, args);
    }

}

package cz.mcity.pashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PashopApplication {

    public static void main(String[] args) {
        SpringApplication.run(PashopApplication.class, args);
    }

}

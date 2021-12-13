package pers.xue.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class SpringJpaApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringJpaApplication.class, args);
    }
}
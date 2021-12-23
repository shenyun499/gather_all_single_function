package pers.xue.datasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class SpringDataSourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringDataSourceApplication.class, args);
    }
}
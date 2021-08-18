package pers.xue.skills;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * StateMachine Case Application
 *
 */
@SpringBootApplication
@EntityScan
public class CaseApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(CaseApplication.class, args);
    }
}
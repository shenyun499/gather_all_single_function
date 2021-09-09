package pers.xue.skills;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EntityScan
public class App
{
    public static void main( String[] args )
    {
        SpringApplication app = new SpringApplication(App.class);
        ConfigurableEnvironment environment = app.run(args).getEnvironment();
        System.out.println("s");
    }

    // Spring Validation默认会校验完所有字段，然后才抛出异常。可以通过一些简单的配置，开启Fali Fast模式，一旦校验失败就立即返回。
/*    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // 快速失败模式
                .failFast(true)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }*/
    }
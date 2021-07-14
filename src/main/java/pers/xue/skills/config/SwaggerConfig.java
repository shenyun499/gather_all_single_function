package pers.xue.skills.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @auther huangzhixue
 * @data 2021/7/12 4:18 下午
 * @Description swagger 配置
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("pers.xue.skills.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 设置swagger的一些信息
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("gather_all_single_function-Swagger2")
                .description("Restful API接口")
                .version("1.0.0")
                .build();
    }
}
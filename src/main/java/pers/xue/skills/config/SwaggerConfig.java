package pers.xue.skills.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther huangzhixue
 * @data 2021/7/12 4:18 下午
 * @Description swagger 配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, getGlobalResponseMessage())
                .globalResponseMessage(RequestMethod.POST, getGlobalResponseMessage())
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

    private List<ResponseMessage> getGlobalResponseMessage() {
        List<ResponseMessage> responseList = new ArrayList<>();
        responseList.add(new ResponseMessageBuilder().code(400).message("Invalid request").responseModel(new ModelRef("ApiError")).build());
        responseList.add(new ResponseMessageBuilder().code(200).message("Successful").responseModel(new ModelRef("ApiError")).build());
        responseList.add(new ResponseMessageBuilder().code(502).message("Server error").responseModel(new ModelRef("ApiError")).build());
        return responseList;
    }
}
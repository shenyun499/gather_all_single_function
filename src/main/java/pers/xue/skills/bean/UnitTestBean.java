package pers.xue.skills.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author huangzhixue
 * @date 2022/1/6 4:16 下午
 * @Description
 */
@Configuration
public class UnitTestBean {
    @Value("unit-test")
    private String unitTest;

    private String unitTest2;

    public String getUnitTest() {
        return unitTest;
    }
}

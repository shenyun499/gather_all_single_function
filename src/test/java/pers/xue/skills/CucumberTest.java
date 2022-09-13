package pers.xue.skills;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * @author huangzhixue
 * @date 2022/9/13 18:40
 * @Description
 *
 * https://cucumber.io/docs/cucumber/
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath: hellocucumber",
        glue = "pers.xue.skills"
//        tags = "@CucumberTest"
)
public class CucumberTest {
}

package pers.xue.boot_jpa_mul_datasouce.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author huangzhixue
 * @date 2024/9/3 11:52
 * @Description
 *
 * 自定义注解，用来标识需要切换数据源的方法
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceSwitch {
    String value() default "primary";
}
package pers.xue.boot_jpa_mul_datasouce.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
/**
 * @author huangzhixue
 * @date 2024/9/3 11:51
 * @Description
 * 使用AOP注入EntityManager类型
 * 通过AOP在方法执行前设置EntityManager，并在方法执行后清除。
 */

@Aspect
@Component
public class EntityManagerFactoryAspect {

    @Before("@annotation(dataSource)")
    public void switchDataSource(JoinPoint point, DataSourceSwitch dataSource) {
        //log.info("使用的数据源是:" + dataSource.value());
        System.out.println("使用的数据源是:" + dataSource.value());
        EntityManagerContextHolder.setEntityManagerFactoryType(dataSource.value());
    }

    @After("@annotation(dataSource)")
    public void restoreDataSource(JoinPoint point, DataSourceSwitch dataSource) {
        EntityManagerContextHolder.clearEntityManagerFactoryType();
    }
}
package pers.xue.boot_jpa_mul_datasouce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huangzhixue
 * @date 2024/9/3 11:49
 * @Description
 *
 * 全局配置实体管理工厂 EntityManagerFactory
 * 配置EntityManagerFactory，确定使用的EntityManagerFactory
 */
@Configuration
public class EntityManagerFactoryConfig {

    @Autowired
    @Qualifier("primaryEntityManagerFactory")
    private Object primaryEntityManagerFactory;

    @Autowired
    @Qualifier("secondaryEntityManagerFactory")
    private Object secondaryEntityManagerFactory;


    @Bean
    public EntityManagerFactory entityManager(){
        String dataSourceType = EntityManagerContextHolder.getEntityManagerFactoryType();

        RoutingEntityManagerFactory routingEntityManagerFactory = new RoutingEntityManagerFactory();

        Map<String, Object> targetEntityManagerFactorys = new HashMap<String, Object>();

        targetEntityManagerFactorys.put("primary", primaryEntityManagerFactory);  //主实体管理工厂
        targetEntityManagerFactorys.put("secondary", secondaryEntityManagerFactory); //次要实体管理工厂

        routingEntityManagerFactory.setTargetEntityManagerFactorys(targetEntityManagerFactorys);// 配置实体管理工厂
        routingEntityManagerFactory.setDefaultTargetEntityManagerFactory(primaryEntityManagerFactory);// 设置默认实体管理工厂

        return routingEntityManagerFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManager());
        return tm;
    }

}


package pers.xue.boot_jpa_mul_datasouce.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

/**
 * @author huangzhixue
 * @date 2024/9/3 11:34
 * @Description
 * 次要数据源配置
 **/
@Configuration
@EnableJpaRepositories(
        basePackages = "com.ts.service",
        entityManagerFactoryRef = "secondaryEntityManagerFactory"
)
public class SecondaryDataSourceConfig {

    @Autowired
    private JpaProperties jpaProperties;


    @Bean(name = "secondaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource secondaryDataSource() {
        return new DruidDataSource();
    }

    @Bean
    public Object secondaryEntityManagerFactory(DataSource secondaryDataSource,
                                                JpaProperties secondaryJpaProperties) {
        EntityManagerFactoryBuilder builder = createEntityManagerFactoryBuilder(secondaryJpaProperties);
        return builder.dataSource(secondaryDataSource).packages("com.ts.model")
                .persistenceUnit("secondaryDataSource").build();
    }

    private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(JpaProperties jpaProperties) {
        JpaVendorAdapter jpaVendorAdapter = createJpaVendorAdapter(jpaProperties);
        return new EntityManagerFactoryBuilder(jpaVendorAdapter, jpaProperties.getProperties(), null);
    }

    private JpaVendorAdapter createJpaVendorAdapter(JpaProperties jpaProperties) {
        // ... map JPA properties as needed
        return new HibernateJpaVendorAdapter();
    }

}
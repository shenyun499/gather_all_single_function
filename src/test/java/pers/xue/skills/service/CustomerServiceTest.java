package pers.xue.skills.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pers.xue.skills.entity.Customer;

import javax.sql.DataSource;

/**
 * @author huangzhixue
 * @date 2022/8/1 16:52
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CustomerServiceTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DataSource dataSource;

    @Autowired
    CustomerService customerService;

    @Before
    public void init() {
        ResourceDatabasePopulator populator =
                new ResourceDatabasePopulator(applicationContext.getResource("classpath:sql/other.sql"));
        DatabasePopulatorUtils.execute(populator, dataSource);
    }

    @Test
    public void testGetCustomer() {
        Customer result = customerService.getCustomer("1");
        Assert.assertNotNull(result);
    }
}

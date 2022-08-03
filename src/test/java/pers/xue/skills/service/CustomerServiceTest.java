package pers.xue.skills.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pers.xue.skills.entity.Customer;
import pers.xue.skills.repository.CustomerRepository;

import static org.mockito.Mockito.*;

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
    CustomerService customerService;

    @Test
    public void testGetCustomer() {
        Customer result = customerService.getCustomer("1");
        Assert.assertNotNull(result);
    }
}

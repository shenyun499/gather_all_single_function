package pers.xue.skills.bean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author huangzhixue
 * @date 2022/1/6 4:18 下午
 * @Description
 *
 * ReflectionTestUtils 通过它对 Mock后的UnitTestBean填充属性
 */
@RunWith(MockitoJUnitRunner.class)
public class UnitTestBeanTest {

    @InjectMocks
    private UnitTestBean unitTestBean;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(unitTestBean, "unitTest", "set successfully");
        ReflectionTestUtils.setField(unitTestBean, "unitTest2", "set successfully");
    }

    @Test
    public void testGetUnitTest() throws Exception {
        String result = unitTestBean.getUnitTest();
        Assert.assertEquals("set successfully", result);
    }
}
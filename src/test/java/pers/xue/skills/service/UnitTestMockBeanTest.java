package pers.xue.skills.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pers.xue.skills.entity.UnitTestEntity;
import pers.xue.skills.repository.UnitTestRepository;

import java.util.Arrays;
import java.util.List;

/**
 * 注意：
 * 1、Mock不需要依赖Spring环境，MockBean是需要Spring容器支持，但是配置环境可以启动test.yml
 * MockBean场景： 可以进行一些简单的大部分的测试，但是如果有些类你不能Mock时，需要用容器生成的类，比如本例子中的
 * UnitTestEntityCheckService，它是一个公共类，可以供很多地方使用，这个时候你需要借助容器中的它去实现，这个时候需要MockBean去Mock一些其它需要Mock的。
 * 2、@RunWith(MockitoJUnitRunner.class) 不能用这个，这个是Mock环境的，不会加载Spring环境
 * 3、启动Spring环境，可以用SpringBootTest、或者可以用Context
 * 4、用ActiveProfiles配置要用的配置类
 * 5、初步测试是MockBean能注入Mock之后的值，说明启动了Spring环境，或者可以通过控制台看到-The following profiles are active: test
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2022-06-09
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UnitTestMockBeanTest {

    @MockBean
    UnitTestRepository unitTestRepository;

    @Autowired
    UnitTestMockBean unitTestMockBean;

    @Test
    public void testGetEligibleUnitTestEntity() {
        List<UnitTestEntity> unitTestEntities = Arrays.asList(new UnitTestEntity(1, "content"));
        Mockito.when(unitTestRepository.findAll()).thenReturn(unitTestEntities);
        List<UnitTestEntity> eligibleUnitTestEntity = unitTestMockBean.getEligibleUnitTestEntity();
        Assert.assertEquals(unitTestEntities, eligibleUnitTestEntity);
    }
}
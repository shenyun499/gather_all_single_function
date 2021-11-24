package pers.xue.skills.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pers.xue.skills.entity.UnitTestEntity;
import pers.xue.skills.repository.UnitTestRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * @author huangzhixue
 * @date 2021/11/24 3:52 下午
 * @Description
 */
@RunWith(MockitoJUnitRunner.class)
public class UnitTestServicesTest {

    @InjectMocks
    private UnitTestServices unitTestServices;
    @Mock
    private UnitTestRepository unitTestRepository;

    @Test
    public void testQueryUnitTestByContent() {
        UnitTestEntity unitTestEntity = new UnitTestEntity(1, "test");
        List<String> list = Arrays.asList("2", "3");
        // spy自己
        UnitTestServices unitTestServicesSpy = spy(unitTestServices);
        // 模拟本类方法
        doReturn(list).when(unitTestServicesSpy).getTestList(any());

        // 模拟行为--unitTestRepository.findAll()，并指定返回自己想要的结果
        Mockito.when(unitTestRepository.findByContent(any())).thenReturn(unitTestEntity);
        // 调用service方法
        Map<UnitTestEntity, List<String>> unitTestEntityListMap = unitTestServicesSpy.queryUnitTestByContent(unitTestEntity, "1", "2");
        // 校验结果
        Assert.assertEquals(list, unitTestEntityListMap.get(unitTestEntity));
    }

}

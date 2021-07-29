package pers.xue.skills.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import pers.xue.skills.entity.UnitTestEntity;
import pers.xue.skills.repository.UnitTestRepository;

import static org.mockito.ArgumentMatchers.any;

/**
 * @auther huangzhixue
 * @data 2021/7/28 3:46 下午
 * @Description
 */
@RunWith(MockitoJUnitRunner.class)
public class UnitTestServiceTest {

    @InjectMocks
    private UnitTestService unitTestService;
    @Mock
    private UnitTestRepository unitTestRepository;

    @Test
    public void testQueryUnitTestByContent() {
        // 模拟行为--unitTestRepository.findAll()，并指定返回自己想要的结果
        Mockito.when(unitTestRepository.findByContent(any())).thenReturn(getUnitTestEntity());
        // 调用service方法
        UnitTestEntity unitTestEntity = unitTestService.queryUnitTestByContent(getUnitTestEntity());
        // 校验结果
        Assert.assertEquals(getUnitTestEntity().getId(), unitTestEntity.getId());
    }

    // 制造一个返回结果
    private UnitTestEntity getUnitTestEntity() {
        return new UnitTestEntity(1, "test");
    }
}
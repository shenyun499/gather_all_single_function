package pers.xue.skills.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pers.xue.skills.entity.UnitTestEntity;
import pers.xue.skills.repository.UnitTestRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * @author huangzhixue
 * @date 2021/11/24 5:30 下午
 * @Description
 */
@RunWith(MockitoJUnitRunner.class)
public class UnitTestBugTest {
    @Mock
    UnitTestRepository unitTestRepository;

    @InjectMocks
    UnitTestBug unitTestBug;

    @Test
    public void testTest3() throws Exception {
        // error
        when(unitTestRepository.findById(anyInt())).thenReturn(Optional.of(new UnitTestEntity(1, "content")));
        // 调用失效，因为anyInt 传了null
        Integer result = unitTestBug.test3(null);
        Assert.assertEquals(null, result);
    }

    @Test
    public void testTest3_solved01() throws Exception {
        // error
        when(unitTestRepository.findById(anyInt())).thenReturn(Optional.of(new UnitTestEntity(1, "content")));
        Integer result = unitTestBug.test3(3);
        Assert.assertEquals(Integer.valueOf(3), result);
    }

    @Test
    public void testTest3_solved02() throws Exception {
        // error
        when(unitTestRepository.findById(any())).thenReturn(Optional.of(new UnitTestEntity(1, "content")));
        Integer result = unitTestBug.test3(null);
        Assert.assertEquals(null, result);
    }
}

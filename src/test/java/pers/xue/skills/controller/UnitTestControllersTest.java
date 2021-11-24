package pers.xue.skills.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import pers.xue.skills.entity.UnitTestEntity;
import pers.xue.skills.remote.req.UnitTestReqDTO;
import pers.xue.skills.remote.rsp.UnitTestRspDTO;
import pers.xue.skills.service.UnitTestService;

import static org.mockito.Mockito.*;

/**
 * @author huangzhixue
 * @date 2021/11/24 3:24 下午
 * @Description
 */
@RunWith(MockitoJUnitRunner.class)
public class UnitTestControllersTest {

    @Mock
    UnitTestService unitTestService;
    @InjectMocks
    UnitTestControllers unitTestControllers;

    UnitTestEntity unitTestEntity = null;
    UnitTestReqDTO unitTestReqDTO = null;
    UnitTestRspDTO unitTestRspDTO = null;
    @Before
    public void init() {
        unitTestEntity = new UnitTestEntity(Integer.valueOf(0), "content");
        unitTestReqDTO = new UnitTestReqDTO();
        unitTestRspDTO = new UnitTestRspDTO();
        unitTestRspDTO.setId(unitTestEntity.getId());
        unitTestEntity.setContent(unitTestEntity.getContent());
    }

    @Test
    public void testUnitTest() throws Exception {
        when(unitTestService.queryUnitTestByContent(any())).thenReturn(unitTestEntity);
        UnitTestRspDTO result = unitTestControllers.unitTest(unitTestReqDTO);
        Assert.assertEquals(unitTestRspDTO.getId(), result.getId());
    }
}

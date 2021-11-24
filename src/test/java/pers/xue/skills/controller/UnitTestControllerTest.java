package pers.xue.skills.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pers.xue.skills.entity.UnitTestEntity;
import pers.xue.skills.service.UnitTestService;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @auther huangzhixue
 * @data 2021/7/29 3:30 下午
 * @Description
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UnitTestController.class)
public class UnitTestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UnitTestService unitTestService;

    @Test
    public void testUnitTest() throws Exception {
        // mock行为
        when(unitTestService.queryUnitTestByContent(any())).thenReturn(getUnitTestEntity());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/unitTest")
                .contentType(MediaType.APPLICATION_JSON) // 参数接收类型，json
                .content(new ObjectMapper().writeValueAsString(getUnitTestEntity())); // 参数内容 json
        mockMvc.perform(requestBuilder).andExpect(status().isOk()); // 响应验证
    }

    // 测试数据
    private UnitTestEntity getUnitTestEntity() {
        return new UnitTestEntity(1, "test");
    }
}
    // get
    /*RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/unitTest")
            .queryParam("param", "value") // 参数内容
            .accept(MediaType.APPLICATION_JSON); // 参数内容 json
        mockMvc.perform(requestBuilder).andExpect(status().isOk()); // 响应验证*/
package pers.xue.skills.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.xue.skills.remote.req.UnitTestReqDTO;
import pers.xue.skills.remote.rsp.UnitTestRspDTO;

/**
 * @auther huangzhixue
 * @data 2021/6/29 3:37 下午
 * @Description
 */
@RestController
public class UnitTestController {

    @PostMapping("/unitTest")
    public UnitTestRspDTO date(@RequestBody UnitTestReqDTO unitTestReqDTO) {
        UnitTestRspDTO unitTestRspDTO = new UnitTestRspDTO();
        unitTestRspDTO.setId(1);
        return unitTestRspDTO;
    }
}

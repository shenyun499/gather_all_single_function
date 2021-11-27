package pers.xue.skills.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.xue.skills.entity.UnitTestEntity;
import pers.xue.skills.remote.req.UnitTestReqDTO;
import pers.xue.skills.remote.rsp.UnitTestRspDTO;
import pers.xue.skills.service.UnitTestService;

/**
 * @auther huangzhixue
 * @data 2021/6/29 3:37 下午
 * @Description
 */
@RestController
public class UnitTestControllers {
    @Autowired
    private UnitTestService unitTestService;

    @PostMapping("/unitTests")
    public UnitTestRspDTO unitTest(@RequestBody UnitTestReqDTO unitTestReqDTO) {
        UnitTestEntity unitTestEntity = unitTestService.queryUnitTestByContent(new UnitTestEntity(unitTestReqDTO.getId(), unitTestReqDTO.getContent()));
        UnitTestRspDTO unitTestRspDTO = new UnitTestRspDTO();
        unitTestRspDTO.setId(unitTestEntity.getId());
        unitTestEntity.setContent(unitTestEntity.getContent());
        return unitTestRspDTO;
    }
}

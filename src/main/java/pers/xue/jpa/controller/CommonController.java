package pers.xue.jpa.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.xue.jpa.remote.req.CommonReqDTO;
import pers.xue.jpa.remote.rsp.CommonRspDTO;
import pers.xue.jpa.service.CommonService;

/**
 * @auther huangzhixue
 * @data 2021/6/29 3:37 下午
 * @Description
 */
@RestController
public class CommonController {
    @Autowired
    private CommonService commonService;

    @PostMapping("/testJpa")
    public CommonRspDTO unitTest(@RequestBody CommonReqDTO commonReqDTO) throws JsonProcessingException {
        return null;
    }

}

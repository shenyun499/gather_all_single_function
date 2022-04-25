package pers.xue.security.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.xue.security.remote.req.CommonReqDTO;
import pers.xue.security.remote.rsp.CommonRspDTO;
import pers.xue.security.service.CommonService;

/**
 * @auther huangzhixue
 * @data 2021/6/29 3:37 下午
 * @Description
 */
@RestController
public class SecurityController {
    @Autowired
    private CommonService commonService;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}

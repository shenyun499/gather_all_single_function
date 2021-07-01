package pers.xue.skills.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.xue.skills.remote.req.DateReqDTO;
import pers.xue.skills.remote.rsp.DateRspDTO;

import java.time.LocalDateTime;

/**
 * @auther huangzhixue
 * @data 2021/6/29 3:37 下午
 * @Description
 */
@RestController
public class DateController {

    @PostMapping("/date")
    public DateRspDTO date(@RequestBody DateReqDTO dateReqDTO) {
        DateRspDTO dateRspDTO = new DateRspDTO();
        dateRspDTO.setCreDateTime(LocalDateTime.now());
        return dateRspDTO;
    }
}

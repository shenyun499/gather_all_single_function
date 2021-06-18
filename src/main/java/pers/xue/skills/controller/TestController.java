package pers.xue.skills.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.xue.skills.config.EnumValidAOP;
import pers.xue.skills.remote.ReqDTO;

import javax.validation.Valid;

@RestController
public class TestController {

    @PostMapping("/validEnumParam")
    public Object validEnumParam(@Valid @RequestBody ReqDTO reqDTO) {
        // ...
        return null;
    }
}

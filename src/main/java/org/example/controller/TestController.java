package org.example.controller;

import org.example.config.EnumValidAOP;
import org.example.remote.ReqDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TestController {

    @EnumValidAOP
    @PostMapping("/validEnumParam")
    public Object validEnumParam(@Valid @RequestBody ReqDTO reqDTO) {
        // ...
        return null;
    }
}

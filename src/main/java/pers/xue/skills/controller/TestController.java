package pers.xue.skills.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.xue.skills.config.EnumValidAOP;
import pers.xue.skills.remote.ReqDTO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Set;

@RestController
public class TestController {
    @Autowired
    private Validator globalValidator;

    @PostMapping("/validEnumParam")
    public Object validEnumParam(@Valid @RequestBody ReqDTO reqDTO, HttpServletRequest request) {
        // ...
        return null;
    }

    @PostMapping("/validEnumParam2")
    public Object validEnumParam2(@RequestBody ReqDTO reqDTO, HttpServletRequest request) {
        // ...编程式校验
        Set<ConstraintViolation<ReqDTO>> validate = globalValidator.validate(reqDTO);
        // 如果校验通过，validate为空；否则，validate包含未校验通过项
        if (validate.isEmpty()) {
            // 校验通过，才会执行业务逻辑处理

        } else {
            for (ConstraintViolation<ReqDTO> userDTOConstraintViolation : validate) {
                // 校验失败，做其它逻辑
                System.out.println(userDTOConstraintViolation);
            }
            return null;
        }
        return null;
    }
}

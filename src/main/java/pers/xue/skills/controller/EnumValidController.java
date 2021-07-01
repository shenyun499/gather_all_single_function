package pers.xue.skills.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.xue.skills.remote.req.EnumValidReqDTO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Set;

@RestController
public class EnumValidController {
    @Autowired
    private Validator globalValidator;

    @PostMapping("/validEnumParam")
    public Object validEnumParam(@Valid @RequestBody EnumValidReqDTO enumValidReqDTO, HttpServletRequest request) {
        // ...
        return null;
    }

    @PostMapping("/validEnumParam2")
    public Object validEnumParam2(@RequestBody EnumValidReqDTO enumValidReqDTO, HttpServletRequest request) {
        // ...编程式校验
        Set<ConstraintViolation<EnumValidReqDTO>> validate = globalValidator.validate(enumValidReqDTO);
        // 如果校验通过，validate为空；否则，validate包含未校验通过项
        if (validate.isEmpty()) {
            // 校验通过，才会执行业务逻辑处理

        } else {
            for (ConstraintViolation<EnumValidReqDTO> userDTOConstraintViolation : validate) {
                // 校验失败，做其它逻辑
                System.out.println(userDTOConstraintViolation);
            }
            return null;
        }
        return null;
    }
}

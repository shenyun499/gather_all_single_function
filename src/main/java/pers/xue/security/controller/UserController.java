package pers.xue.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangzhixue
 * @date 2022/4/24 10:16 下午
 * @Description
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 获取当前用户
     *
     * @param authentication
     * @return
     */
    @RequestMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication) {
        return authentication.getPrincipal();
    }
}

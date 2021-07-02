package pers.xue.skills.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.xue.skills.entity.JpaTest;
import pers.xue.skills.repository.JpaTestRepository;
import pers.xue.skills.service.JpaTestService;

/**
 * @auther huangzhixue
 * @data 2021/7/2 10:21 下午
 * @Description
 */
@RestController
public class JpaTestController {

    @Autowired
    private JpaTestService jpaTestService;

    @Autowired
    private JpaTestRepository jpaTestRepository;

    @PostMapping("/getJpaTestByContent")
    public Object getJpaTestByContent(@RequestBody JpaTest jpaTest) {
        return jpaTestService.queryJpaTestByContent(jpaTest);
    }

    @PostMapping("/createJpaTest")
    public Object createJpaTest(@RequestBody JpaTest jpaTest) {
        return jpaTestRepository.save(jpaTest);
    }
}

package pers.xue.skills.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.xue.skills.entity.JpaTest;
import pers.xue.skills.repository.JpaTestRepository;

/**
 * @auther huangzhixue
 * @data 2021/7/2 10:16 下午
 * @Description
 */
@Service
public class JpaTestService {
    @Autowired
    private JpaTestRepository jpaTestRepository;

    public JpaTest queryJpaTestByContent(JpaTest jpaTest) {
        return jpaTestRepository.findByContent(jpaTest.getContent());
    }
}

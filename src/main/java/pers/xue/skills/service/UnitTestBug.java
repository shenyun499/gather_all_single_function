package pers.xue.skills.service;

import org.springframework.beans.factory.annotation.Autowired;
import pers.xue.skills.entity.UnitTestEntity;
import pers.xue.skills.repository.UnitTestRepository;

/**
 * @author huangzhixue
 * @date 2021/11/24 5:28 下午
 * @Description
 */
public class UnitTestBug {

    @Autowired
    UnitTestRepository unitTestRepository;

    /**
     * 同一个方法，多次调用却失效了，请注意mock时的传参
     * @param param
     * @return
     */
    public Integer test3(Integer param) {
        UnitTestEntity unitTestEntity = unitTestRepository.findById(param).orElseThrow(() -> new RuntimeException("error"));
        return param;
    }

}

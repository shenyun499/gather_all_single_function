package pers.xue.skills.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.xue.skills.entity.UnitTestEntity;
import pers.xue.skills.repository.UnitTestRepository;

/**
 * @auther huangzhixue
 * @data 2021/7/2 10:16 下午
 * @Description
 */
@Service
public class UnitTestService {
    @Autowired
    private UnitTestRepository unitTestRepository;

    public UnitTestEntity queryUnitTestByContent(UnitTestEntity unitTestEntity) {
        return unitTestRepository.findByContent(unitTestEntity.getContent());
    }
}

package pers.xue.skills.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.xue.skills.entity.UnitTestEntity;
import pers.xue.skills.repository.UnitTestRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 业务类
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2022-06-09
 */
@Component
public class UnitTestMockBean {
    @Autowired
    private UnitTestEntityCheckService unitTestEntityCheckService;

    @Autowired
    private UnitTestRepository unitTestRepository;

    public List<UnitTestEntity> getEligibleUnitTestEntity() {
        List<UnitTestEntity> unitTestEntities = unitTestRepository.findAll();
        return unitTestEntities
                .stream()
                .filter(u -> unitTestEntityCheckService.unitTestEntityEligibleChecking(u)).
                collect(Collectors.toList());
    }
}

package pers.xue.skills.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.xue.skills.entity.UnitTestEntity;
import pers.xue.skills.repository.UnitTestRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther huangzhixue
 * @data 2021/7/2 10:16 下午
 * @Description
 */
@Service
public class UnitTestServices {
    @Autowired
    private UnitTestRepository unitTestRepository;

    public Map<UnitTestEntity, List<String>> queryUnitTestByContent(UnitTestEntity unitTestEntity, String... ids) {
        List<String> testList = getTestList(ids);
        UnitTestEntity content = unitTestRepository.findByContent(unitTestEntity.getContent());
        return new HashMap<UnitTestEntity, List<String>>(){{put(content, testList);}};
    }

    // 测试queryUnitTestByContent时， 不希望走这个方法
    public List<String> getTestList(String... ids) {
        return Arrays.asList(ids);
    }
}

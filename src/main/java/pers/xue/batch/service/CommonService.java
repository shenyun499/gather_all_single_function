package pers.xue.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.xue.batch.entity.CommonEntity;
import pers.xue.batch.repository.CommonRepository;

/**
 * @auther huangzhixue
 * @data 2021/7/2 10:16 下午
 * @Description
 */
@Service
public class CommonService {
    @Autowired
    private CommonRepository commonRepository;

    public CommonEntity queryUnitTestByContent(CommonEntity commonEntity) {
        return commonRepository.findByContent(commonEntity.getContent());
    }
}

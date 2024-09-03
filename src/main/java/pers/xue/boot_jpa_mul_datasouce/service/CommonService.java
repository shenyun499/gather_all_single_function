package pers.xue.boot_jpa_mul_datasouce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.xue.boot_jpa_mul_datasouce.config.DataSourceSwitch;
import pers.xue.boot_jpa_mul_datasouce.entity.CommonEntity;
import pers.xue.boot_jpa_mul_datasouce.repository.CommonRepository;

/**
 * @auther huangzhixue
 * @data 2021/7/2 10:16 下午
 * @Description
 */
@Service
public class CommonService {
    @Autowired
    private CommonRepository commonRepository;

    @DataSourceSwitch("secondary")
    public CommonEntity queryUnitTestByContent(String content) {
        return commonRepository.findByContent(content);
    }
}

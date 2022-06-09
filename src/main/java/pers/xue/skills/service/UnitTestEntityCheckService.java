package pers.xue.skills.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.xue.skills.entity.UnitTestEntity;

/**
 * 用来check 一些东西
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2022-06-09
 */
@Component
public class UnitTestEntityCheckService {

    public Boolean unitTestEntityEligibleChecking(UnitTestEntity unitTestEntity) {
        if (unitTestEntity.getContent().length() > 0) {
            // TODO: 2022/6/9  这里假设会拿到unitTestEntity的id去数据库校验，所以还需要注入一个repository
            return true;
        }
        return false;
    }
}

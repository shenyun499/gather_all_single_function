package pers.xue.skills.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pers.xue.skills.entity.CaseDO;

/**
 * @author huangzhixue
 * @date 2021/7/2 10:13 下午
 * @Description
 */
@Repository
public interface CaseRepository extends CrudRepository<CaseDO, Integer> {

    CaseDO findByContent(String content);
}

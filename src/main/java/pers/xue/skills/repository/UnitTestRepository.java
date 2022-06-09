package pers.xue.skills.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pers.xue.skills.entity.UnitTestEntity;

/**
 * @auther huangzhixue
 * @data 2021/7/2 10:13 下午
 * @Description
 */
@Repository
public interface UnitTestRepository extends JpaRepository<UnitTestEntity, Integer> {

    UnitTestEntity findByContent(String content);
}

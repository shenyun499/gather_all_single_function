package pers.xue.skills.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pers.xue.skills.entity.JpaTest;

/**
 * @auther huangzhixue
 * @data 2021/7/2 10:13 下午
 * @Description
 */
@Repository
public interface JpaTestRepository extends CrudRepository<JpaTest, Integer> {

    JpaTest findByContent(String content);
}

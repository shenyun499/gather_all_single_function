package pers.xue.datasource.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pers.xue.datasource.entity.CommonEntity;

/**
 * @auther huangzhixue
 * @data 2021/7/2 10:13 下午
 * @Description
 */
@Repository
public interface CommonRepository extends CrudRepository<CommonEntity, Integer> {

    CommonEntity findByContent(String content);
}

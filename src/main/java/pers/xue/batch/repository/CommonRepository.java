package pers.xue.batch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pers.xue.batch.entity.CommonEntity;

/**
 * @auther huangzhixue
 * @data 2021/7/2 10:13 下午
 * @Description
 */
@Repository
public interface CommonRepository extends JpaRepository<CommonEntity, Integer> {

    CommonEntity findByContent(String content);

    Page<CommonEntity> findByContent(String content, Pageable pageable);
}

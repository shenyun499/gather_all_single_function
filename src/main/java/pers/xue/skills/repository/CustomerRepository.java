package pers.xue.skills.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pers.xue.skills.entity.Customer;

/**
 * @author huangzhixue
 * @date 2022/8/1 16:47
 * @Description
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

}

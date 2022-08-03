package pers.xue.skills.service;

import org.springframework.stereotype.Service;
import pers.xue.skills.entity.Customer;
import pers.xue.skills.repository.CustomerRepository;

/**
 * @author huangzhixue
 * @date 2022/8/1 16:49
 * @Description
 */
@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomer(String customerNumber) {
        Customer customer = customerRepository.findById(customerNumber).orElse(null);
        return customer;
    }
}

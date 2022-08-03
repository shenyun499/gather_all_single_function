package pers.xue.skills.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author huangzhixue
 * @date 2022/8/1 16:40
 * @Description
 */
@Getter
@Setter
@Entity(name = "account")
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "accountNumber")
    private String accountNumber;

    @ManyToOne
    @JoinColumn(name = "customerNumber", referencedColumnName = "customerNumber")
    private Customer customer;
}

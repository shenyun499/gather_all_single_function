package pers.xue.skills.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author huangzhixue
 * @date 2022/8/1 16:38
 * @Description
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "customer")
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "customerNumber")
    private String customerNumber;

    /**
     * 1 对 多关系，一个客户可能有多个账号
     */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;
}

package pers.xue.batch.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @auther huangzhixue
 * @data 2021/7/2 10:08 下午
 * @Description
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "common")
public class CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "content", length = 200)
    private String content;
}

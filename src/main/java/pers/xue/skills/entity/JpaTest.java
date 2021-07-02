package pers.xue.skills.entity;

import javax.persistence.*;

/**
 * @auther huangzhixue
 * @data 2021/7/2 10:08 下午
 * @Description
 */
@Entity
@Table(name = "jpa_test")
public class JpaTest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "content", length = 200)
    private String content;
    // ...


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

package pers.xue.skills.entity;

import javax.persistence.*;

/**
 * @author huangzhixue
 * @date 2021/7/2 10:08 下午
 * @Description
 */
@Entity
@Table(name = "case")
public class CaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "status", length = 200)
    private String status;

    @Column(name = "name")
    private String name;

    @Column(name = "content", length = 200)
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

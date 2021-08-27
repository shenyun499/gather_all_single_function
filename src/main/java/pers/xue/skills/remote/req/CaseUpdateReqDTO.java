package pers.xue.skills.remote.req;

/**
 * @author huangzhixue
 * @date 2021/8/23 5:25 下午
 * @Description
 * 单纯更新信息接口，不是根据事件触发
 */
public class CaseUpdateReqDTO {
    private Integer id;

    private String name;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

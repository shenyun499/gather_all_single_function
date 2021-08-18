package pers.xue.skills.remote.req;

/**
 * @author huangzhixue
 * @date 2021/8/17 2:51 下午
 * @Description
 * 状态机更新请求参数
 */
public class CaseUpdateReqDTO {
    private Integer id;

    private String name;

    private String content;

    private String status;

    private String event;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}

package pers.xue.boot_jpa_mul_datasouce.remote.rsp;

public class CommonRspDTO {

    public CommonRspDTO(String content) {
        this.content = content;
    }

    public CommonRspDTO() {
    }

    private Integer id;

    private String content;

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

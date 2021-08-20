package pers.xue.skills.remote.rsp;

import java.util.List;

/**
 * @author huangzhixue
 * @date 2021/8/20 10:17 上午
 * @Description
 * 全查询，响应信息
 */
public class CaseListRspDTO {
    private List<CaseRspDTO> caseRspDTOList;

    public List<CaseRspDTO> getCaseRspDTOList() {
        return caseRspDTOList;
    }

    public void setCaseRspDTOList(List<CaseRspDTO> caseRspDTOList) {
        this.caseRspDTOList = caseRspDTOList;
    }
}

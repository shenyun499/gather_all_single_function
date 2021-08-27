package pers.xue.skills.config;

/**
 * @author huangzhixue
 * @date 2021/8/20 11:53 上午
 * @Description
 */
public class CaseException extends Exception {

    private CaseConstant caseConstant;

    public CaseException(String message, CaseConstant caseConstant) {
        super(message);
        this.caseConstant = caseConstant;
    }

    public CaseConstant getCaseConstant() {
        return caseConstant;
    }
}

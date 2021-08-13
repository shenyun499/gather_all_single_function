package pers.xue.skills.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.xue.skills.enums.Events;
import pers.xue.skills.enums.States;
import pers.xue.skills.remote.req.CaseReqDTO;
import pers.xue.skills.remote.rsp.CaseRspDTO;
import pers.xue.skills.service.CaseService;

/**
 * @author huangzhixue
 * @date 2021/8/12 10:37 上午
 * @Description
 * 状态机 控制类
 */
@RestController("/case")
public class CaseController {
    @Autowired
    private StateMachine<States, Events> stateMachine;

    @Autowired
    private CaseService caseService;

    @PostMapping("/createCase")
    public CaseRspDTO createCase(@RequestBody CaseReqDTO caseReqDTO) {
        stateMachine.sendEvent(Events.START);
        //stateMachine.sendEvent(Events.WAIT_NO_PARAM);
        //stateMachine.sendEvent(Events.NOTIFY);
        stateMachine.sendEvent(Events.RUN_END);
        return null;
    }

    @PostMapping("/updateCase")
    public CaseRspDTO updateCase(@RequestBody CaseReqDTO caseReqDTO) {
        return null;
    }

    @GetMapping("/testCaseConvert")
    public CaseRspDTO testCaseConvert() {
        stateMachine.sendEvent(Events.START);
        stateMachine.sendEvent(Events.WAIT_NO_PARAM);
        stateMachine.sendEvent(Events.NOTIFY);
        stateMachine.sendEvent(Events.RUN_END);
        return null;
    }
}

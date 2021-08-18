package pers.xue.skills.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import pers.xue.skills.enums.Events;
import pers.xue.skills.enums.States;

/**
 * @author huangzhixue
 * @date 2021/8/17 2:25 下午
 * @Description
 */
@Component
public class StateMachineServiceConfig {
    @Autowired
    private StateMachineService<States, Events> stateMachineService;

    private StateMachine<States, Events> currentStateMachine;


    public synchronized StateMachine<States, Events> getStateMachine(String machineId) throws Exception {
        if (currentStateMachine == null) {
            currentStateMachine = stateMachineService.acquireStateMachine(machineId);
            currentStateMachine.start();
        } else if (!ObjectUtils.nullSafeEquals(currentStateMachine.getId(), machineId)) {
            stateMachineService.releaseStateMachine(currentStateMachine.getId());
            currentStateMachine.stop();
            currentStateMachine = stateMachineService.acquireStateMachine(machineId);
            currentStateMachine.start();
        }
        return currentStateMachine;
    }
}

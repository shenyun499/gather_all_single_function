package pers.xue.skills.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import pers.xue.skills.config.StateMachineServiceConfig;
import pers.xue.skills.entity.CaseDO;
import pers.xue.skills.enums.Events;
import pers.xue.skills.enums.States;
import pers.xue.skills.remote.req.CaseCreateReqDTO;
import pers.xue.skills.remote.req.CaseUpdateReqDTO;
import pers.xue.skills.remote.rsp.CaseRspDTO;
import pers.xue.skills.repository.CaseRepository;

import java.util.Locale;

/**
 * @author huangzhixue
 * @date 22021/8/12 10:45 上午
 * @Description
 *
 */
@Service
public class CaseService {
    private static final Logger logger = LoggerFactory.getLogger(CaseService.class);

    @Autowired
    private StateMachineService<States, Events> stateMachineService;

    @Autowired
    private CaseTransactionService caseTransactionService;

    @Autowired
    private StateMachineServiceConfig stateMachineServiceConfig;

    //private StateMachine<States, Events> currentStateMachine;


    @Autowired
    private CaseRepository caseRepository;

    public CaseDO queryUnitTestByContent(CaseDO caseDO) {
        return caseRepository.findByContent(caseDO.getContent());
    }

    public ResponseEntity<CaseRspDTO> create(CaseCreateReqDTO caseCreateReqDTO) {
        CaseDO caseDO = new CaseDO();
        BeanUtils.copyProperties(caseCreateReqDTO, caseDO);
        caseDO.setStatus(States.NEW.name());
        CaseDO resultCaseDO = caseTransactionService.saveCaseAndStateMachine(caseDO);
        CaseRspDTO caseRspDTO = new CaseRspDTO();
        BeanUtils.copyProperties(resultCaseDO, caseRspDTO);
        return ResponseEntity.ok(caseRspDTO);
    }

    public ResponseEntity<CaseRspDTO> update(CaseUpdateReqDTO caseUpdateReqDTO) {
        Assert.notNull(caseUpdateReqDTO.getId(), "case_id field is not null");
        Assert.notNull(caseUpdateReqDTO.getStatus(), "status field is not null");
        Assert.notNull(caseUpdateReqDTO.getEvent(), "event field is not null");
        CaseDO caseDO = caseRepository.findById(caseUpdateReqDTO.getId()).orElse(null);
        Assert.notNull(caseDO, "case data is not exist");

        BeanUtils.copyProperties(caseUpdateReqDTO, caseDO);
        try {
            StateMachine<States, Events> stateMachine = stateMachineServiceConfig.getStateMachine(String.valueOf(caseDO.getId()));
            if (stateMachine.sendEvent(Events.valueOf(caseUpdateReqDTO.getEvent().toUpperCase(Locale.ROOT)))) {
                // start statemachine
                caseDO = caseRepository.save(caseDO);
            } else {
                logger.info("need to convert states error");
                return ResponseEntity.unprocessableEntity().build();
            }
        } catch (Exception e) {
            logger.error("id: [{}]异常结束", caseDO.getId(), e);
            return ResponseEntity.unprocessableEntity().build();
        }
        CaseRspDTO caseRspDTO = new CaseRspDTO();
        BeanUtils.copyProperties(caseDO, caseRspDTO);
        return ResponseEntity.ok(caseRspDTO);
    }

/*    private synchronized StateMachine<States, Events> getStateMachine(String machineId) throws Exception {
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
    }*/
}

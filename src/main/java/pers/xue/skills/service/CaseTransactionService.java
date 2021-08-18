package pers.xue.skills.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.xue.skills.config.StateMachineServiceConfig;
import pers.xue.skills.entity.CaseDO;
import pers.xue.skills.repository.CaseRepository;

import javax.transaction.Transactional;

/**
 * @author huangzhixue
 * @date 2021/8/18 12:54 下午
 * @Description
 */
@Service
public class CaseTransactionService {
    private static final Logger logger = LoggerFactory.getLogger(CaseTransactionService.class);

    @Autowired
    private StateMachineServiceConfig stateMachineServiceConfig;
    @Autowired
    private CaseRepository caseRepository;

    @Transactional(rollbackOn = Exception.class)
    public CaseDO saveCaseAndStateMachine(CaseDO caseDO) {
        try {
            caseDO = caseRepository.save(caseDO);
            stateMachineServiceConfig.getStateMachine(String.valueOf(caseDO.getId()));
        } catch (Exception e) {
            logger.error("case name: {}, case fail to persist，and the reason：", caseDO.getName(), e);
        }
        return caseDO;
    }
}

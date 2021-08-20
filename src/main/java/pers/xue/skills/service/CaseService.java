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
import pers.xue.skills.remote.rsp.CaseListRspDTO;
import pers.xue.skills.remote.rsp.CaseRspDTO;
import pers.xue.skills.repository.CaseRepository;

import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private CaseRepository caseRepository;

    public ResponseEntity<CaseListRspDTO> list() {
        Iterable<CaseDO> repositoryAll = caseRepository.findAll();
        CaseListRspDTO caseListRspDTO = new CaseListRspDTO();
        List<CaseRspDTO> caseRspDTOList = new ArrayList<>();
        for (CaseDO caseDO : repositoryAll) {
            CaseRspDTO caseRspDTO = new CaseRspDTO();
            BeanUtils.copyProperties(caseDO, caseRspDTO);
            caseRspDTOList.add(caseRspDTO);
        }
        caseListRspDTO.setCaseRspDTOList(caseRspDTOList);
        return ResponseEntity.ok(caseListRspDTO);
    }

    public ResponseEntity<CaseRspDTO> query(Integer id) {
        Assert.notNull(id, "id field is not null");
        CaseDO caseDO = caseRepository.findById(id).orElse(null);
        CaseRspDTO caseRspDTO = null;
        if (!ObjectUtils.isEmpty(caseDO)) {
            caseRspDTO = new CaseRspDTO();
            BeanUtils.copyProperties(caseDO, caseRspDTO);
        }
        return ResponseEntity.ok(caseRspDTO);
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

    public ResponseEntity<CaseRspDTO> delete(Integer id) {
        boolean existsById = caseRepository.existsById(id);
        if (!existsById) {
            logger.error("case id : {} is not exists，delete fail!", id);
            throw new RuntimeException("record is not exists，delete fail!");
        }
        caseRepository.deleteById(id);
        return ResponseEntity.ok(new CaseRspDTO());
    }
}

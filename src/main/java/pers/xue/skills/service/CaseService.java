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
import pers.xue.skills.config.CaseConstant;
import pers.xue.skills.config.CaseException;
import pers.xue.skills.config.StateMachineServiceConfig;
import pers.xue.skills.entity.CaseDO;
import pers.xue.skills.enums.Events;
import pers.xue.skills.enums.States;
import pers.xue.skills.remote.req.CaseCreateReqDTO;
import pers.xue.skills.remote.req.CaseUpdateByEventReqDTO;
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

    public ResponseEntity<CaseRspDTO> query(Integer id) throws CaseException {
        Assert.notNull(id, "id field is not null");
        CaseDO caseDO = caseRepository.findById(id).orElse(null);
        if (ObjectUtils.isEmpty(caseDO)) {
            throw new CaseException("delete record fail!", CaseConstant.RECORD_IS_NOT_EXITST);
        }
        CaseRspDTO caseRspDTO = new CaseRspDTO();
        BeanUtils.copyProperties(caseDO, caseRspDTO);
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
        CaseDO caseDO = caseRepository.findById(caseUpdateReqDTO.getId()).orElse(null);
        Assert.notNull(caseDO, "case data is not exist");

        caseDO.setContent(caseUpdateReqDTO.getContent() != null ? caseUpdateReqDTO.getContent() : caseDO.getContent());
        caseDO.setName(caseUpdateReqDTO.getName() != null ? caseUpdateReqDTO.getName() : caseDO.getName());
        //caseTransactionService.saveCase(caseDO);
        caseDO = caseRepository.save(caseDO);
        CaseRspDTO caseRspDTO = new CaseRspDTO();
        BeanUtils.copyProperties(caseDO, caseRspDTO);
        return ResponseEntity.ok(caseRspDTO);
    }

    public ResponseEntity<CaseRspDTO> delete(Integer id) throws CaseException {
        boolean existsById = caseRepository.existsById(id);
        if (!existsById) {
            logger.error("case id : {} is not exists，delete fail!", id);
            throw new CaseException("delete fail!", CaseConstant.RECORD_IS_NOT_EXITST);
        }
        caseRepository.deleteById(id);
        return ResponseEntity.ok(new CaseRspDTO());
    }

    public ResponseEntity<CaseRspDTO> updateByEvent(CaseUpdateByEventReqDTO caseUpdateByEventReqDTO) throws Exception {
        Assert.notNull(caseUpdateByEventReqDTO.getId(), "case_id field is not null");
        Assert.notNull(caseUpdateByEventReqDTO.getStatus(), "status field is not null");
        Assert.notNull(caseUpdateByEventReqDTO.getEvent(), "event field is not null");
        CaseDO caseDO = caseRepository.findById(caseUpdateByEventReqDTO.getId()).orElse(null);
        Assert.notNull(caseDO, "case data is not exist");

        caseDO.setContent(caseUpdateByEventReqDTO.getContent() != null ? caseUpdateByEventReqDTO.getContent() : caseDO.getContent());
        caseDO.setName(caseUpdateByEventReqDTO.getName() != null ? caseUpdateByEventReqDTO.getName() : caseDO.getName());
        caseDO.setName(caseUpdateByEventReqDTO.getStatus() != null ? caseUpdateByEventReqDTO.getStatus() : caseDO.getStatus());
        try {
            StateMachine<States, Events> stateMachine = stateMachineServiceConfig.getStateMachine(String.valueOf(caseDO.getId()));
            if (stateMachine.sendEvent(Events.valueOf(caseUpdateByEventReqDTO.getEvent().toUpperCase(Locale.ROOT)))) {
                // start statemachine
                caseDO = caseRepository.save(caseDO);
            } else {
                logger.info("convert states error, please check the event is right");
                throw new CaseException("update case fail!", CaseConstant.CONVERT_STATES_ERROR);
            }
        } catch (Exception e) {
            logger.error("id: [{}]异常结束", caseDO.getId(), e);
            return ResponseEntity.unprocessableEntity().build();
        }
        CaseRspDTO caseRspDTO = new CaseRspDTO();
        BeanUtils.copyProperties(caseDO, caseRspDTO);
        return ResponseEntity.ok(caseRspDTO);
    }

}

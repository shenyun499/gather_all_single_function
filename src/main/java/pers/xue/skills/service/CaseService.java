package pers.xue.skills.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.xue.skills.entity.CaseEntity;
import pers.xue.skills.repository.CaseRepository;

/**
 * @author huangzhixue
 * @date 22021/8/12 10:45 上午
 * @Description
 *
 */
@Service
public class CaseService {
    @Autowired
    private CaseRepository caseRepository;

    public CaseEntity queryUnitTestByContent(CaseEntity caseEntity) {
        return caseRepository.findByContent(caseEntity.getContent());
    }
}

package pers.xue.skills.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pers.xue.skills.config.CaseException;
import pers.xue.skills.remote.req.CaseCreateReqDTO;
import pers.xue.skills.remote.req.CaseUpdateByEventReqDTO;
import pers.xue.skills.remote.req.CaseUpdateReqDTO;
import pers.xue.skills.remote.rsp.CaseListRspDTO;
import pers.xue.skills.remote.rsp.CaseRspDTO;
import pers.xue.skills.service.CaseService;

/**
 * @author huangzhixue
 * @date 2021/8/12 10:37 上午
 * @Description
 * 状态机 控制类
 */
@RestController
@RequestMapping("/case")
public class CaseController {

    @Autowired
    private CaseService caseService;

    @GetMapping("/")
    public ResponseEntity<CaseListRspDTO> list() {
        return caseService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaseRspDTO> query(@PathVariable("id") Integer id) throws CaseException {
        return caseService.query(id);
    }

    @PostMapping("/")
    public ResponseEntity<CaseRspDTO> create(@RequestBody CaseCreateReqDTO caseCreateReqDTO) {
        return caseService.create(caseCreateReqDTO);
    }

    @PutMapping("/")
    public ResponseEntity<CaseRspDTO> update(@RequestBody CaseUpdateReqDTO caseUpdateReqDTO) throws Exception {
        return caseService.update(caseUpdateReqDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CaseRspDTO> delete(@PathVariable("id") Integer id) throws CaseException {
        return caseService.delete(id);
    }

    @PutMapping("/updateByEvent")
    public ResponseEntity<CaseRspDTO> updateByEvent(@RequestBody CaseUpdateByEventReqDTO caseUpdateByEventReqDTO) throws Exception {
        return caseService.updateByEvent(caseUpdateByEventReqDTO);
    }
}

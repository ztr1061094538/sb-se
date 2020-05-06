package com.tg.enterprise.controller;

import com.tg.enterprise.biz.impl.AssociatedProcessBizImpl;
import com.tg.enterprise.config.VerifyCommitDate;
import com.tg.enterprise.model.AssociatedProcess;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.vo.ResponseVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: em2-parent
 * @author: jikai.sun
 * @create: 2018-12-06
 * 产品能耗及占比
 **/
@RestController
@RequestMapping("AssociatedProcess")
public class AssociatedProcessController {

    @Autowired
    AssociatedProcessBizImpl associatedProcessBiz;


    @PostMapping("insert")
    @ApiOperation(value = "新增存储产品明细数据", consumes = "application/json", response = ResponseVO.class)
    @Transactional
    @VerifyCommitDate
    public ResponseVO insertAssociatedProcess(@RequestBody AssociatedProcess associatedProcess) {
        if(associatedProcess == null){return new ResponseVO<>(ErrorCode.INNER_ERROR, "传入数据为空");}
        //根据pid  uid 查询list
        List<AssociatedProcess> associatedProcessList = associatedProcessBiz.selectList
                (new AssociatedProcess(associatedProcess.getUid(),associatedProcess.getPid()));
        associatedProcessList.add(associatedProcess);
        if (verfilyAssociate(associatedProcessList)) {
            associatedProcessBiz.insert(associatedProcess);
            return new ResponseVO<>(ErrorCode.OK, "新增成功");
        } else {
            return new ResponseVO<>(ErrorCode.INNER_ERROR, "相同工序的能耗占比不得超过100%");
        }
    }

    @PostMapping("update")
    @ApiOperation(value = "新增存储产品明细数据", consumes = "application/json", response = ResponseVO.class)
    @Transactional
    @VerifyCommitDate
    public ResponseVO updateAssociatedProcess(@RequestBody AssociatedProcess associatedProcess) {
        if(associatedProcess == null){return new ResponseVO<>(ErrorCode.INNER_ERROR, "传入数据为空");}
        List<AssociatedProcess> associatedProcessList = associatedProcessBiz.selectList
                (new AssociatedProcess(associatedProcess.getUid(),associatedProcess.getPid()));
        List<AssociatedProcess> list = new ArrayList<>();
        for (AssociatedProcess process : associatedProcessList) {
            if(process.getId().intValue() != associatedProcess.getId().intValue()) {
                list.add(process);
            }
        }
        list.add(associatedProcess);
        if (verfilyAssociate(list)) {
            associatedProcessBiz.update(associatedProcess);
            return new ResponseVO<>(ErrorCode.OK, "更新");
        } else {
            return new ResponseVO<>(ErrorCode.INNER_ERROR, "相同工序的能耗占比不得超过100%");
        }
    }

    @PostMapping("delete")
    @ApiOperation(value = "存储产品明细数据", consumes = "application/json", response = ResponseVO.class)
    @Transactional
    @VerifyCommitDate
    public ResponseVO deleteAssociatedProcess(@RequestBody String associatId) {
        if (associatId != null) {
            associatedProcessBiz.deleteById(associatId);
            return new ResponseVO<>(ErrorCode.OK, "删除成功");
        } else {
            return new ResponseVO<>(ErrorCode.INNER_ERROR, "传入数据为空");
        }
    }


    @PostMapping("queryList")
    @ApiOperation(value = "能源及占比定义", consumes = "application/json", response = ResponseVO.class)
    public ResponseVO<List<AssociatedProcess>> selectAssociatedProcess(@RequestBody String pid) {
        if (pid != null) {
            AssociatedProcess associatedProcess = new AssociatedProcess();
            associatedProcess.setPid(Integer.valueOf(pid));
            List<AssociatedProcess> associatedProcesses = associatedProcessBiz.selectList(associatedProcess);
            return new ResponseVO<>(ErrorCode.OK, "查询成功", associatedProcesses);
        } else {
            return new ResponseVO<>(ErrorCode.INNER_ERROR, "传入数据为空");
        }
    }

    public boolean verfilyAssociate(List<AssociatedProcess> associatedProcess){
        if(! CollectionUtils.isEmpty(associatedProcess)){
            AtomicInteger atomicInteger = new AtomicInteger(0);
            associatedProcess.stream().forEach(ass ->{
                atomicInteger.addAndGet(ass.getProportion());
            });
            if(atomicInteger.get() > 100){
                return false;
            }
        }
        return true;
    }
}
















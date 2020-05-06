package com.tg.enterprise.controller;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.impl.EnergyProtocolTypeBizImpl;
import com.tg.enterprise.model.EnergyType;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.vo.PageRequestVO;
import com.tg.enterprise.vo.PageResponseVO;
import com.tg.enterprise.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: em2-parent
 * @author: jikai.sun
 * @create: 2018-12-19
 **/

@RestController("/energyProtocol")
@Slf4j
public class EnergyProtocolController {

    @Autowired
    EnergyProtocolTypeBizImpl energyProtocolTypeBiz;

    @PostMapping("query")
    public PageResponseVO<EnergyType> queryList(@RequestBody PageRequestVO<EnergyType> entity){
        PageInfo<EnergyType> pageInfo = energyProtocolTypeBiz.pageQueryList(entity.getParams(), entity.getPageIndex(), entity.getPageSize());
        PageResponseVO<EnergyType> responseVO = new PageResponseVO<>();
        responseVO.setRows(pageInfo.getList());
        responseVO.setTotal(pageInfo.getTotal());
        return responseVO;
    }


    @PostMapping("edit")
    public ResponseVO edit(@RequestBody EnergyType energyProtocolType){
        energyProtocolTypeBiz.update(energyProtocolType);
        return new ResponseVO<>(ErrorCode.OK, "ok");
    }
}

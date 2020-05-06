package com.tg.enterprise.controller;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IEnergyEquipmentContactBiz;
import com.tg.enterprise.biz.IEnterpriseBiz;
import com.tg.enterprise.biz.IProcessCodeBiz;
import com.tg.enterprise.biz.IProcessCodeUnitBiz;
import com.tg.enterprise.model.*;
import com.tg.enterprise.util.Contants;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.util.SessionUtil;
import com.tg.enterprise.vo.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 生产工序表 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author panxinchao
 * @date 2018-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * panxinchao			2018-12-05			新增
 */ 
 
@Slf4j
@RestController
@RequestMapping("/processCode")
public class ProcessCodeController{
	
	@Autowired
    private IProcessCodeBiz processCodeBiz;
	
	@Autowired
    private IProcessCodeUnitBiz processCodeUnitBiz;
	
	@Autowired
	private IEnterpriseBiz enterpriseBiz;
	
	@Autowired
	private IEnergyEquipmentContactBiz contactBiz;
	
	@ApiOperation(value = "生产工序表单条查询", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/selectById", consumes = "application/json", method = RequestMethod.POST)
    public ResponseVO<ProcessCode> selectById(@RequestBody Long id) {
        ProcessCode processCode = new ProcessCode();
        try {
            processCode = processCodeBiz.selectById(id);
        } catch (Exception e) {
            log.error("selectById", e);
            return  new ResponseVO<ProcessCode>(ErrorCode.BG_GETDATA_ERROR, Contants.ERROR_GET);
        }
        return new ResponseVO<ProcessCode>(ErrorCode.OK, "ok", processCode);
    }


    @ApiOperation(value = "生产工序表条件分页列表", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/selectForPage", consumes = "application/json", method = RequestMethod.POST)
    public PageResponseVO<ProcessCode> selectForPage(@RequestBody PageRequestVO<ProcessCode> processCode, HttpServletRequest request) {
        PageResponseVO<ProcessCode> ResponseVO = new PageResponseVO<ProcessCode>(ErrorCode.OK, "ok");
		User userVO = SessionUtil.getUser(request.getSession());
		processCode.getParams().setCompanyId(userVO.getCid());
        PageInfo<ProcessCode> queryPageList = processCodeBiz.selectForPage(processCode.getParams(), processCode.getPageIndex(), processCode.getPageSize());
        ResponseVO.setRows(queryPageList.getList());
        ResponseVO.setTotal(queryPageList.getTotal());
        return ResponseVO;
    }


    @ApiOperation(value = "生产工序表新增接口", response = CommonResponse.class, httpMethod = "POST")
    @RequestMapping(value = "/insert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
//    @RecordChange
    public CommonResponse add(@RequestBody ProcessCode processCode, HttpServletRequest request) {
        try {
    		User userVO = SessionUtil.getUser(request.getSession());
    		processCode.setCompanyId(userVO.getCid());
            Enterprise enterprise = enterpriseBiz.selectById(userVO.getCid());
    		processCode.setIndustryCode(enterprise.getIndustry_code());
    		ProcessCode selectByCode = processCodeBiz.selectByCode(processCode.getCode(),userVO.getCid());
    		if (selectByCode!= null) {
				return new CommonResponse(ErrorCode.BG_DATABASE_ERROR,"工序编码已存在");
			}
            processCodeBiz.insert(processCode);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }


    @ApiOperation(value = "生产工序表更新接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/update", consumes = "application/json", method = RequestMethod.POST)
//    @RecordChange
    public CommonResponse update(@RequestBody ProcessCode processCodeQueryVO, HttpServletRequest request) {
        try {
            ProcessCode processCode = processCodeBiz.selectById(processCodeQueryVO.getId());
            if(processCode == null){
                return new CommonResponse(ErrorCode.BG_DATABASE_ERROR,"生产工序不存在");
            }
            //生产单元修改
            ProcessCodeUnit processCodeUnit2 = new ProcessCodeUnit();
            processCodeUnit2.setCid(processCodeQueryVO.getId());
            List<ProcessCodeUnit> selectList = processCodeUnitBiz.selectList(processCodeUnit2);
            if("00".equals(processCode.getCode()) && selectList.size() > 0){
                return new CommonResponse(ErrorCode.BG_DATABASE_ERROR,"全厂下面没有自定义生产单元，请先删除");
            }
            processCodeBiz.update(processCodeQueryVO);
            //修改采集项
            User userVO = SessionUtil.getUser(request.getSession());
            Enterprise enterprise = enterpriseBiz.selectById(userVO.getCid());
            EnergyEquipmentContact energyEquipmentContactQueryVO = new EnergyEquipmentContact();
            energyEquipmentContactQueryVO.setCompanyId(enterprise.getId());
            energyEquipmentContactQueryVO.setCode(processCode.getCode());
            List<EnergyEquipmentContact> energyEquipmentContacts = contactBiz.selectList(energyEquipmentContactQueryVO);
            for (EnergyEquipmentContact energyEquipmentContact:
                    energyEquipmentContacts) {
                energyEquipmentContact.setCode(processCodeQueryVO.getCode());
                contactBiz.update(energyEquipmentContact);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }
	
	@ApiOperation(value = "生产工序表批量删除接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/delByIds", consumes = "application/json", method = RequestMethod.POST)
//	@RecordChange
    public CommonResponse delByIds(@RequestBody List<Long> ids, HttpServletRequest request) {
        try {
        	List<Long> idList = new ArrayList<>();
        	ProcessCodeUnit unit = new ProcessCodeUnit();
        	EnergyEquipmentContact energyEquipmentContact = new EnergyEquipmentContact();
            User user = SessionUtil.getUser(request.getSession());
            for (Long long1 : ids) {
        		ProcessCode selectById2 = processCodeBiz.selectById(long1);
                if(user.getCid().intValue() != selectById2.getCompanyId().intValue()) {
                    return new CommonResponse(ErrorCode.INVALID_PARAM, "参数错误");
                }
        		//生产工序code
        		unit.setCid(selectById2.getId());
        		List<ProcessCodeUnit> selectList = processCodeUnitBiz.selectList(unit);
        		energyEquipmentContact.setCode(selectById2.getCode());
        		energyEquipmentContact.setCompanyId(selectById2.getCompanyId());
        		List<EnergyEquipmentContact> selectList2 = contactBiz.selectList(energyEquipmentContact);
        		if (!selectList.isEmpty()||!selectList2.isEmpty()) {
                    return new CommonResponse(ErrorCode.BG_DATABASE_ERROR,"该工序下存在工序单元或用能设备,无法删除");
        		}else if (selectList.isEmpty()&&selectList2.isEmpty()) {
        			idList.add(long1);
				}
        	}
            processCodeBiz.delByIds(idList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }
}
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 生产工序单元表 VO
 * @Copyright 苏州太谷电力股份有限公司
 * @author panxinchao
 * @date 2018-12-05
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * panxinchao			2018-12-05			新增
 */ 
 
@Slf4j
@RestController
@RequestMapping("/processCodeUnit")
public class ProcessCodeUnitController{
	
	@Autowired
    private IProcessCodeUnitBiz processCodeUnitBiz;

	@Autowired
    private IProcessCodeBiz processCodeBiz;

	@Autowired
	private IEnterpriseBiz enterpriseBiz;
	
	@Autowired
	private IEnergyEquipmentContactBiz contactBiz;
	
	
	@ApiOperation(value = "生产工序单元表单条查询", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/selectById", consumes = "application/json", method = RequestMethod.POST)
    public ResponseVO<ProcessCodeUnit> selectById(@RequestBody Long id) {
        ProcessCodeUnit processCodeUnit = new ProcessCodeUnit();
        try {
            processCodeUnit = processCodeUnitBiz.selectById(id);
        } catch (Exception e) {
            log.error("selectById", e);
            return  new ResponseVO<ProcessCodeUnit>(ErrorCode.BG_GETDATA_ERROR, Contants.ERROR_GET);
        }
        return new ResponseVO<ProcessCodeUnit>(ErrorCode.OK, "ok", processCodeUnit);
    }


    @ApiOperation(value = "生产工序单元表条件分页列表", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/selectForPage", consumes = "application/json", method = RequestMethod.POST)
    public PageResponseVO<ProcessCodeUnit> selectForPage(@RequestBody PageRequestVO<ProcessCodeUnit> processCodeUnit, HttpServletRequest request) {
        PageResponseVO<ProcessCodeUnit> ResponseVO = new PageResponseVO<ProcessCodeUnit>(ErrorCode.OK, "ok");
        try {
    		User userVO = SessionUtil.getUser(request.getSession());
    		processCodeUnit.getParams().setCompanyId(userVO.getCid());
            PageInfo<ProcessCodeUnit> queryPageList = processCodeUnitBiz.selectForPage(processCodeUnit.getParams(), processCodeUnit.getPageIndex(), processCodeUnit.getPageSize());
            ResponseVO.setRows(queryPageList.getList());
            ResponseVO.setTotal(queryPageList.getTotal());
		} catch (Exception e) {
            log.error(e.getMessage(), e);
		}

        return ResponseVO;
    }


    @ApiOperation(value = "生产工序单元表新增接口", response = CommonResponse.class, httpMethod = "POST")
    @RequestMapping(value = "/insert", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public CommonResponse add(@RequestBody ProcessCodeUnit processCodeUnit, HttpServletRequest request) {
        try {
            ProcessCode processCode = processCodeBiz.selectById(processCodeUnit.getCid());
            if(processCode == null){
                return new CommonResponse(ErrorCode.BG_DATABASE_ERROR,"上级生产工序不存在");
            }
            if("00".equals(processCode.getCode())){
                return new CommonResponse(ErrorCode.BG_DATABASE_ERROR,"全厂下面没有工序单元");
            }
            User userVO = SessionUtil.getUser(request.getSession());
            processCodeUnit.setCompanyId(userVO.getCid());
            Enterprise enterprise = enterpriseBiz.selectById(userVO.getCid());
    		processCodeUnit.setIndustryCode(enterprise.getIndustry_code());
    		ProcessCodeUnit processCodeUnit2 = new ProcessCodeUnit();
    		processCodeUnit2.setCodeUnit(processCodeUnit.getCodeUnit());
    		processCodeUnit2.setCid(processCodeUnit.getCid());
    		List<ProcessCodeUnit> selectList = processCodeUnitBiz.selectList(processCodeUnit2);
    		if (selectList.size() != 0) {
                return new CommonResponse(ErrorCode.BG_DATABASE_ERROR,"工序单元编码已存在");
			}
    		if ("00".equals(processCodeUnit.getCodeUnit())) {
                return new CommonResponse(ErrorCode.BG_DATABASE_ERROR,"‘00’ 是全部，请添加其他的编码");
			}

            processCodeUnitBiz.insert(processCodeUnit);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }


    @ApiOperation(value = "生产工序单元表更新接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/update", consumes = "application/json", method = RequestMethod.POST)
//    @RecordChange
    @Transactional
    public CommonResponse update(@RequestBody ProcessCodeUnit processCodeUnitQueryVO, HttpServletRequest request) {
        try {
            ProcessCode processCode = processCodeBiz.selectById(processCodeUnitQueryVO.getCid());
            if(processCode == null){
                return new CommonResponse(ErrorCode.BG_DATABASE_ERROR,"上级生产工序不存在");
            }
            ProcessCodeUnit processCodeUnit = processCodeUnitBiz.selectById(processCodeUnitQueryVO.getId());
            if (processCodeUnit == null) {
                return new CommonResponse(ErrorCode.BG_DATABASE_ERROR,"工序单元不存在");
            }
            User userVO = SessionUtil.getUser(request.getSession());
            processCodeUnitQueryVO.setCompanyId(userVO.getCid());
            Enterprise enterprise = enterpriseBiz.selectById(userVO.getCid());
            ProcessCodeUnit processCodeUnit2 = new ProcessCodeUnit();
            processCodeUnit2.setCodeUnit(processCodeUnitQueryVO.getCodeUnit());
            processCodeUnit2.setCid(processCodeUnitQueryVO.getCid());
            List<ProcessCodeUnit> selectList = processCodeUnitBiz.selectList(processCodeUnit2);
            if (selectList.size() != 0  && !processCodeUnitQueryVO.getId().equals(selectList.get(0).getId())) {
                return new CommonResponse(ErrorCode.BG_DATABASE_ERROR,"工序单元编码已存在");
            }
            if ("00".equals(processCodeUnitQueryVO.getCodeUnit())) {
                return new CommonResponse(ErrorCode.BG_DATABASE_ERROR,"‘00’ 是全部，请添加其他的编码");
            }
            //修改采集项
            EnergyEquipmentContact energyEquipmentContactQueryVO = new EnergyEquipmentContact();
            energyEquipmentContactQueryVO.setCompanyId(enterprise.getId());
            energyEquipmentContactQueryVO.setCode(processCode.getCode());
            energyEquipmentContactQueryVO.setCode_unit(processCodeUnit.getCodeUnit());
            List<EnergyEquipmentContact> energyEquipmentContacts = contactBiz.selectList(energyEquipmentContactQueryVO);
            processCodeUnitBiz.update(processCodeUnitQueryVO);
            for (EnergyEquipmentContact energyEquipmentContact:
            energyEquipmentContacts) {
                energyEquipmentContact.setCode_unit(processCodeUnitQueryVO.getCodeUnit());
                contactBiz.update(energyEquipmentContact);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }
	
	@ApiOperation(value = "生产工序单元表批量删除接口", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/delByIds", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse delByIds(@RequestBody List<Long> ids) {
        try {
        	List<Long> idList = new ArrayList<>();
        	EnergyEquipmentContact energyEquipmentContact = new EnergyEquipmentContact();
        	for (Long long1 : ids) {
            	energyEquipmentContact.setCode_unit(String.valueOf(long1));
        		List<EnergyEquipmentContact> selectList = contactBiz.selectList(energyEquipmentContact);
        		if (!selectList.isEmpty()) {
                    return new CommonResponse(ErrorCode.BG_DATABASE_ERROR,"该工序单元下存在用能设备");
				}else {
					idList.add(long1);
				}
			}
            processCodeUnitBiz.delByIds(idList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }
	
}
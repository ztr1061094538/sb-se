package com.tg.enterprise.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IEnergyEquipmentBiz;
import com.tg.enterprise.model.EnterpriseEnergyEquipment;
import com.tg.enterprise.model.User;
import com.tg.enterprise.util.Contants;
import com.tg.enterprise.util.DateUtil;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.util.SessionUtil;
import com.tg.enterprise.vo.*;
import com.tg.lygem2.constant.UserTypeEnum;
import com.tg.lygem2.vo.Result;
import com.tg.lygem2.vo.crud.ExposeUser;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/energyEquipment/")
@Slf4j
public class EnergyEquipmentController {
	@Autowired
	private IEnergyEquipmentBiz energyEquipmentBiz;

    @Autowired
    private ThreadLocal<Result<Object>> threadLocal;
	
    @ApiOperation(value = "新增企业用能设备", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/add", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse add(@RequestBody EnterpriseEnergyEquipmentVO energyEquipmentVO, HttpServletRequest request) {
    	try {
            ExposeUser user = JSON.parseObject(threadLocal.get().getMsg(), ExposeUser.class);
            EnterpriseEnergyEquipment energyEquipment = new EnterpriseEnergyEquipment();
            PropertyUtils.copyProperties(energyEquipment,energyEquipmentVO);
            energyEquipment.setStart_year(DateUtil.getLongTime(energyEquipmentVO.getStart_year1()));
            energyEquipment.setEnterprise_id(Integer.valueOf(user.getEnterprise_id()));
        	energyEquipmentBiz.add(energyEquipment);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
		}
		return new CommonResponse(ErrorCode.OK, "ok");
    }
    
    @ApiOperation(value = "获取详情", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/getDetailById", consumes = "application/json", method = RequestMethod.POST)
    public ResponseVO<EnterpriseEnergyEquipment> getDetailById(@RequestBody int id){
		EnterpriseEnergyEquipment enterpriseEnergyEquipment = new EnterpriseEnergyEquipment();
		try {
	        enterpriseEnergyEquipment = energyEquipmentBiz.selectById(id);
		} catch (Exception e) {
			return new ResponseVO<>(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
		}
		return new ResponseVO<EnterpriseEnergyEquipment>(ErrorCode.OK, "ok",enterpriseEnergyEquipment);
    	
    }
    
	@ApiOperation(value = "修改企业用能设备", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/update", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse update(@RequestBody EnterpriseEnergyEquipmentVO energyEquipmentVO) {
        try {
        	EnterpriseEnergyEquipment energyEquipment = new EnterpriseEnergyEquipment();
            PropertyUtils.copyProperties(energyEquipment,energyEquipmentVO);
        	EnterpriseEnergyEquipment selectById = energyEquipmentBiz.selectById(energyEquipment.getId());
        	if (null == selectById) {
        		return new CommonResponse(ErrorCode.INVALID_PARAM, "该记录已经删除");
			}
            energyEquipment.setStart_year(DateUtil.getLongTime(energyEquipmentVO.getStart_year1()));
            energyEquipmentBiz.update(energyEquipment);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }
	
	@ApiOperation(value = "删除企业用能设备", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/delByIds", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse delByIds(@RequestBody List<Integer> ids) {
        try {
        	energyEquipmentBiz.delByIds(ids);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }
	
	@ApiOperation(value = "获取企业用能设备列表", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/queryPageList", consumes = "application/json", method = RequestMethod.POST)
	public PageResponseVO<EnterpriseEnergyEquipmentVO> queryPageList(@RequestBody PageRequestVO<EnterpriseEnergyEquipmentVO> data, HttpServletRequest request){
        PageResponseVO<EnterpriseEnergyEquipmentVO> responseVO = new PageResponseVO<EnterpriseEnergyEquipmentVO>(ErrorCode.OK, "ok");
        try{
            ExposeUser user = JSON.parseObject(threadLocal.get().getMsg(), ExposeUser.class);
            if(user.getUser_type() == UserTypeEnum.company.getType() || user.getUser_type() == UserTypeEnum.company_read.getType()) {
                data.getParams().setEnterprise_id(Integer.valueOf(user.getEnterprise_id()));
            }
            PageInfo<EnterpriseEnergyEquipment> page = energyEquipmentBiz.queryPageList(data.getParams(),
                    data.getPageIndex(), data.getPageSize());
            List<EnterpriseEnergyEquipmentVO> voList = new ArrayList<EnterpriseEnergyEquipmentVO>();
            if (page.getTotal() > 0) {
                for (EnterpriseEnergyEquipment energyEquipment : page.getList()) {
                    EnterpriseEnergyEquipmentVO vo = new EnterpriseEnergyEquipmentVO();
                    PropertyUtils.copyProperties(vo,energyEquipment);
                    if (energyEquipment.getStart_year() == null) {
                    	vo.setStart_year1(null);
					}
                    vo.setStart_year1(DateUtil.getDate(energyEquipment.getStart_year()));
                    if (energyEquipment.getEquipment_picture() == null|| energyEquipment.getEquipment_picture().length() <=0) {
                    	vo.setEquipment_picture1("");
					}else {
						vo.setEquipment_picture1("/oss/link/"+energyEquipment.getEquipment_picture());
					}
                    Integer selectSumbyEid = energyEquipmentBiz.selectSumbyEid();
                    if (selectSumbyEid == null) {
                    	responseVO.setNum(0);
					}else {
						responseVO.setNum(selectSumbyEid);
					}
                    voList.add(vo);
                }
            }
            responseVO.setRows(voList);
            responseVO.setTotal(page.getTotal());
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new PageResponseVO<EnterpriseEnergyEquipmentVO>(ErrorCode.BG_GETDATA_ERROR,Contants.ERROR_GET);
        }
        return responseVO;
	}

    public static void main(String[] args) {
        int i = Integer.parseInt("0001111", 2);
        int j = i & 15;
        System.out.println(i);
        System.out.println(j);
    }
}

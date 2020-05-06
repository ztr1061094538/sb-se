package com.tg.enterprise.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.*;
import com.tg.enterprise.model.EntType;
import com.tg.enterprise.model.Enterprise;
import com.tg.enterprise.model.Industry;
import com.tg.enterprise.model.User;
import com.tg.enterprise.util.*;
import com.tg.enterprise.vo.*;
import com.tg.lygem2.adapt.VerifySecurity;
import com.tg.lygem2.vo.Result;
import com.tg.lygem2.vo.crud.ExposeUser;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */
@RestController
@RequestMapping("/company/")
@Slf4j
public class EnterpriseController
{
	@Resource
    private IEnterpriseBiz biz;
	
    @Autowired
    IAreaBiz areaBizImpl;
    @Autowired
    private IIndustryBiz industryBiz;
    @Autowired
    private IEntTypeBiz entTypeBiz;

    @Autowired
    private ThreadLocal<Result<Object>> threadLocal;

    @VerifySecurity
    @ApiOperation(value = "根据id查询企业", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/getOwnEnterprise", consumes = "application/json", method = RequestMethod.POST)
    public ResponseVO<EnterpriseVO> getOwnEnterprise(HttpServletRequest request) {
        EnterpriseVO vo = new EnterpriseVO();
        try {
            ExposeUser user = JSON.parseObject(threadLocal.get().getMsg(), ExposeUser.class);
            Integer companyId = Integer.parseInt(user.getEnterprise_id());
            Enterprise m = biz.selectById(companyId);
            PropertyUtils.copyProperties(vo, m);
            Industry industry= industryBiz.selectById(m.getIndustry_code());
            String getIndustry = null;
            String getIndustryCode1 = null;
            String getIndustryCode2 = null;
            if(industry != null){
                getIndustry = industry.getFullName();
                getIndustryCode2 = industry.getPcode();
                Industry industry2= industryBiz.selectById(industry.getPcode());
                if(industry2 != null){
                    getIndustryCode1 = industry2.getPcode();
                }
            }
            String getScale = SysDictUtil.getCodeNode(m.getEnergy_scale());
            String getType = entTypeBiz.selectNameById(m.getType_code());
            if (m.getManage_code()!= null) {
                String getManage = SysDictUtil.getCodeNode(m.getManage_code());
                vo.setManage_name(getManage);
            }
            List<String> industryCodeArr = new ArrayList<>();
            industryCodeArr.add(getIndustryCode1);
            industryCodeArr.add(getIndustryCode2);
            industryCodeArr.add(m.getIndustry_code());
            CodeVO district =  SysDictUtil.getAreaByCode(m.getArea());
            if(district!=null){
                vo.setArea(district.getName());
            }
            vo.setIndustry_name(getIndustry);
            vo.setScale_name(getScale);
            vo.setType_name(getType);
            vo.setIndustryCodeArr(industryCodeArr);
            vo.setPass_date1(DateUtil.getDate(m.getPass_date()));
            vo.setRegister_date1(DateUtil.getDate(m.getRegister_date()));
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return  new ResponseVO<EnterpriseVO>(ErrorCode.BG_GETDATA_ERROR, Contants.ERROR_GET);
        }
        return new ResponseVO<EnterpriseVO>(ErrorCode.OK, "ok", vo);
    }
    
    @ApiOperation(value = "根据id查询企业", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/getDetailById", consumes = "application/json", method = RequestMethod.POST)
    public ResponseVO<EnterpriseVO> getDetailById(@RequestBody Integer id) {
        EnterpriseVO vo = new EnterpriseVO();
        try {
            Enterprise m = biz.selectById(id);
            PropertyUtils.copyProperties(vo, m);
            vo.setPass_date1(DateUtil.getDate(m.getPass_date()));
            vo.setRegister_date1(DateUtil.getDate(m.getRegister_date()));
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return  new ResponseVO<EnterpriseVO>(ErrorCode.BG_GETDATA_ERROR, Contants.ERROR_GET);
        }
        return new ResponseVO<EnterpriseVO>(ErrorCode.OK, "ok", vo);
    }

    @ApiOperation(value = "新增企业", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/add", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse add(@RequestBody EnterpriseVO entity) {
        try {
            Enterprise m = new Enterprise();
            PropertyUtils.copyProperties(m,entity);
            m.setPass_date(DateUtil.getLongTime(entity.getPass_date1()));
            m.setRegister_date(DateUtil.getLongTime(entity.getRegister_date1()));
            if(biz.add(m)==-1) {
            	return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, "统一社会信用代码已存在");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }

    @ApiOperation(value = "获取企业列表", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/getList", consumes = "application/json", method = RequestMethod.POST)
    public PageResponseVO<EnterpriseVO> getList(@RequestBody PageRequestVO<EnterpriseQueryVO> data, HttpServletRequest request) {
        PageResponseVO<EnterpriseVO> responseVO = new PageResponseVO<EnterpriseVO>(ErrorCode.OK, "ok");
        try{
            PageInfo<Enterprise> page = biz.queryPageList(data.getParams(),
                    data.getPageIndex(), data.getPageSize());
            List<EnterpriseVO> voList = new ArrayList<EnterpriseVO>();
            List<String> filenames = new ArrayList<>();
            for (Enterprise m : page.getList()) 
            {
            	if(StringUtils.isNotBlank(m.getEnterprise_data())){
					filenames.add(m.getEnterprise_data());
				}
            }
            if (page.getTotal() > 0) {
                for (Enterprise m : page.getList()) {
                    EnterpriseVO vo = new EnterpriseVO();
                    PropertyUtils.copyProperties(vo, m);
                    String getIndustry = SysDictUtil.getCodeNode(m.getIndustry_code());
                    String getScale = SysDictUtil.getCodeNode(m.getEnergy_scale());
                    String getType = SysDictUtil.getCodeNode(m.getType_code());
                    if (m.getManage_code()!= null) {
                        String getManage = SysDictUtil.getCodeNode(m.getManage_code());
                        vo.setManage_name(getManage);
					}
                    CodeVO district =  SysDictUtil.getAreaByCode(m.getArea());
                    if(district!=null){
                        vo.setArea(district.getName());
                    }
                    vo.setIndustry_name(getIndustry);
                    vo.setScale_name(getScale);
                    vo.setType_name(getType);
                    vo.setPass_date1(DateUtil.getDate(m.getPass_date()));
                    vo.setRegister_date1(DateUtil.getDate(m.getRegister_date()));
                    voList.add(vo);
                }
            }
            responseVO.setRows(voList);
            responseVO.setTotal(page.getTotal());
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new PageResponseVO<EnterpriseVO>(ErrorCode.BG_GETDATA_ERROR,Contants.ERROR_GET);
        }
        return responseVO;
    }
    
    @ApiOperation(value = "修改企业", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/update", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse update(@RequestBody EnterpriseVO vo) {
        try {
            Enterprise entity = new Enterprise();
            PropertyUtils.copyProperties(entity, vo);
            Enterprise selectById = biz.selectById(entity.getId());
            if (vo.getIndustryCodeArr() == null || vo.getIndustryCodeArr().size() < 3) {
                return new CommonResponse(ErrorCode.INVALID_PARAM, "需要选择到三级子行业");
            }
            if (null == selectById) {
            	return new CommonResponse(ErrorCode.INVALID_PARAM, "该记录已经删除");
			}
            entity.setIndustry_code(vo.getIndustryCodeArr().get(2));
            entity.setPass_date(DateUtil.getLongTime(vo.getPass_date1()));
            entity.setRegister_date(DateUtil.getLongTime(vo.getRegister_date1()));
            biz.update(entity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }

    @ApiOperation(value = "删除企业", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/delByIds", consumes = "application/json", method = RequestMethod.POST)
    public CommonResponse delByIds(@RequestBody List<Integer> ids) {
        try {
            biz.delByIds(ids);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }
}

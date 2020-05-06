package com.tg.enterprise.controller;

import com.tg.enterprise.biz.IEnergyProcessTargetBiz;
import com.tg.enterprise.biz.IEnergyProtocolTypeBiz;
import com.tg.enterprise.config.VerifyLastMonth;
import com.tg.enterprise.model.EnergyEquipment;
import com.tg.enterprise.model.EnergyProcessTarget;
import com.tg.enterprise.model.EnergyType;
import com.tg.enterprise.model.User;
import com.tg.enterprise.util.Contants;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.util.SessionUtil;
import com.tg.enterprise.vo.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 重点工序能源消费指标
 */
@RestController
@RequestMapping("/energyProcessTarget")
@Slf4j
public class EnergyProcessTargetController {

    @Resource
    private IEnergyProcessTargetBiz biz;
    @Resource
    private IEnergyProtocolTypeBiz energyType;

    private static final String INPUT_TYPE_SGTB = "5";

    @ApiOperation(value = "获取重点工序指标", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/getList", consumes = "application/json", method = RequestMethod.POST)
    public PageResponseVO<EnergyProcessVO> getList(@RequestBody PageRequestVO<EnergyProcessVO> data, HttpServletRequest request) {
        PageResponseVO<EnergyProcessVO> responseVO = new PageResponseVO<>(ErrorCode.OK, "ok");
        try{
            EnergyProcessVO param = data.getParams();
            EnergyEquipment equitQueryVO = new EnergyEquipment();
            EnergyProcessTarget processTarget = new EnergyProcessTarget();
            Integer enterpriseId = param.getCompanyId();
            if(enterpriseId == null){
                User userVO = SessionUtil.getUser(request.getSession());
                enterpriseId =userVO.getCid();
            }
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH,-1);
            Date date = cal.getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
            Long month = Long.valueOf(format.format(date));
            param.setUpload_date(month);

            processTarget.setEnterprise_id(enterpriseId);
            processTarget.setUpload_date(param.getUpload_date());
            equitQueryVO.setCompanyId(enterpriseId);
            equitQueryVO.setInputType(INPUT_TYPE_SGTB);//只查询手工填报的
            List<EnergyEquipment> page = biz.queryEquitList(equitQueryVO);
            List<EnergyProcessVO> list = new ArrayList<>();
            List<EnergyProcessTarget> processList = biz.queryList(processTarget);
            Map<Long ,EnergyProcessTarget> map = new HashMap<>();
            for (EnergyProcessTarget target:processList ) {
                map.put(target.getProcess_id(),target);
            }
            List<EnergyType> protocolTypes = energyType.queryList(null);
            Map<String, EnergyType> protocolMap = new HashMap<>();
            for(EnergyType type:protocolTypes){
                protocolMap.put(type.getCode(),type);
            }
            int num=1;
            for (EnergyEquipment enity:page) {
                EnergyProcessVO processVO = new EnergyProcessVO();
                processVO.setCompanyId(enity.getCompanyId());
                processVO.setProcess_id(enity.getId());
                processVO.setName(enity.getName());
                if(map.containsKey(enity.getId())){
                    processVO.setData_value(map.get(enity.getId()).getData_value());
                    processVO.setRemark(map.get(enity.getId()).getRemark());
                }
                String energyType = enity.getCategory();
                processVO.setEnergyTypeName( protocolMap.get(energyType).getName());
                processVO.setUnit(protocolMap.get(energyType).getUnit());
                processVO.setUpload_date(param.getUpload_date());
                processVO.setId(num++);
                list.add(processVO);
            }
                responseVO.setRows(list);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new PageResponseVO<EnergyProcessVO>(ErrorCode.BG_GETDATA_ERROR, Contants.ERROR_GET);
        }
        return responseVO;
    }

    @ApiOperation(value = "修改重点工序指标", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/update", consumes = "application/json", method = RequestMethod.POST)
    @VerifyLastMonth
    public CommonResponse update(@RequestBody EnergyProcessVO entity, HttpServletRequest request) {
        Integer enterpriseId = entity.getCompanyId();
        if(enterpriseId == null){
            User userVO = SessionUtil.getUser(request.getSession());
            enterpriseId =userVO.getCid();
        }
        EnergyProcessTarget target = biz.selectByProcess(entity);
        if(target==null){
            EnergyProcessTarget temp = new EnergyProcessTarget();
            temp.setUpload_date(entity.getUpload_date());
            temp.setData_value(entity.getData_value());
            temp.setEnterprise_id(enterpriseId);
            temp.setRemark(entity.getRemark());
            temp.setProcess_id(entity.getProcess_id());
            biz.add(temp);
        }else{
            entity.setCompanyId(enterpriseId);
            biz.updateProcess(entity);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }

}

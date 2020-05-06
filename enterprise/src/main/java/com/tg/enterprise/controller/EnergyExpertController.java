package com.tg.enterprise.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.IEnergyExpertBiz;
import com.tg.enterprise.model.EnergyExpert;
import com.tg.enterprise.util.Contants;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.vo.CommonResponse;
import com.tg.enterprise.vo.PageRequestVO;
import com.tg.enterprise.vo.PageResponseVO;
import com.tg.enterprise.vo.ResponseVO;
import com.tg.lygem2.constant.UserTypeEnum;
import com.tg.lygem2.vo.Result;
import com.tg.lygem2.vo.crud.ExposeUser;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangjianbo on 2018/3/7
 */
@RestController
@RequestMapping("/energyExpert")
@Slf4j
public class EnergyExpertController {

    @Autowired
    private IEnergyExpertBiz biz;

    @Autowired
    private ThreadLocal<Result<Object>> threadLocal;

    @Value("${tg.getPicture.path}")
    private String getPicturePath;

    /**
     * 查询所有
     * @param data
     * @return
     */
    @ApiOperation(value = "查询所有节能专家列表", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value="/getList",method = RequestMethod.POST)
    public PageResponseVO<EnergyExpert> getList(@RequestBody PageRequestVO<EnergyExpert> data){
        PageResponseVO<EnergyExpert> result = new PageResponseVO<EnergyExpert>();
        try {
            ExposeUser user = JSON.parseObject(threadLocal.get().getMsg(), ExposeUser.class);
            if(user.getUser_type() == UserTypeEnum.company.getType() || user.getUser_type() == UserTypeEnum.company_read.getType()) {
                data.getParams().setEnterprise_id(Integer.valueOf(user.getEnterprise_id()));
            }
            PageInfo<EnergyExpert> info = biz.queryPageList(data.getParams(),data.getPageIndex(),data.getPageSize());
            List<EnergyExpert> voList = new ArrayList<EnergyExpert>();
            if(info.getTotal()>0){
                for(EnergyExpert e:info.getList()){
                    EnergyExpert vo = new EnergyExpert();
                    PropertyUtils.copyProperties(vo, e);
                    if (StringUtils.isNotBlank(vo.getHeadImage()))
                    {
                        String headImage = vo.getHeadImage();
                        String[] split = headImage.split(",");
                        StringBuilder sb = new StringBuilder();
                        for (String s : split) {
                            sb.append(getPicturePath).append(s).append(",");
                        }
                        vo.setHeadImage1(sb.substring(0,sb.length()-1));
                    }
                    voList.add(vo);
                }
            }
            result.setCode(ErrorCode.OK);
            result.setMsg("OK");
            result.setTotal(info.getTotal());
            result.setRows(voList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setCode(ErrorCode.INNER_ERROR);
            result.setMsg("服务器错误");
        }
        return result;
    }

    /**
     * 添加节能专家
     * @return
     */
    @ApiOperation(value = "添加节能专家", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonResponse add(@RequestBody EnergyExpert EnergyExpert){
        try {
            ExposeUser user = JSON.parseObject(threadLocal.get().getMsg(), ExposeUser.class);
            if(user != null) {
                EnergyExpert.setEnterprise_id(Integer.valueOf(user.getEnterprise_id()));
            }
            biz.add(EnergyExpert);
            JSONObject data = new JSONObject();
            data.put("id",EnergyExpert.getId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, Contants.ERROR_MESSAGE);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }

    /**
     * 修改节能专家
     * @param EnergyExpert
     * @return
     */
    @ApiOperation(value = "修改节能专家", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public JSONObject edit(@RequestBody EnergyExpert EnergyExpert){
        JSONObject result = new JSONObject();
        try {
            biz.update(EnergyExpert);
            result.put("code",ErrorCode.OK);
            result.put("msg","ok");
            JSONObject data = new JSONObject();
            data.put("id",EnergyExpert.getId());
            result.put("data",data);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.put("code", ErrorCode.INNER_ERROR);
            result.put("msg", "failed");
        }
        return result;
    }

    /**
     * 删除某一条节能专家
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除某一条节能专家", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public JSONObject del(@RequestBody List<Integer> ids){
        JSONObject result = new JSONObject();
        try {
           biz.delByIds(ids);
           result.put("code", ErrorCode.OK);
           result.put("msg", "ok");
           result.put("ids", ids);
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            result.put("code", ErrorCode.INNER_ERROR);
            result.put("msg", "failed");
        }
        return result;
    }
    @ApiOperation(value = "单个淘汰设备分类详情", consumes = "application/json", response = ResponseVO.class)
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public ResponseVO<EnergyExpert> detail(@RequestBody Integer id) {
        EnergyExpert applyData = new EnergyExpert();
        try {
             applyData = biz.selectById(id);
            if (StringUtils.isNotBlank(applyData.getHeadImage()))
            {
                String headImage = applyData.getHeadImage();
                String[] split = headImage.split(",");
                StringBuilder sb = new StringBuilder();
                for (String s : split) {
                    sb.append(getPicturePath).append(s).append(",");
                }
                applyData.setHeadImage1(sb.substring(0,sb.length()-1));
            }
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            return new ResponseVO<EnergyExpert>(ErrorCode.BG_GETDATA_ERROR, Contants.ERROR_GET);
        }
        return new ResponseVO<EnergyExpert>(ErrorCode.OK, "ok", applyData);
    }

}

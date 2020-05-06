package com.tg.enterprise.controller;

import com.tg.enterprise.biz.IOutputEnergyTargetBiz;
import com.tg.enterprise.biz.IProductBiz;
import com.tg.enterprise.config.VerifyLastMonth;
import com.tg.enterprise.model.OutputEnergyTarget;
import com.tg.enterprise.model.Product;
import com.tg.enterprise.model.User;
import com.tg.enterprise.util.Contants;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.util.OutputProduct;
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

/** * 产量产值指标 */
@RestController
@RequestMapping("/outputEnergyTarget")
@Slf4j
public class OutputEnergyTargetController {

    @Resource
    private IOutputEnergyTargetBiz biz;

    @Resource
    private IProductBiz productBiz;

    @ApiOperation(value = "新增产量产值指标", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/add", consumes = "application/json", method = RequestMethod.POST)
    @VerifyLastMonth
    public CommonResponse add(@RequestBody OutputEnergyTarget entity, HttpServletRequest request) {
        User userVO = SessionUtil.getUser(request.getSession());
        Integer enterpriseId =userVO.getCid();
        entity.setEnterprise_id(enterpriseId);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,-1);
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Long month = Long.valueOf(format.format(date));
        entity.setUpload_date(month);
        OutputEnergyTarget target = biz.selectById(entity.getProduct(),entity.getEnterprise_id(),entity.getUpload_date());
        if(target!=null){
            return new CommonResponse(ErrorCode.BG_DATABASE_ERROR, "该产品数据已经存在");
        }
        biz.add(entity);
        return new CommonResponse(ErrorCode.OK, "ok");
    }

    @ApiOperation(value = "获取产量产值指标", consumes = "application/json", response = PageResponseVO.class)
    @RequestMapping(value = "/getList", consumes = "application/json", method = RequestMethod.POST)
    public PageResponseVO<OutputEnergyTarget> getList(@RequestBody PageRequestVO<OutputEnergyQueryVO> queryVO, HttpServletRequest request) {
        PageResponseVO<OutputEnergyTarget> responseVO = new PageResponseVO<OutputEnergyTarget>(ErrorCode.OK, "ok");
        try{
            //查询出前2个
            OutputEnergyQueryVO data = queryVO.getParams();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH,-1);
            Date date = cal.getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
            Long month = Long.valueOf(format.format(date));
            data.setUpload_date(month);
            List<OutputEnergyTarget> lastList = new ArrayList<>();
            User userVO = SessionUtil.getUser(request.getSession());
            Integer enterpriseId =userVO.getCid();
            data.setEnterprise_id(enterpriseId);
            Long upload_date = data.getUpload_date();
            List<String> list = new ArrayList<>();
            list.add(OutputProduct.GROSS_OUTPUT.getCode());
            list.add(OutputProduct.INDUSTRIAL_VALUE.getCode());
            data.setList(list);
            List<OutputEnergyTarget> outputList = biz.queryList(data);
            Long count=1l;
            Product product = new Product();
            product.setEnterprise_id(enterpriseId);
            List<Product> products = productBiz.selectList(product);
            Map<String,String> productMap = new HashMap<>();
            for (Product entity:products ) {
                productMap.put(entity.getId().toString(),entity.getName());
            }
            productMap.put(OutputProduct.GROSS_OUTPUT.getCode(),OutputProduct.GROSS_OUTPUT.getName());
            productMap.put(OutputProduct.INDUSTRIAL_VALUE.getCode(),OutputProduct.INDUSTRIAL_VALUE.getName());
            Map<String,OutputEnergyTarget> map = new HashMap<>();
            for (OutputEnergyTarget entity:outputList) {
                entity.setType("1");
                if(productMap.containsKey(entity.getProduct())){
                    entity.setProduct_name(productMap.get(entity.getProduct()));
                }
                entity.setDel_ids(entity.getId());
                entity.setProduct1(entity.getProduct());
                map.put(entity.getProduct(),entity);
            }
            if(map.containsKey(OutputProduct.GROSS_OUTPUT.getCode())){
                map.get(OutputProduct.GROSS_OUTPUT.getCode()).setId(count++);
                map.get(OutputProduct.GROSS_OUTPUT.getCode()).setUnit(OutputProduct.GROSS_OUTPUT.getUnit());
                lastList.add(map.get(OutputProduct.GROSS_OUTPUT.getCode()));
            }else{
                OutputEnergyTarget temp = new OutputEnergyTarget();
                temp.setProduct(OutputProduct.GROSS_OUTPUT.getCode());
                temp.setProduct_name(OutputProduct.GROSS_OUTPUT.getName());
                temp.setEnterprise_id(enterpriseId);
                temp.setType("1");
                temp.setUpload_date(upload_date);
                temp.setUnit(OutputProduct.GROSS_OUTPUT.getUnit());
                temp.setId(count++);
                temp.setProduct1(OutputProduct.GROSS_OUTPUT.getCode());
                lastList.add(temp);
            }

            if(map.containsKey(OutputProduct.INDUSTRIAL_VALUE.getCode())){
             
                map.get(OutputProduct.INDUSTRIAL_VALUE.getCode()).setId(count++);
                map.get(OutputProduct.INDUSTRIAL_VALUE.getCode()).setUnit(OutputProduct.INDUSTRIAL_VALUE.getUnit());
                lastList.add(map.get(OutputProduct.INDUSTRIAL_VALUE.getCode()));
            }else{
                OutputEnergyTarget temp = new OutputEnergyTarget();
                temp.setProduct(OutputProduct.INDUSTRIAL_VALUE.getCode());
                temp.setProduct_name(OutputProduct.INDUSTRIAL_VALUE.getName());
                temp.setEnterprise_id(enterpriseId);
                temp.setUpload_date(upload_date);
                temp.setType("1");
                temp.setUnit(OutputProduct.INDUSTRIAL_VALUE.getUnit());
                temp.setProduct1(OutputProduct.INDUSTRIAL_VALUE.getCode());
                temp.setId(count++);
                lastList.add(temp);
            }
            data.setList(null);
            List<OutputEnergyTarget> otherList = biz.queryList(data);
            for (OutputEnergyTarget entity:otherList) {
                entity.setDel_ids(entity.getId());
                if(productMap.containsKey(entity.getProduct())){
                    entity.setProduct_name(productMap.get(entity.getProduct()));
                }
                entity.setId(count++);
                entity.setProduct1(entity.getProduct());
                lastList.add(entity);
            }
            responseVO.setRows(lastList);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new PageResponseVO<OutputEnergyTarget>(ErrorCode.BG_GETDATA_ERROR, Contants.ERROR_GET);
        }
        return responseVO;
    }

    @ApiOperation(value = "修改产量产值指标", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/update", consumes = "application/json", method = RequestMethod.POST)
    @VerifyLastMonth
    public CommonResponse update(@RequestBody OutputEnergyTarget entity) {
        entity.setProduct(entity.getProduct1());
        OutputEnergyTarget target = biz.selectById(entity.getProduct(),entity.getEnterprise_id(),entity.getUpload_date());
        if(target ==null){
            biz.add(entity);
        }else{
            biz.update(entity);
        }
        return new CommonResponse(ErrorCode.OK, "ok");
    }

    @ApiOperation(value = "删除产量产值指标", consumes = "application/json", response = CommonResponse.class)
    @RequestMapping(value = "/delByIds", consumes = "application/json", method = RequestMethod.POST)
    @VerifyLastMonth
    public CommonResponse delByIds(@RequestBody List<Long> ids) {

            biz.delByIds(ids);
        return new CommonResponse(ErrorCode.OK, "ok");
    }
}

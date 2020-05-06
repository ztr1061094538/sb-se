package com.tg.enterprise.controller;

import com.github.pagehelper.PageInfo;
import com.tg.enterprise.biz.impl.AssociatedProcessBizImpl;
import com.tg.enterprise.biz.impl.ProductBizImpl;
import com.tg.enterprise.config.VerifyCommitDate;
import com.tg.enterprise.model.AssociatedProcess;
import com.tg.enterprise.model.Product;
import com.tg.enterprise.model.User;
import com.tg.enterprise.util.Constant;
import com.tg.enterprise.util.Contants;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.util.SessionUtil;
import com.tg.enterprise.vo.PageRequestVO;
import com.tg.enterprise.vo.PageResponseVO;
import com.tg.enterprise.vo.ResponseVO;
import com.tg.enterprise.vo.UserVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: em2-parent
 * @author: jikai.sun
 * @create: 2018-12-06
 * 产品能耗及占比
 **/
@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductBizImpl productBiz;

    @Autowired
    AssociatedProcessBizImpl associatedProcessBiz;

//    @Autowired
//    IEnterprisesTypeProductBiz typeProductBiz;
//
//    @Autowired
//    EnterpriseInvoker enterpriseInvoker;

    @PostMapping("save")
    @ApiOperation(value = "存储产品信息", consumes = "application/json", response = ResponseVO.class)
    @Transactional
    @VerifyCommitDate
    public ResponseVO saveProduct(@RequestBody Product product, HttpServletRequest request) {
        if (product == null) {
            return new ResponseVO<>(ErrorCode.INNER_ERROR, "请检查传入问题，数据为空或传入非法时间");
        }
        User userVO = SessionUtil.getUser(request.getSession());
//        ResponseVO responseVO = this.verifyProduct(product, userVO);
        product.setEnterprise_id(userVO.getCid());
        productBiz.insert(product);
        return new ResponseVO<>(ErrorCode.OK, "新增成功");
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除产品信息", consumes = "application/json", response = ResponseVO.class)
    @Transactional
    @VerifyCommitDate
    public ResponseVO deletProduct(@RequestBody Product product, HttpServletRequest request) {
        User userVO = SessionUtil.getUser(request.getSession());
        if(userVO == null){
            return new ResponseVO<>(ErrorCode.NOT_LOGIN, Contants.UNAUTHORIZED);
        }
        if (product == null) {
            return new ResponseVO<>(ErrorCode.INNER_ERROR, "请检查传入问题，数据为空或传入非法时间");
        }
        productBiz.delete(product);
        AssociatedProcess associatedProcess = new AssociatedProcess();
        associatedProcess.setPid(product.getId());
        associatedProcessBiz.delete(associatedProcess);
        return new ResponseVO<>(ErrorCode.OK, "删除成功");
    }

    @PostMapping("update")
    @ApiOperation(value = "更新产品信息", consumes = "application/json", response = ResponseVO.class)
    @Transactional
    @VerifyCommitDate
    public ResponseVO updateProduct(@RequestBody Product product, HttpServletRequest request) {
        User userVO = SessionUtil.getUser(request.getSession());
        if (product == null) {
            return new ResponseVO<>(ErrorCode.INNER_ERROR, "请检查传入问题，数据为空或传入非法时间");
        }
//        ResponseVO responseVO = this.verifyProduct(product, userVO);
        product.setEnterprise_id(userVO.getCid());
        productBiz.update(product);
        return new ResponseVO<>(ErrorCode.OK, "更新成功");
    }

    @PostMapping("query")
    @ApiOperation(value = "查询产品信息", consumes = "application/json", response = ResponseVO.class)
    public PageResponseVO<Product> queryProduct(@RequestBody PageRequestVO<Product> vo, HttpServletRequest request) {
        User userVO = SessionUtil.getUser(request.getSession());
        Product params = vo.getParams();
        params.setEnterprise_id(userVO.getCid());
        PageInfo<Product> productPageInfo = productBiz.selectForPage(params, vo.getPageIndex(), vo.getPageSize());
        PageResponseVO<Product> objectPageResponseVO = new PageResponseVO<>();
        objectPageResponseVO.setCode(ErrorCode.OK);
        objectPageResponseVO.setRows(productPageInfo.getList());
        objectPageResponseVO.setTotal(productPageInfo.getTotal());
        //查询时返回一个值是不是千家火电
//        List<EnterprisesTypeProduct> manageCode = this.getManageCode(userVO);
//        if (CollectionUtils.isEmpty(manageCode)) {
//            objectPageResponseVO.setMsg("false");
//        }else{
//            objectPageResponseVO.setMsg("true");
//        }
        return objectPageResponseVO;
    }

//    //校验传入数据
//    public ResponseVO verifyProduct(Product product, UserVO userVO) {
//        if (userVO == null) {
//            return new ResponseVO<>(ErrorCode.INNER_ERROR, "无法获取用户信息");
//        }
//        //获得manage_code
//        ResponseVO<EnterpriseModel> modelResponseVO = enterpriseInvoker.getEnterpriseById(
//                new RequestToken().toString(), userVO.getEnterprise_id());
//        if (modelResponseVO == null || modelResponseVO.getData() == null) {
//            return new ResponseVO<>(ErrorCode.INNER_ERROR, "远程请求调用失败");
//        }
//        EnterpriseModel modelResponseVOData = modelResponseVO.getData();
//        List<EnterprisesTypeProduct> productList = typeProductBiz.getProduct(modelResponseVOData.getManage_code());
//        if (productList != null && !CollectionUtils.isEmpty(productList)) {
//            for (EnterprisesTypeProduct p : productList) {
//                if (p.getName().equals(product.getName())) {
//                    return new ResponseVO<>(ErrorCode.OK, "ok");
//                }
//            }
//        }
//        return new ResponseVO<>(ErrorCode.INNER_ERROR, "传入数据格式错误");
//    }
//
//    //获得manage_code
//    public List<EnterprisesTypeProduct> getManageCode(UserVO userVO) {
//        ResponseVO<EnterpriseModel> modelResponseVO = enterpriseInvoker.getEnterpriseById(
//                new RequestToken().toString(), userVO.getEnterprise_id());
//        if (modelResponseVO == null || modelResponseVO.getData() == null) {
//            return null;
//        }
//        EnterpriseModel modelResponseVOData = modelResponseVO.getData();
//        return typeProductBiz.getProduct(modelResponseVOData.getManage_code());
//    }
}

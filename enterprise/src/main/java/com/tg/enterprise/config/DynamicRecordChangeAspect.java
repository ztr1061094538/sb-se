package com.tg.enterprise.config;

import com.tg.enterprise.biz.IPlatformParamBiz;
import com.tg.enterprise.model.PlatformParam;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

/**
 * @program: em2-parent
 * @author: jikai.sun
 * @create: 2018-12-11
 **/
@Aspect
@Component
@Slf4j
public class DynamicRecordChangeAspect {

//    @Autowired
//    TodoProcessBizImpl todoProcessBiz;

    @Value("${tg.commit.startDate:1}")
    private Integer cStartDate;

    //@Value("${tg.commit.endDate:10}")
    private Integer cEndDate;

    @Autowired
    IPlatformParamBiz platformParamBiz;

//    @After("@annotation(rc)")
//    public void isChange(JoinPoint point, RecordChange rc)
//    {
//        HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        HttpSession session = httpRequest.getSession();
//        UserVO userVO = SessionUtil.getUser(session);
//        if (userVO != null)
//        {
//            TodoProcess todoProcess = new TodoProcess();
//            todoProcess.setEnterprise_id(userVO.getEnterprise_id());
//            DynamicDataSourceContextHolder.setDataSourceType("em2");
//            todoProcessBiz.insert(todoProcess);
//            log.debug("clz msg " + point.getSignature());
//        }
//    }


    @Around("@annotation(v)")
    public Object verifyCommitDate(ProceedingJoinPoint point, VerifyCommitDate v) throws Throwable {
        PlatformParam platformParam = platformParamBiz.selectById("deadline");
        this.cEndDate = Integer.valueOf(platformParam == null ? "11" : platformParam.getValue());
        LocalDate now = LocalDate.now();
        LocalDate startDate = LocalDate.of(now.getYear(),now.getMonth(),cStartDate);
        LocalDate endDate = LocalDate.of(now.getYear(),now.getMonth(),cEndDate);
        if(now.isAfter(startDate) && now.isBefore(endDate)){
            return point.proceed();
        }else {
            log.error("请在允许时间内修改");
            return new ResponseVO<>(ErrorCode.INNER_ERROR,"请在"+cEndDate+"号内修改，如有问题请联系管理员");
        }
    }

    @Around("@annotation(v)")
    public Object verifyLastMonth(ProceedingJoinPoint point, VerifyLastMonth v) throws Throwable {
        PlatformParam platformParam = platformParamBiz.selectById("deadline");
        this.cEndDate = Integer.valueOf(platformParam == null ? "11" : platformParam.getValue());
        LocalDate now = LocalDate.now();
        LocalDate startDate = LocalDate.of(now.getYear(),now.getMonth(),cStartDate);
        LocalDate endDate = LocalDate.of(now.getYear(),now.getMonth(),cEndDate);
        if(now.isAfter(startDate) && now.isBefore(endDate)){
            return point.proceed();
        }else {
            log.error("请在允许时间内修改，新增上月数据");
            return new ResponseVO<>(ErrorCode.INNER_ERROR,"请在"+cEndDate+"号内修改，新增上月数据");
        }
    }


}









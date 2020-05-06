package com.tg.lygem2.config;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tg.lygem2.adapt.VerifySecurity;
import com.tg.lygem2.constant.ConstantKey;
import com.tg.lygem2.constant.ErrorCodeMsgEnum;
import com.tg.lygem2.feign.ISecurityServiceBiz;
import com.tg.lygem2.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-29
 **/
@Aspect
@Component
@Slf4j
public class ProxyVerfiyAspect {

	public ProxyVerfiyAspect() {
	}

	@Autowired
	ISecurityServiceBiz iSecurityServiceBiz;

	@Autowired
	ThreadLocal<Result<Object>> threadLocal;

	@Resource(name = "feginObjectMapper")
	ObjectMapper objectMapper;

	@Around("@annotation(verifySecurity)")
	public void verfiyControl(ProceedingJoinPoint point, VerifySecurity verifySecurity) throws Throwable {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		HttpServletResponse response = requestAttributes.getResponse();
		String uri = request.getRequestURI().toString();
		if (log.isInfoEnabled())
			log.info("[verify uri is]===" + uri);
		response.setContentType("application/json;charset=UTF-8");
		Cookie[] cookies = request.getCookies();
		String token = request.getHeader(ConstantKey.TOKENHEADER);
		if (StringUtils.isEmpty(token) && null != cookies) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(ConstantKey.TOKENHEADER) && !StringUtils.isEmpty(cookie.getValue())) {
					token = ConstantKey.JWT_PREFIX + cookie.getValue();
					break;
				}
			}
		}
		Result<Object> result = iSecurityServiceBiz.securityUri(token, uri, request.getHeader("cid"));
		if (log.isInfoEnabled())
			log.info("[return result]===" + result != null ? result.getMsg() : "result is null");
		threadLocal.set(result);
		if (ErrorCodeMsgEnum.SUCESS.getCode() == result.getCode()) {
			Object o = point.proceed();
			if (o != null) {
				response.getWriter().print(objectMapper.writeValueAsString(o));
			}
			threadLocal.remove();
		} else {
			response.getWriter().print(JSON.toJSON(result));
		}
	}
}
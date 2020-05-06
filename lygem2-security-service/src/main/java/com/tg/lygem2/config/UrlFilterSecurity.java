package com.tg.lygem2.config;

import com.alibaba.fastjson.JSONObject;
import com.tg.lygem2.constant.ConstantKey;
import com.tg.lygem2.service.MenuService;
import com.tg.lygem2.vo.BasicUser;
import com.tg.lygem2.vo.Menu;
import com.tg.lygem2.vo.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-26
 **/
@Component
@Slf4j
public class UrlFilterSecurity implements FilterInvocationSecurityMetadataSource {

    @Autowired
    MenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    ThreadLocal<String> threadLocal;

    private final String VERFIYURI = "/verifyUri";

    /**
     * 拿到当前请求的有权限访问的角色有哪些
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {

        String requestUrl = ((FilterInvocation) o).getRequestUrl();

        for (String s :
                ConstantKey.NOFILTERURI) {
            if (s.equals(requestUrl)) return null;
        }

        if (VERFIYURI.equals(requestUrl)) {
            requestUrl = ((FilterInvocation) o).getRequest().getHeader("uri");
            if(log.isInfoEnabled())log.info("[Auth Security uri is]" + requestUrl);
        }
        try {
        	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        	if (authentication instanceof AnonymousAuthenticationToken)
        	{
        		if(log.isInfoEnabled())log.info("[Auth Security principal]" + authentication.getPrincipal());
        		return SecurityConfig.createList(ConstantKey.ROLELOGIN);
        	}
        	else
        	{
	        	BasicUser principal = (BasicUser) SecurityContextHolder.getContext()
	                    .getAuthentication()
	                    .getPrincipal();
	            if (null != principal) {
	                if(log.isInfoEnabled())log.info("[Auth Security principal]" + JSONObject.toJSONString(principal));
	                threadLocal.set(JSONObject.toJSONString(principal));
	            }
        	}
        } catch (Exception e) {
            log.error("找不到用户信息", e);
        }
        List<Menu> allMenu = menuService.getAllMenu();
        for (Menu menu : allMenu) {
            if (antPathMatcher.match(menu.getUrl(), requestUrl) && menu.getRoles().size() > 0) {
                List<Role> roles = menu.getRoles();
                int size = roles.size();
                String[] values = new String[size];
                for (int i = 0; i < size; i++) {
                    values[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(values);
            }
        }

        return SecurityConfig.createList(ConstantKey.ROLELOGIN);

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}

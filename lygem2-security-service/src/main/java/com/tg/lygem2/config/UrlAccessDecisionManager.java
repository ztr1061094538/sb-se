package com.tg.lygem2.config;

import com.tg.lygem2.constant.ConstantKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-26
 **/
@Component
@Slf4j
public class UrlAccessDecisionManager implements AccessDecisionManager {


    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, AuthenticationException {

        Iterator<ConfigAttribute> iterator = collection.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute ca = iterator.next();

            String needRole = ca.getAttribute();
            if (ConstantKey.ROLELOGIN.equals(needRole)) {
                if(log.isInfoEnabled())log.error("[UrlAccessDecisionManager] method decide ===== user not login");
                throw new BadCredentialsException("未登录");
            }
            //如果有值就是模拟登陆
            String company = ((FilterInvocation) o).getRequest().getHeader("company");
            if(StringUtils.isNotBlank(company)){
                /**
                 * 需要添加定义某个企业可以模拟登陆的企业
                 */
                if(log.isInfoEnabled())log.info("=== Simulation on ===" + company);
                return;
            }
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().trim().equals(needRole)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
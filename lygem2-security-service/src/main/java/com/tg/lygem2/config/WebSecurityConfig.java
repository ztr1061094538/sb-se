package com.tg.lygem2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tg.lygem2.bean.User;
import com.tg.lygem2.config.jwt.CustomAuthenticationProvider;
import com.tg.lygem2.config.jwt.JWTAuthenticationFilter;
import com.tg.lygem2.config.jwt.JWTLoginFilter;
import com.tg.lygem2.constant.ConstantKey;
import com.tg.lygem2.constant.ErrorCodeMsgEnum;
import com.tg.lygem2.constant.NoFilter;
import com.tg.lygem2.service.ICompanyBiz;
import com.tg.lygem2.service.IUserLoginLogsBiz;
import com.tg.lygem2.service.UserService;
import com.tg.lygem2.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter 
{
	@Value("${login.imageCodeFlag:true}")
	boolean imagecodeFlag;
    @Autowired
    UserService userService;
    @Autowired
    UrlFilterSecurity urlFilterSecurity;
    @Autowired
    UrlAccessDecisionManager urlAccessDecisionManager;
    @Autowired
    AuthenticationDeniedHandler authenticationDeniedHandler;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private ICompanyBiz iCompanyBiz;
    @Autowired
    private IUserLoginLogsBiz iUserLoginLogsBiz;

    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	//auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
		auth.authenticationProvider(new CustomAuthenticationProvider(userService, new BCryptPasswordEncoder()));
	}

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(ConstantKey.ERRORPAGE).antMatchers(ConstantKey.LOGINPAGE)
                .antMatchers(NoFilter.AUTH_WHITELIST);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(urlFilterSecurity);
                        o.setAccessDecisionManager(urlAccessDecisionManager);
                        return o;
                    }
                    //loginPage is failed to redirect / uri
                }).and().formLogin().loginPage(ConstantKey.ERRORPAGE).loginProcessingUrl("/login").usernameParameter(ConstantKey.USERNAME).passwordParameter(ConstantKey.PASSWORD).permitAll().failureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                httpServletResponse.setContentType(ConstantKey.CONTENTTYPE);
                PrintWriter out = httpServletResponse.getWriter();
                Result<Object> objectResult = new Result<>();
                objectResult.setCode(ErrorCodeMsgEnum.SUCESS.getCode());
                if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                    objectResult.setMsg("用户名或密码输入错误，登录失败!");
                } else if (e instanceof DisabledException) {
                    objectResult.setMsg("账户被禁用，登录失败，请联系管理员!");
                } else {
                    objectResult.setMsg("登录失败!");
                }
                out.write(objectMapper.writeValueAsString(objectResult));
                out.flush(); 
                out.close();
            }
        }).successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                httpServletResponse.setContentType("application/json;charset=utf-8");
                
                PrintWriter out = httpServletResponse.getWriter();
                ObjectMapper objectMapper = new ObjectMapper();
                String content = objectMapper.writeValueAsString(Result.instance(ErrorCodeMsgEnum.SUCESS.getCode(),objectMapper.writeValueAsString(getCurrentRole())));
                out.write(content);
                out.flush();
                out.close();
            }
        }).and().logout().permitAll().and().csrf().disable().exceptionHandling().accessDeniedHandler(authenticationDeniedHandler).and()
                .addFilter(new JWTLoginFilter(authenticationManager(),imagecodeFlag,iCompanyBiz,iUserLoginLogsBiz))
                //验证token
                .addFilter(new JWTAuthenticationFilter(authenticationManager()));
    }

    public User getCurrentRole() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}


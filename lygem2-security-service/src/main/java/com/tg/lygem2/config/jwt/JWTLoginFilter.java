package com.tg.lygem2.config.jwt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tg.lygem2.bean.Company;
import com.tg.lygem2.config.UserPassInfoAuthenticationToken;
import com.tg.lygem2.constant.ConstantKey;
import com.tg.lygem2.constant.ErrorCodeMsgEnum;
import com.tg.lygem2.service.ICompanyBiz;
import com.tg.lygem2.service.IUserLoginLogsBiz;
import com.tg.lygem2.vo.*;
import com.tg.lygem2.vo.crud.ExposeUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-30
 **/
@Slf4j
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private boolean imageCodeFlag;

	private ICompanyBiz iCompanyBiz;

	private IUserLoginLogsBiz iUserLoginLogsBiz;
    
    public JWTLoginFilter(AuthenticationManager authenticationManager, boolean imageCodeFlag, ICompanyBiz iCompanyBiz, IUserLoginLogsBiz iUserLoginLogsBiz) {
        this.authenticationManager = authenticationManager;
        this.imageCodeFlag = imageCodeFlag;
        this.iCompanyBiz = iCompanyBiz;
        this.iUserLoginLogsBiz = iUserLoginLogsBiz;
    }

    // 接收并解析用户凭证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        String username = req.getParameter(ConstantKey.USERNAME);
        String password = req.getParameter(ConstantKey.PASSWORD);
        String requestUrl = req.getRequestURI();
        if (requestUrl.equals(ConstantKey.LOGINPAGE_URI)) 
        {
            if (!verflyImageCode(req, res)) {
                return null;
            }
        }

        try {
        	return authenticationManager
        			.authenticate(new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>()));
        }catch(AuthenticationException e) {
        	refreshClientLoginFailedCounts(req, res);
        	throw e;
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        // builder the token
        String token = null;
        try {
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            LoginInfo info = new LoginInfo();
            info.setLogin_name(auth.getName());
            // 定义存放角色集合的对象
            for (GrantedAuthority grantedAuthority : authorities) {
                info.getRoles().add(grantedAuthority.getAuthority());
            }

            // 设置过期时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, 30);
            Date time = calendar.getTime();
            UserPassInfoAuthenticationToken userPassInfoAuthenticationToken = (UserPassInfoAuthenticationToken) auth;
            BasicUser user = JSON.parseObject(userPassInfoAuthenticationToken.getThreadLocal().get(), BasicUser.class);
            info.setUser_detail(user);
            saveLoginLogs(userPassInfoAuthenticationToken, request.getHeader("x-real-ip"));
            //清空登录失败的次数
            clearClientLoginFailedCounts(response);
            token = Jwts.builder()
                    .setSubject(JSONObject.toJSONString(info))
                    .setExpiration(time)
                    .signWith(SignatureAlgorithm.HS512, ConstantKey.SIGNING_KEY) //设置算法
                    .compact();
            response.addHeader(ConstantKey.TOKENHEADER, ConstantKey.JWT_PREFIX + token);
            Cookie cookie = new Cookie(ConstantKey.TOKENHEADER, token);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie); 
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(JSON.toJSONString(new Result<LoginResult>(0, "登陆成功",new LoginResult(auth.getName(),ConstantKey.JWT_PREFIX + token,user.getUser_type(),user.getEnterprise_id()))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearClientLoginFailedCounts(HttpServletResponse res) {
  	  Cookie cookie = new Cookie(ConstantKey.LOGIN_FAILED_COUNT, null);
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        res.addCookie(cookie);
  }
  
  /**
   * 用户名密码验证失败，在Cookie中加入失败的次数，前后端根据cookie判断是否需要校验验证码
   * @param req
   * @param res
   */
  private void refreshClientLoginFailedCounts(HttpServletRequest req, HttpServletResponse res) {
		Cookie[] cookieList = req.getCookies();
		String loginFailedCounts = "1";
		if (cookieList != null) {
	        for (int i = 0; i < cookieList.length; i++) {
	        	if (cookieList[i].getName().equals(ConstantKey.LOGIN_FAILED_COUNT)
	        			&& StringUtils.isNotBlank(cookieList[i].getValue())) {
	        		try {
						loginFailedCounts = String.valueOf(Integer.parseInt(cookieList[i].getValue()) + 1);
						break;
	        		} catch (NumberFormatException e) {
	        			log.error(e.getMessage(), e);
	        		}
	        	}
	        }
		}
		Cookie cookie = new Cookie(ConstantKey.LOGIN_FAILED_COUNT, loginFailedCounts);
      cookie.setPath("/");
   	cookie.setMaxAge(ConstantKey.LOGIN_FAILED_COUNT_REFRESH_MILLSEC);
      res.addCookie(cookie);
  }
  
	/**
	 * 存储登录日志
	 * 
	 * @param userPassInfoAuthenticationToken
	 * @param ip
	 */
	private void saveLoginLogs(UserPassInfoAuthenticationToken userPassInfoAuthenticationToken, String ip) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		ExposeUser user = JSON.parseObject(userPassInfoAuthenticationToken.getThreadLocal().get(), ExposeUser.class);
		UserLoginLogs userLoginLogs = new UserLoginLogs();
		userLoginLogs.setLogin_name(user.getUsername());
		userLoginLogs.setLogin_ip(ip);
		userLoginLogs.setLogin_time(Long.valueOf(sdf.format(new Date())));
		if (user.getUser_type() == 2) {
			Company company = iCompanyBiz.selectByid(Integer.valueOf(user.getEnterprise_id()));
			userLoginLogs.setOrg_id(company.getOrg_id());
			userLoginLogs.setCompany_id(Integer.valueOf(user.getEnterprise_id()));
		} else {
			userLoginLogs.setOrg_id(Integer.valueOf(user.getEnterprise_id()));
		}
		iUserLoginLogsBiz.insert(userLoginLogs);
	}

    public Boolean verflyImageCode(HttpServletRequest req, HttpServletResponse res) 
    {
    	if (!imageCodeFlag)
    	{
    		Cookie cookie = new Cookie("bmp_code", null);
            cookie.setMaxAge(-1);
            cookie.setPath("/");
            res.addCookie(cookie);
    		return true;
    	}
        String imageCode = req.getParameter("imageCode");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Cookie[] cookieList = req.getCookies();
        res.setContentType("application/json;charset=UTF-8");
        if (cookieList == null) {
            try {
                res.getWriter().print(JSON.toJSONString(Result.instance(ErrorCodeMsgEnum.INNER_ERROR.getCode(), "请输入验证码")));
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            return false;
        }
        String bmp_code = null;
        int bmp_login_failed_count = 0;
        for (int i = 0; i < cookieList.length; i++) {
            if (cookieList[i].getName().equals("bmp_code") && StringUtils.isNotBlank(cookieList[i].getValue())) {
                try {
                    bmp_code = URLDecoder.decode(cookieList[i].getValue(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (cookieList[i].getName().equals(ConstantKey.LOGIN_FAILED_COUNT) && StringUtils.isNotBlank(cookieList[i].getValue())) {
                try {
                	bmp_login_failed_count = Integer.parseInt(cookieList[i].getValue());
                }catch (NumberFormatException e) {
                	bmp_login_failed_count = Integer.MAX_VALUE;
                	log.error(e.getMessage(), e);
				}
            }
        }
        log.info("login bmp code is" + bmp_code);
        Cookie cookie = new Cookie("bmp_code", null);
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        res.addCookie(cookie);
        if(bmp_login_failed_count < 1) {
    		return true;
        }
        if (!bCryptPasswordEncoder.matches(imageCode.toUpperCase(), bmp_code)) 
        {
            try {
                res.getWriter().print(JSON.toJSONString(Result.instance(ErrorCodeMsgEnum.INNER_ERROR.getCode(), "验证码验证错误")));
                return false;
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return true;
    }
}
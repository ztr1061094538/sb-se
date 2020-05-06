package com.tg.enterprise.config;

import com.alibaba.fastjson.JSONObject;
import com.tg.enterprise.util.ErrorCode;
import com.tg.enterprise.util.SystemConstants;
import com.tg.enterprise.vo.CommonResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


//@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

	@Bean
	public SecurityInterceptor getSecurityInterceptor() {
		return new SecurityInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
		addInterceptor.addPathPatterns("/**");//这里不需要加enterprise,因为application中已经配置了server.context-path
		// 排除配置
		addInterceptor.excludePathPatterns("/account/logout");
		addInterceptor.excludePathPatterns("/account/logout");
		addInterceptor.excludePathPatterns("/account/login");
		addInterceptor.excludePathPatterns("/account/getCode");
		addInterceptor.excludePathPatterns("/dataBoard/**");
		addInterceptor.excludePathPatterns("/swagger-ui.html");
		addInterceptor.excludePathPatterns("/swagger-resources/**");
		addInterceptor.excludePathPatterns("/webjars/**");
		addInterceptor.excludePathPatterns("/static/**");
		//报表导出接口，没有header需要排除
		addInterceptor.excludePathPatterns("/consumption/export");
	}

	private class SecurityInterceptor implements HandlerInterceptor {

		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
			HttpSession session = request.getSession(false);
			if (session != null && session.getAttribute(SystemConstants.SESSION_KEY) != null)
				return true;

			// 跳转登录
			//response.sendRedirect("/login");
			doFailure(response);
			return false;
		}
	}
	
	
	public void doFailure(ServletResponse response) throws IOException {
		CommonResponse commonResponse = new CommonResponse(ErrorCode.OPT_UNAUTHORIZED, "未登录");
		String content = JSONObject.toJSONString(commonResponse);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(content);
	}
}

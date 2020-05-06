package com.tg.lygem2.config;

import com.alibaba.fastjson.JSON;
import com.tg.lygem2.constant.ErrorCodeMsgEnum;
import com.tg.lygem2.vo.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-26
 **/
@Component
public class AuthenticationDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse resp, AccessDeniedException e) throws IOException, ServletException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.write(JSON.toJSON(new Result<Object>(ErrorCodeMsgEnum.AUTH_ERROR.getCode(), "权限不足，请联系管理员!")).toString());
        out.flush();
        out.close();
    }
}

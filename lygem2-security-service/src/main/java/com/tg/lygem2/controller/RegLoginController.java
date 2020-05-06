package com.tg.lygem2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tg.lygem2.constant.ErrorCodeMsgEnum;
import com.tg.lygem2.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-27
 **/
@RestController
public class RegLoginController {
    @Autowired
    ObjectMapper objectMapper;

    @RequestMapping("/login_error")
    public void login(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.write(objectMapper.writeValueAsString(Result.instance(ErrorCodeMsgEnum.AUTH_ERROR.getCode(), "尚未登录，请登录!")));
        out.flush();
        out.close();
    }
}

package com.tg.enterprise.controller;

import com.alibaba.fastjson.JSON;
import com.tg.enterprise.biz.IUserService;
import com.tg.enterprise.model.User;
import com.tg.enterprise.util.*;
import com.tg.enterprise.vo.ChangePwdRequest;
import com.tg.enterprise.vo.CommonResponse;
import com.tg.enterprise.vo.LoginRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.Map;

/**
 * 数据同步controller
 */
@RestController
@RequestMapping("/account")
@Slf4j
public class LoginController {


	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public CommonResponse login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginRequestVo loginRequestVo) {
		CommonResponse result = new CommonResponse();
			try {
				if(StringUtils.isBlank(loginRequestVo.getImageCode())) {
					return new CommonResponse(ErrorCode.INVALID_PARAM, "请填写验证码");
				}
				String ymz_code = request.getSession().getAttribute("yzm_code")+"";
				if(!ymz_code.equalsIgnoreCase(loginRequestVo.getImageCode())) {
					return new CommonResponse(ErrorCode.INVALID_PARAM, "验证码错误");
				}
				String pwd = MD5UtilTool.MD5(loginRequestVo.getLoginPass());
				User user = userService.getUser(loginRequestVo.getLoginName(), pwd);
				if(user == null) {
					result.setMsg("用户不存在或者密码错误");
					result.setCode(ErrorCode.INVALID_PARAM);
				}else {
					result.setMsg("登录成功");
					result.setCode(ErrorCode.OK);
					HttpSession session = request.getSession();
					session.setAttribute(SystemConstants.SESSION_KEY, JSON.toJSONString(user));
					session.setMaxInactiveInterval(SystemConstants.SESSION_EXPIRE_TIME);//过期时间30分钟
					Cookie cookie = new Cookie(Contants.ROLE, user.getRole());
					cookie.setHttpOnly(false);
					cookie.setPath("/");
					cookie.setMaxAge(SystemConstants.SESSION_EXPIRE_TIME);
					response.addCookie(cookie);
				}
			} catch (Throwable e) {
				log.error("getEnergyByTerimalid error",e);
				result.setCode(ErrorCode.INNER_ERROR);
				result.setMsg("服务器错误");
			}
		return result;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public CommonResponse logout(HttpServletRequest request, HttpServletResponse response) {
		CommonResponse result = new CommonResponse();
		try {
			HttpSession session = request.getSession();
			session.removeAttribute(SystemConstants.SESSION_KEY);
			session.invalidate();
			result.setCode(ErrorCode.OK);
			result.setMsg("redirect:/login");
		} catch (Throwable e) {
			log.error("getEnergyByTerimalid error",e);
			result.setCode(ErrorCode.INNER_ERROR);
			result.setMsg("服务器错误");
		}
		return result;
	}

	@RequestMapping(value="/changePwd",method = RequestMethod.POST, consumes="application/json")
	public CommonResponse changePwd(HttpServletRequest request,@RequestBody ChangePwdRequest data)
	{
		try
		{
			User userVO = SessionUtil.getUser(request.getSession());
			if (userVO == null)
			{
				CommonResponse verifyPwdResponse = new CommonResponse(ErrorCode.NOT_LOGIN, "未登录用户");
				return verifyPwdResponse;
			}
			if (!data.getNewpwd().equals(data.getNewpwd1())) {
				CommonResponse verifyPwdResponse = new CommonResponse(ErrorCode.PASSWORD_ERROR, "两次输入的新密码不相同");
				return verifyPwdResponse;
			}
			if (userVO.getLoginPass().equals(MD5UtilTool.MD5(data.getOldpwd())))
			{
				userService.changePassword(userVO.getId(), data.getNewpwd());
				CommonResponse verifyPwdResponse = new CommonResponse(ErrorCode.OK, "ok");
				return verifyPwdResponse;
			}
			else
			{
				CommonResponse verifyPwdResponse = new CommonResponse(ErrorCode.PASSWORD_ERROR, "密码错误");
				return verifyPwdResponse;
			}
		}
		catch(Exception ex)
		{
			log.error("修改密码异常",ex);
			return new CommonResponse(ErrorCode.INNER_ERROR, "未知错误");
		}
	}

	@RequestMapping(value = "/getCode",method = RequestMethod.GET)
	public void getCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 调用工具类生成的验证码和验证码图片
		Map<String, Object> codeMap = CodeUtil.generateCodeAndPic();

		// 将四位数字的验证码保存到Session中。
		HttpSession session = request.getSession();
		session.setAttribute("yzm_code", codeMap.get("code").toString());

		// 禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", -1);

		response.setContentType("image/jpeg");

		// 将图像输出到Servlet输出流中。
		ServletOutputStream sos;
		try {
			sos = response.getOutputStream();
			ImageIO.write((RenderedImage) codeMap.get("codePic"), "jpeg", sos);
			sos.close();
			log.info("生成二维码成功：code："+codeMap.get("code").toString());
		} catch (IOException e) {
			log.info("生成验证码失败");
			e.printStackTrace();
		}
	}

//	//对外获取缓存
//	@RequestMapping(value = "logb",method = RequestMethod.POST) // , method = RequestMethod.POST)
//	@ResponseBody
//	public String logb(HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		String userInfo= (String)session.getAttribute(SystemConstants.SESSION_KEY);
//		StringBuilder sb = new StringBuilder();
//		if(userInfo!=null)
//		{
//			sb.append(session.getId()).append(";").append(session).append(";").append(session.getCreationTime()).append(";").append(JSON.parseObject(userInfo,User.class).toString());
//		}
//		return sb.toString();
//	}
}

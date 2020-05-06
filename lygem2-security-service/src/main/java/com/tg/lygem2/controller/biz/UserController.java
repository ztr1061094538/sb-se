package com.tg.lygem2.controller.biz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageInfo;
import com.tg.lygem2.bean.User;
import com.tg.lygem2.constant.ErrorCodeMsgEnum;
import com.tg.lygem2.exception.UsernameIsExitedException;
import com.tg.lygem2.service.IMailBiz;
import com.tg.lygem2.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/users")
@Api("用户操作")
public class UserController extends BaseController {

	@Autowired
	private RedisTemplate<Object, Object> redisTemplater;
	
	@Autowired
	private IMailBiz iMailBiz;
	
	private static String dictionary_prefix = "epp-auth:sms:phone";
	
	private static long exists_time = 120 * 1000;

    @PostMapping("/register")
    @ApiOperation(value = "用户注册", notes = "")
    @Transactional
    public Result<?> signup(@RequestBody LoginUser user) {
		if (null == user || StringUtils.isBlank(user.getName()) || StringUtils.isBlank(user.getPhone())
				|| StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())
				|| null == user.getUser_type() || StringUtils.isBlank(user.getEnterprise_id())) {
			return Result.instance(ErrorCodeMsgEnum.PARAMS_ERROR.getCode(), ErrorCodeMsgEnum.PARAMS_ERROR.getMsg());
		}
		
		User oldUser = userService.selectByUserPhone(user.getPhone());
		if (null != oldUser) {
			return Result.instance(ErrorCodeMsgEnum.REGISTER_ERROR.getCode(), ErrorCodeMsgEnum.REGISTER_ERROR.getMsg());
		}
    	
        if (userService.isExistUser(user.getUsername())) {
            throw new UsernameIsExitedException("用户已经存在");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if (userService.registerUser(user)) {
            if (bridgService.saveRegisterUserOfRole(user))
                return Result.instance(ErrorCodeMsgEnum.SUCESS.getCode(), "用户注册成功");
        }
        return Result.instance(ErrorCodeMsgEnum.INNER_ERROR.getCode(), "用户注册失败");
    }

    @PostMapping("/changePassWord")
    @ApiOperation(value = "", notes = "")
    @Transactional
    public Result<?> updatePassword(@RequestBody EditPassWord editPassWord) {
        if (StringUtils.isNotBlank(editPassWord.getPassword()) && StringUtils.isNotBlank(editPassWord.getOldPassword())) {
            User user = userService.selectUser(editPassWord.getUserName());
            if(!bCryptPasswordEncoder.matches(editPassWord.getOldPassword(), user.getPassword())){
                return Result.instance(ErrorCodeMsgEnum.INNER_ERROR.getCode(), "您传入旧密码不匹配，请重试");
            }
            LoginUser loginUser = new LoginUser();
            loginUser.setId(user.getId());
            loginUser.setPassword(bCryptPasswordEncoder.encode(editPassWord.getPassword()));
            if (userService.updatePassword(loginUser)) {
                return Result.instance(ErrorCodeMsgEnum.SUCESS.getCode(), "用户更新成功");
            }
            return Result.instance(ErrorCodeMsgEnum.INNER_ERROR.getCode(), "用户更新失败");
        }
        return Result.instance(ErrorCodeMsgEnum.INNER_ERROR.getCode(), "传入密码为空");
    }

    @PostMapping("/update")
    @ApiOperation(value = "用户更新", notes = "")
    @Transactional
    public Result<?> updateUser(@RequestBody LoginUser user) {
    	User old_user = userService.findUserById(user.getId());
		if (!old_user.getPhone().equals(user.getPhone())) {
			User new_user = userService.selectByUserPhone(user.getPhone());
			if (null != new_user) {
				return Result.instance(ErrorCodeMsgEnum.REGISTER_ERROR.getCode(), ErrorCodeMsgEnum.REGISTER_ERROR.getMsg());
			}
		}
		
		if (!old_user.getUsername().equals(user.getUsername())) {
			if (userService.isExistUser(user.getUsername())) {
				return Result.instance(ErrorCodeMsgEnum.ERROR_HAS_LOWER.getCode(), "用户已经存在");
			}
		}
    	
        if (user.getChangPs()) {
            user.setPassword(bCryptPasswordEncoder.encode("123456"));
            userService.updatePassword(user);
        }
        if (userService.updateUser(user)) {
//            if(user.getIsChangRoleIds()){
//                bridgService.updateUserOfRole(user);
//            }
            bridgService.updateUserOfRole(user);
            return Result.instance(ErrorCodeMsgEnum.SUCESS.getCode(), "用户更新成功");
        }
        return Result.instance(ErrorCodeMsgEnum.INNER_ERROR.getCode(), "用户更新失败");
    }

    @PostMapping("/getAll")
    @ApiOperation(value = "查询用户列表", notes = "")
    @Transactional
    public String findAll(@RequestBody UserCondition userCondition) throws JsonProcessingException {
        List<LoginUser> all = userService.findAll(userCondition);
        PageInfo<LoginUser> loginUserPageInfo = new PageInfo<>(all);
        return objectMapper.writeValueAsString(new Result<List<LoginUser>>(ErrorCodeMsgEnum.SUCESS.getCode(), ErrorCodeMsgEnum.SUCESS.getMsg(), all, loginUserPageInfo.getTotal()));
    }
    
	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "发送短信", notes = "")
	public CommonResponse sendMessage(@RequestBody EditPassWordByPhoneVO req) {
		try {
			if (null == req || StringUtils.isBlank(req.getPhone())) {
				return new CommonResponse(ErrorCodeMsgEnum.PARAMS_ERROR.getCode(), ErrorCodeMsgEnum.PARAMS_ERROR.getMsg());
			}
			User user = userService.selectByUserPhone(req.getPhone());
			if (null == user) {
				return new CommonResponse(ErrorCodeMsgEnum.UNREGISTER_ERROR.getCode(), ErrorCodeMsgEnum.UNREGISTER_ERROR.getMsg());
			}
			
			String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			StringBuilder sb = new StringBuilder(6);
			for (int i = 0; i < 6; i++) {
				char ch = str.charAt(new Random().nextInt(str.length()));
				sb.append(ch);
			}
			String verificationCode = sb.toString();
			Object object = redisTemplater.opsForValue().get(dictionary_prefix + req.getPhone());
			if (object != null) {
				ResponseVO<String> sendSms1 = new ResponseVO<String>();
				sendSms1.setCode(ErrorCodeMsgEnum.IS_SEND_ERROR.getCode());
				sendSms1.setMsg("已发送过,请查看手机");
				return sendSms1;
			}
			List<String> mobiles = new ArrayList<String>();
			mobiles.add(req.getPhone());
			CommonResponse sendSms = iMailBiz.sendSms(mobiles, "您好，您的验证码是：" + verificationCode);// 发送短信
			if (sendSms.getMsg().startsWith("success")) {
				redisTemplater.opsForValue().set(dictionary_prefix + req.getPhone(), verificationCode, exists_time,
						TimeUnit.MILLISECONDS);// 将验证信息存入redis
				sendSms.setCode(ErrorCodeMsgEnum.SUCESS.getCode());
			} else {
				sendSms.setCode(ErrorCodeMsgEnum.SMS_SEND_ERROR.getCode());
			}
			return sendSms;
		} catch (Exception ex) {
			log.info("未知错误");
			return new ResponseVO<String>(ErrorCodeMsgEnum.INNER_ERROR.getCode(), "未知错误");
		}
	}
	
    @PostMapping("/changePasswordByPhone")
    @ApiOperation(value = "手机号找回密码", notes = "")
	public CommonResponse changePassWordByPhone(@RequestBody EditPassWordByPhoneVO req) {
		if (null == req || StringUtils.isBlank(req.getPhone()) || StringUtils.isBlank(req.getSms_code())
				|| StringUtils.isBlank(req.getNew_password()) || StringUtils.isBlank(req.getNew_password_again())) {
			return new CommonResponse(ErrorCodeMsgEnum.PARAMS_ERROR.getCode(), ErrorCodeMsgEnum.PARAMS_ERROR.getMsg());
		}
		if (!req.getNew_password_again().equals(req.getNew_password())) {
			return new CommonResponse(ErrorCodeMsgEnum.MODIFYPW_DIFFER_ERROR.getCode(), ErrorCodeMsgEnum.MODIFYPW_DIFFER_ERROR.getMsg());
		}

		Object object = redisTemplater.opsForValue().get(dictionary_prefix + req.getPhone());
		if (object != null) {
			if (!req.getSms_code().equals((String) object)) {
				return new CommonResponse(ErrorCodeMsgEnum.SMSCODE_ERROR.getCode(), ErrorCodeMsgEnum.SMSCODE_ERROR.getMsg());
			}
		} else {
			return new CommonResponse(ErrorCodeMsgEnum.SMSCODE_TIMEOUT_ERROR.getCode(), ErrorCodeMsgEnum.SMSCODE_TIMEOUT_ERROR.getMsg());
		}
		User user = userService.selectByUserPhone(req.getPhone());
		if (null == user) {
			return new CommonResponse(ErrorCodeMsgEnum.UNREGISTER_ERROR.getCode(), ErrorCodeMsgEnum.UNREGISTER_ERROR.getMsg());
		}
		LoginUser loginUser = new LoginUser();
		loginUser.setId(user.getId());
		loginUser.setPassword(bCryptPasswordEncoder.encode(req.getNew_password()));
		if (userService.updatePassword(loginUser)) {
			return new CommonResponse(ErrorCodeMsgEnum.SUCESS.getCode(), "用户密码更新成功");
		} else {
			return new CommonResponse(ErrorCodeMsgEnum.BG_DATABASE_ERROR.getCode(), "用户更新失败");
		}
	}
    
    @PostMapping("/del")
    @ApiOperation(value = "删除用户列表", notes = "")
    public CommonResponse delByIds(@RequestBody List<Integer> list) {
    	boolean b = userService.delByIds(list);
    	if (b == true) {
			bridgService.delByUserIds(list);
			return new CommonResponse(ErrorCodeMsgEnum.SUCESS.getCode(), "用户删除成功");
		}else {
			return new CommonResponse(ErrorCodeMsgEnum.BG_DATABASE_ERROR.getCode(), "用户删除失败");
		}
	}

}
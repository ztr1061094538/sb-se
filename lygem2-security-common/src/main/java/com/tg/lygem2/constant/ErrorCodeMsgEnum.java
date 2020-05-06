package com.tg.lygem2.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCodeMsgEnum {
	SUCESS(0,"成功"),
	AUTH_ERROR(101,"没有授权"),
	SIGN_ERROR(102,"数字签名校验失败"),
	REQUEST_REPEAT_ERROR(103,"重复请求"),
	REQUEST_OVERTIME_ERROR(104,"请求过期，当前请求时间与系统时间相差超过10分钟"),
	MATCHING_ERROR(105,"当前上报方报文cid与秘钥不匹配"),
	PARSE_ERROR(201,"数据解析失败"),
	DECRYPT_ERROR(202,"报文解密失败"),
	INNER_ERROR(500,"服务器错误"),
	UPLOAD_ERROR(501,"上传文件错误"),
	BG_DATABASE_ERROR(502,"操作数据库失败"),
	BG_GETDATA_ERROR(503,"获取数据失败"),
	VALIDATE_ERROR(504,"数据校验错误"),
	EXCEL_ERROR(505,"excel解析报错"),
	PARAMS_ERROR(506,"接口参数错误"),
	ERROR_HAS_LOWER(507,"存在下级单位，不允许删除"),
	ERROR_HAS_NODATE(508,"查询无结果"),
	ERROR_IS_ADMIN(509,"查询系统数据，不允许更改"),
	DAY_IS_OVER(510,"查询天数超出长度"),
	ERROR_IS_PARENT(511,"机构不允许挂在子机构下"),
	ERROR_IS_HASE(512,"已存在"),
	ERROR_NO_HASE(513,"不存在"),
	ERROR_TOO_MUCH(514,"超出数量"),
	HAS_NO_FILE(515,"文件不存在"),
	FORBID_UPDATE_SEND(516,"禁止修改已发送消息"),
	NO_ALLOW_CLEAN(517,"不存在清洗计划，不允许清洗"),
	ERROR_HAS_USERS(518,"存在用户，不允许删除"),
	ERROR_HAS_MONITORS(519,"存在设备，不允许删除"),
	SMS_SEND_ERROR(600,"短信发送失败"),
	MODIFYPW_DIFFER_ERROR(601,"两次密码不一致"),
	SMSCODE_TIMEOUT_ERROR(602,"短信验证码失效"),
	SMSCODE_ERROR(603,"短信验证码错误"),
	UNREGISTER_ERROR(604,"当前手机号未注册"),
	REGISTER_ERROR(605,"当前手机号已注册"),
	IS_SEND_ERROR(606,"当前手机号已发送过");
	
	private int code;
	private String msg;
	
	public static ErrorCodeMsgEnum parse(int code) {
		for (ErrorCodeMsgEnum entity : ErrorCodeMsgEnum.values()) {
			if (code == entity.getCode()) {
				return entity;
			}
		}
		return null;
	}
}

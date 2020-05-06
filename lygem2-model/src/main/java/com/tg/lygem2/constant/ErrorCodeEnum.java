package com.tg.lygem2.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCodeEnum {
	SUCESS(0,"成功"),
	AUTH_ERROR(101,"没有授权"),
	SIGN_ERROR(102,"数字签名校验失败"),
	REQUEST_REPEAT_ERROR(103,"重复请求"),
	REQUEST_OVERTIME_ERROR(104,"请求过期，当前请求时间与系统时间相差超过10分钟"),
	MATCHING_ERROR(105,"当前上报方报文cid与秘钥不匹配"),
	NO_AUTH(107,"没有权限"),
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
	INVALID_PARAM(515,"文件不存在"),
	FORBID_UPDATE_SEND(516,"禁止修改已发送消息"),
	NO_ALLOW_CLEAN(517,"不存在清洗计划，不允许清洗"),
	ERROR_HAS_USERS(518,"存在用户，不允许删除"),
	ERROR_HAS_MONITORS(519,"存在下级设备，不允许删除"),
	ERROR_CLEAN_TIME(520,"清洗时间错误"),
	CATERING_TYPE_HAS_EXISTED(521,"餐饮类型已存在"),
	CATERING_TYPE_CANNOT_UPDATE(522,"餐饮类型不能修改"),
	ERROR_HAS_CONTACT(523, "存在联系人，不允许删除"),
	ERROR_MONITOR_TYPE(600,"设备类型错误"),
	ERROR_MONITOR_PARENT(601,"设备所属上级错误"),
	ERROR_HAS_ELECTRICID(602,"电力监测编号已存在"),
	ERROR_HAS_LAMPBLACKID(603,"浓度监测编号已存在"),
	ERROR_NO_PARENT(604, "上级设备不存在"),
	ERROR_PARENT(605, "选择上级错误"),
	ERROR_UPDATE_MONITORTYPE(606, "不允许修改设备类型"),
	ERROR_HAS_MN(607,"mn已存在"),
	ERROR_UPDATE_LAST_CLEAN_TIME(608,"该净化器已填报清洗，禁止修改上次清洗日期");

	private int code;
	private String msg;
	
	public static ErrorCodeEnum parse(int code) {
		for (ErrorCodeEnum entity : ErrorCodeEnum.values()) {
			if (code == entity.getCode()) {
				return entity;
			}
		}
		return null;
	}

}

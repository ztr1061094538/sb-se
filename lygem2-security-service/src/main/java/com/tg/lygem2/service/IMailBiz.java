package com.tg.lygem2.service;

import com.tg.lygem2.vo.CommonResponse;

import java.util.List;
import java.util.Map;

public interface IMailBiz 
{
	/**
	 * 使用模板发送邮件
	 * @param from 发件人
	 * @param to 收件人
	 * @param subject 主题
	 * @param templateSource 模板名称
	 * @param params 模板参数
	 * @return
	 */
	boolean sendMailWithTemplate(String from, String[] to, String subject, String templateSource, Map<String, Object> params);

	/**
	 * 发送邮件
	 * @param from 发件人
	 * @param to 收件人
	 * @param subject 主题
	 * @param text 内容
	 * @param htmlFlag 是否包含html内容
	 * @return
	 */
	boolean sendMail(String from, String[] to, String subject, String text, boolean htmlFlag);

	/**
	 * 发送短信
	 */
	CommonResponse sendSms(List<String> mobile, String body);
}

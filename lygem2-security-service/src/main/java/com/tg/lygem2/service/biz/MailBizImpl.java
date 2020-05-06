package com.tg.lygem2.service.biz;

import com.tg.lygem2.constant.ErrorCodeMsgEnum;
import com.tg.lygem2.service.IMailBiz;
import com.tg.lygem2.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.stream.Collectors.joining;

@Service
@Slf4j
public class MailBizImpl implements IMailBiz {
	private ExecutorService pool = Executors.newFixedThreadPool(10);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateEngine templateEngine;

	@Value("${tg.sms.timeOut}")
	private String timeOut;

	@Value("${tg.sms.encode}")
	private String encode;

	@Value("${tg.sms.username}")
	private String username;

	@Value("${tg.sms.password}")
	private String password_md5;

	@Value("${tg.sms.apikey}")
	private String apikey;

	@Override
	public boolean sendMailWithTemplate(String from, String[] to, String subject, String templateSource,
			Map<String, Object> params) {
		String mailcontent = build(templateSource, params);
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom(from);
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			messageHelper.setText(mailcontent, true);
		};
		try {
			pool.execute(new Runnable() {
				@Override
				public void run() {
					mailSender.send(messagePreparator);
				}
			});
			return true;
		} catch (MailException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 根据模板名称生成内容
	 * 
	 * @param templateSource
	 * @param params
	 * @return
	 */
	private String build(String templateSource, Map<String, Object> params) {
		Context context = new Context();
		for (Entry<String, Object> entry : params.entrySet()) {
			context.setVariable(entry.getKey(), entry.getValue());
		}
		return templateEngine.process(templateSource, context);
	}

	@Override
	public boolean sendMail(String from, String[] to, String subject, String text, boolean htmlFlag) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom(from);
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			messageHelper.setText(text, htmlFlag);
		};
		try {
			pool.execute(new Runnable() {
				@Override
				public void run() {
					mailSender.send(messagePreparator);
				}
			});
			return true;
		} catch (MailException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public CommonResponse sendSms(List<String> mobile, String content) {
		System.setProperty("sun.net.client.defaultConnectTimeout", "3000");
		System.setProperty("sun.net.client.defaultReadTimeout", "3000");
		StringBuffer buffer = new StringBuffer();
		try {
			String contentUrlEncode = URLEncoder.encode(content, encode);
			String newmobile = mobile.stream().collect(joining(","));
			buffer.append("http://m.5c.com.cn/api/send/index.php?username=" + username + "&password_md5=" + password_md5
					+ "&mobile=" + newmobile + "&apikey=" + apikey + "&content=" + contentUrlEncode + "&encode="
					+ encode);
			URL url = new URL(buffer.toString());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Connection", "Keep-Alive");
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String result = reader.readLine();
			return new CommonResponse(ErrorCodeMsgEnum.SUCESS.getCode(), result);
		} catch (Exception e) {
			log.error("em2-sys-service[sendSmsMethod]", e);
			return new CommonResponse(ErrorCodeMsgEnum.INNER_ERROR.getCode(), e.getMessage());
		}
	}

}

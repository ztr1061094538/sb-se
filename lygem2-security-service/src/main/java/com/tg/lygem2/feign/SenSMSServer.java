package com.tg.lygem2.feign;

import com.tg.lygem2.service.IMailBiz;
import com.tg.lygem2.vo.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feign/senSMS/")
public class SenSMSServer {
	@Autowired
	private IMailBiz mailBiz;

	@RequestMapping(value = "/sendSms", method = RequestMethod.POST, consumes = "application/json")
	public CommonResponse getAccountByUsername(@RequestParam("mobi") List<String> mobi,
			@RequestParam("body") @RequestBody String body) {
		return mailBiz.sendSms(mobi, body);
	}
}

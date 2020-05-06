package com.tg.lygem2.feign;

import com.tg.lygem2.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

public interface ISecurityServiceBiz 
{
	@PostMapping("/verifyUri")
    Result<Object> securityUri(@RequestHeader("Authorization") String token, @RequestHeader("uri") String securityUri, @RequestHeader("cid") String cid);
}

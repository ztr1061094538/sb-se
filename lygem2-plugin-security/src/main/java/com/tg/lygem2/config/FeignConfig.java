package com.tg.lygem2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.tg.lygem2.feign.ISecurityServiceBiz;
import com.tg.lygem2.vo.Result;
import com.tg.plugin.feign.client.FeignFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ProxyVerfiyAspect.class)
public class FeignConfig 
{
    @Value("${em2.security.url}")
    private String securityUrl;

    @Bean
    public ISecurityServiceBiz loginInvoke(FeignFactory factory) 
    {
        return factory.create(securityUrl, ISecurityServiceBiz.class);
    }

    @Bean
    public ThreadLocal<Result<Object>> threadLocal(){
        return new ThreadLocal<Result<Object>>();
    }
    @Bean("feginObjectMapper")
    public ObjectMapper objectMapper()
    {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        mapper.registerModule(simpleModule);
        return mapper;
    }
}
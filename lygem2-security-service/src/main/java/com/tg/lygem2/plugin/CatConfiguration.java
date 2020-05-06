package com.tg.lygem2.plugin;

import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatConfiguration 
{
	@Bean
    public Interceptor[] interceptors(DataSourceProperties properties) throws Exception {
        return new Interceptor[]{new CatMybatisInterceptor(properties.getUrl())};
    }
}

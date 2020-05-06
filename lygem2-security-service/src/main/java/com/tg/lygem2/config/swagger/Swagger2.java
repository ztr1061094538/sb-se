package com.tg.lygem2.config.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-10-10
 **/
@Configuration
@EnableSwagger2
public class Swagger2
{
	@Value("${springfox.swagger.host}")
	private String swaggerhost;
    @Bean
    public Docket createRestApi()
    {
    	Docket docket = new Docket(DocumentationType.SWAGGER_2);
    	if (!swaggerhost.equals("null"))
    	{
    		docket .host(swaggerhost);
    	}
//                .pathMapping("/auth")
    	return docket.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tg.lygem2"))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("鉴权中心")
                .description("数据中心")
                .contact(new Contact("Jikai.Sun","","sunjk@cn95598.com"))
                .version("1.0")
                .build();
    }
}

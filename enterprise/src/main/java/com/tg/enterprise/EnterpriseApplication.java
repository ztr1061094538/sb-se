package com.tg.enterprise;

import com.tg.lygem2.adapt.EnableSecurity;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication()
@Slf4j
@MapperScan("com.tg.enterprise.dao")
@EnableSecurity
public class EnterpriseApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(EnterpriseApplication.class, args);
    }
}

package com.tg.lygem2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@MapperScan("com.tg.lygem2.bean.mapper")
public class Lygem2SecurityServiceApplication {
    @Bean
    public ThreadLocal<String> threadLocal(){
        return new ThreadLocal<String>();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(Lygem2SecurityServiceApplication.class, args);
    }

}

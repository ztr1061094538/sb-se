package com.tg.lygem2.adapt;

import com.tg.lygem2.config.FeignConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({FeignConfig.class})
public @interface EnableSecurity
{

}

package com.tg.enterprise.config;

import java.lang.annotation.*;

/**
 * @program: em2-parent
 * @author: jikai.sun
 * @create: 2018-12-11
 **/
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VerifyCommitDate {
}

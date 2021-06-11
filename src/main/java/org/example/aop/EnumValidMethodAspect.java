package org.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.example.config.EnumValidAOP;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class EnumValidMethodAspect {
    @Pointcut(value = "@annotation(org.example.config.EnumValidAOP)")
    public void access() {
        System.out.println("access");
    }

    @Before("access()")
    public void before() {
        System.out.println("before...");
    }

    @Around("@annotation(enumValidAOP)")
    public Object around(ProceedingJoinPoint pj, EnumValidAOP enumValidAOP) {
        return "";
    }

}

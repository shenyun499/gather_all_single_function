package pers.xue.skills.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import pers.xue.skills.config.EnumValidAOP;

@Component
@Aspect
public class EnumValidMethodAspect {
    @Pointcut(value = "@annotation(pers.xue.skills.config.EnumValidAOP)")
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

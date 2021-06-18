package pers.xue.skills.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class EnumValidFieldAspect {
    @Pointcut("execution(* pers.xue.skills.controller..*.*(..))")
    public void pointcut() {
        System.out.println("access");
    }

    @Before("pointcut()")
    public void before(JoinPoint point) {
        // 获得参数
        Object[] args = point.getArgs();
        // 切入的目标对象
        Object target = point.getTarget();
        // 切入的方法
        Method method = ((MethodSignature) point.getSignature()).getMethod();
    }

/*    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pj) {
        // 获得参数
        Object[] args = pj.getArgs();
        // 切入的目标对象
        Object target = pj.getTarget();
        // 切入的方法
        Method method = ((MethodSignature) pj.getSignature()).getMethod();
        return "";
    }*/

}

package pers.xue.skills.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.lang.annotation.Annotation;
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


        Annotation[] annotations = null;

        // 切入的目标对象
        Object target = point.getTarget();
        // 切入的方法
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                Annotation ann = parameterAnnotations[i][j];
                Validated validatedAnn = (Validated) AnnotationUtils.getAnnotation(ann, Validated.class);
                if (validatedAnn != null || ann.annotationType().getSimpleName().startsWith("Valid")) {
                    Object hints = validatedAnn != null ? validatedAnn.value() : AnnotationUtils.getValue(ann);
                    Object[] validationHints = hints instanceof Object[] ? (Object[])((Object[])hints) : new Object[]{hints};
                    break;
                }
            }
        }


        // 下面这段代码何来？
        // org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver.validateIfApplicable
        // 其实就是判断参数有没有@Valid注解，对加了此注解的进行校验
        // 关于注解（如@Size/@Max等）更多想了解的，甚至源码，后面持续更新
        Annotation[] var4 = annotations;
        int var5 = annotations.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Annotation ann = var4[var6];
            Validated validatedAnn = (Validated) AnnotationUtils.getAnnotation(ann, Validated.class);
            if (validatedAnn != null || ann.annotationType().getSimpleName().startsWith("Valid")) {
                Object hints = validatedAnn != null ? validatedAnn.value() : AnnotationUtils.getValue(ann);
                Object[] validationHints = hints instanceof Object[] ? (Object[])((Object[])hints) : new Object[]{hints};
                break;
            }
        }

    }

/*    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pj) {
        // 获得参数
        Object[] args = pj.getArgs();
        // 切入的目标对象
        Object target = pj.getTarget();
        // 切入的方法
        Method method = ((MethodSignature) pj.getSignature()).getMethod().;
        return "";
    }*/

}

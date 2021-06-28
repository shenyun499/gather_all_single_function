package pers.xue.skills.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import pers.xue.skills.config.EnumValidAOP;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Component
@Aspect
public class EnumValidFieldAspect {
    @Pointcut("execution(* pers.xue.skills.controller..*.*(..))")
    public void pointcut() {
        System.out.println("access");
    }

    @Before("pointcut()")
    public void before(JoinPoint point) throws IllegalAccessException {
        // 切入的方法
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                Annotation ann = parameterAnnotations[i][j];
                String anName = ann.annotationType().getSimpleName();
                if (anName.startsWith("Valid") || anName.startsWith("Validated")) {
                    // 存在校验，进行校验
                    isValid(point.getArgs());
                    return;
                }
            }
        }


        // 下面这段代码何来？
        // org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver#validateIfApplicable
        // 其实就是判断参数有没有@Valid注解，对加了此注解的进行校验
        // 关于注解（如@Size/@Max等）更多想了解的，甚至源码，后面持续更新
    }

    private void isValid(Object[] args) throws IllegalAccessException {
        for (Object object : args) {
            Class<?> aClass = object.getClass();
            Field[] fields = aClass.getDeclaredFields();
            // 针对一个class 的所有field进行校验
            for (Field field : fields) {
                EnumValidAOP enumValid = field.getAnnotation(EnumValidAOP.class);
                // 为null则说明该字段不需要校验
                if (StringUtils.isEmpty(enumValid)) {
                    continue;
                }
                // 获取该字段的值
                field.setAccessible(true);
                String fieldValue = (String)field.get(object);
                Class<?> value = enumValid.value();
                // 是枚举则进入校验
                if (value.isEnum()) {
                    Object[] enumConstants = value.getEnumConstants();
                    boolean sign = false;
                    for (Object enumConstant : enumConstants) {
                        // 只要有一个匹配，就退出该字段的校验（tip：对于枚举类必须重写toString方法）
                        if (enumConstant.toString().equals(fieldValue)) {
                            sign = true;
                            break;
                        }
                    }
                    if (!sign) {
                        //todo: 不匹配，可以做点其他，这里暂时打印一下
                        System.out.println(fieldValue + "是一个错误值");
                        throw new RuntimeException(fieldValue + "is a incorrect value");
                    }
                }
            }
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

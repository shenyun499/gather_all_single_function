package org.example.datastructure.tools;


import com.sun.xml.internal.ws.util.StringUtils;

import lang.reflect.Method;
import util.Collection;

/**
 * 参数校验
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2020-07-15
 */
public class ValidateParam {
    /**
     * 校验必填参数
     *
     * @param object 待校验的类
     * @param fields 必填参数
     * @throws IllegalArgumentException 校验不通过会抛出的异常
     */
    @SuppressWarnings("unchecked")
    public static void validateParam(Object object, String... fields) throws Exception {
        //校验obj不能为空
        if (object == null) {
            throw new IllegalArgumentException("param is null!");
        }
        //Assert.notNull(object, "param is null!");

        if (fields == null || fields.length == 0) {
            return;//没有必填项直接返回
        }
        Class c = object.getClass();
        for (String field : fields) {
            Method method;
            String methodName = "get".concat(field.substring(0, 1).toUpperCase()).concat(field.substring(1, field.length()));
            try {
                method = c.getDeclaredMethod(methodName, null);
            } catch (NoSuchMethodException ne) {
                method = c.getSuperclass().getDeclaredMethod(methodName, null);
            }
            Object obj = method.invoke(object);
            if (obj == null) {
                throw new IllegalArgumentException(field + " is null!");
            }

            if (obj instanceof String && (obj.toString() == null || obj.toString().length() == 0 || obj.toString().trim().length() == 0)) {
                throw new IllegalArgumentException(field + " is null!");
            }

            if (obj instanceof Collection && ((Collection) obj).size() == 0) {
                throw new IllegalArgumentException(field + " is null!");
            }
        }
    }

}

//使用
/*try {
        CommonUtils.validateParam(对象, "属性1", "属性1");
        } catch（Exception e） {
        //有异常，说明校验不通过，
        }*/

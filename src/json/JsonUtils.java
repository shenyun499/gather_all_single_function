package json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * fast json操作
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2020-09-02
 */
public class JsonUtils {
    /**
     * 日志
     */
    protected static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    /**
     * Bean To Json 转化
     *
     * @param obj obj
     * @return Json
     */
    public static String toJSONPrettyFormat(Object obj) {
        return JSONObject.toJSONString(obj,
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.DisableCircularReferenceDetect
        );
    }

    /**
     * Bean To Json 转化
     *
     * @param obj obj
     * @return Json
     */
    public static String toJSON(Object obj) {
        return JSONObject.toJSONString(obj,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.DisableCircularReferenceDetect
        );
    }

    /**
     * Json To Bean 转化
     *
     * @param text     json字符串
     * @param objClass 目标类Class
     * @return 目标对象
     */
    public static <T> T toBean(String text, Class<T> objClass) {
        return JSON.parseObject(text, objClass);
    }

    /**
     * Object To Bean 转化
     *
     * @param object   json字符串
     * @param objClass 目标类Class
     * @return 目标对象
     */
    public static <T> T toBean(Object object, Class<T> objClass) {
        String json = toJSON(object);
        return JSON.parseObject(json, objClass);
    }

    /**
     * Object To JSONObject 转化
     *
     * @param object obj对象
     * @return JSONObject
     */
    public static JSONObject toJsonObject(Object object) {
        String json = null;
        if (object instanceof String) {
            json = String.valueOf(object);
        } else {
            json = toJSON(object);
        }
        return JSON.parseObject(json);
    }

    /**
     * Object To JSONArray 转化
     *
     * @param object obj对象
     * @return JSONArray
     */
    public static JSONArray toJsonArray(Object object) {
        String json = null;
        if (object instanceof String) {
            json = String.valueOf(object);
        } else {
            json = toJSON(object);
        }
        return JSONArray.parseArray(json);
    }

    /**
     * Object To ListJSONArray转化
     * @param object obj对象
     * @param clazz 转转类
     * @return List
     */
    public static <T> List<T> toList(Object object, Class<T> clazz) {
        String json = null;
        if (object instanceof String) {
            json = String.valueOf(object);
        } else {
            json = toJSON(object);
        }
        return JSONArray.parseArray(json, clazz);
    }
}

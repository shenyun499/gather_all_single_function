package org.example.datastructure.xmlandjavabean;

import org.springframework.beans.BeansException;
import org.springframework.util.StringUtils;

import util.List;

/**
 * bean复制
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-03-26
 */
public class BeanUtils {

    /**
     * 对象拷贝
     *
     * @param source 有值对象
     * @param target 需要被赋值的对象
     */
    public static void copyProperties(Object source, Object target) {
        try {
            if (!StringUtils.isEmpty(source)) {
                org.springframework.beans.BeanUtils.copyProperties(source, target);
            }
        } catch (BeansException e) {
        }
    }

    /**
     * 对象拷贝
     *
     * @param source 有值的集合
     * @param target 需要被赋值的集合
     * @param tType  需要被赋值的类型
     */
    public static <T, S> void copyPropertiesList(List<S> source, List<T> target, Class<T> tType) {
        try {
            for (S sItem : source) {
                T tItem = tType.newInstance();
                org.springframework.beans.BeanUtils.copyProperties(sItem, tItem);
                target.add(tItem);
            }
        } catch (Exception e) {
        }
    }
}

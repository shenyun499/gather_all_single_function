package xmlandjavabean;

import java.util.List;

/**
 * 封装DTO包名、类名、属性类型、属性名称
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-01-14
 */
public class JavaBeanDTO {
    /**
     * 类名
     */
    private String clazzName;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 属性类型集合
     */
    private List<String> fieldType;

    /**
     * 属性名称集合
     */
    private List<String> fieldName;

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<String> getFieldType() {
        return fieldType;
    }

    public void setFieldType(List<String> fieldType) {
        this.fieldType = fieldType;
    }

    public List<String> getFieldName() {
        return fieldName;
    }

    public void setFieldName(List<String> fieldName) {
        this.fieldName = fieldName;
    }
}

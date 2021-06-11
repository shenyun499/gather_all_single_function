package org.example.datastructure.xmlandjavabean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.xml.bind.annotation.XmlElement;
import io.File;
import io.FileOutputStream;
import io.IOException;
import io.OutputStreamWriter;
import lang.reflect.Field;
import lang.reflect.ParameterizedType;
import lang.reflect.Type;
import nio.charset.StandardCharsets;
import util.*;
import util.concurrent.ConcurrentHashMap;

/**
 * JavaBean 转成DTO工具类
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-01-12
 */
public class JavaBeanToDTO {

    private Logger logger = LoggerFactory.getLogger(JavaBeanToDTO.class);
    /**
     * 所有转换确定的类型容器。key：转换前的值 value：转换后的值
     */
    private Map<String, String> initTypeMap = null;

    /**
     * 保存生成DTO的属性名称，用于去重
     */
    private Set<String> dtoSet = null;

    /**
     * 保存所有最终需要生成的DTO集合
     */
    private List<JavaBeanDTO> dtoLists = new ArrayList<>();

    /**
     * 存放特殊需要继续扁平的对象名称
     */
    private List<String> pendLists = null;

    public JavaBeanToDTO() {
        initTypeMap = new ConcurrentHashMap<>(32);
        dtoSet = new HashSet<>(64);
        pendLists = new ArrayList<>();
        // 8大基本类型初始化入容器
        initTypeMap.put("char", "char");
        initTypeMap.put("byte", "byte");
        initTypeMap.put("short", "short");
        initTypeMap.put("int", "int");
        initTypeMap.put("long", "long");
        initTypeMap.put("float", "float");
        initTypeMap.put("double", "double");
        initTypeMap.put("boolean", "boolean");

        // 8大包装类类型初始化入容器
        initTypeMap.put("Character", "Character");
        initTypeMap.put("Byte", "Byte");
        initTypeMap.put("Short", "Short");
        initTypeMap.put("Integer", "Integer");
        initTypeMap.put("Long", "Long");
        initTypeMap.put("Float", "Float");
        initTypeMap.put("Double", "Double");
        initTypeMap.put("Boolean", "Boolean");

        // String、BegDecimal类型初始化入容器
        initTypeMap.put("String", "String");
        initTypeMap.put("BigDecimal", "BigDecimal");

        // 枚举--判断一下这个类是否是枚举在迭代，如果是枚举，则直接返回，替换类型为String
        initTypeMap.put("enum", "String");
        // XMLGregorianCalendar转成LocalDateTime
        initTypeMap.put("XMLGregorianCalendar", "LocalDateTime");
    }

    public static void main(String[] args) {
        JavaBeanToDTO javaBeanToDTO = new JavaBeanToDTO();
        //javaBeanToDTOUtils.generateDTOFiles("com.hisun.pojo.pacs00300107.Document", "Document", "xx");
        //javaBeanToDTOUtils.generateDTOFiles("com.hisun.pojo.fpsplst00100101.Document", "Document", "xx");
        javaBeanToDTO.generateDTOFiles("com.hisun.pojo.pacs00300107.Document",  "D:\\支付\\1月\\two\\demo\\src\\main\\java\\com\\demo\\dto");
    }

    /**
     * 根据类的全限定名称，生成DTO文件（函数入口）
     *
     * @param clazzAllName 类的全限定名称
     * @param generateFilePate 生成目录
     */
    //List<String> packageRoot
    public void generateDTOFiles(String clazzAllName, String generateFilePate) {
        logger.info("\ncom.demo.util.JavaBeanToDTOUtils.generateDTOFiles---开始生成DTO文件，生成的类为：" + clazzAllName + "，路径为：" + generateFilePate);

        // 初始化，将第一个需要扁平的对象填入集合
        pendLists.add(clazzAllName);

        // 获得所有需要生成的DTO
        while (!CollectionUtils.isEmpty(pendLists)) {
            JavaBeanDTO javaBeanDTO = new JavaBeanDTO();
            List<String> fieldType = new ArrayList<>();
            List<String> fieldName = new ArrayList<>();

            clazzAllName = pendLists.get(0);
            // 递归解析对象，得到最终扁平后的dtoSet（重）
            generateDTOList("", clazzAllName, fieldType, fieldName);

            // 保存一个DTO值
            int pos = clazzAllName.lastIndexOf(".");
            javaBeanDTO.setPackageName(clazzAllName.substring(0, pos));
            javaBeanDTO.setClazzName(clazzAllName.substring(pos + 1) + "DTO");
            javaBeanDTO.setFieldType(fieldType);
            javaBeanDTO.setFieldName(fieldName);
            dtoLists.add(javaBeanDTO);

            // 后置处理-1、清空原来dtoSet集合，2、pendLists清空已经生成的dto元素，
            postProcess();
        }
        logger.info("\ncom.demo.util.JavaBeanToDTOUtils.generateDTOFiles---待生成文件的DTOList集合生成为：" + dtoLists.toString());
        // 根据扁平后的dtoSet生成Java文件
        if (!CollectionUtils.isEmpty(dtoLists)) {
            for (JavaBeanDTO javaBeanDTO : dtoLists) {
                if (!generateDTOFile(javaBeanDTO, generateFilePate)) {
                    // 生成文件失败，提前结束
                    throw new RuntimeException("\ncom.demo.util.JavaBeanToDTOUtils.generateDTOFiles---生成文件失败");
                }
            }
        }
        logger.info("\ncom.demo.util.JavaBeanToDTOUtils.generateDTOFiles---DTO文件已经，生成的类为：" + clazzAllName + "，路径为：" + generateFilePate);
    }

    /**
     * 生成DTO类主要函数，递归函数
     *
     * @param parentName xml名称拼接
     * @param clazzAllName 类的全限定名称
     */
    public void generateDTOList(String parentName, String clazzAllName, List<String> fieldType, List<String> fieldName) {
        // 填充dtoSet的key:feildValue，value:feildType
        String feildType = null;
        String feildValue = null;

        try {
            Class<?> aClass = Class.forName(clazzAllName);
            String clazzName = this.formatClazzName(clazzAllName);
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                // 获取属性名称
                String type = this.formatClazzName(field.getType().getName());
                // 全限定名称
                String typeAll = field.getType().getName();
                // 拿到属性名称
                feildValue = field.getName();
                // 属性名称首字符转小写
                feildValue =  this.firstLower(feildValue);
                // 1、根据类型判断是否在初始化数组中，8大基本类型及包装类和String、BigDecimal、XMLGregorianCalendar
                if (initTypeMap.containsKey(type)) {
                    // 如果属性名称重复，则去重
                    if (dtoSet.contains(feildValue)) {
                        feildValue = preventRepeated(parentName, field.getName());
                    }
                    feildType = initTypeMap.get(type);
                    dtoSet.add(feildValue);
                    fieldType.add(feildType);
                    fieldName.add(feildValue);
                }
                // 2、枚举类特殊处理
                else if (isEnumType(typeAll)) {
                    // 如果属性名称重复，则去重
                    if (dtoSet.contains(feildValue)) {
                        feildValue = preventRepeated(parentName, field.getName());
                    }
                    feildType = initTypeMap.get("enum");
                    dtoSet.add(feildValue);
                    fieldType.add(feildType);
                    fieldName.add(feildValue);
                }
                // 3、List类型特殊处理
                else if ("List".equals(type)) {
                    // 得到List集合中实例具体类型 普通类型直接装入，特殊类型还需要处理
                    Type genericType = field.getGenericType();
                    ParameterizedType pt = (ParameterizedType) genericType;
                    // 得到对象list中实例的类型
                    Class clz = (Class) pt.getActualTypeArguments()[0];
                    type = formatClazzName(clz.getName());
                    // 组装List<XXDO>
                    if (initTypeMap.containsKey(type)) {
                        // 普通类型
                        feildType = initTypeMap.get(type);
                    } else if (isEnumType(typeAll)) {
                        // 枚举
                        feildType = initTypeMap.get("enum");
                    } else {
                        // TODO: 没考虑List<List<xxDO>>这种情况
                        // 对象-添加入pendList待处理集合，需要重新生成新的DTO
                        pendLists.add(clz.getName());
                        feildType = type + "DTO";
                    }
                    feildType = "List<" + feildType + ">";
                    // 如果属性名称重复，则去重
                    if (dtoSet.contains(feildValue)) {
                        feildValue = preventRepeated(parentName, field.getName());
                    }
                    dtoSet.add(feildValue);
                    fieldType.add(feildType);
                    fieldName.add(feildValue);
                }
                // 4、对象，递归处理
                else {
                    //读取注解，获取注解名称
                    XmlElement xmlElement = field.getAnnotation(XmlElement.class);
                    String xmlElementName = "";
                    if (!StringUtils.isEmpty(xmlElement)) {
                        xmlElementName = xmlElement.name();
                    }
                    parentName = formatParentName(parentName, xmlElementName);
                    generateDTOList(parentName, typeAll, fieldType, fieldName);
                }
            }
        } catch (ClassNotFoundException e) {
            logger.error("\ncom.demo.util.JavaBeanToDTOUtils.generateDTOList--包路径不存在：" + clazzAllName);
            e.printStackTrace();
        }
    }

    /**
     * 类型是否是枚举
     * @param clazzAllName
     * @return true枚举类/false非枚举类
     */
    public boolean isEnumType(String clazzAllName) {
        Class<?> aClass = null;
        try {
            aClass = Class.forName(clazzAllName);
        } catch (ClassNotFoundException e) {
            logger.error("\ncom.demo.util.JavaBeanToDTOUtils.isEnumType---包路径不存在：" + clazzAllName);
            e.printStackTrace();
        }
        return aClass.isEnum();
    }

    /**
     * 由全限定类名格式化得到类名
     * @param clazzName
     * @return
     */
    public String formatClazzName(String clazzName) {
        return clazzName.replaceAll(".*\\.","");
    }

    /**
     * 首字符转小写，满足驼峰命名
     *
     * @param clazzName
     * @return 首字母小写字符串
     */
    public String firstLower(String clazzName) {
        // 本来就是小写，直接返回
        if (Character.isLowerCase(clazzName.charAt(0))) {
            return clazzName;
        }
        return clazzName.replaceFirst(clazzName.substring(0, 1), clazzName.substring(0, 1).toLowerCase());
    }

    /**
     * 首字符转大写写，满足get/set
     *
     * @param feildName
     * @return 首字母小写字符串
     */
    public String firstUpper(String feildName) {
        // 本来就是小写，直接返回
        if (Character.isUpperCase(feildName.charAt(0))) {
            return feildName;
        }
        return feildName.replaceFirst(feildName.substring(0, 1), feildName.substring(0, 1).toUpperCase());
    }

    /**
     * 拼接新的xmlElement名称，提供给重复属性名截取
     *
     * @param parentName 父xmlElement节点名
     * @param xmlElementName 当前节点xmlElenment名
     * @return 拼接后的xmlElement节点名称
     */
    private String formatParentName(String parentName, String xmlElementName) {
        if (!StringUtils.isEmpty(parentName) && !StringUtils.isEmpty(xmlElementName)) {
            return parentName + "_" + xmlElementName;
        } else if (StringUtils.isEmpty(parentName)) {
            return xmlElementName;
        } else if (StringUtils.isEmpty(xmlElementName)) {
            return parentName;
        }
        return "";
    }

    /**
     * 得到不重复的名称，为了满足元数据平台dto不允许重复原则
     *
     * @param parentName 父传入xml名称组合
     * @param clazzName 当前类名称
     * @return 新的不重复的属性名称
     */
    public String preventRepeated(String parentName, String clazzName) {
        // 切割parent字符串，也就是上一层层element节点的名称集合
        String[] preParentNames = parentName.split("_");
        // 存放上一层element叠加名称(如上一层test1，上上一层test2，如果上一层拼接结果不符合，则采用test1test2拼接)
        StringBuffer preClazzName = new StringBuffer();
        String newClazzName = null;
        // 尝试拼接上一层Element并判断拼接后的名称是否满足不重复
        for (int i = preParentNames.length - 1; i >= 0; i--) {
            preClazzName.append(preParentNames[i]);
            // 新的属性名称
            newClazzName = preClazzName.toString() + clazzName;
            // 首字母小写满足驼峰命名
            newClazzName = this.firstLower(newClazzName);
            if (!dtoSet.contains(newClazzName)) {
                return newClazzName;
            }
        }
        // TODO: 不满足，抛出异常，后面可以加上随机数让其满足
        throw new RuntimeException("没有满足的名称");
    }

    /**
     * 后置处理-每次生成了一个DTO后需要清空dtoSet和pendLists第一个元素，以继续提供给后续生成DTO重复使用
     */
    public void postProcess() {
        // 清空dtoSet 元素
        dtoSet.clear();

        // 清掉pendLists第一个元素
        pendLists.remove(0);
    }

    /**
     * 生成单个DTO文件
     *
     * @param javaBeanDTO DTO信息
     * @param generateFilePate 输出路径
     * @return Boolean true生成成功，false生成失败
     */
    public Boolean generateDTOFile(JavaBeanDTO javaBeanDTO, String generateFilePate) {
        StringBuffer classBuffer = new StringBuffer();
        HashMap<String, String> impPackages = new HashMap<>();
        // 拼接 package包名
        int pos = javaBeanDTO.getPackageName().lastIndexOf(".");
        String suffixStr = javaBeanDTO.getPackageName().substring(pos + 1);
        classBuffer.append("package ").append("com.demo.dto.").append(suffixStr).append(";\n\n");

        // 生成DTO字段信息及get/set信息，顺便将需要导入的包生成在impPackages中
        StringBuffer resolveFieldAndSG = resolveFieldAndSG(javaBeanDTO, impPackages);

        // 拼接import信息，上面方法得到包信息
        if (!impPackages.isEmpty()) {
            for (String  impPackage : impPackages.values()) {
                classBuffer.append("import ").append(impPackage).append(";\n");
            }
            classBuffer.append("\n");
        }

        // 拼接public class +类信息
        classBuffer.append("public class ").append(javaBeanDTO.getClazzName()).append(" {\n\n");

        // 拼接前面生成的字段信息和set/get信息
        classBuffer.append(resolveFieldAndSG);

        // 拼接结尾信息
        classBuffer.append("}");
        // TODO: toString需求有需要在加
        // 生成Java文件
        return generateClassFile(classBuffer, generateFilePate + "\\" + suffixStr, javaBeanDTO.getClazzName());
    }

    /**
     * 返回DTO的字段信息和set/get信息
     * @param javaBeanDTO DTO信息
     * @param impPackages
     * @return
     */
    private StringBuffer resolveFieldAndSG(JavaBeanDTO javaBeanDTO, HashMap<String, String> impPackages) {
        String lineSpace = "    ";
        // 生成set方法 规则组合
        String setString = lineSpace + "public void set%s(%s %s){\n" +
                lineSpace + lineSpace + "this.%s = %s;\n" +
                lineSpace + "}\n\n";
        // 生成get方法 规则组合
        String getString = lineSpace + "public %s get%s(){\n" +
                lineSpace + lineSpace + "return this.%s;\n" +
                lineSpace + "}\n\n";
        StringBuffer fieldBuffer = new StringBuffer();
        StringBuffer sgBuffer = new StringBuffer();
        // 获取字段类型和字段名称
        List<String> fieldType = javaBeanDTO.getFieldType();
        List<String> fieldName = javaBeanDTO.getFieldName();
        for (int i = 0; i < fieldType.size(); i++) {
            // 获取字段属性和字段名称
            String fieldTp = fieldType.get(i);
            String fildNm = fieldName.get(i);
            // 根据字段属性判断是否需要导入包，要则存储
            if ("BigDecimal".equals(fieldTp)) {
                impPackages.put("BigDecimal", "math.BigDecimal");
            } else if ("LocalDateTime".equals(fieldTp)) {
                impPackages.put("LocalDateTime", "time.LocalDateTime");
            } else if (fieldTp.length() > 4 && "List".equals(fieldTp.substring(0, 4))) {
                impPackages.put("List", "util.*");
            }
            // 拼接字段属性和字段名称
            fieldBuffer.append(lineSpace).append("private ").append(fieldTp).append(" ").append(fildNm).append(";\n\n");

            // 拼接set get方法
            // 字段名称转成大写，满足set/get
            String newFiledName = this.firstUpper(fildNm);
            sgBuffer.append(String.format(setString, newFiledName, fieldTp, fildNm,
                    fildNm, fildNm));
            sgBuffer.append(String.format(getString, fieldTp, newFiledName, fildNm));
        }
        fieldBuffer.append(sgBuffer);
        return fieldBuffer;
    }

    /**
     * 生成Java类文件
     *
     * @param classBuffer 类字符串信息
     * @param generateFilePate  生成路径
     * @param fileName    文件名
     * @Return Boolean 成功true 失败false
     */
    public Boolean generateClassFile(StringBuffer classBuffer, String generateFilePate, String fileName) {
        File classPath = new File(generateFilePate);
        // 校验文件路径，不存在则创建
        if (!checkClassPath(classPath)) {
            logger.error("\ncom.demo.util.JavaBeanToDTOUtils-generateClassFile生成文件失败，请检查路径是否有权限创建、是否可以写");
            return false;
        }

        File classFile = new File(classPath, fileName + ".java");

        // 文件不存在，创建新的文件
        if (!classFile.exists()) {
            try {
                if (!classFile.createNewFile()) return false;
            } catch (IOException e) {
                logger.error("com.demo.util.JavaBeanToDTOUtils-generateClassFile生成文件失败", e);
                return false;
            }
        } else {
            // 文件存在，检查文件是否可以被覆盖
            if (!classFile.isFile() || !classFile.canWrite()) return false;
        }
        // 输出
        try(FileOutputStream fos = new FileOutputStream(classFile);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);) {
            osw.write(classBuffer.toString());
            osw.flush();
        } catch (IOException e) {
            logger.error("com.demo.util.JavaBeanToDTOUtils-generateClassFile生成文件失败", e);
        }
        return true;
    }

    /**
     * 校验类路径
     *
     * @param classPath 路径
     */
    private boolean checkClassPath(File classPath) {
        if (!classPath.exists() || !classPath.isDirectory()) {
            return classPath.mkdirs();
        }
        return classPath.canWrite();
    }
}

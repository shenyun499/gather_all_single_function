package path;

import tools.JudgeUtils;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 包路径操作
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-03-18
 */
public class PackageUtil {
    private static Map<String, Class<?>> operationXmlDocDOMap;

    private static Object lockLoadXmlDocDO = new Object();

    public static Map<String, Class<?>> getOperationXmlDocDOMap() throws ClassNotFoundException {
        if (operationXmlDocDOMap == null) {
            synchronized (lockLoadXmlDocDO) {
                if (operationXmlDocDOMap == null) {
                    operationXmlDocDOMap = new HashMap<>(32);
                }
                String packageName = "com.hisun.kont.ptgw.fps.javabean";
                // List<String> classNames = getClassName(packageName);
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                List<String> classNames = getClassName(packageName, true);
                if (classNames != null) {
                    for (String className : classNames) {
                        if(className.indexOf("Document")!=-1){
                            int firIndex = className.indexOf("com");
                            className = className.substring(firIndex);
                            Class<?> clazz = Class.forName(className);
                            XmlRootElement xmlRootElement = clazz.getAnnotation(XmlRootElement.class);
                            if(JudgeUtils.isEmpty(xmlRootElement)){
                                continue;
                            }
                            String namespace = xmlRootElement.namespace();
                            int be = namespace.lastIndexOf(":");
                            String FpsMsgNmId = namespace.substring(be+1,namespace.length());
                            if(JudgeUtils.isEmpty(FpsMsgNmId)){
                                continue;
                            }
                            String key = xmlRootElement.name();
                            key = FpsMsgNmId + "_" + key;
                            operationXmlDocDOMap.put(key,clazz);
                        }
                    }
                }
//                Map<String, Object> beansWithAnnotationMap = SpringContextsUtil1.getBeansWithAnnotation(XmlRootElement.class);
//                Class<? extends Object> clazz = null;
//
//                for (Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()) {
//                    clazz = entry.getValue().getClass();//获取到实例对象的class信息
//                    XmlRootElement xmlRootElement = clazz.getAnnotation(XmlRootElement.class);
//                    String namespace = xmlRootElement.namespace();
//                    int be = namespace.lastIndexOf(":");
//                    String FpsMsgNmId = namespace.substring(be+1,namespace.length());
//                    if (xmlRootElement == null) {
//                        continue;
//                    }
//                    String key = xmlRootElement.name();
//                    if (!StringUtils.isEmpty(FpsMsgNmId)) {
//                        key = FpsMsgNmId + "_" + key;
//                    }
//                    operationXmlDocDOMap.put(key, clazz);
//                }
            }
        }
        return operationXmlDocDOMap;
    }

    public static void main(String[] args) throws Exception {
        String packageName = "com.hisun.kont.pojo";
        // List<String> classNames = getClassName(packageName);
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        List<String> classNames = getClassName(packageName, true);
        if (classNames != null) {
            for (String className : classNames) {
                if(className.indexOf("Document")!=-1){

                }
            }
        }
    }

    /**
     * 获取某包下（包括该包的所有子包）所有类
     * @param packageName 包名
     * @return 类的完整名称
     */
    public static List<String> getClassName(String packageName) {
        return getClassName(packageName, true);
    }

    /**
     * 获取某包下所有类
     * @param packageName 包名
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    public static List<String> getClassName(String packageName, boolean childPackage) {
        List<String> fileNames = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");
        URL url = loader.getResource(packagePath);
        if (url != null) {
            String type = url.getProtocol();
            if (type.equals("file")) {
                fileNames = getClassNameByFile(url.getPath(), null, childPackage);
            } else if (type.equals("jar")) {
                fileNames = getClassNameByJar(url.getPath(), childPackage);
            }
        } else {
            fileNames = getClassNameByJars(((URLClassLoader) loader).getURLs(), packagePath, childPackage);
        }
        return fileNames;
    }

    /**
     * 从项目文件获取某包下所有类
     * @param filePath 文件路径
     * @param className 类名集合
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByFile(String filePath, List<String> className, boolean childPackage) {
        List<String> myClassName = new ArrayList<String>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                if (childPackage) {
                    myClassName.addAll(getClassNameByFile(childFile.getPath(), myClassName, childPackage));
                }
            } else {
                String childFilePath = childFile.getPath();
                if (childFilePath.endsWith(".class")) {
                    childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9, childFilePath.lastIndexOf("."));
                    childFilePath = childFilePath.replace("\\", ".");
                    myClassName.add(childFilePath);
                }
            }
        }

        return myClassName;
    }

    /**
     * 从jar获取某包下所有类
     * @param jarPath jar文件路径
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByJar(String jarPath, boolean childPackage) {
        List<String> myClassName = new ArrayList<String>();
        String[] jarInfo = jarPath.split("!");
        String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
        String packagePath = jarInfo[1].substring(1);
        try {
            JarFile jarFile = new JarFile(jarFilePath);
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                if (entryName.endsWith(".class")) {
                    if (childPackage) {
                        if (entryName.startsWith(packagePath)) {
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                            myClassName.add(entryName);
                        }
                    } else {
                        int index = entryName.lastIndexOf("/");
                        String myPackagePath;
                        if (index != -1) {
                            myPackagePath = entryName.substring(0, index);
                        } else {
                            myPackagePath = entryName;
                        }
                        if (myPackagePath.equals(packagePath)) {
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                            myClassName.add(entryName);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myClassName;
    }

    /**
     * 从所有jar中搜索该包，并获取该包下所有类
     * @param urls URL集合
     * @param packagePath 包路径
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByJars(URL[] urls, String packagePath, boolean childPackage) {
        List<String> myClassName = new ArrayList<String>();
        if (urls != null) {
            for (int i = 0; i < urls.length; i++) {
                URL url = urls[i];
                String urlPath = url.getPath();
                // 不必搜索classes文件夹
                if (urlPath.endsWith("classes/")) {
                    continue;
                }
                String jarPath = urlPath + "!/" + packagePath;
                myClassName.addAll(getClassNameByJar(jarPath, childPackage));
            }
        }
        return myClassName;
    }
}

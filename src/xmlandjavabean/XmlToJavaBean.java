//package xmlandjavabean;
//
//import com.hisun.kont.common.exception.KontException;
//import com.hisun.kont.common.utils.JudgeUtils;
//import com.hisun.kont.ptgw.fps.contants.MsgCdEnum;
//import com.hisun.kont.ptgw.fps.javabean.apphdr.BusinessApplicationHeaderV01;
//import com.hisun.kont.ptgw.fps.javabean.bizdata.BizData;
//import org.dom4j.Document;
//import org.dom4j.Element;
//import org.dom4j.io.SAXReader;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Unmarshaller;
//import javax.xml.transform.stream.StreamSource;
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.StringReader;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//
//@Component
//public class XmlToJavaBean {
//
//    private static final Logger logger = LoggerFactory.getLogger(XmlToJavaBean.class);
//
//    /**
//     * xml文件配置转换为对象
//     *
//     * @param xmlPath xml文件路径
//     * @return java对象
//     * @throws JAXBException
//     * @throws IOException
//     */
//    public static Object xmlToBean(String xmlPath, Class<?> load1, Class<?> load2) throws JAXBException {
//        JAXBContext context = JAXBContext.newInstance(load2);
//        Unmarshaller unmarshaller = context.createUnmarshaller();
//        Object object = unmarshaller.unmarshal(new File(xmlPath));
//        return object;
//    }
//
//    public Document getDocument(String xml) {
//        Document document = null;
//        try(ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes("UTF-8"))) {
//            SAXReader saxReader = new SAXReader();
//            document = saxReader.read(bais);
//        } catch (Exception ex) {
//            logger.error("com.hisun.kont.ptgw.fps.util.XmlToJavaBean.getDocument" + "解析xml文件错误");
//            KontException.throwKontException(MsgCdEnum.XML_TO_JAVABEAN_FAIL, ex);
//        }
//        return document;
//    }
//
//    /**
//     * 获取所有的BizData节点列表
//     *
//     * @return
//     */
//    public List<Element> getBizDataElement(Document document) {
//        List<Element> bizDataList = new LinkedList<>();
//
//        Element root = document.getRootElement();
//        //    removeNameSpace(root);
//        List<Element> msgBody = getMultipleElement(root, "MsgBody");
//        if (msgBody.size() == 0) {
//            return bizDataList;
//        }
//        bizDataList = getMultipleElement(msgBody.get(0), "BizData");
//
//        return bizDataList;
//    }
//
//    //删除指定子节点
//    public void upElement(Element element, String childrenName, Element upelement) {
//        List<Element> nodes = element.elements(childrenName);
//        if (nodes.size() == 0) {
//            nodes = element.elements();
//            for (Element e : nodes) {
//                upElement(e, childrenName, upelement);
//            }
//        } else {
//            for (Element e : nodes) {
//                element.remove(e);
//            }
//            element.add(upelement);
//        }
//
//    }
//
//    // 获取指定子节点
//    public List<Element> getElement(Element element, String childrenName) {
//        List<Element> nodes = element.elements(childrenName);
//        if (nodes.size() == 0) {
//            nodes = element.elements();
//            for (Element e : nodes) {
//                nodes = getElement(e, childrenName);
//            }
//        }
//        return nodes;
//
//    }
//
//    /**
//     * 获取指定节点指定名称的子节点
//     *
//     * @param element
//     * @param childrenName
//     * @return
//     */
//    public List<Element> getMultipleElement(Element element, String childrenName) {
//        List<Element> reles = new LinkedList<>();
//        for (Iterator<Element> rootlter = element.elementIterator(); rootlter.hasNext(); ) {
//            Element ele = rootlter.next();
//            if (!childrenName.equals(ele.getName())) {
//                continue;
//            }
//            reles.add(ele);
//        }
//        return reles;
//    }
//
//    /**
//     * 获取bizDataElement获取报文头对象
//     *
//     * @param bizDataElement
//     * @return
//     */
//    public Object getAppHdrByBizDataElement(Element bizDataElement) {
//        if (bizDataElement == null) {
//            return null;
//        }
//        List<Element> elements = getMultipleElement(bizDataElement, "AppHdr");
//        if (elements.size() == 0) {
//            return null;
//        }
//        Object appHdr = null;
//        // BusinessApplicationHeaderV01 appHdr = new BusinessApplicationHeaderV01();
//        try {
//            String appHdrStr = elements.get(0).asXML();
//            // 去除命名空间
//            // 拿到appHdr报文第一个>结尾的位置
//            int appFirEnId = appHdrStr.indexOf(">");
//            appHdrStr = "<AppHdr xmlns=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.01\">" + appHdrStr.substring(appFirEnId + 1);
//
//            appHdr = toBean(appHdrStr, BusinessApplicationHeaderV01.class);
//        } catch (Exception ex) {
//            logger.error("com.hisun.kont.ptgw.fps.util.XmlToJavaBean.getAppHdrByBizDataElement" + "解析xml文件错误", ex);
//            KontException.throwKontException(MsgCdEnum.XML_TO_JAVABEAN_FAIL, ex);
//        }
//        return appHdr;
//    }
//
//    public Object resolveXml(String msgNmId, Element element) throws Exception {
//        Object iFpsMsgDO = new Object();
//        Iterator rootlter = element.elementIterator();
//        while (rootlter.hasNext()) {
//            Element ele = (Element) rootlter.next();
//            Class<?> clazz = PackageUtil.getOperationXmlDocDOMap().get(ele.getName());
//            if (clazz == null) {
//                clazz = PackageUtil.getOperationXmlDocDOMap().get(msgNmId + "_" + ele.getName());
//            }
//            if (clazz == null) {
//                if (ele.elements().size() > 0) {
//                    iFpsMsgDO = resolveXml(msgNmId, ele);
//                }
//                continue;
//            }
//            iFpsMsgDO = toBean(ele.asXML(),clazz);
//        }
//        return iFpsMsgDO;
//    }
//
//    public <T> T toBean(String xml, Class<T> clazz) throws Exception {
//        JAXBContext context = JAXBContext.newInstance(clazz);
//        Unmarshaller unmarshaller = context.createUnmarshaller();
//        StringReader reader = new StringReader(xml);
//        T result = (T) unmarshaller.unmarshal(new StreamSource(reader));
//        reader.close();
//        return result;
//    }
//
//    public BizData generteJavaBean(String xml) {
//        Document document = getDocument(xml);
//        List<Element> bizDataElementList = getBizDataElement(document);
//        BusinessApplicationHeaderV01 appHdr = null;
//        Object doc = null;
//        for (Element bizdata : bizDataElementList) {
//            appHdr = (BusinessApplicationHeaderV01) getAppHdrByBizDataElement(bizdata);
//            try {
//                doc = resolveXml(appHdr.getMsgDefIdr(), bizdata);
//            } catch (Exception e) {
//                logger.error("com.hisun.kont.ptgw.fps.util.XmlToJavaBean.generteJavaBean" + "解析xml文件错误", e);
//                KontException.throwKontException(MsgCdEnum.XML_TO_JAVABEAN_FAIL, e);
//            }
//        }
//        // 如果解析后信息还是不存在，则报错
//        if (JudgeUtils.isNull(appHdr) || JudgeUtils.isNull(doc)) {
//            logger.info("com.hisun.kont.ptgw.fps.util.XmlToJavaBean.generteJavaBean" + "解析xml文件错误");
//            KontException.throwKontException(MsgCdEnum.XML_TO_JAVABEAN_FAIL);
//        }
//        return BizData.getInstance(appHdr, doc);
//    }
//}
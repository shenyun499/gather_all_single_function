//package org.example.datastructure.xmlandjavabean;
//
//
//import com.hisun.kont.common.exception.KontException;
//import com.hisun.kont.common.utils.JudgeUtils;
//import com.hisun.kont.ptgw.org.example.datastructure.file.bean.org.example.datastructure.entity.FpsCertDO;
//import com.hisun.kont.ptgw.org.example.datastructure.file.bean.mybatis.dao.FpsCertDao;
//import com.hisun.kont.ptgw.fps.contants.CommonConst;
//import com.hisun.kont.ptgw.fps.contants.MsgCdEnum;
//import com.hisun.kont.ptgw.fps.javabean.apphdr.BusinessApplicationHeaderV01;
//import com.hisun.kont.ptgw.fps.javabean.bizdata.BizData;
//import fps.FpsTransportSecurity;
//import fps.security.ITransportSecurity;
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;
//import org.dom4j.io.OutputFormat;
//import org.dom4j.io.XMLWriter;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
//import io.*;
//import nio.charset.StandardCharsets;
//import util.HashMap;
//import util.Map;
//
///**
// * JavaBean转成Xml文件
// *
// * @author ：HUANG ZHI XUE
// * @org.example.datastructure.date ：Create in 2021-01-19
// */
//@Component
//public class JavaBeanToXml {
////    @Resource
////    private FpsCertDao fpsCertDao;
//
//    @Value("${org.example.datastructure.file.genertexml.org.example.datastructure.path}")
//    private String generteFilePath;
//
//    private static final Map<String, String> nameSpaceMap = new HashMap<>();
//
//    private static final Logger logger = LoggerFactory.getLogger(JavaBeanToXml.class);
//
//    /**
//     * @param bizData
//     * @return 生成xml 字符串
//     * @throws DocumentException
//     * @throws JAXBException
//     * @throws IOException
//     */
//    public String generteXml(BizData bizData) throws JAXBException, DocumentException, IOException {
//        BusinessApplicationHeaderV01 appHdrBean = bizData.getAppHdr();
//        Object documentBean = bizData.getDoc();
//        // 拿到这个报文名称 如pacs003，先拼写生成xml文件名，后面再根据要求修改
//        String msgDefIdr = appHdrBean.getMsgDefIdr();
//        // 根据报文拿到命名空间
//        String nameSpace = msgDefIdr.substring(0, 3).equals("fps") ? CommonConst.FPSMSG_SCHEMA_LOCATION_ADRS : CommonConst.FPSMSG_SCHEMA_LOCATION;
//
//        // 拼接文件头
//        Element rootEl = DocumentHelper.createElement("FpsMsg");
//        rootEl.addNamespace("", nameSpace + msgDefIdr);
//        rootEl.addAttribute("xsi:schemaLocation", nameSpace + msgDefIdr + " " + msgDefIdr + ".xsd");
//        rootEl.addAttribute("xmlns:xsi", CommonConst.FPSMSG_XMLROOT_XSI);
//
//        // 添加证书节点
//        Element msgHdr = rootEl.addElement("MsgHdr", nameSpace + msgDefIdr);
//        Element signSN = msgHdr.addElement("SignSN");
//        Element encrptnSN = msgHdr.addElement("EncrptnSN");
//        msgHdr.addElement("Sign").setText("null");
//        msgHdr.addElement("Pwd").setText("null");
//        // 从数据库拿到最新证书信息，填充数据
//        FpsCertDO latestCert = fpsCertDao.getLatestCert();
//        if (JudgeUtils.isNull(latestCert)) {
//            KontException.throwKontException(MsgCdEnum.FPSCERT_RECORD_NOT_EXIEST);
//        }
//        signSN.setText(latestCert.getBankPrivateNo());
//        encrptnSN.setText(latestCert.getBankPublicNo());
//
//        // 添加业务节点
//        Element msgBody = rootEl.addElement("MsgBody", nameSpace + msgDefIdr);
//        Element bizDataEl = msgBody.addElement("BizData",nameSpace + msgDefIdr);
//        bizDataEl.add(this.getAppHdrOrDocumentXml(appHdrBean).getRootElement());
//        bizDataEl.add(this.getAppHdrOrDocumentXml(documentBean).getRootElement());
//
//        String tempMsgXml = rootEl.asXML();
//
//        // 去除节点命名空间AppHdr/Document
//        tempMsgXml = removeSpaceName(tempMsgXml);
//
//        tempMsgXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + tempMsgXml;
//        // 格式化报文
//        tempMsgXml = this.formatXmlStr(DocumentHelper.parseText(tempMsgXml));
//        logger.info("com.hisun.kont.ptgw.fps.util.JavaBeanToXml.generteXml，明文报文：" + tempMsgXml);
//        return tempMsgXml;
//    }
//
//    /**
//     * 生成xml文件
//     */
//    public Boolean generteXmlFiles(BizData bizData) throws JAXBException, DocumentException, IOException {
//        BusinessApplicationHeaderV01 appHdrBean = bizData.getAppHdr();
//        Object documentBean = bizData.getDoc();
//        // 拿到这个报文名称 如pacs003，先拼写生成xml文件名，后面再根据要求修改
//        String msgDefIdr = appHdrBean.getMsgDefIdr();
//        int prefix = msgDefIdr.indexOf(".");
//        String fileName = msgDefIdr.substring(0, prefix) + msgDefIdr.substring(prefix + 1, prefix + 4);
//
//        // 根据报文拿到命名空间
//        String nameSpace = msgDefIdr.substring(0, 3).equals("fps") ? CommonConst.FPSMSG_SCHEMA_LOCATION_ADRS : CommonConst.FPSMSG_SCHEMA_LOCATION;
//
//        // 拼接文件头
//        Element rootEl = DocumentHelper.createElement("FpsMsg");
//        rootEl.addNamespace("", nameSpace + msgDefIdr);
//        rootEl.addAttribute("xsi:schemaLocation", nameSpace + msgDefIdr + " " + msgDefIdr + ".xsd");
//        rootEl.addAttribute("xmlns:xsi", CommonConst.FPSMSG_XMLROOT_XSI);
//
//        // 添加证书节点
//        Element msgHdr = rootEl.addElement("MsgHdr", nameSpace + msgDefIdr);
//        Element signSN = msgHdr.addElement("SignSN");
//        Element encrptnSN = msgHdr.addElement("EncrptnSN");
//        msgHdr.addElement("Sign").setText("null");
//        msgHdr.addElement("Pwd").setText("null");
//        // 从数据库拿到最新证书信息，填充数据
//        FpsCertDO latestCert = fpsCertDao.getLatestCert();
//        if (JudgeUtils.isNull(latestCert)) {
//            KontException.throwKontException(MsgCdEnum.FPSCERT_RECORD_NOT_EXIEST);
//        }
//        signSN.setText(latestCert.getBankPrivateNo());
//        encrptnSN.setText(latestCert.getBankPublicNo());
//
//        // 添加业务节点
//        Element msgBody = rootEl.addElement("MsgBody", nameSpace + msgDefIdr);
//        Element bizDataEl = msgBody.addElement("BizData",nameSpace + msgDefIdr);
//        bizDataEl.add(this.getAppHdrOrDocumentXml(appHdrBean).getRootElement());
//        bizDataEl.add(this.getAppHdrOrDocumentXml(documentBean).getRootElement());
//
//        String tempMsgXml = rootEl.asXML();
//
//        // 去除节点命名空间AppHdr/Document
//        tempMsgXml = removeSpaceName(tempMsgXml);
//        tempMsgXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + tempMsgXml;
//        // 格式化报文
//        tempMsgXml = this.formatXmlStr(DocumentHelper.parseText(tempMsgXml));
//        logger.info("com.hisun.kont.ptgw.fps.util.JavaBeanToXml.generteXmlFiles，明文报文：" + tempMsgXml);
//        // 对明文加密、加签
//        String amcmPrivateKey = latestCert.getAmcmPrivateKey();
//        String bankPublicKey = latestCert.getBankPublicKey();
//        ITransportSecurity ts = new FpsTransportSecurity(bankPublicKey, amcmPrivateKey);
//        try {
//            tempMsgXml = ts.encrypt(tempMsgXml);
//        } catch (Exception e) {
//            logger.error("com.hisun.kont.ptgw.fps.util.JavaBeanToXml.generteXmlFiles，报文加密失败", e);
//            KontException.throwKontException(MsgCdEnum.DECRYPT_FAIL, e);
//        }
//        logger.info("com.hisun.kont.ptgw.fps.util.JavaBeanToXml.generteXmlFiles，密文报文：" + tempMsgXml);
//        // 生成xml文件并返回
//        return generateXmlFile(tempMsgXml, generteFilePath, fileName);
//    }
//
//    /**
//     * 单独生成xml节点（appHdr或者document，根据传入的object决定）
//     *
//     * @param object    需要生成的JavaBean对象
//     * @return String removeNameSpaceNode 需要移除的命名空间
//     * @throws IOException
//     * @throws JAXBException
//     * @throws DocumentException
//     */
//    public Document getAppHdrOrDocumentXml(Object object) throws JAXBException, DocumentException {
//        JAXBContext context = JAXBContext.newInstance(object.getClass());
//        Marshaller marshaller = context.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        //去掉生成xml的默认报文头
//        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        marshaller.marshal(object, baos);
//        String xmlObj = new String(baos.toByteArray());
//        // TODO: 去除命名空间
//        // 拿到前缀
////        int fir = xmlObj.indexOf(" ");
////        String prefix = xmlObj.substring(0, fir) + ">";
////        // 拿到后缀
////        fir = xmlObj.indexOf(">");
////        xmlObj = xmlObj.substring(fir + 1);
////        // 拼接
////        xmlObj = prefix + xmlObj;
//        // 生成XML文档
//        Document document = DocumentHelper.parseText(xmlObj);
//        return document;
//    }
//
//    /**
//     * 格式化字符串
//     *
//     * @param document
//     * @return
//     */
//    public String formatXmlStr(Document document) throws IOException {
//        // 设置XML文档格式
//        OutputFormat outputFormat = OutputFormat.createPrettyPrint();
//        // 设置XML编码方式,即是用指定的编码方式保存XML文档到字符串(String),这里也可以指定为GBK或是ISO8859-1
//        outputFormat.setEncoding("UTF-8");
//        // 设置是否缩进
//        outputFormat.setIndent(true);
//        // 以四个空格方式实现缩进
//        outputFormat.setIndent("    ");
//        // 设置是否换行
//        outputFormat.setNewlines(true);
//        // stringWriter字符串是用来保存XML文档的
//        StringWriter stringWriter = new StringWriter();
//        // xmlWriter是用来把XML文档写入字符串的(工具)
//        XMLWriter xmlWriter = null;
//        try {
//            xmlWriter = new XMLWriter(stringWriter, outputFormat);
//            // 把创建好的XML文档写入字符串
//            xmlWriter.write(document);
//        } catch (IOException e) {
//            logger.error("com.hisun.kont.ptgw.fps.util.JavaBeanToXml.formatXmlStr,格式化xml失败", e);
//        } finally {
//            if (JudgeUtils.isNotNull(xmlWriter)) {
//                xmlWriter.close();
//            }
//        }
//        return stringWriter.toString();
//    }
//
//    /**
//     * 生成Java类文件
//     *
//     * @param classBuffer      xml字符串信息
//     * @param generateFilePate 生成路径
//     * @param fileName         文件名
//     * @Return Boolean 成功true 失败false
//     */
//    public Boolean generateXmlFile(String classBuffer, String generateFilePate, String fileName) {
//        File classPath = new File(generateFilePate);
//        // 校验文件路径，不存在则创建
//        if (!checkXmlPath(classPath)) {
//            logger.error("\ncom.demo.util.JavaBeanToDTOUtils-generateClassFile生成文件失败，请检查路径是否有权限创建、是否可以写");
//            return false;
//        }
//
//        File classFile = new File(classPath, fileName + ".xml");
//
//        // 文件不存在，创建新的文件
//        if (!classFile.exists()) {
//            try {
//                if (!classFile.createNewFile()) {
//                    return false;
//                }
//            } catch (IOException e) {
//                logger.error("com.hisun.kont.ptgw.fps.util.JavaBeanToXml.generateXmlFile-生成xml文件失败", e);
//                return false;
//            }
//        } else {
//            // 文件存在，检查文件是否可以被覆盖
//            if (!classFile.isFile() || !classFile.canWrite()) {
//                return false;
//            }
//        }
//        // 输出
//        try (FileOutputStream fos = new FileOutputStream(classFile);
//             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);) {
//            osw.write(classBuffer);
//            osw.flush();
//        } catch (IOException e) {
//            logger.error("com.hisun.kont.ptgw.fps.util.JavaBeanToXml.generateXmlFile-生成xml文件失败", e);
//        }
//        return true;
//    }
//
//    /**
//     * 校验路径
//     *
//     * @param xmlPath 路径
//     */
//    private boolean checkXmlPath(File xmlPath) {
//        if (!xmlPath.exists() || !xmlPath.isDirectory()) {
//            return xmlPath.mkdirs();
//        }
//        return xmlPath.canWrite();
//    }
//
//    /**
//     * 去除App命名空间
//     * @param tempMsgXml
//     * @return
//     */
//    private String removeSpaceName(String tempMsgXml) throws DocumentException {
//        // 拿到命名空间apphdr起始位置
//        int preIndex = tempMsgXml.indexOf("<AppHdr xmlns=", 0);
//        if (preIndex > 0) {
//            // 去除AppHdr的命名空间
//            String preXmlStr = tempMsgXml.substring(0, preIndex);
//            String appHdr = tempMsgXml.substring(preIndex);
//            // 拿到appHdr报文第一个>结尾的位置
//            int appFirEnId = appHdr.indexOf(">");
//            appHdr = "<AppHdr>" + appHdr.substring(appFirEnId + 1);
//            tempMsgXml = preXmlStr + appHdr;
//            // TODO: 暂时没有要去除Document的命名空间
//        }
//
//        // 根据2个位置将报文分成三个位置
//        /*String appHdr = tempMsgXml.substring(preIndex, sufIndex);
//        String document = tempMsgXml.substring(sufIndex);
//
//        // 拿到appHdr报文第一个>结尾的位置
//        int appFirEnId = appHdr.indexOf(">");
//        appHdr = "<AppHdr>" + appHdr.substring(appFirEnId + 1);
//        // 拿到document报文第一个>结尾的位置
//        int docFirEnId = document.indexOf(">");
//        document = "<Document>" + document.substring(docFirEnId + 1);*/
//        return tempMsgXml;
//    }
//
//    public JavaBeanToXml() {
//        nameSpaceMap.put("admi.002.001.01", "urn:iso:std:iso:20022:tech:xsd:");
//        nameSpaceMap.put("admi.004.001.02", "urn:iso:std:iso:20022:tech:xsd:");
//        nameSpaceMap.put("admi.007.001.01", "urn:iso:std:iso:20022:tech:xsd:");
//        nameSpaceMap.put("camt.052.001.08", "urn:iso:std:iso:20022:tech:xsd:");
//        nameSpaceMap.put("camt.054.001.08", "urn:iso:std:iso:20022:tech:xsd:");
//        nameSpaceMap.put("camt.056.001.09", "urn:iso:std:iso:20022:tech:xsd:");
//        nameSpaceMap.put("camt.060.001.05", "urn:iso:std:iso:20022:tech:xsd:");
//        nameSpaceMap.put("fps.admi.001.001.01", "urn:amcm:fps:xsd:");
//        nameSpaceMap.put("fps.admi.002.001.01", "urn:amcm:fps:xsd:");
//        nameSpaceMap.put("fps.admi.003.001.01", "urn:amcm:fps:xsd:");
//        nameSpaceMap.put("fps.admi.004.001.01", "urn:amcm:fps:xsd:");
//        nameSpaceMap.put("fps.adrs.001.001.01", "urn:amcm:fps:xsd:");
//        nameSpaceMap.put("fps.adrs.002.001.01", "urn:amcm:fps:xsd:");
//        nameSpaceMap.put("fps.adrs.003.001.01", "urn:amcm:fps:xsd:");
//        nameSpaceMap.put("fps.adrs.004.001.01", "urn:amcm:fps:xsd:");
//        nameSpaceMap.put("fps.adrs.005.001.01", "urn:amcm:fps:xsd:");
//        nameSpaceMap.put("fps.adrs.006.001.01", "urn:amcm:fps:xsd:");
//        nameSpaceMap.put("fps.adrs.007.001.01", "urn:amcm:fps:xsd:");
//        nameSpaceMap.put("fps.adrs.008.001.01", "urn:amcm:fps:xsd:");
//        nameSpaceMap.put("fps.adrs.009.001.01", "urn:amcm:fps:xsd:");
//        nameSpaceMap.put("pacs.002.001.11", "urn:iso:std:iso:20022:tech:xsd:");
//        nameSpaceMap.put("pacs.004.001.10", "urn:iso:std:iso:20022:tech:xsd:");
//        nameSpaceMap.put("pacs.008.001.09","urn:iso:std:iso:20022:tech:xsd:");
//        nameSpaceMap.put("pacs.028.001.04", "urn:iso:std:iso:20022:tech:xsd:");
//    }
//}

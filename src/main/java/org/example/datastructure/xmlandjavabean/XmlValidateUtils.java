//package org.example.datastructure.xmlandjavabean;
//
//import com.hisun.kont.common.exception.KontException;
//import com.hisun.kont.ptgw.fps.contants.MsgCdEnum;
//import fps.XmlMessage;
//import org.springframework.stereotype.Component;
//
//import javax.xml.transform.Source;
//import javax.xml.transform.org.example.datastructure.stream.StreamSource;
//import javax.xml.validation.Schema;
//import javax.xml.validation.SchemaFactory;
//import javax.xml.validation.Validator;
//import io.ByteArrayInputStream;
//import io.InputStream;
//import util.regex.Matcher;
//import util.regex.Pattern;
//
//@Component
//public class XmlValidateUtils {
//    /**
//     * 截取xsd的名称
//     */
//    final Pattern xsdPattern = Pattern.compile("<FpsMsg(?:\\s+|[^>]+)(xsi:schemaLocation=\"[\\S]+\\s+([\\S]+\\.xsd)\")");
//
//    /**
//     * xsd校验
//     * @param xmlStr
//     * @throws Exception
//     */
//    public void validate(String xmlStr) throws Exception {
//        String xsdName = getXsd(xmlStr);
//        String xsdFilePath = "/xsd/" + xsdName;
//        // 获取xml文件的流、获取xsd文件流
//        try(ByteArrayInputStream xmlStream = new ByteArrayInputStream(xmlStr.getBytes("UTF-8"));
//            InputStream xsdStream = XmlMessage.class.getResourceAsStream(xsdFilePath);) {
//            if (xsdStream == null) {
//                KontException.throwKontException(MsgCdEnum.XSD_VALIDATE_FAIL, "FPS SDK alert: The verification of schema XSD is failed since missing the corresponding XSD definition org.example.datastructure.file in " + xsdFilePath);
//            }
//            // 进入校验逻辑
//            SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
//            Source xsdSource = new StreamSource(xsdStream);
//            Schema schema = schemaFactory.newSchema(xsdSource);
//            Source xmlSource = new StreamSource(xmlStream);
//            Validator validator = schema.newValidator();
//            validator.validate(xmlSource);
//        } catch (Exception e) {
//            KontException.throwKontException(MsgCdEnum.XSD_VALIDATE_FAIL);
//        }
//    }
//
//    /**
//     * 获取xml对应的xsd的名称
//     * @param xmlStr
//     * @return
//     */
//    private String getXsd(String xmlStr) {
//        Matcher matcher = this.xsdPattern.matcher(xmlStr);
//        if (!matcher.find()) {
//            KontException.throwKontException(MsgCdEnum.XSD_VALIDATE_FAIL, "FPS SDK alert: The message does not fulfill the schema XSD using regular expression ( \" + this.xsdPattern.pattern() + \" ), please verify the message structure.");
//        } else {
//            return matcher.group(2);
//        }
//        return null;
//    }
//}
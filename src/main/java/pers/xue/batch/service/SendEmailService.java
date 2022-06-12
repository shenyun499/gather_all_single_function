package pers.xue.batch.service;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2022-06-12
 */
public class SendEmailService {

//    /**
//     * 发送邮件
//     *
//     * @return
//     * @throws Exception
//     */
//    public static void noticeMaill(String suppliername, String supplieremail, List<SendMailReqBody> abNormalList, List<SendMailReqBody> normalList) {
//        StringBuilder content = new StringBuilder("<html><head></head><body><h2>已有实施日期，还未下发异常CA提醒</h2>");
//        Date date = (new Date());
//        content.append(suppliername + ",您好! 下面是今日已存在实施日期且未下发的CA任务,请检查！\n");
//
//        content.append("<table border=\"5\" style=\"border:solid 1px #E8F2F9;font-size=12px;;font-size:18px;\">");
//        content.append("<tr style=\"background-color: #428BCA; color:#ffffff\"><th>序号</th><th>CA号</th><th>实施日期</th><th>OA对接时间</th><th>下发状态</th><th>处理状态</th></tr>");
//        int i = 0;
//        for (SendMailReqBody sendMailReqBody : abNormalList) {
//            i++;
//            content.append("<tr>");
//            content.append("<td align=\"center\">" + i + "</td>"); //序号
//            content.append("<td align=\"center\">" + sendMailReqBody.getCaNo() + "</td>"); //CA号
//            content.append("<td align=\"center\">" + sendMailReqBody.getImplementDate() + "</td>"); //实施日期
//            content.append("<td align=\"center\">" + sendMailReqBody.getOadockingtime() + "</td>"); //oa对接时间
//            content.append("<td align=\"center\">" + "未下发" + "</td>"); //下发状态
//
//            String abnormalstatus = sendMailReqBody.getAbnormalstatus();
//            //异常状态
//            content.append("<td align=\"center\" style=\"background-color: #DC143C; color:#ffffff\">" + abnormalstatus + "</td>"); //处理状态
//
//
//            content.append("</tr>");
//        }
//        content.append("</table>");
//
//        content.append("\n");
//        content.append("<br />");
//        content.append("各位领导好! 下面是今日正常下发的CA任务,请检查！\n");
//        content.append("<table border=\"5\" style=\"border:solid 1px #E8F2F9;font-size=12px;;font-size:18px;\">");
//        content.append("<tr style=\"background-color: #428BCA; color:#ffffff\"><th>序号</th><th>CA号</th><th>实施日期</th><th>OA对接时间</th><th>下发状态</th><th>处理状态</th></tr>");
//        int k = 0;
//        for (SendMailReqBody sendMailReqBody : normalList) {
//            k++;
//            content.append("<tr>");
//            content.append("<td align=\"center\">" + k + "</td>"); //序号
//            content.append("<td align=\"center\">" + sendMailReqBody.getCaNo() + "</td>"); //CA号
//            content.append("<td align=\"center\">" + sendMailReqBody.getImplementDate() + "</td>"); //实施日期
//            content.append("<td align=\"center\">" + sendMailReqBody.getOadockingtime() + "</td>"); //oa对接时间
//            content.append("<td align=\"center\">" + "已下发" + "</td>"); //下发状态
//
//            String abnormalstatus = sendMailReqBody.getAbnormalstatus();
//            //正常
//            content.append("<td align=\"center\" style=\"background-color: #006400; color:#ffffff\">" + abnormalstatus + "</td>"); //处理状态
//            content.append("</tr>");
//        }
//
//        content.append("</table>");
//        content.append("<h3>请各位领导仔细校验，谢谢配合！</h3>");
//        content.append("</body></html>");
//
//        sendTxtMassMail(supplieremail, content.toString());
//    }
//
//
//
//
//    /**
//     * 群发邮件
//     *
//     * @param to
//     * @param content
//     */
//    public static void sendTxtMassMail(String to, String content) {
//        Properties props = new Properties();
//        props.setProperty("mail.host", "smtp.qq.com");  // 设置QQ邮件服务器
//        props.setProperty("mail.transport.protocol", "smtp"); // 邮件发送协议
//        props.setProperty("mail.smtp.auth", "true"); // 需要验证用户名密码
//
//        // QQ邮箱设置SSL加密
//        MailSSLSocketFactory sf = new MailSSLSocketFactory();
//        sf.setTrustAllHosts(true);
//        props.put("mail.smtp.ssl.enable", "true");
//        props.put("mail.smtp.ssl.socketFactory", sf);
//
//        //1、创建定义整个应用程序所需的环境信息的 Session 对象
//        Session session = Session.getDefaultInstance(prop, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                //传入发件人的姓名和授权码
//                return new PasswordAuthentication("619046217@qq.com","16位授权码");
//            }
//        });
//
//        // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
//        // 用（你可以在控制台（console)上看到发送邮件的过程）
//        session.setDebug(true);
//
//        // 用session为参数定义消息对象
//        MimeMessage message = new MimeMessage(session);
//        try {
//            // 加载发件人地址
//            message.setFrom(new InternetAddress(from));
//            // 加载收件人地址
//            //处理批量的情况,多邮箱会用;分隔
//            InternetAddress[] address = null;
//            if (to != null && to.trim().length() > 0) {
//                String[] arr = to.split(";");
//                int receiverCount = arr.length;
//                if (receiverCount > 0) {
//                    address = new InternetAddress[receiverCount];
//                    for (int i = 0; i < receiverCount; i++) {
//                        address[i] = new InternetAddress(arr[i]);
//                    }
//                }
//                if (address != null && address.length > 0) {
//                    message.setRecipients(Message.RecipientType.TO, address);
//                    // 加载标题
//                    message.setSubject(subject);
//
//                    // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
//                    Multipart multipart = new MimeMultipart();
//
//                    // 设置邮件的文本内容
//                    BodyPart contentPart = new MimeBodyPart();
//                    contentPart.setText(content);
//                    multipart.addBodyPart(contentPart);
//                    // 将multipart对象放到message中
////                    message.setContent(multipart);
//                    message.setContent(content,"text/html;charset=utf-8");
//                    // 保存邮件
//                    message.saveChanges();
//                    // 发送邮件
//                    Transport transport = session.getTransport("smtp");
//                    // 连接服务器的邮箱
//                    transport.connect(host, user, pwd);
//                    // 把邮件发送出去
//                    transport.sendMessage(message, message.getAllRecipients());
//                    transport.close();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}

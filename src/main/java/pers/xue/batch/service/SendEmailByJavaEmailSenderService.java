package pers.xue.batch.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pers.xue.batch.entity.SendEmailBean;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2022-06-12
 *
 * 1、为啥会链接失败？
 * org.springframework.mail.MailSendException: Mail server connection failed; nested exception is javax.mail.MessagingException: Could not connect to SMTP host: smtp.qq.com, port: 465, response: -1. Failed messages: javax.mail.MessagingException: Could not c
 * https://www.liangzl.com/get-article-detail-173532.html
 *
 * 2、如果JavaMailSender 提示 找不到bean无法注入？
 * yml 文件配置可能出错了
 */
@Slf4j
@Service
public class SendEmailByJavaEmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String content) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");
        // SimpleMailMessage message = new SimpleMailMessage();
        try {
            message.setFrom("1104428690@qq.com");
            message.setTo("1104428690@qq.com");
            message.setSubject("标题：测试标题");
            message.setText(content, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("send email error", e);
        }
    }

    //@Test
    public void sendEmailTest() {

        StringBuffer content = new StringBuffer();
        content.append("请看下面失败的事件！\n");

        content.append("<table border=\"5\" style=\"border:solid 1px #E8F2F9;font-size=12px;;font-size:18px;\">");
        content.append("<tr style=\"background-color: #428BCA; color:#ffffff\"><th>序号</th><th>Date</th><th>Event</th><th>Number</th><th>Count</th><th>Status</th></tr>");
        int i = 0;
        List<SendEmailBean> sendEmailBeans = Stream.of(
                SendEmailBean.builder()
                        .date(LocalDateTime.now())
                        .event("error")
                        .number("1234567")
                        .count(1)
                        .status("fail")
                        .build(),
                SendEmailBean.builder()
                        .date(LocalDateTime.now())
                        .event("error")
                        .number("123456789")
                        .count(2)
                        .status("fail")
                        .build()
        ).collect(Collectors.toList());
        for (SendEmailBean sendEmailBean : sendEmailBeans) {
            i++;
            content.append("<tr>");
            content.append("<td align=\"center\">" + i + "</td>"); //序号
            content.append("<td align=\"center\">" + sendEmailBean.getDate() + "</td>");
            content.append("<td align=\"center\">" + sendEmailBean.getEvent() + "</td>");
            content.append("<td align=\"center\">" + sendEmailBean.getNumber() + "</td>");
            content.append("<td align=\"center\">" + sendEmailBean.getCount() + "</td>");
            content.append("<td align=\"center\">" + sendEmailBean.getStatus() + "</td>");
        }
        content.append("</table>");
        content.append("\n");
        content.append("<br />");
        this.sendEmail(content.toString());
    }

    @Test
    public void testSendSimple() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1104428690@qq.com");
        message.setTo("******@qq.com");
        message.setSubject("标题：测试标题");
        message.setText("测试内容部份");
        javaMailSender.send(message);
    }
}

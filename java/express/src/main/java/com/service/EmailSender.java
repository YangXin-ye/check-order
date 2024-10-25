package com.service;

/**
 * @date 2024/10/25 22:34
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class EmailSender {


    @Value("${email.account}")
    private String account;

    @Value("${email.authorizationCode}")
    private String authorizationCode;


    public void sendVerificationCode(String to, String verificationCode) {
        // QQ邮箱SMTP服务器
        String host = "smtp.qq.com";
        String from = account; // 替换为你的QQ邮箱
        String password = authorizationCode; // 替换为你的QQ邮箱授权码

        // 配置邮件会话
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465"); // QQ邮箱支持的端口
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // 创建会话
        Session session = Session.getInstance(properties);
        session.setDebug(true); // 可选，调试用

        try {
            // 创建邮件消息
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("验证码");
            message.setText("【收支系统】您正在注册，您的验证码为: " + verificationCode+"，请注意保管，不要透露给任何人");

            // 发送邮件
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            System.out.println("验证码邮件发送成功！");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("发送邮件失败：" + e.getMessage());
        }
    }

}

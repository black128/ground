package com.zrw.playground.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zrw
 */
@Slf4j
@RestController
public class SendMailUtil {


    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    @RequestMapping("mail")
    public void sendMail(){
        //创建简单邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        //谁发的
        message.setFrom(from);
        //谁要接收
        message.setTo("zhangxin07@sinosoft.com.cn");
        //邮件标题
        message.setSubject("张老师，你好：");
        //邮件内容
        message.setText("        哈哈哈哈红红火火恍恍惚惚 好玩");
        try {
            mailSender.send(message);
            System.out.println("发送普通邮件成功");
        } catch (MailException e) {
            e.printStackTrace();
            log.error("普通邮件方失败");
        }
    }
}

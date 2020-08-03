package com.gwssi.devops.utilitypage.mail;

import com.gwssi.devops.utilitypage.config.AppConfig;
import com.gwssi.devops.utilitypage.config.MailConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MailHelperBuilder {
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private MailConfig mailConfig;
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendSimpleMessage(String subject, String text, String... recivers) {
        String reciver = "";
        if (recivers == null || recivers.length == 0) {
            reciver = mailConfig.getReciver();
        } else {
            reciver = recivers[0];
        }
        if (StringUtils.isEmpty(reciver)) {
            return;
        }
        text = "服务器地址:"+appConfig.getServerAddress() + "\n" + text;
        for (String mailAddr : reciver.split(",")) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(mailAddr);
                message.setFrom(mailConfig.getFrom());
                message.setSubject(subject);
                message.setText(text);
                javaMailSender.send(message);
            } catch (MailException e) {
                log.error("发送邮件失败=" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void sendSimpleMessagesAll(String subject, String text) {
        this.sendSimpleMessage(subject, text, mailConfig.getRecivers());
    }

    public void sendSimpleMessagesWaring(String subject, String text) {
        this.sendSimpleMessage(subject, text, mailConfig.getReciverWaring());
    }
}

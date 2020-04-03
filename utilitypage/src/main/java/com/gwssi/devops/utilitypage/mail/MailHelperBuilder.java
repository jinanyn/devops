package com.gwssi.devops.utilitypage.mail;

import com.gwssi.devops.utilitypage.util.MailConfig;
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
    private MailConfig mailConfig;

    public void sendSimpleMessage(JavaMailSender javaMailSender, String subject, String text) {
        String reciver = mailConfig.getReciver();
        if(StringUtils.isEmpty(reciver)){
            return ;
        }
        for(String mailAddr : reciver.split(",")){
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(mailAddr);
                message.setFrom(mailConfig.getFrom());
                message.setSubject(subject);
                message.setText(text);
                javaMailSender.send(message);
            } catch (MailException e) {
                log.error("发送邮件失败="+e.getMessage());
                e.printStackTrace();
            }
        }
    }
}

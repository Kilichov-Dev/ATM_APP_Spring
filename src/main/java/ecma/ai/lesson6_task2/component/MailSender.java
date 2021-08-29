package ecma.ai.lesson6_task2.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailSender {
    @Autowired
    JavaMailSender mailSender;

    public boolean send(String to, String text) throws MessagingException {
        String from = "email@gmail.com";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setSubject("Confirm email");
        helper.setFrom(from);
        helper.setTo(to);
        helper.setText(text, true);
        mailSender.send(message);
        return true;
    }

    public boolean mailTextAdd(String email, String AtmAddress) throws MessagingException {
        String text =AtmAddress+" ATMda pul kam qoldi!";

        return send(email, text);
    }

}

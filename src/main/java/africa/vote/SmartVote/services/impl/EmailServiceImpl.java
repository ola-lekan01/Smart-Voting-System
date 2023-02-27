package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Async
    @Override
    public void sendEmail(String receiverMail, String email) {
        try {
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage, "utf-8");
            mimeMessageHelper.setSubject("Kindly confirm your email address");
            mimeMessageHelper.setTo(receiverMail);
            mimeMessageHelper.setFrom("adulojujames@gmail.com");
            mimeMessageHelper.setText(email, true);
            javaMailSender.send(mailMessage);
            System.out.println("Mail sent successfully");
        } catch (MessagingException e) {
            log.info("Problem: " + e.getMessage());
            throw new RuntimeException();
        } catch (MailException e) {
            log.info("Problem 2: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}

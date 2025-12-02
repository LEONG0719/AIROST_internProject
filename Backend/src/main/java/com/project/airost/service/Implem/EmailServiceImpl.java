package com.project.airost.service.Implem;

import com.project.airost.domain.User;
import com.project.airost.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(User user, String siteUrl) {
        String toAddress = user.getEmail();
        String fromAddress = "your-email@gmail.com"; // Match application.properties
        String senderName = "Airost Lost & Found";
        String subject = "Please verify your registration";

        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Airost Team.";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);

            content = content.replace("[[name]]", user.getFullName());
            String verifyUrl = siteUrl + "/api/auth/verify?code=" + user.getVerificationCode();
            content = content.replace("[[URL]]", verifyUrl);

            helper.setText(content, true); // true = HTML

            mailSender.send(message);
            System.out.println("Email sent to " + user.getEmail());

        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
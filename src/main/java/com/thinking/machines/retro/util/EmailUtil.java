package com.thinking.machines.retro.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil 
{
@Autowired
private JavaMailSender javaMailSender;
public void sendOtpEmail(String email, String otp) throws MessagingException 
{
MimeMessage mimeMessage = javaMailSender.createMimeMessage();
MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
mimeMessageHelper.setTo(email);
mimeMessageHelper.setSubject("Please Verify Your Email Address");
mimeMessageHelper.setText("""

Dear User,
Thank you for signing up with Retro! To complete your registration and access our platform's features, please verify your email address by clicking the link below:

<div>
<a href="http://localhost:8080/verify-account?email=%s&otp=%s" target="_blank">click link to verify</a>
</div>



If you did not register for an account with Retro, please disregard this email.

Thank you,
Retro Mart Team


 """.formatted(email, otp), true);
javaMailSender.send(mimeMessage);
}

public void sendSuccessfulEmail(String firstName,String lastName,String email) throws MessagingException
{
String message="Dear ";
message+=firstName+" ";
message+=lastName;
message+=",\nCongratulations! You have successfully completed the registration process on Retro. Welcome aboard!\nFeel free to explore our platform and discover all the amazing features we have to offer. If you have any questions or need assistance, don't hesitate to reach out to our support team.\nThank you for joining us, and we look forward to serving you!\nBest regards,\nRetro Mart Team";


MimeMessage mimeMessage = javaMailSender.createMimeMessage();
MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
mimeMessageHelper.setTo(email);
mimeMessageHelper.setSubject("Welcome to Retro!");
mimeMessageHelper.setText(message);

javaMailSender.send(mimeMessage);

}

public void sendSuccessfullyAddedEmail(String email,String subject,String body)
{
try
{
MimeMessage mimeMessage = javaMailSender.createMimeMessage();
MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
mimeMessageHelper.setTo(email);
mimeMessageHelper.setSubject(subject);
mimeMessageHelper.setText(body);

javaMailSender.send(mimeMessage);

}catch(Exception e)
{
System.out.println(e);
}


}


}
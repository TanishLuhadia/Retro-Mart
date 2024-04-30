package com.thinking.machines.Service;
import com.thinking.machines.retro.beans.*;
import com.thinking.machines.retro.entity.*;
import com.thinking.machines.retro.repository.*;
import com.thinking.machines.retro.util.*;
import jakarta.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService 
{
@Autowired
private OtpUtil otpUtil;
@Autowired
private EmailUtil emailUtil;
public void register(RegistrationBean registerDto) 
{
String otp = otpUtil.generateOtp();
try 
{
emailUtil.sendOtpEmail(registerDto.getEmail(), otp);
} 
catch (MessagingException e) 
{
throw new RuntimeException("Unable to send otp please try again");
}
registerDto.setOTP(otp);
registerDto.setOTPGeneratedTime(LocalDateTime.now());
registerDto.setActive(false);
}

public String verifyAccount(String email, String otp) 
{
RegistrationDAO registrationDAO;
registrationDAO=new RegistrationDAO();
RegistrationDTO registrationDTO;
registrationDTO=new RegistrationDTO();
ActionResponse actionResponse;
actionResponse=new ActionResponse();
if(registrationDAO.getByEmail(email))
{
registrationDTO=registrationDAO.getUserByEmail(email));
if (registrationDTO.getOTP().equals(otp) && Duration.between(registrationDTO.getOTPGeneratedTime(),LocalDateTime.now()).getSeconds() < (1 * 60)) 
{
registrationDAO.setUserActive(email);
actionResponse.setSuccessful(true);
actionResponse.setException("");
actionResponse.setResult("User verified");
System.out.println("Verified User");
return actionResponse;
}
else
{
actionResponse.setSuccessful(false);
actionResponse.setException("Please regenerate OTP and try again");
actionResponse.setResult("");
System.out.println("Regenerate OTP");
return actionResponse;
}
}
else
{
actionResponse.setSuccessful(false);
actionResponse.setException("Not a valid email");
actionResponse.setResult("");
System.out.println("Invalid User");
return actionResponse;
}
}
public String regenerateOtp(String email) 
{


User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
String otp = otpUtil.generateOtp();
try 
{
emailUtil.sendOtpEmail(email, otp);
}
catch (MessagingException e) 
{
throw new RuntimeException("Unable to send otp please try again");
}
user.setOtp(otp);
user.setOtpGeneratedTime(LocalDateTime.now());
userRepository.save(user);
return "Email sent... please verify account within 1 minute";
}
public String login(LoginDto loginDto) 
{
User user = userRepository.findByEmail(loginDto.getEmail())
        .orElseThrow(
            () -> new RuntimeException("User not found with this email: " + loginDto.getEmail()));
 if (!loginDto.getPassword().equals(user.getPassword())) 
{
return "Password is incorrect";
} 
else if (!user.isActive()) 
{
return "your account is not verified";
}
return "Login successful";
}
}
package com.thinking.machines.retro.Service;
import com.thinking.machines.retro.beans.*;
import com.thinking.machines.retro.dto.*;
import com.thinking.machines.retro.dao.*;

import com.thinking.machines.retro.util.*;
import com.thinking.machines.retro.*;

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
public void sendSuccessfullyAddedEmail(String email,String subject,String body)
{
emailUtil.sendSuccessfullyAddedEmail(email,subject,body);
}

public ActionResponse verifyAccount(String email, String otp) 
{
ActionResponse actionResponse;
actionResponse=new ActionResponse();
RegistrationDTO registrationDTO=null;
try
{
RegistrationDAO registrationDAO;
registrationDAO=new RegistrationDAO();
if(registrationDAO.getByEmail(email))
{
registrationDTO=registrationDAO.getUserByEmail(email);
if (registrationDTO.getOTP().equals(otp) && Duration.between(registrationDTO.getOTPGeneratedTime(),LocalDateTime.now()).getSeconds() < (1 * 60)) 
{
registrationDAO.setUserActive(email);
actionResponse.setSuccessful(true);
actionResponse.setException("");
actionResponse.setResult("User verified");
System.out.println("Verified User");
emailUtil.sendSuccessfulEmail(registrationDTO.getFirstName(),registrationDTO.getLastName(),registrationDTO.getEmail());
return actionResponse;
}
else if(registrationDAO.checkItsActive(email))
{
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
System.out.println("Please sRegenerate OTP");
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
}catch(Exception e)
{
actionResponse.setSuccessful(false);
actionResponse.setException(e.getMessage());
actionResponse.setResult("");
System.out.println("Exception");
return actionResponse;

}
}


public ActionResponse regenerateOTP(String email) 
{
ActionResponse actionResponse;
actionResponse=new ActionResponse();

try
{
RegistrationDAO registrationDAO;
registrationDAO=new RegistrationDAO();
RegistrationDTO registrationDTO;
if(registrationDAO.getByEmail(email))
{
String otp = otpUtil.generateOtp();
try 
{
emailUtil.sendOtpEmail(email, otp);
}
catch (MessagingException e) 
{
actionResponse.setSuccessful(false);
actionResponse.setException(e.getMessage());
actionResponse.setResult("");
System.out.println("Some error");
return actionResponse;
}
registrationDAO.setOTP(email,otp);
registrationDAO.setOTPGeneratedTime(email,LocalDateTime.now());
registrationDAO.setUserActive(email);
actionResponse.setSuccessful(true);
actionResponse.setException("");
actionResponse.setResult("OTP regenerated");
System.out.println("OTP regenerated");
return actionResponse;
}
else
{
actionResponse.setSuccessful(false);
actionResponse.setException("Not a valid email");
actionResponse.setResult("");
System.out.println("Invalid User");
return actionResponse;
}
}catch(Exception e)
{
actionResponse.setSuccessful(false);
actionResponse.setException(e.getMessage());
actionResponse.setResult("");
System.out.println("Exception");
return actionResponse;

}

}

/*
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
*/

}
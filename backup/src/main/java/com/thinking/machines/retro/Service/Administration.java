package com.thinking.machines.retro.Service;
import com.thinking.machines.retro.beans.*;
import com.thinking.machines.retro.dao.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;
import com.thinking.machines.retro.*;
import com.google.gson.*;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.*;
@Controller
public class Administration
{


@Autowired
private UserService userService;

@Autowired
private JavaMailSender mailSender;
public void sendEmail(String toEmail,String subject,String body)
{
SimpleMailMessage message=new SimpleMailMessage();
message.setFrom("tanishjain5801@gmail.com");
message.setTo(toEmail);
message.setText(body);
message.setSubject(subject);
mailSender.send(message);
System.out.println("Mail sent successfully");
}


@PostMapping("/register")
@ResponseBody
public ActionResponse register(RegistrationBean registrationBean)
{
ActionResponse actionResponse;
actionResponse=new ActionResponse();
System.out.println(registrationBean.getFirstName());
System.out.println(registrationBean.getLastName());
System.out.println(registrationBean.getEmail());
System.out.println(registrationBean.getPassword());
System.out.println(registrationBean.getConfirmPassword());
String email;
email=registrationBean.getEmail();
RegistrationDAO registrationDAO=new RegistrationDAO();
try
{
if(registrationDAO.getByEmail(email))
{
actionResponse.setSuccessful(false);
actionResponse.setException("Email already exist");
actionResponse.setResult("");
System.out.println("Sending error");
return actionResponse;
}
}catch(DAOException daoException)
{
actionResponse.setSuccessful(false);
actionResponse.setException(daoException.getMessage());
actionResponse.setResult("");
System.out.println("Sending error");
return actionResponse;
}
try
{
userService.register(registrationBean);
registrationDAO.add(registrationBean);
}catch(Exception e)
{
actionResponse.setSuccessful(false);
actionResponse.setException(e.getMessage());
actionResponse.setResult("");
System.out.println("Sending error");
return actionResponse;
}
// sendEmail(registrationBean.getEmail(),"Registration On Retro Successfull","Congratulations! you are now part of our Retro Family\nThank You!!");
actionResponse.setSuccessful(true);
actionResponse.setException("");
actionResponse.setResult("Linked Send please verify your account");
System.out.println("Sending Successfully");
return actionResponse;
}



@PutMapping("/verify-account")
public ResponseEntity<String> verifyAccount(@RequestParam String email,
      @RequestParam String otp) 
{
userService.verifyAccount(email,otp);
//return new ResponseEntity<>(userService.verifyAccount(email, otp), HttpStatus.OK);
 }
  @PutMapping("/regenerate-otp")
  public ResponseEntity<String> regenerateOtp(@RequestParam String email) {
    return new ResponseEntity<>(userService.regenerateOtp(email), HttpStatus.OK);
  }
  @PutMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
    return new ResponseEntity<>(userService.login(loginDto), HttpStatus.OK);
  }









}

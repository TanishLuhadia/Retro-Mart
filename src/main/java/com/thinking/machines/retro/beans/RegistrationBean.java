package com.thinking.machines.retro.beans;
import java.time.LocalDateTime;
public class RegistrationBean implements java.io.Serializable
{
private String firstName;
private String lastName;
private String email;
private String password;
private String confirmPassword;
private Boolean active;
private String OTP;
private LocalDateTime OTPGeneratedTime;
public RegistrationBean()
{
this.firstName=null;
this.lastName=null;
this.email=null;
this.password=null;
this.confirmPassword=null;
this.active=false;
this.OTP=null;
this.OTPGeneratedTime=null;
}
public void setFirstName(String firstName)
{
this.firstName=firstName;
}
public String getFirstName()
{
return this.firstName;
}

public void setLastName(String lastName)
{
this.lastName=lastName;
}
public String getLastName()
{
return this.lastName;
}


public void setEmail(String email)
{
this.email=email;
}
public String getEmail()
{
return this.email;
}

public void setPassword(String password)
{
this.password=password;
}
public String getPassword()
{
return this.password;
}

public void setConfirmPassword(String confirmPassword)
{
this.confirmPassword=confirmPassword;
}
public String getConfirmPassword()
{
return this.confirmPassword;
}
public void setActive(Boolean active)
{
this.active=active;
}
public Boolean getActive()
{
return this.active;
}

public void setOTP(String OTP)
{
this.OTP=OTP;
}
public String getOTP()
{
return this.OTP;
}

public void setOTPGeneratedTime(LocalDateTime otpGeneratedTime)
{
this.OTPGeneratedTime=otpGeneratedTime;
}
public LocalDateTime getOTPGeneratedTime()
{
return this.OTPGeneratedTime;
}
}
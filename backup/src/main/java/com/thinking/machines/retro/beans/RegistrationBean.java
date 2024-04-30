package com.thinking.machines.retro.beans;
public class RegistrationBean implements java.io.Serializable
{
private String firstName;
private String lastName;
private String email;
private String password;
private String confirmPassword;
public RegistrationBean()
{
this.firstName=null;
this.lastName=null;
this.email=null;
this.password=null;
this.confirmPassword=null;
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



}
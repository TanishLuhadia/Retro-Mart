package com.thinking.machines.retro.beans;
public class UserBean implements java.io.Serializable
{
private String email;
private String password;
public UserBean()
{
this.email=null;
this.password=null;
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


}
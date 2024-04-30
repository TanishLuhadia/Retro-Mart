package com.thinking.machines.retro.dto;
public class UserDTO 
{
private String email;
private String password;
private Boolean active;
public UserDTO()
{
this.email=null;
this.password=null;
this.active=false;
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

public void setActive(Boolean active)
{
this.active=active;
}
public Boolean getActive()
{
return this.active;
}
}
package com.thinking.machines.retro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpRequest {
    private String phoneNumber;
private String username;
public void setPhoneNumber(String phoneNumber)
{
this.phoneNumber=phoneNumber;
}
public void setUsername(String username)
{
this.username=username;
}
public String getPhoneNumber()
{
return this.phoneNumber;
}
public String getUsername()
{
return this.username;
}


}

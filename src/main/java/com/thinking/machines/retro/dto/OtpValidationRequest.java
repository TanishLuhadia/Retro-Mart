package com.thinking.machines.retro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpValidationRequest {
	private String otpNumber;
private String email;
public void setOtpNumber(String otp)
{
this.otpNumber=otp;
}	
public void setEmail(String email)
{
this.email=email;
}
public String getOtpNumber()
{
return this.otpNumber;
}	
public String getEmail()
{
return this.email;
}

}

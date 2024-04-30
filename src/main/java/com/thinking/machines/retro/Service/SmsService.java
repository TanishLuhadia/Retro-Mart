package com.thinking.machines.retro.Service;

import java.text.DecimalFormat;
import java.util.*;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import com.thinking.machines.retro.beans.*;
import com.thinking.machines.retro.dao.*;
import com.thinking.machines.retro.dto.*;

import com.thinking.machines.retro.config.TwilioConfig;
import com.thinking.machines.retro.dto.*;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class SmsService {

@Autowired
private TwilioConfig twilioConfig;
Map<String, String> otpMap = new HashMap<>();

public void sendSuccessfullyAddedMessage(String contactNumber,String subject,String body)
{
String msg="Subject : "+subject+"\n"+body;
try
{
PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber()); // from
PhoneNumber to = new PhoneNumber(contactNumber);//to
Message message = Message
			        .creator(to, from,
			                msg)
			        .create();
		
} catch (Exception e) {
			e.printStackTrace();
		}

}






public OtpResponseDto sendSMS(OtpRequest otpRequest) 
{
OtpResponseDto otpResponseDto = null;
try
 {
PhoneNumber to = new PhoneNumber(otpRequest.getPhoneNumber());//to
PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber()); // from
String otp = generateOTP();
System.out.println("OTP is : "+otp);
String otpMessage = "Dear Customer , Your OTP is  " + otp + " for verifying phone number. Thank You Team Retro Mart.";
Message message = Message
			        .creator(to, from,
			                otpMessage)
			        .create();
			otpMap.put(otpRequest.getUsername(), otp);
			otpResponseDto = new OtpResponseDto(OtpStatus.DELIVERED, otpMessage);
		
} catch (Exception e) {
			e.printStackTrace();
			otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, e.getMessage());
		}
System.out.println("Sending otp map to "+otpRequest.getUsername());
		return otpResponseDto;
	}
	
public String validateOtp(OtpValidationRequest otpValidationRequest) 
{
for (Map.Entry<String, String> entry : otpMap.entrySet()) {
        if (otpValidationRequest.getEmail().equals(entry.getKey()) &&
            otpValidationRequest.getOtpNumber().equals(entry.getValue())) {
            otpMap.remove(entry.getKey());
            System.out.println("Valid otp");
            return "OTP is valid!";
        }
    }
System.out.println("Invalid otp");

            return "OTP is invalid!";
     
	}
	
	private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }




public String validateOtpBuyer(OtpValidationRequest otpValidationRequest) 
{
System.out.println("11111111111111111");
System.out.println("("+otpValidationRequest.getOtpNumber()+")");
System.out.println("("+otpValidationRequest.getEmail()+")");
for (Map.Entry<String, String> entry : otpMap.entrySet()) {
        if (otpValidationRequest.getEmail().equals(entry.getKey()) &&
            otpValidationRequest.getOtpNumber().equals(entry.getValue())) {
            otpMap.remove(entry.getKey());
            System.out.println("Valid otp");

            return "OTP is valid!";
        }
    }
System.out.println("Invalid otp");

            return "OTP is invalid!";
     
	}




}
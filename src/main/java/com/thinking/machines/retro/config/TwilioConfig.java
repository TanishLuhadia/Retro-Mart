package com.thinking.machines.retro.config;
import java.util.Properties;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.*;
import org.springframework.stereotype.Component;
import lombok.Data;
@Configuration
@Component
@ConfigurationProperties(prefix="twilio")
@Data
public class TwilioConfig 
{
private String accountSid;
private String authToken;
private String phoneNumber;

}
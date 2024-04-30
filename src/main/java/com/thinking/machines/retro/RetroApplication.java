package com.thinking.machines.retro;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.thinking.machines.retro.config.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import jakarta.annotation.PostConstruct;
import com.twilio.Twilio;


@SpringBootApplication
@EnableConfigurationProperties
public class RetroApplication {


@Autowired
private TwilioConfig twilioConfig;
@PostConstruct
public void setup()
{
Twilio.init(twilioConfig.getAccountSid(),twilioConfig.getAuthToken());
}
	public static void main(String[] args) {
		SpringApplication.run(RetroApplication.class, args);
	}

}

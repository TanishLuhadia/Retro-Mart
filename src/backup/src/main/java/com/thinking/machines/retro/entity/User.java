package com.thinking.machines.retro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Data
public class User 
{
@GeneratedValue(strategy = GenerationType.IDENTITY)
private firstName ;
private String lastName;
private String email;
private String password;
private String confirmPassword
private boolean active;
private String otp;
private LocalDateTime otpGeneratedTime;
}
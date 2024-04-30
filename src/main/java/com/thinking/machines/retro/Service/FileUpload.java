package com.thinking.machines.retro.Service;
import com.thinking.machines.retro.beans.*;
import com.thinking.machines.retro.dao.*;
import com.thinking.machines.retro.dto.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.*;
import com.thinking.machines.retro.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import com.google.gson.*;
import java.io.*;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
@Component
public class FileUpload
{
public final String Upload_dir="C:\\SpringBoot2\\retro\\src\\main\\resources\\static\\images";
public boolean uploadFile(MultipartFile f1,MultipartFile f2,MultipartFile f3,MultipartFile f4,MultipartFile f5)
{
boolean f=false;
try
{
String uniqueFileName1 = saveFile(f1);
String uniqueFileName2 = saveFile(f2);
String uniqueFileName3 = saveFile(f3);
String uniqueFileName4 = saveFile(f4);
String uniqueFileName5 = saveFile(f5);
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
CarBean car=(CarBean) session.getAttribute("car");
ImageDAO imageDAO=new ImageDAO();
imageDAO.add(car.getId(),uniqueFileName1,uniqueFileName2,uniqueFileName3,uniqueFileName4,uniqueFileName5);
f=true;
}catch(Exception e)
{
e.printStackTrace();
}
return f;
}


public boolean uploadProfile(MultipartFile f1)
{
boolean f=false;
try
{
String uniqueFileName1 = saveFile(f1);
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
UserBean user=(UserBean) session.getAttribute("loggedInUser");
ImageDAO imageDAO=new ImageDAO();
imageDAO.uploadProfile(user.getEmail(),uniqueFileName1);
f=true;
}catch(Exception e)
{
e.printStackTrace();
}
return f;
}


public boolean uploadFileForBike(MultipartFile f1,MultipartFile f2,MultipartFile f3,MultipartFile f4,MultipartFile f5)
{
boolean f=false;
try
{
String uniqueFileName1 = saveFile(f1);
String uniqueFileName2 = saveFile(f2);
String uniqueFileName3 = saveFile(f3);
String uniqueFileName4 = saveFile(f4);
String uniqueFileName5 = saveFile(f5);
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
BikeBean bike=(BikeBean) session.getAttribute("bike");
ImageDAO imageDAO=new ImageDAO();
imageDAO.addBike(bike.getId(),uniqueFileName1,uniqueFileName2,uniqueFileName3,uniqueFileName4,uniqueFileName5);
f=true;
}catch(Exception e)
{
e.printStackTrace();
}
return f;
}

  private String saveFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFileName = generateUniqueFileName() + fileExtension;
        
        Files.copy(
            file.getInputStream(),
            Paths.get(Upload_dir + File.separator + uniqueFileName),
            StandardCopyOption.REPLACE_EXISTING
             );
return uniqueFileName;
}

 private static String generateUniqueFileName() {
        return System.currentTimeMillis() + "_" + UUID.randomUUID().toString();
    }



}

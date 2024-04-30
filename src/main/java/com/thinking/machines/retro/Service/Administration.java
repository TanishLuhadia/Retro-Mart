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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.springframework.http.MediaType;
import com.google.gson.*;
import java.io.*;
import 
jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.*;
import org.springframework.util.StreamUtils;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
@Controller
@Slf4j
public class Administration
{
@Autowired 
UserService userService;
@Autowired
private JavaMailSender mailSender;
@Autowired
private SmsService smsService;
@Autowired 
private FileUpload fileUpload;
@Autowired 
private FileService fileService;

@PostMapping("/register")
@ResponseBody
public ActionResponse register(RegistrationBean registrationBean)
{
ActionResponse actionResponse;
actionResponse=new ActionResponse();
System.out.println(registrationBean.getFirstName());
System.out.println(registrationBean.getLastName());
System.out.println(registrationBean.getEmail());
System.out.println(registrationBean.getPassword());
System.out.println(registrationBean.getConfirmPassword());
String email;
email=registrationBean.getEmail();
RegistrationDAO registrationDAO=new RegistrationDAO();
try
{
if(registrationDAO.getByEmail(email))
{
actionResponse.setSuccessful(false);
actionResponse.setException("Email already exist");
actionResponse.setResult("");
System.out.println("Sending error");
return actionResponse;
}
}catch(DAOException daoException)
{
actionResponse.setSuccessful(false);
actionResponse.setException(daoException.getMessage());
actionResponse.setResult("");
System.out.println("Sending error");
return actionResponse;
}
try
{
userService.register(registrationBean);
registrationDAO.add(registrationBean);
}catch(Exception e)
{
actionResponse.setSuccessful(false);
actionResponse.setException(e.getMessage());
actionResponse.setResult("");
System.out.println("Sending error");
return actionResponse;
}
//sendEmail(registrationBean.getEmail(),"Registration On Retro Successfull","Congratulations! you are now part of our Retro Family\nThank You!!");
actionResponse.setSuccessful(true);
actionResponse.setException("");
actionResponse.setResult("Linked Send please verify your account");
System.out.println("Sending Successfully");
return actionResponse;
}


@GetMapping("/verify-account")
public String verifyAccount(@RequestParam String email,@RequestParam String otp) 
{
System.out.println("Verifying account method got called");
ActionResponse actionResponse=userService.verifyAccount(email,otp);
if(actionResponse.getSuccessful()) return "redirect:/login.html";
return "redirect:/regenerate.html";
}


@GetMapping("/regenerate")
@ResponseBody
public ActionResponse regenerateOtp(@RequestParam("email") String email) 
{
System.out.println("Email : "+email);
ActionResponse actionResponse=userService.regenerateOTP(email);
return actionResponse;
}

@PostMapping("/authenticate")
@ResponseBody
public ActionResponse authenticate(UserBean userBean) 
{
ActionResponse actionResponse;
String email;
String password;
email=userBean.getEmail();
password=userBean.getPassword();
System.out.println("Email : "+email+" Password : "+password);
RegistrationDAO registrationDAO=new RegistrationDAO();
UserDTO user;
try
{
user=registrationDAO.getLoggedUserEmail(email);
}catch(DAOException daoException)
{
actionResponse=new ActionResponse();
actionResponse.setSuccessful(false);
actionResponse.setException(daoException.getMessage());
actionResponse.setResult("");
System.out.println("*****");
return actionResponse;
}
System.out.println("Active : "+user.getActive());
Boolean active=user.getActive();

if(!active)
{
actionResponse=new ActionResponse();
actionResponse.setSuccessful(false);
actionResponse.setException("Please verify the account");
actionResponse.setResult("");
System.out.println("#####");
return actionResponse;
}
String actualPassword=user.getPassword();
if(actualPassword.equals(password)==false)
{
actionResponse=new ActionResponse();
actionResponse.setSuccessful(false);
actionResponse.setException("Wrong password");
actionResponse.setResult("");
System.out.println("&&&&&&");
return actionResponse;
}
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
session.setAttribute("loggedInUser", userBean);
actionResponse=new ActionResponse();
actionResponse.setSuccessful(true);
actionResponse.setException("");
actionResponse.setResult("Logged In");
System.out.println("Logged In");
return actionResponse;
}




@ResponseBody
@GetMapping("/process")
public String processSMS()
{
return "SMS sent";
}


@GetMapping("/sendOTP")
@ResponseBody
public OtpResponseDto sendOtp(CarBean carBean)
{
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
UserBean user=(UserBean) session.getAttribute("loggedInUser");
carBean.setEmail(user.getEmail());
System.out.println("Brand : "+carBean.getBrand());
System.out.println("Model : "+carBean.getModel());
System.out.println("Varient : "+carBean.getVarient());
System.out.println("Year : "+carBean.getYear());
System.out.println("Fuel : "+carBean.getFuel());
System.out.println("Transmission : "+carBean.getTransmission());
System.out.println("Driven : "+carBean.getDriven());
System.out.println("Number of owner : "+carBean.getNumberOfOwner());
System.out.println("Condition : "+carBean.getConditions());
System.out.println("Features : "+carBean.getFeatures());
System.out.println("State : "+carBean.getState());
System.out.println("City : "+carBean.getCity());
System.out.println("Price : "+carBean.getPrice());
System.out.println("Email : "+carBean.getEmail());
session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
session.setAttribute("car",carBean);
System.out.println("Sending otp to phone number : "+carBean.getContactNumber());
OtpRequest otpRequest;
otpRequest=new OtpRequest();
otpRequest.setPhoneNumber(carBean.getContactNumber());
otpRequest.setUsername(carBean.getEmail());
return smsService.sendSMS(otpRequest);
}

@GetMapping("/sendOTPForBike")
@ResponseBody
public OtpResponseDto sendOtp(BikeBean bikeBean)
{
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
UserBean user=(UserBean) session.getAttribute("loggedInUser");
bikeBean.setEmail(user.getEmail());
System.out.println("Brand : "+bikeBean.getBrand());
System.out.println("Model : "+bikeBean.getModel());
System.out.println("Year : "+bikeBean.getYear());
System.out.println("Fuel : "+bikeBean.getFuel());
System.out.println("Driven : "+bikeBean.getDriven());
System.out.println("Number of owner : "+bikeBean.getNumberOfOwner());
System.out.println("Condition : "+bikeBean.getConditions());
System.out.println("Features : "+bikeBean.getFeatures());
System.out.println("State : "+bikeBean.getState());
System.out.println("City : "+bikeBean.getCity());
System.out.println("Price : "+bikeBean.getPrice());
System.out.println("Email : "+bikeBean.getEmail());
session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
session.setAttribute("bike",bikeBean);
System.out.println("Sending otp to phone number : "+bikeBean.getContactNumber());
OtpRequest otpRequest;
otpRequest=new OtpRequest();
otpRequest.setPhoneNumber(bikeBean.getContactNumber());
otpRequest.setUsername(bikeBean.getEmail());
return smsService.sendSMS(otpRequest);
}


@GetMapping("/sendOTPBuyer")
@ResponseBody
public OtpResponseDto sendOtpBuyer(@RequestParam("number") String phoneNumber)
{
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
UserBean user=(UserBean) session.getAttribute("loggedInUser");
System.out.println("Sending otp to phone number : "+phoneNumber);
OtpRequest otpRequest;
otpRequest=new OtpRequest();
otpRequest.setPhoneNumber(phoneNumber);
session.setAttribute("buyerNumber",phoneNumber);
otpRequest.setUsername(user.getEmail());
return smsService.sendSMS(otpRequest);
}




@GetMapping("/validateOTP")
@ResponseBody
public String validateOtp(@RequestParam("otp") String otp)
{
OtpValidationRequest otpValidationRequest=new OtpValidationRequest();
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
CarBean car=(CarBean) session.getAttribute("car");
otpValidationRequest.setOtpNumber(otp);
System.out.println(otp);
System.out.println(car.getEmail());
otpValidationRequest.setEmail(car.getEmail());
System.out.println(" i am here");
return smsService.validateOtp(otpValidationRequest);
}


@GetMapping("/validateOTPBike")
@ResponseBody
public String validateOtpBike(@RequestParam("otp") String otp)
{
OtpValidationRequest otpValidationRequest=new OtpValidationRequest();
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
BikeBean bike=(BikeBean) session.getAttribute("bike");
otpValidationRequest.setOtpNumber(otp);
System.out.println(otp);
System.out.println(bike.getEmail());
otpValidationRequest.setEmail(bike.getEmail());
System.out.println(" i am here");
return smsService.validateOtp(otpValidationRequest);
}

@GetMapping("/validateOTPBuyer")
@ResponseBody
public String validateOtpBuyer(@RequestParam("otp") String otp)
{
OtpValidationRequest otpValidationRequest=new OtpValidationRequest();
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
UserBean user=(UserBean) session.getAttribute("loggedInUser");
otpValidationRequest.setOtpNumber(otp);
System.out.println("222222222222222"+otp);
System.out.println(user.getEmail());
otpValidationRequest.setEmail(user.getEmail());
System.out.println(" i am here");
return smsService.validateOtpBuyer(otpValidationRequest);
}


@ResponseBody
@GetMapping("/addCar")
public ActionResponse add()
{
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
CarBean car=(CarBean) session.getAttribute("car");
ActionResponse actionResponse;
actionResponse=new ActionResponse();
CarDAO carDAO=new CarDAO();
try
{
int code=carDAO.add(car);
car.setId(code);
session.setAttribute("car",car);
}catch(Exception e)
{
actionResponse.setSuccessful(false);
actionResponse.setException(e.getMessage());
actionResponse.setResult("");
System.out.println("Sending error");
return actionResponse;
}
actionResponse.setSuccessful(true);
actionResponse.setException("");
actionResponse.setResult("Car details added");
System.out.println("Added");
return actionResponse;
}

@ResponseBody
@GetMapping("/addBike")
public ActionResponse addBike()
{
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
BikeBean bike=(BikeBean) session.getAttribute("bike");
ActionResponse actionResponse;
actionResponse=new ActionResponse();
BikeDAO bikeDAO=new BikeDAO();
try
{
int code=bikeDAO.add(bike);
bike.setId(code);
session.setAttribute("bike",bike);
}catch(Exception e)
{
actionResponse.setSuccessful(false);
actionResponse.setException(e.getMessage());
actionResponse.setResult("");
System.out.println("Sending error");
return actionResponse;
}
actionResponse.setSuccessful(true);
actionResponse.setException("");
actionResponse.setResult("Bike details added");
System.out.println("Added");
return actionResponse;
}

  public static String encode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // Handle the exception as needed
            e.printStackTrace();
            return value; // Return the original value in case of an error
        }
    }

    
@PostMapping("/uploadImages")
    public String handleFileUpload(
            @RequestParam("file1") MultipartFile frontImage,
            @RequestParam("file2") MultipartFile leftSideImage,
            @RequestParam("file3") MultipartFile rightSideImage,
            @RequestParam("file4") MultipartFile backImage,
            @RequestParam("file5") MultipartFile topImage
    ) 
{
ActionResponse actionResponse=new ActionResponse();
System.out.println(frontImage.getOriginalFilename());
System.out.println(frontImage.getSize());
System.out.println(frontImage.getContentType());
System.out.println(frontImage.getName());
if(frontImage.isEmpty())
{
String reason = "One or more files are empty";
String encodedReason = encode(reason);
String redirectURL = "redirect:/error.html?reason=" + encodedReason;
return redirectURL;
}
if(!frontImage.getContentType().equals("image/jpeg") || !leftSideImage.getContentType().equals("image/jpeg") || !rightSideImage.getContentType().equals("image/jpeg") || !topImage.getContentType().equals("image/jpeg") || !backImage.getContentType().equals("image/jpeg"))
{
String reason = "One or more files are not of type jpeg";
String encodedReason = encode(reason);
String redirectURL = "redirect:/error.html?reason=" + encodedReason;
return redirectURL;
}
boolean f=fileUpload.uploadFile(frontImage,leftSideImage,rightSideImage,backImage,topImage);
if(!f)
{
String reason = "Server error";
String encodedReason = encode(reason);
String redirectURL = "redirect:/error.html?reason=" + encodedReason;
return redirectURL;
}

HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
CarBean car=(CarBean) session.getAttribute("car");
UserBean user=(UserBean) session.getAttribute("loggedInUser");
String body="Dear Customer";
body+=",\nCongratulations! Your details have been successfully submitted.\nThank you for choosing our platform.\nHere is a summary of your submission:\n";
body+="Brand : ";
body+=car.getBrand()+"\n";
body+="Model : ";
body+=car.getModel()+"\n";
body+="Varient : ";
body+=car.getVarient()+"\n";
body+="Year of purchasing : ";
body+=car.getYear()+"\n";
body+="Fuel Type : ";
body+=car.getFuel()+"\n";
body+="Transmission : ";
body+=car.getTransmission()+"\n";
body+="K.M Driven : ";
body+=car.getDriven()+"\n";
body+="Number of owner : ";
body+=car.getNumberOfOwner()+"\n";
body+="Car condition : ";
body+=car.getConditions()+"\n";
body+="Features : ";
body+=car.getFeatures()+"\n";
body+="State : ";
body+=car.getState()+"\n";
body+="City : ";
body+=car.getCity()+"\n";
body+="Price : ";
body+=car.getPrice()+"\n";
body+="Contact Number : ";
body+=car.getContactNumber()+"\n";
body+="Email : ";
body+=car.getEmail()+"\n";
body+="If you have any further questions or need assistance, feel free to contact our support team at \n";
body+="tanishjain5801@gmail.com\n";
body+="Thank you for choosing us!\n";
body+="Best regards,\n";
body+="Team Retro Mart";
userService.sendSuccessfullyAddedEmail(car.getEmail(),"Submission Successful",body);

smsService.sendSuccessfullyAddedMessage(car.getContactNumber(),"Submission Successful",body);


return "AddedSuccessful";



  }


@ResponseBody
@PostMapping("/uploadProfile")
    public String handleProfileUpload(
            @RequestParam("file1") MultipartFile profile) 
{
ActionResponse actionResponse=new ActionResponse();
System.out.println(profile.getOriginalFilename());
System.out.println(profile.getSize());
System.out.println(profile.getContentType());
System.out.println(profile.getName());
if(profile.isEmpty())
{
return "No";
}
if(!profile.getContentType().equals("image/jpeg"))
{
String reason = "File is not of type jpeg";
String encodedReason = encode(reason);
return "No";
}
boolean f=fileUpload.uploadProfile(profile);
if(!f)
{
return "No";

}
return "Uploaded";
  }





@PostMapping("/uploadImagesBike")
    public String handleFileUploadBike(
            @RequestParam("file1") MultipartFile frontImage,
            @RequestParam("file2") MultipartFile leftSideImage,
            @RequestParam("file3") MultipartFile rightSideImage,
            @RequestParam("file4") MultipartFile backImage,
            @RequestParam("file5") MultipartFile topImage
    ) 
{
ActionResponse actionResponse=new ActionResponse();
System.out.println(frontImage.getOriginalFilename());
System.out.println(frontImage.getSize());
System.out.println(frontImage.getContentType());
System.out.println(frontImage.getName());
if(frontImage.isEmpty())
{
String reason = "One or more files are empty";
String encodedReason = encode(reason);
String redirectURL = "redirect:/error.html?reason=" + encodedReason;
return redirectURL;
}
if(!frontImage.getContentType().equals("image/jpeg") || !leftSideImage.getContentType().equals("image/jpeg") || !rightSideImage.getContentType().equals("image/jpeg") || !topImage.getContentType().equals("image/jpeg") || !backImage.getContentType().equals("image/jpeg"))
{
String reason = "One or more files are not of type jpeg";
String encodedReason = encode(reason);
String redirectURL = "redirect:/error.html?reason=" + encodedReason;
return redirectURL;
}
boolean f=fileUpload.uploadFileForBike(frontImage,leftSideImage,rightSideImage,backImage,topImage);
if(!f)
{
String reason = "Server error";
String encodedReason = encode(reason);
String redirectURL = "redirect:/error.html?reason=" + encodedReason;
return redirectURL;
}

HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
BikeBean bike=(BikeBean) session.getAttribute("bike");
UserBean user=(UserBean) session.getAttribute("loggedInUser");
String body="Dear Customer";
body+=",\nCongratulations! Your details have been successfully submitted.\nThank you for choosing our platform.\nHere is a summary of your submission:\n";
body+="Brand : ";
body+=bike.getBrand()+"\n";
body+="Model : ";
body+=bike.getModel()+"\n";
body+="Year of purchasing : ";
body+=bike.getYear()+"\n";
body+="Fuel Type : ";
body+=bike.getFuel()+"\n";
body+="K.M Driven : ";
body+=bike.getDriven()+"\n";
body+="Number of owner : ";
body+=bike.getNumberOfOwner()+"\n";
body+="Car condition : ";
body+=bike.getConditions()+"\n";
body+="Features : ";
body+=bike.getFeatures()+"\n";
body+="State : ";
body+=bike.getState()+"\n";
body+="City : ";
body+=bike.getCity()+"\n";
body+="Price : ";
body+=bike.getPrice()+"\n";
body+="Contact Number : ";
body+=bike.getContactNumber()+"\n";
body+="Email : ";
body+=bike.getEmail()+"\n";
body+="If you have any further questions or need assistance, feel free to contact our support team at \n";
body+="tanishjain5801@gmail.com\n";
body+="Thank you for choosing us!\n";
body+="Best regards,\n";
body+="Team Retro Mart";
userService.sendSuccessfullyAddedEmail(bike.getEmail(),"Submission Successful",body);
smsService.sendSuccessfullyAddedMessage(bike.getContactNumber(),"Submission Successful",body);
return "AddedSuccessful";
}




@PostMapping("/car/getByEmail")
@ResponseBody
public List<CarDTO> getByEmail(@RequestParam("email") String email) 
{
CarDAO carDAO=new CarDAO();
List<CarDTO> lst=null;
try
{
lst=carDAO.getByEmail(email);
}catch(DAOException exception)
{
System.out.println(exception);
}
return lst;
}

@PostMapping("/bike/getByEmail")
@ResponseBody
public List<BikeDTO> getByEmailBike(@RequestParam("email") String email) 
{
BikeDAO bikeDAO=new BikeDAO();
List<BikeDTO> lst=null;
try
{
lst=bikeDAO.getByEmail(email);
}catch(DAOException exception)
{
System.out.println(exception);
}
return lst;
}

@GetMapping("/car/getAll")
@ResponseBody
public List<CarDTO> getAll() 
{
CarDAO carDAO=new CarDAO();
List<CarDTO> lst=null;
try
{
lst=carDAO.getAll();
}catch(DAOException exception)
{
System.out.println(exception);
}
return lst;
}

@GetMapping("/bike/getAll")
@ResponseBody
public List<BikeDTO> getAllBike() 
{
BikeDAO bikeDAO=new BikeDAO();
List<BikeDTO> lst=null;
try
{
lst=bikeDAO.getAll();
}catch(DAOException exception)
{
System.out.println(exception);
}
return lst;
}


@GetMapping("/car/getCarByCarId")
@ResponseBody
public CarDTO getCarByCarId(@RequestParam("carId") int carId) 
{
CarDAO carDAO=new CarDAO();
CarDTO car=null;
try
{
car=carDAO.getByCarId(carId);
}catch(DAOException exception)
{
System.out.println(exception);
}
return car;
}


@GetMapping("/bike/getBikeByBikeId")
@ResponseBody
public BikeDTO getBikeByBikeId(@RequestParam("bikeId") int bikeId) 
{
BikeDAO bikeDAO=new BikeDAO();
BikeDTO bike=null;
try
{
bike=bikeDAO.getByBikeId(bikeId);
}catch(DAOException exception)
{
System.out.println(exception);
}
return bike;
}


@GetMapping("/image/getAll")
@ResponseBody
public List<ImageDTO> getAllImage() 
{
ImageDAO imageDAO=new ImageDAO();
List<ImageDTO> lst=null;
try
{
lst=imageDAO.getAll();
}catch(DAOException exception)

{
System.out.println(exception);
}
return lst;
}

@GetMapping("/user/getUser")
@ResponseBody
public RegistrationDTO getUser(@RequestParam("email") String email) 
{
System.out.println("in /user/getUser");
System.out.println(email);
RegistrationDAO registrationDAO=new RegistrationDAO();
RegistrationDTO registrationDTO=null;
try
{
registrationDTO=registrationDAO.getUserByEmail(email);
}catch(DAOException exception)
{
System.out.println(exception+"in getUser");
}
return registrationDTO;
}



@PostMapping("/user/update")
@ResponseBody
public String getUser(@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName ,@RequestParam("email") String email) 
{
System.out.println("in /user/update");
System.out.println(email);
RegistrationDAO registrationDAO=new RegistrationDAO();
try
{
registrationDAO.updateUser(email,firstName,lastName);
}catch(DAOException exception)
{
System.out.println(exception);
}
return "Updated";
}


@GetMapping("/image/getImageByCarId")
@ResponseBody
public ImageDTO getImageByCarId(@RequestParam("carId") int carId) 
{
System.out.println("Inside image/getImageByCarId");
System.out.println(carId);
ImageDAO imageDAO=new ImageDAO();
ImageDTO imageDTO=null;
try
{
imageDTO=imageDAO.getImageByCarId(carId);
}catch(DAOException exception)
{
System.out.println(exception);
}
return imageDTO;
}


@GetMapping("/image/getImageByBikeId")
@ResponseBody
public ImageDTO getImageByBikeId(@RequestParam("bikeId") int bikeId) 
{
System.out.println("Inside image/getImageByBikeId");
System.out.println(bikeId);
ImageDAO imageDAO=new ImageDAO();
ImageDTO imageDTO=null;
try
{
imageDTO=imageDAO.getImageByBikeId(bikeId);
}catch(DAOException exception)
{
System.out.println(exception);
}
return imageDTO;
}

@GetMapping("/login")
public String login() 
{
UserBean user;
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
user=(UserBean) session.getAttribute("loggedInUser");
if(user==null) return "login";
return "category";
}

@GetMapping("/newIndex")
public String newIndex() 
{
UserBean user;
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
user=(UserBean) session.getAttribute("loggedInUser");
if(user==null) return "login";
return "newIndex";
}


@ResponseBody
@GetMapping("/getLoggedInUser")
public String getLoggedInUser() 
{
UserBean user;
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
user=(UserBean) session.getAttribute("loggedInUser");
if(user==null) return "-";
return user.getEmail();
}



@GetMapping("/category")
public String category() 
{
UserBean user;
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
user=(UserBean) session.getAttribute("loggedInUser");
if(user==null) return "login";
return "category";
}

@GetMapping("/carAdd")
public String categories() 
{
UserBean user;
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
user=(UserBean) session.getAttribute("loggedInUser");
if(user==null) return "login";
return "carAdd";
}



@GetMapping("/bikeAdd")
public String Bikecategories() 
{
UserBean user;
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
user=(UserBean) session.getAttribute("loggedInUser");
if(user==null) return "login";
return "Bike";
}

@GetMapping("/mobileAdd")
public String Mobilecategories() 
{
UserBean user;
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
user=(UserBean) session.getAttribute("loggedInUser");
if(user==null) return "login";
return "Mobile";
}
@GetMapping("/furnitureAdd")
public String Furniturecategories() 
{
UserBean user;
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
user=(UserBean) session.getAttribute("loggedInUser");
if(user==null) return "login";
return "Furniture";
}


@GetMapping("/otp")
public String otp() 
{
UserBean user;
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
user=(UserBean) session.getAttribute("loggedInUser");
if(user==null) return "login";
return "otp";
}

@GetMapping("/otpBike")
public String otpBike() 
{
UserBean user;
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
user=(UserBean) session.getAttribute("loggedInUser");
if(user==null) return "login";
return "otpBike";
}

@GetMapping("/otpBuyer")
public String otpBuyer() 
{
UserBean user;
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
user=(UserBean) session.getAttribute("loggedInUser");
if(user==null) return "login";
return "otpBuyer";
}


@GetMapping("/otpBuyerBike")
public String otpBuyerBike() 
{
UserBean user;
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
user=(UserBean) session.getAttribute("loggedInUser");
if(user==null) return "login";
return "otpBuyerBike";
}


@GetMapping("/successful")
public String successfull() 
{
UserBean user;
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
user=(UserBean) session.getAttribute("loggedInUser");
if(user==null) return "login";
return "successful";
}

@GetMapping("/successfulBike")
public String successfullBike() 
{
UserBean user;
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
user=(UserBean) session.getAttribute("loggedInUser");
if(user==null) return "login";
return "successfulBike";
}

@GetMapping("/successfulBuyer")
public String successfullBuyer() 
{
UserBean user;
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
user=(UserBean) session.getAttribute("loggedInUser");
if(user==null) return "login";
String buyerNumber=(String) session.getAttribute("buyerNumber");
String buyerEmail=user.getEmail();
int carId=(Integer) session.getAttribute("id");
CarDAO car=null;
CarDTO carDTO=null;
RegistrationDAO registrationDAO=null;
RegistrationDTO registrationDTO=null;
String sellerNumber="";
String sellerEmail="";
String buyerName="";
try
{
registrationDAO=new RegistrationDAO();
car=new CarDAO();
carDTO=car.getByCarId(carId);
sellerNumber=carDTO.getContactNumber();
sellerEmail=carDTO.getEmail();
registrationDTO=registrationDAO.getUserByEmail(buyerEmail);
buyerName=registrationDTO.getFirstName()+" "+registrationDTO.getLastName();
}catch(Exception e)
{
}
System.out.println("Seller Email : "+sellerEmail);
System.out.println("Buyer Email : "+sellerEmail);
System.out.println("Seller number : "+sellerNumber);
System.out.println("Buyer number : "+buyerNumber);

String body="Dear Customer";
body+=",\nCongratulations! Your details has been successfully shared with seller.\nSeller will contact you as soon as possible.\nThank you for choosing our platform.\nHere is a summary of your interest:\n";
body+="Brand : ";
body+=carDTO.getBrand()+"\n";
body+="Model : ";
body+=carDTO.getModel()+"\n";
body+="Varient : ";
body+=carDTO.getVarient()+"\n";
body+="Year of purchasing : ";
body+=carDTO.getYear()+"\n";
body+="Fuel Type : ";
body+=carDTO.getFuel()+"\n";
body+="Transmission : ";
body+=carDTO.getTransmission()+"\n";
body+="K.M Driven : ";
body+=carDTO.getDriven()+"\n";
body+="Number of owner : ";
body+=carDTO.getNumberOfOwner()+"\n";
body+="Car condition : ";
body+=carDTO.getConditions()+"\n";
body+="Features : ";
body+=carDTO.getFeatures()+"\n";
body+="State : ";
body+=carDTO.getState()+"\n";
body+="City : ";
body+=carDTO.getCity()+"\n";
body+="Price : ";
body+=carDTO.getPrice()+"\n";
body+="If you have any further questions or need assistance, feel free to contact our support team at \n";
body+="tanishjain5801@gmail.com\n";
body+="Thank you for choosing us!\n";
body+="Best regards,\n";
body+="Team Retro Mart";
userService.sendSuccessfullyAddedEmail(buyerEmail,"Submission Successful",body);
smsService.sendSuccessfullyAddedMessage(buyerNumber,"Submission Successful",body);








String bodyy="Dear Customer";
bodyy+=",\nCongratulations! You get a buyer.\nThank you for choosing our platform.\nHere is a summary of buyer:\n";
bodyy+="Name : ";
bodyy+=buyerName+"\n";
bodyy+="Email : ";
bodyy+=buyerEmail+"\n";
bodyy+="Contact Number : ";
bodyy+=buyerNumber+"\n";
bodyy+="If you have any further questions or need assistance, feel free to contact our support team at \n";
bodyy+="tanishjain5801@gmail.com\n";
bodyy+="Thank you for choosing us!\n";
bodyy+="Best regards,\n";
bodyy+="Team Retro Mart";
userService.sendSuccessfullyAddedEmail(sellerEmail,"Buyer Reached",bodyy);
smsService.sendSuccessfullyAddedMessage(sellerNumber,"Buyer Reached",bodyy);








return "successfulBuyer";
}



@GetMapping("/successfulBuyerBike")
public String successfullBuyerBike() 
{
UserBean user;
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
user=(UserBean) session.getAttribute("loggedInUser");
if(user==null) return "login";
String buyerNumber=(String) session.getAttribute("buyerNumber");
String buyerEmail=user.getEmail();
int bikeId=(Integer) session.getAttribute("id");
BikeDAO bike=null;
BikeDTO bikeDTO=null;
RegistrationDAO registrationDAO=null;
RegistrationDTO registrationDTO=null;
String sellerNumber="";
String sellerEmail="";
String buyerName="";
try
{
registrationDAO=new RegistrationDAO();
bike=new BikeDAO();
bikeDTO=bike.getByBikeId(bikeId);
sellerNumber=bikeDTO.getContactNumber();
sellerEmail=bikeDTO.getEmail();
registrationDTO=registrationDAO.getUserByEmail(buyerEmail);
buyerName=registrationDTO.getFirstName()+" "+registrationDTO.getLastName();
}catch(Exception e)
{
}
System.out.println("Seller Email : "+sellerEmail);
System.out.println("Buyer Email : "+sellerEmail);
System.out.println("Seller number : "+sellerNumber);
System.out.println("Buyer number : "+buyerNumber);

String body="Dear Customer";
body+=",\nCongratulations! Your details has been successfully shared with seller.\nSeller will contact you as soon as possible.\nThank you for choosing our platform.\nHere is a summary of your interest:\n";
body+="Brand : ";
body+=bikeDTO.getBrand()+"\n";
body+="Model : ";
body+=bikeDTO.getModel()+"\n";
body+="Year of purchasing : ";
body+=bikeDTO.getYear()+"\n";
body+="Fuel Type : ";
body+=bikeDTO.getFuel()+"\n";
body+="K.M Driven : ";
body+=bikeDTO.getDriven()+"\n";
body+="Number of owner : ";
body+=bikeDTO.getNumberOfOwner()+"\n";
body+="Bike condition : ";
body+=bikeDTO.getConditions()+"\n";
body+="Features : ";
body+=bikeDTO.getFeatures()+"\n";
body+="State : ";
body+=bikeDTO.getState()+"\n";
body+="City : ";
body+=bikeDTO.getCity()+"\n";
body+="Price : ";
body+=bikeDTO.getPrice()+"\n";
body+="If you have any further questions or need assistance, feel free to contact our support team at \n";
body+="tanishjain5801@gmail.com\n";
body+="Thank you for choosing us!\n";
body+="Best regards,\n";
body+="Team Retro Mart";
userService.sendSuccessfullyAddedEmail(buyerEmail,"Submission Successful",body);
smsService.sendSuccessfullyAddedMessage(buyerNumber,"Submission Successful",body);








String bodyy="Dear Customer";
bodyy+=",\nCongratulations! You get a buyer.\nThank you for choosing our platform.\nHere is a summary of buyer:\n";
bodyy+="Name : ";
bodyy+=buyerName+"\n";
bodyy+="Email : ";
bodyy+=buyerEmail+"\n";
bodyy+="Contact Number : ";
bodyy+=buyerNumber+"\n";
bodyy+="If you have any further questions or need assistance, feel free to contact our support team at \n";
bodyy+="tanishjain5801@gmail.com\n";
bodyy+="Thank you for choosing us!\n";
bodyy+="Best regards,\n";
bodyy+="Team Retro Mart";
userService.sendSuccessfullyAddedEmail(sellerEmail,"Buyer Reached",bodyy);
smsService.sendSuccessfullyAddedMessage(sellerNumber,"Buyer Reached",bodyy);








return "successfulBuyerBike";
}



@GetMapping("/image")
public String image() 
{
UserBean user;
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
user=(UserBean) session.getAttribute("loggedInUser");
if(user==null) return "login";
return "image";
}


@GetMapping("/imageBike")
public String imageBike() 
{
UserBean user;
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
user=(UserBean) session.getAttribute("loggedInUser");
if(user==null) return "login";
return "imageBike";
}


@ResponseBody
@PostMapping("/showMoreDetail")
public ActionResponse showMoreDetail(@RequestParam("id") int carId,@RequestParam("email") String email,@RequestParam("product") String product) 
{
ActionResponse actionResponse=null;
System.out.println("**************("+carId+") and ("+email+") and ("+product+")********* ");
try
{
actionResponse=new ActionResponse();
UserBean user;
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
user=(UserBean) session.getAttribute("loggedInUser");
session.setAttribute("id",carId);
session.setAttribute("email",email);
session.setAttribute("product",product);
if(user==null) 
{
actionResponse.setSuccessful(false);
actionResponse.setException("Not loggedIn");
actionResponse.setResult("");
return actionResponse;
}
actionResponse.setSuccessful(true);
actionResponse.setException("");
actionResponse.setResult("Logged in user");
}catch(Exception e)
{
}
return actionResponse;
}

@ResponseBody
@PostMapping("/getProfile")
public ActionResponse getProfile(@RequestParam("email") String email) 
{
ActionResponse actionResponse=null;
System.out.println("in service in getProfile");
ImageDAO imageDAO=null;
try
{
imageDAO=new ImageDAO();
String image=imageDAO.getProfile(email);
actionResponse=new ActionResponse();
actionResponse.setSuccessful(true);
actionResponse.setException("");
actionResponse.setResult(image);
System.out.println(image);
}catch(Exception e)
{
actionResponse=new ActionResponse();
actionResponse.setSuccessful(false);
actionResponse.setException("");
actionResponse.setResult(e.getMessage());
}
return actionResponse;
}


@ResponseBody
@PostMapping("/addDetails")
public ActionResponse addDetails(@RequestParam("id") int carId,@RequestParam("email") String email,@RequestParam("product") String product) 
{
ActionResponse actionResponse=null;
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
System.out.println("In addDetails**************("+carId+") and ("+email+")*********");
try
{
session.setAttribute("id",carId);
session.setAttribute("email",email);
session.setAttribute("product",product);

actionResponse.setSuccessful(true);
actionResponse.setException("");
actionResponse.setResult("Details added");
}catch(Exception e)
{
}
return actionResponse;
}



@GetMapping("/showDetails")
public String showDetails() 
{
UserBean user;
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
user=(UserBean) session.getAttribute("loggedInUser");
if(user==null) return "loginForPurchase";
String product=(String) session.getAttribute("product");
System.out.println("Product is : "+product);
if(product.equalsIgnoreCase("car"))
{
return "carShowDetails";
}
else if(product.equalsIgnoreCase("bike"))
{
return "bikeShowDetails";
}

return "showDetails";
}



@ResponseBody
@PostMapping("/getCarId")
public int getCarId()
{
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
int carId =(Integer)session.getAttribute("id");
if(carId!=0) return carId;
return 0;
}

@ResponseBody
@PostMapping("/delete/car")
public String deleteCar(@RequestParam("carId") int carId)
{
CarDAO car=null;
try
{
car=new CarDAO();
car.deleteCar(carId);
}catch(Exception e)
{
}
System.out.println("Returning newprofiles from deleteCar");
return "newprofiles";
}


@GetMapping("/newprofile")
public String newProfile()
{
UserBean user;
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
user=(UserBean) session.getAttribute("loggedInUser");
if(user==null) return "loginForProfileViewer";
return "newprofiles";
}

@PostMapping("/delete/bike")
public String deleteBike(@RequestParam("bikeId") int bikeId)
{
BikeDAO bike=null;
try
{
bike=new BikeDAO();
bike.deleteBike(bikeId);
}catch(Exception e)
{
}
return "newprofiles";
}





@ResponseBody
@PostMapping("/getBikeId")
public int getBikeId()
{
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
int bikeId =(Integer)session.getAttribute("id");
if(bikeId!=0) return bikeId;
return 0;
}


@ResponseBody
@PostMapping("/getEmail")
public String getEmail()
{
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
String email=(String) session.getAttribute("email");
if(email!="") return email;
return "";
}

@ResponseBody
@PostMapping("/getProduct")
public String getProduct()
{
HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
String product=(String) session.getAttribute("product");
if(product!="") return product;
return "";
}
@GetMapping(value="/images/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
public void downloadImages(@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException
{
System.out.println("Inside downloadImages");
InputStream resource=this.fileService.getResource("c:/springBoot2/retro/src/main/resources/static/images/",imageName);
response.setContentType(MediaType.IMAGE_JPEG_VALUE);
StreamUtils.copy(resource,response.getOutputStream());

}


}
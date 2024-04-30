package com.thinking.machines.retro.dao;
import java.sql.*;
import java.time.LocalDateTime;
import com.thinking.machines.retro.beans.*;
import com.thinking.machines.retro.dto.*;

public class RegistrationDAO
{
public Boolean getByEmail(String email) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from registration where email=?");
preparedStatement.setString(1,email);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true) return true;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return false;
}

public Boolean checkItsActive(String email) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from registration where email=?");
preparedStatement.setString(1,email);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true)
{
return resultSet.getBoolean("active");
}
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return false;
}

public RegistrationDTO getUserByEmail(String email) throws DAOException
{
RegistrationDTO registrationDTO;
registrationDTO=new RegistrationDTO();
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from registration where email=?");
preparedStatement.setString(1,email);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true)
{
registrationDTO.setFirstName(resultSet.getString("firstName").trim());
registrationDTO.setLastName(resultSet.getString("lastName").trim());
registrationDTO.setEmail(resultSet.getString("email").trim());
registrationDTO.setPassword(resultSet.getString("password").trim());
registrationDTO.setConfirmPassword(resultSet.getString("confirmPassword").trim());
registrationDTO.setActive(resultSet.getBoolean("active"));
registrationDTO.setOTP(resultSet.getString("OTP").trim());
registrationDTO.setOTPGeneratedTime(resultSet.getTimestamp("OTPGeneratedTime").toLocalDateTime());
}
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return registrationDTO;
}



public void add(RegistrationBean registration) throws DAOException
{
try
{
String email=registration.getEmail();
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select firstName from registration where email=?");
preparedStatement.setString(1,email);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Email : "+email+" exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into registration(firstName,lastName,email,password,confirmPassword,active,OTP,OTPGeneratedTime) values (?,?,?,?,?,?,?,?)");
preparedStatement.setString(1,registration.getFirstName());
preparedStatement.setString(2,registration.getLastName());
preparedStatement.setString(3,registration.getEmail());
preparedStatement.setString(4,registration.getPassword());
preparedStatement.setString(5,registration.getConfirmPassword());
preparedStatement.setBoolean(6,registration.getActive());
preparedStatement.setString(7,registration.getOTP());
preparedStatement.setTimestamp(8, Timestamp.valueOf(registration.getOTPGeneratedTime()));
preparedStatement.executeUpdate();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}



public void updateUser(String email,String firstName,String lastName) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select firstName from registration where email=?");
preparedStatement.setString(1,email);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Email : "+email);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update registration set firstName=?,lastName=? where  email=?");
preparedStatement.setString(1,firstName);
preparedStatement.setString(2,lastName);
preparedStatement.setString(3,email);
preparedStatement.executeUpdate();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}




public void setUserActive(String email) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select firstName from registration where email=?");
preparedStatement.setString(1,email);
ResultSet resultSet=preparedStatement.executeQuery();
resultSet.next();
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update  registration set active=true where email=?");
preparedStatement.setString(1,email);
preparedStatement.executeUpdate();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

public void setOTP(String email,String otp) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select firstName from registration where email=?");
preparedStatement.setString(1,email);
ResultSet resultSet=preparedStatement.executeQuery();
resultSet.next();
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update  registration set OTP=? where email=?");
preparedStatement.setString(1,otp);
preparedStatement.setString(2,email);
preparedStatement.executeUpdate();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public void setOTPGeneratedTime(String email,LocalDateTime date) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select firstName from registration where email=?");
preparedStatement.setString(1,email);
ResultSet resultSet=preparedStatement.executeQuery();
resultSet.next();
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update  registration set OTPGeneratedTime=? where email=?");
preparedStatement.setTimestamp(1, Timestamp.valueOf(date));
preparedStatement.setString(2,email);
preparedStatement.executeUpdate();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

public UserDTO getLoggedUserEmail(String email) throws DAOException
{
UserDTO user=new UserDTO();
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select email,password,active from registration where email=?");
preparedStatement.setString(1,email);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid email : "+email+"int getLoggedInUser");
}
user.setEmail(resultSet.getString("email").trim());
user.setPassword(resultSet.getString("password").trim());
user.setActive(resultSet.getBoolean("active"));
System.out.println(" I am here");
System.out.println(user.getEmail());
System.out.println(user.getPassword());
System.out.println(user.getActive());
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return user;
}

}
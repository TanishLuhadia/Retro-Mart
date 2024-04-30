package com.thinking.machines.retro.dao;
import java.sql.*;
import com.thinking.machines.retro.beans.*;
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
preparedStatement=connection.prepareStatement("insert into registration(firstName,lastName,email,password,confirmPassword) values (?,?,?,?,?)");
preparedStatement.setString(1,registration.getFirstName());
preparedStatement.setString(2,registration.getLastName());
preparedStatement.setString(3,registration.getEmail());
preparedStatement.setString(4,registration.getPassword());
preparedStatement.setString(5,registration.getConfirmPassword());
preparedStatement.executeUpdate();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}





/*
public Boolean authenicate(Administrator administrator) throws DAOException
{
String uname,pass,username,password;
username=administrator.getUsername();
password=administrator.getPassword();
uname="";
pass="";
try
{
System.out.println("Hello i am inside AdministratorDAO.java");
Connection connection=DAOConnection.getConnection();
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select username , password from administrator");
while(resultSet.next())
{
uname=resultSet.getString("username");
pass=resultSet.getString("password").trim();
}
if(uname.equals(username) && pass.equals(password))
{
return true;
}
resultSet.close();
statement.close();
connection.close();
}catch(Exception exception)
{
System.out.println(exception);
}
return false;
}
*/
}
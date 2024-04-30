package com.thinking.machines.retro.dao;
import java.sql.*;
import com.thinking.machines.retro.beans.*;
import com.thinking.machines.retro.dto.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.*;
public class ProfileDAO
{
public void add(String email,String f1) throws DAOException
{
try
{
PreparedStatement preparedStatement;
Connection connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("insert into profile_image(email,image) values (?,?)");
preparedStatement.setString(1,email);
preparedStatement.setString(2,f1);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public ProfileDTO getProfileByEmail(String email) throws DAOException
{
ProfileDTO profileDTO=null;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from profile_image where email=?");
preparedStatement.setString(1,email);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid email : "+email);
}
preparedStatement=connection.prepareStatement("select email,image from profile_image where email=?");
preparedStatement.setString(1,email);
resultSet=preparedStatement.executeQuery();
String mailId;
String img;
while(resultSet.next())
{
mailId=resultSet.getString("email").trim();
img=resultSet.getString("image").trim();
profileDTO=new ProfileDTO();
profileDTO.setEmail(mailId);
profileDTO.setImage(img);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return profileDTO;
}




}
package com.thinking.machines.retro.dao;
import java.sql.*;
import com.thinking.machines.retro.beans.*;
import com.thinking.machines.retro.dto.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.*;
public class ImageDAO
{
public void add(int code,String f1,String f2,String f3,String f4,String f5) throws DAOException
{
try
{
PreparedStatement preparedStatement;
Connection connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("insert into car_image(car_id,frontImage,leftSideImage,rightSideImage,backImage,topImage) values (?,?,?,?,?,?)");
preparedStatement.setInt(1,code);
preparedStatement.setString(2,f1);
preparedStatement.setString(3,f2);
preparedStatement.setString(4,f3);
preparedStatement.setString(5,f4);
preparedStatement.setString(6,f5);

preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

public void uploadProfile(String email,String f1) throws DAOException
{
try
{
PreparedStatement preparedStatement;
Connection connection=DAOConnection.getConnection();

preparedStatement=connection.prepareStatement("select image from profile_image where email=?");
preparedStatement.setString(1,email);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into profile_image(email,image) values (?,?)");
preparedStatement.setString(1,email);
preparedStatement.setString(2,f1);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}
else
{
updateProfile(email,f1);
}


}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

public String getProfile(String email) throws DAOException
{
String image="";
try
{
System.out.println("Get profile got invoked");
PreparedStatement preparedStatement;
Connection connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select  image from profile_image where email=?");
preparedStatement.setString(1,email);
ResultSet resultSet=preparedStatement.executeQuery();
while(resultSet.next())
{
image=resultSet.getString("image");
}
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException("1000_F_589932782_vQAEAZhHnq1QCGu5ikwrYaQD0Mmurm0N (1).jpg");
}
if(image.length()!=0) return image;
else 
{
throw new DAOException("1000_F_589932782_vQAEAZhHnq1QCGu5ikwrYaQD0Mmurm0N (1).jpg");
}
}


public void updateProfile(String email,String f1) throws DAOException
{
try
{
System.out.println("Update profile got invoked");
PreparedStatement preparedStatement;
Connection connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("update profile_image set image=? where email=?");
preparedStatement.setString(1,f1);
preparedStatement.setString(2,email);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}



public void addBike(int code,String f1,String f2,String f3,String f4,String f5) throws DAOException
{
try
{
PreparedStatement preparedStatement;
Connection connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("insert into bike_image(bike_id,frontImage,leftSideImage,rightSideImage,backImage,topImage) values (?,?,?,?,?,?)");
preparedStatement.setInt(1,code);
preparedStatement.setString(2,f1);
preparedStatement.setString(3,f2);
preparedStatement.setString(4,f3);
preparedStatement.setString(5,f4);
preparedStatement.setString(6,f5);

preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}

}




public ImageDTO getImageByCarId(int carId) throws DAOException
{
ImageDTO imageDTO=null;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from car_image where car_id=?");
preparedStatement.setInt(1,carId);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid car Id : "+carId);
}
preparedStatement=connection.prepareStatement("select id,frontImage,leftSideImage,rightSideImage,backImage,topImage,car_id from car_image where car_id=?");
preparedStatement.setInt(1,carId);
resultSet=preparedStatement.executeQuery();
int id;
String frontImage;
String leftSideImage;
String rightSideImage;
String backImage;
String topImage;
int car_id;
while(resultSet.next())
{
id=resultSet.getInt("id");
frontImage=resultSet.getString("frontImage").trim();
leftSideImage=resultSet.getString("leftSideImage").trim();
rightSideImage=resultSet.getString("rightSideImage").trim();
backImage=resultSet.getString("backImage").trim();
topImage=resultSet.getString("topImage").trim();
car_id=resultSet.getInt("car_id");
imageDTO=new ImageDTO();
imageDTO.setId(id);
imageDTO.setFrontImage(frontImage);
imageDTO.setLeftSideImage(leftSideImage);
imageDTO.setRightSideImage(rightSideImage);
imageDTO.setBackImage(backImage);
imageDTO.setTopImage(topImage);
imageDTO.setCarId(car_id);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return imageDTO;
}



public ImageDTO getImageByBikeId(int bikeId) throws DAOException
{
ImageDTO imageDTO=null;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from bike_image where bike_id=?");
preparedStatement.setInt(1,bikeId);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid bike Id : "+bikeId);
}
preparedStatement=connection.prepareStatement("select id,frontImage,leftSideImage,rightSideImage,backImage,topImage,bike_id from bike_image where bike_id=?");
preparedStatement.setInt(1,bikeId);
resultSet=preparedStatement.executeQuery();
int id;
String frontImage;
String leftSideImage;
String rightSideImage;
String backImage;
String topImage;
int bike_id;
while(resultSet.next())
{
id=resultSet.getInt("id");
frontImage=resultSet.getString("frontImage").trim();
leftSideImage=resultSet.getString("leftSideImage").trim();
rightSideImage=resultSet.getString("rightSideImage").trim();
backImage=resultSet.getString("backImage").trim();
topImage=resultSet.getString("topImage").trim();
bike_id=resultSet.getInt("bike_id");
imageDTO=new ImageDTO();
imageDTO.setId(id);
imageDTO.setFrontImage(frontImage);
imageDTO.setLeftSideImage(leftSideImage);
imageDTO.setRightSideImage(rightSideImage);
imageDTO.setBackImage(backImage);
imageDTO.setTopImage(topImage);
imageDTO.setBikeId(bike_id);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return imageDTO;
}



public List<ImageDTO>getAll() throws DAOException
{
List<ImageDTO> images;
images=new LinkedList<>();
ImageDTO imageDTO=null;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from car_image");
ResultSet resultSet=preparedStatement.executeQuery();
int id;
String frontImage;
String leftSideImage;
String rightSideImage;
String backImage;
String topImage;
int carId;
while(resultSet.next())
{
id=resultSet.getInt("id");
frontImage=resultSet.getString("frontImage").trim();
leftSideImage=resultSet.getString("leftSideImage").trim();
rightSideImage=resultSet.getString("rightSideImage").trim();
backImage=resultSet.getString("backImage").trim();
topImage=resultSet.getString("topImage").trim();
carId=resultSet.getInt("car_id");
imageDTO=new ImageDTO();
imageDTO.setId(id);
imageDTO.setFrontImage(frontImage);
imageDTO.setLeftSideImage(leftSideImage);
imageDTO.setRightSideImage(rightSideImage);
imageDTO.setBackImage(backImage);
imageDTO.setTopImage(topImage);
imageDTO.setCarId(carId);
images.add(imageDTO);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return images;
}


// done

}
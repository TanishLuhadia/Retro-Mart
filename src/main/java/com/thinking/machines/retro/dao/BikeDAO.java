package com.thinking.machines.retro.dao;
import java.sql.*;
import com.thinking.machines.retro.beans.*;
import com.thinking.machines.retro.dto.*;
import java.time.*;
import java.util.*;
import java.math.*;
public class BikeDAO
{
public int add(BikeBean bikeBean) throws DAOException
{
int code=0;
try
{
PreparedStatement preparedStatement;
Connection connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("insert into bike(model,year,price,driven,conditions,fuel,features,contactNumber,email,brand,numberOfOwner,state,city,date,product) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,bikeBean.getModel());
preparedStatement.setInt(2,bikeBean.getYear());
preparedStatement.setDouble(3,bikeBean.getPrice());
preparedStatement.setInt(4,bikeBean.getDriven());
preparedStatement.setString(5,bikeBean.getConditions());
preparedStatement.setString(6,bikeBean.getFuel());
preparedStatement.setString(7,bikeBean.getFeatures());
preparedStatement.setString(8,bikeBean.getContactNumber());
preparedStatement.setString(9,bikeBean.getEmail());
preparedStatement.setString(10,bikeBean.getBrand());
preparedStatement.setString(11,bikeBean.getNumberOfOwner());
preparedStatement.setString(12,bikeBean.getState());
preparedStatement.setString(13,bikeBean.getCity());
preparedStatement.setDate(14,java.sql.Date.valueOf(LocalDate.now()));
preparedStatement.setString(15,"bike");
preparedStatement.executeUpdate();
ResultSet resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
code=resultSet.getInt(1);
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return code;
}



public void deleteBike(int bikeId) throws DAOException
{
try
{
PreparedStatement preparedStatement;
Connection connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("delete from bike_image where bike_id=?");

preparedStatement.setInt(1,bikeId);
preparedStatement.executeUpdate();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from bike where id=?");

preparedStatement.setInt(1,bikeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
System.out.println("Deleted");
}catch(SQLException sqlException)
{
System.out.println(sqlException);
throw new DAOException(sqlException.getMessage());
}
}



public List<BikeDTO> getByEmail(String email) throws DAOException
{
List<BikeDTO> bikes;
bikes=new LinkedList<>();
BikeDTO bikeDTO=null;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select id from bike where email=?");
preparedStatement.setString(1,email);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid email Id : "+email+"or no record found");
}
preparedStatement=connection.prepareStatement("select bike.id,bike.brand,bike.model,bike.year,bike.fuel,bike.driven,bike.numberOfOwner,bike.conditions,bike.features,bike.state,bike.city,bike.price,bike.contactNumber,bike.date,bike.product from bike where email=?");
preparedStatement.setString(1,email);
resultSet=preparedStatement.executeQuery();
int id;
String brand;
String model;
int year;
String fuel;
int driven;
String numberOfOwner;
String conditions;
String features;
String state;
String city;
String product;
Double price;
String contactNumber;
java.util.Date date;
while(resultSet.next())
{
id=resultSet.getInt("id");
brand=resultSet.getString("brand").trim();
model=resultSet.getString("model").trim();
year=resultSet.getInt("year");
fuel=resultSet.getString("fuel").trim();
driven=resultSet.getInt("driven");
numberOfOwner=resultSet.getString("numberOfOwner").trim();
conditions=resultSet.getString("conditions").trim();
features=resultSet.getString("features").trim();
state=resultSet.getString("state").trim();
city=resultSet.getString("city").trim();
price=resultSet.getDouble("price");
contactNumber=resultSet.getString("contactNumber").trim();
date=resultSet.getDate("date");
product=contactNumber=resultSet.getString("product").trim();
bikeDTO=new BikeDTO();
bikeDTO.setId(id);
bikeDTO.setBrand(brand);
bikeDTO.setModel(model);
bikeDTO.setYear(year);
bikeDTO.setFuel(fuel);
bikeDTO.setDriven(driven);
bikeDTO.setNumberOfOwner(numberOfOwner);
bikeDTO.setConditions(conditions);
bikeDTO.setFeatures(features);
bikeDTO.setState(state);
bikeDTO.setCity(city);
bikeDTO.setPrice(price);
bikeDTO.setContactNumber(contactNumber);
bikeDTO.setEmail(email);
bikeDTO.setDate(date);
bikeDTO.setProduct(product);
bikes.add(bikeDTO);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return bikes;
}
public List<BikeDTO>getAll() throws DAOException
{
List<BikeDTO> bikes;
bikes=new LinkedList<>();
BikeDTO bikeDTO=null;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select bike.id,bike.brand,bike.model,bike.year,bike.fuel,bike.driven,bike.numberOfOwner,bike.conditions,bike.features,bike.state,bike.city,bike.price,bike.contactNumber,bike.email,bike.date ,bike.product from bike ");
ResultSet resultSet=preparedStatement.executeQuery();
int id;
String brand;
String model;
int year;
String fuel;
int driven;
String numberOfOwner;
String conditions;
String features;
String state;
String city;
String product;
Double price;
String contactNumber;
String email;
java.util.Date date;
while(resultSet.next())
{
id=resultSet.getInt("id");
brand=resultSet.getString("brand").trim();
model=resultSet.getString("model").trim();
year=resultSet.getInt("year");
fuel=resultSet.getString("fuel").trim();
driven=resultSet.getInt("driven");
numberOfOwner=resultSet.getString("numberOfOwner").trim();
conditions=resultSet.getString("conditions").trim();
features=resultSet.getString("features").trim();
state=resultSet.getString("state").trim();
city=resultSet.getString("city").trim();
price=resultSet.getDouble("price");
contactNumber=resultSet.getString("contactNumber").trim();
email=resultSet.getString("email").trim();
date=resultSet.getDate("date");
product=resultSet.getString("product").trim();
bikeDTO=new BikeDTO();
bikeDTO.setId(id);
bikeDTO.setBrand(brand);
bikeDTO.setModel(model);
bikeDTO.setYear(year);
bikeDTO.setFuel(fuel);
bikeDTO.setDriven(driven);
bikeDTO.setNumberOfOwner(numberOfOwner);
bikeDTO.setConditions(conditions);
bikeDTO.setFeatures(features);
bikeDTO.setState(state);
bikeDTO.setCity(city);
bikeDTO.setPrice(price);
bikeDTO.setContactNumber(contactNumber);
bikeDTO.setEmail(email);
bikeDTO.setDate(date);
bikeDTO.setProduct(product);
bikes.add(bikeDTO);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return bikes;
}


public BikeDTO getByBikeId(int bikeId) throws DAOException
{
BikeDTO bikeDTO=null;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select brand from bike where id=?");
preparedStatement.setInt(1,bikeId);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid bike Id : "+bikeId);
}
preparedStatement=connection.prepareStatement("select bike.id,bike.brand,bike.model,bike.year,bike.fuel,bike.driven,bike.numberOfOwner,bike.conditions,bike.features,bike.state,bike.city,bike.price,bike.contactNumber,bike.email,bike.date,bike.product from bike where id=?");
preparedStatement.setInt(1,bikeId);
resultSet=preparedStatement.executeQuery();
int id;
String brand;
String model;
int year;
String fuel;
int driven;
String numberOfOwner;
String conditions;
String features;
String email;
String state;
String city;
String product;
Double price;
String contactNumber;
java.util.Date date;
while(resultSet.next())
{
id=resultSet.getInt("id");
brand=resultSet.getString("brand").trim();
model=resultSet.getString("model").trim();
year=resultSet.getInt("year");
fuel=resultSet.getString("fuel").trim();

driven=resultSet.getInt("driven");
numberOfOwner=resultSet.getString("numberOfOwner").trim();
conditions=resultSet.getString("conditions").trim();
features=resultSet.getString("features").trim();
state=resultSet.getString("state").trim();
city=resultSet.getString("city").trim();
price=resultSet.getDouble("price");
contactNumber=resultSet.getString("contactNumber").trim();
email=resultSet.getString("email").trim();
date=resultSet.getDate("date");
product=resultSet.getString("product").trim();
bikeDTO=new BikeDTO();
bikeDTO.setId(id);
bikeDTO.setBrand(brand);
bikeDTO.setModel(model);
bikeDTO.setYear(year);
bikeDTO.setFuel(fuel);
bikeDTO.setDriven(driven);
bikeDTO.setNumberOfOwner(numberOfOwner);
bikeDTO.setConditions(conditions);
bikeDTO.setFeatures(features);
bikeDTO.setState(state);
bikeDTO.setCity(city);
bikeDTO.setPrice(price);
bikeDTO.setContactNumber(contactNumber);
bikeDTO.setEmail(email);
bikeDTO.setDate(date);
bikeDTO.setProduct(product);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return bikeDTO;
}

}
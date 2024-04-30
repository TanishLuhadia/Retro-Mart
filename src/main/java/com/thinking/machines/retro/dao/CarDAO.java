package com.thinking.machines.retro.dao;
import java.sql.*;
import com.thinking.machines.retro.beans.*;
import com.thinking.machines.retro.dto.*;
import java.time.*;
import java.util.*;
import java.math.*;
public class CarDAO
{
public int add(CarBean carBean) throws DAOException
{
int code=0;
try
{
PreparedStatement preparedStatement;
Connection connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("insert into car(model,year,price,driven,conditions,transmission,fuel,features,contactNumber,email,brand,varient,numberOfOwner,state,city,date,product) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,carBean.getModel());
preparedStatement.setInt(2,carBean.getYear());
preparedStatement.setDouble(3,carBean.getPrice());
preparedStatement.setInt(4,carBean.getDriven());
preparedStatement.setString(5,carBean.getConditions());
preparedStatement.setString(6,carBean.getTransmission());
preparedStatement.setString(7,carBean.getFuel());
preparedStatement.setString(8,carBean.getFeatures());
preparedStatement.setString(9,carBean.getContactNumber());
preparedStatement.setString(10,carBean.getEmail());
preparedStatement.setString(11,carBean.getBrand());
preparedStatement.setString(12,carBean.getVarient());
preparedStatement.setString(13,carBean.getNumberOfOwner());
preparedStatement.setString(14,carBean.getState());
preparedStatement.setString(15,carBean.getCity());
preparedStatement.setDate(16,java.sql.Date.valueOf(LocalDate.now()));
preparedStatement.setString(17,"car");
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


public void deleteCar(int carId) throws DAOException
{
try
{
PreparedStatement preparedStatement;
Connection connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("delete from car_image where car_id=?");

preparedStatement.setInt(1,carId);
preparedStatement.executeUpdate();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from car where id=?");

preparedStatement.setInt(1,carId);
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

public List<CarDTO> getByEmail(String email) throws DAOException
{
List<CarDTO> cars;
cars=new LinkedList<>();
CarDTO carDTO=null;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select id from car where email=?");
preparedStatement.setString(1,email);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid email Id : "+email+"or no record found");
}
preparedStatement=connection.prepareStatement("select car.id,car.brand,car.model,car.varient,car.year,car.fuel,car.transmission,car.driven,car.numberOfOwner,car.conditions,car.features,car.state,car.city,car.price,car.contactNumber,car.date,car.product from car where email=?");
preparedStatement.setString(1,email);
resultSet=preparedStatement.executeQuery();
int id;
String brand;
String model;
String varient;
int year;
String fuel;
String transmission;
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
varient=resultSet.getString("varient").trim();
year=resultSet.getInt("year");
fuel=resultSet.getString("fuel").trim();
transmission=resultSet.getString("transmission").trim();
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
carDTO=new CarDTO();
carDTO.setId(id);
carDTO.setBrand(brand);
carDTO.setModel(model);
carDTO.setVarient(varient);
carDTO.setYear(year);
carDTO.setFuel(fuel);
carDTO.setTransmission(transmission);
carDTO.setDriven(driven);
carDTO.setNumberOfOwner(numberOfOwner);
carDTO.setConditions(conditions);
carDTO.setFeatures(features);
carDTO.setState(state);
carDTO.setCity(city);
carDTO.setPrice(price);
carDTO.setContactNumber(contactNumber);
carDTO.setEmail(email);
carDTO.setDate(date);
carDTO.setProduct(product);
cars.add(carDTO);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return cars;
}
public List<CarDTO>getAll() throws DAOException
{
List<CarDTO> cars;
cars=new LinkedList<>();
CarDTO carDTO=null;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select car.id,car.brand,car.model,car.varient,car.year,car.fuel,car.transmission,car.driven,car.numberOfOwner,car.conditions,car.features,car.state,car.city,car.price,car.contactNumber,car.email,car.date ,car.product from car ");
ResultSet resultSet=preparedStatement.executeQuery();
int id;
String brand;
String model;
String varient;
int year;
String fuel;
String transmission;
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
varient=resultSet.getString("varient").trim();
year=resultSet.getInt("year");
fuel=resultSet.getString("fuel").trim();
transmission=resultSet.getString("transmission").trim();
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
carDTO=new CarDTO();
carDTO.setId(id);
carDTO.setBrand(brand);
carDTO.setModel(model);
carDTO.setVarient(varient);
carDTO.setYear(year);
carDTO.setFuel(fuel);
carDTO.setTransmission(transmission);
carDTO.setDriven(driven);
carDTO.setNumberOfOwner(numberOfOwner);
carDTO.setConditions(conditions);
carDTO.setFeatures(features);
carDTO.setState(state);
carDTO.setCity(city);
carDTO.setPrice(price);
carDTO.setContactNumber(contactNumber);
carDTO.setEmail(email);
carDTO.setDate(date);
carDTO.setProduct(product);
cars.add(carDTO);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return cars;
}


public CarDTO getByCarId(int carId) throws DAOException
{
CarDTO carDTO=null;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select brand from car where id=?");
preparedStatement.setInt(1,carId);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid car Id : "+carId);
}
preparedStatement=connection.prepareStatement("select car.id,car.brand,car.model,car.varient,car.year,car.fuel,car.transmission,car.driven,car.numberOfOwner,car.conditions,car.features,car.state,car.city,car.price,car.contactNumber,car.email,car.date,car.product from car where id=?");
preparedStatement.setInt(1,carId);
resultSet=preparedStatement.executeQuery();
int id;
String brand;
String model;
String varient;
int year;
String fuel;
String transmission;
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
varient=resultSet.getString("varient").trim();
year=resultSet.getInt("year");
fuel=resultSet.getString("fuel").trim();
transmission=resultSet.getString("transmission").trim();
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
carDTO=new CarDTO();
carDTO.setId(id);
carDTO.setBrand(brand);
carDTO.setModel(model);
carDTO.setVarient(varient);
carDTO.setYear(year);
carDTO.setFuel(fuel);
carDTO.setTransmission(transmission);
carDTO.setDriven(driven);
carDTO.setNumberOfOwner(numberOfOwner);
carDTO.setConditions(conditions);
carDTO.setFeatures(features);
carDTO.setState(state);
carDTO.setCity(city);
carDTO.setPrice(price);
carDTO.setContactNumber(contactNumber);
carDTO.setEmail(email);
carDTO.setDate(date);
carDTO.setProduct(product);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return carDTO;
}

}
package com.thinking.machines.retro.dao;
import java.sql.*;
public class DAOConnection
{
private DAOConnection(){}
static public Connection getConnection() throws DAOException
{
Connection connection=null;
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/retro","retrouser","retrouser");
return connection;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
}
package com.thinking.machines.retro.dto;
import java.util.*;
public class ImageDTO
{
private int id;
private String frontImage;
private String leftSideImage;
private String rightSideImage;
private  String backImage;
private String topImage;
private int carId;
private int bikeId;
public ImageDTO()
{
}
public void setId(int id)
{
this.id=id;
}
public int getId()
{
return this.id;
}
public void setFrontImage(String frontImage) 
{
this.frontImage=frontImage;
}
public String getFrontImage() 
{
return this.frontImage;
}

public void setLeftSideImage(String leftSideImage) 
{
this.leftSideImage=leftSideImage;
}
public String getLeftSideImage() 
{
return this.leftSideImage;
}

public void setRightSideImage(String rightSideImage) 
{
this.rightSideImage=rightSideImage;
}
public String getRightSideImage() 
{
return this.rightSideImage;
}

public void setBackImage(String backImage) 
{
this.backImage=backImage;
}
public String getBackImage() 
{
return this.backImage;
}

public void setTopImage(String topImage) 
{
this.topImage=topImage;
}
public String getTopImage() 
{
return this.topImage;
}

public void setCarId(int carId) 
{
this.carId=carId;
}
public int getCarId() 
{
return this.carId;
}

public void setBikeId(int bikeId) 
{
this.bikeId=bikeId;
}
public int getBikeId() 
{
return this.bikeId;
}


}
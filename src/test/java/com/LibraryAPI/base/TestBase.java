package com.LibraryAPI.base;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestBase 
{
	public static RequestSpecification httpRequest;
	public static Response response;
	//public String isbn = "NPMN193";
	//public String isbnID = "NPMN193100";
	
	public Logger logger;
	
	@BeforeClass
	public void setUp()
	{
		logger = Logger.getLogger("LibraryRestAPI");
		PropertyConfigurator.configure("log4j.properties");
		logger.setLevel(Level.DEBUG);
		
	}

}

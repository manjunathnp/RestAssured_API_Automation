package com.LibraryAPI.testCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import  com.LibraryAPI.base.*;

import io.restassured.RestAssured;
import io.restassured.http.Method;

public class TC_001_GetAllBooks extends TestBase
{
	final String authorName = "Mathew Pushpa";
	
	@BeforeClass
	void getAllBooks() throws InterruptedException
	{
		logger.info("*********Started TC_001_GetAllBooks **********");
		
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		httpRequest = RestAssured.given();
		response = httpRequest.request(Method.GET,"Library/GetBook.php?AuthorName=");
		
		Thread.sleep(5);	
	}
	
	@Test
	void checkResposeBody_getAllBooks()
	{
		logger.info("***********  Checking Respose Body **********");
		
		String responseBody = response.getBody().asPrettyString();
		//logger.info("Response Body==>"+responseBody);
		Assert.assertTrue(responseBody!=null);
		
	}
	
	@Test
	void checkStatusCode_getAllBooks()
	{
		logger.info("***********  Checking Status Code **********");
		
		int statusCode = response.getStatusCode(); // Getting status code
		logger.info("Status Code:" + statusCode); //200
		Assert.assertEquals(statusCode, 200);
		
	}
	
	@Test
	void checkResponseTime_getAllBooks()
	{
		logger.info("***********  Checking Response Time **********");
		
		long responseTime = response.getTime(); // Getting status Line
		logger.info("Response_Time: " + responseTime);
		
		if(responseTime>2000)
			logger.warn("Response Time is greater than 100ms");
		
		Assert.assertTrue(responseTime<2000);
		
	}
	
	@Test
	void checkStatusLine_getAllBooks()
	{
		logger.info("***********  Checking Connection **********");
		
		String statusLine = response.getStatusLine(); // Getting status Line
		logger.info("Status_Line: " + statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	
	}
	
	@Test
	void checkContentType_getAllBooks()
	{
		logger.info("***********  Checking Content Type **********");
		
		String contentType = response.header("Content-Type");
		logger.info("Content-Type: " + contentType);
		Assert.assertEquals(contentType, "application/json;charset=UTF-8");
	}
	
	@Test
	void checkserverType_getAllBooks()
	{
		logger.info("***********  Checking Server Type **********");
		
		String serverType = response.header("Server");
		logger.info("Server-Type: " +serverType); 
		Assert.assertEquals(serverType, "Apache/2.4.18 (Ubuntu)");
	
	}
	
		
	@AfterClass
	void tearDown()
	{
		logger.info("*********  Finished TC_001_GetAllBooks **********");
	}
}

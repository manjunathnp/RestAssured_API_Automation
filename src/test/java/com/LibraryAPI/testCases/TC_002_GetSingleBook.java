package com.LibraryAPI.testCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.LibraryAPI.base.TestBase;
import com.LibraryAPI.utils.RestUtils;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TC_002_GetSingleBook extends TestBase
{
	RequestSpecification httpRequest;
	Response response;
	
	final String isbn = RestUtils.isbnNumber();
	final String aisle = RestUtils.aisleNumber();
	final String author = RestUtils.authorName();
	final String isbnID = "NPMN905728"; // Dont delete
	
	@BeforeClass
	void getSingleBook() throws InterruptedException
	{
	
		logger.info("********* Started TC_002_GetSingleBook **********");
		
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		httpRequest = RestAssured.given();
		response = httpRequest.request(Method.GET, "/Library/GetBook.php?ID="+isbnID);
	
	}
	
	@Test(enabled=true)
	void checkStatusCode_getSingleBook()
	{
		logger.info("***********  Checking Status Code **********");
		int statusCode = response.getStatusCode(); // Getting status code
		logger.info("Status_Code: "+statusCode);
		Assert.assertEquals(statusCode, 200);
	}
	
	@Test
	void checkResposeBody_getSingleBook()
	{
		logger.info("***********  Checking Response Body **********");
		String responseBody = response.getBody().asPrettyString();
		JsonPath js = new JsonPath(responseBody);
		//Assert.assertEquals(js.getString("isbn"), "["+isbn+"]");
		String isbnID_Resp = js.get("[0].isbn");
		String aisle_Resp = js.get("[0].aisle");
		String isbn = isbnID_Resp + aisle_Resp;
		
		logger.info("Response_Body: \n"+responseBody);
		//System.out.println(isbn);
		Assert.assertEquals(isbn, isbnID);
		
		/*
		JsonPath jsonPathEvaluator = response.jsonPath();
		String isbnId_Resp=jsonPathEvaluator.get("[0].isbn");
		String aisle_Resp = jsonPathEvaluator.get("[0].asile");
		String isbn = isbnId_Resp+aisle_Resp;
		System.out.println(isbn);
		*/
		
	}
	
	@Test
	void checkResponseTime_getSingleBook()
	{
		logger.info("***********  Checking Response Time **********");
		long responseTime = response.getTime(); // Getting Response Time 
		logger.info("Response_Time: "+responseTime);
		
		Assert.assertTrue(responseTime<6000);
		
	}
	
	@Test
	void checkContentType_getSingleBook()
	{
		logger.info("***********  Checking Content-Type **********");
		String contentType = response.header("Content-Type");
		logger.info("Content-Type: "+contentType);
		Assert.assertEquals(contentType, "application/json;charset=UTF-8");
	}
	
	@Test
	void checkserverType_getSingleBook()
	{
		logger.info("***********  Checking Server **********");
		String serverType = response.header("Server");
		logger.info("Server-Type: "+serverType);
		Assert.assertEquals(serverType, "Apache/2.4.18 (Ubuntu)");
	}
	
	@Test
	void checkStatusLine_getSingleBook()
	{
		logger.info("***********  Checking Connection **********");
		
		String statusLine = response.getStatusLine(); // Getting status Line
		logger.info("Status_Line: "+ statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	
	}
	
	@AfterClass
	void tearDown()
	{
		logger.info("*********  Finished TC_002_GetSingleBook **********");
	}

}

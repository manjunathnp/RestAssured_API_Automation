package com.LibraryAPI.testCases;

import org.json.simple.JSONObject;
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

public class TC_004_UpdateBook extends TestBase
{
	RequestSpecification httpRequest;
	Response response;
	
	final String isbn = RestUtils.isbnNumber();
	final String updatedAisle = RestUtils.updatedAisleNum();
	final String author = RestUtils.authorName();
	final String isbnID = isbn.concat(updatedAisle);

	
	@SuppressWarnings("unchecked")
	@BeforeClass
	public void updateBook() throws InterruptedException
	{
		logger.info("*********Started TC_003_UpdateBook **********");
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		httpRequest = RestAssured.given();
		

		// Add a header stating the Request body is a JSON
		JSONObject requestParams = new JSONObject();
		requestParams.put("name","Tester Testing Book");
		requestParams.put("isbn", isbn); 
		requestParams.put("aisle", updatedAisle);
		requestParams.put("author", author);
		
		// Add the JSON to the body of the request
		httpRequest.header("Content-Type", "application/json");
		
		// Add the JSON to the body of the request
		httpRequest.body(requestParams.toJSONString());
		
		response = httpRequest.request(Method.POST, "/Library/Addbook.php");
		
		System.out.println(isbn);
		System.out.println(updatedAisle);
		System.out.println(author);
		
	}
	
	@Test(priority=1)
	void checkStatusCode_updateBook()
	{
		logger.info("********* Status_Code_Validation **********");
		int statusCode = response.getStatusCode(); // Gettng status code
		logger.info("Status_Code: "+statusCode);
		Assert.assertEquals(statusCode, 200);
	}
	
	@Test(priority=2)
	void checkResposeBody_updateBook()
	{
		logger.info("********* Response_Body_Validation **********");
		String responseBody = response.getBody().asPrettyString();
		JsonPath js = new JsonPath(responseBody);
		
		String msg = js.get("Msg");
		String ID = js.get("ID");
		
		logger.info("Response_Body: \n"+responseBody);
				
		Assert.assertEquals(responseBody.contains(msg), true);
		Assert.assertEquals(responseBody.contains(ID), true);
		

	}
	
	@Test(priority=3)
	void validateUpdatedBook_updateBook() throws InterruptedException
	{
	
		logger.info("********* Updated_Book_Validation **********");
		response = httpRequest.request(Method.GET, "/Library/GetBook.php?ID="+isbnID);
		String responseBody = response.getBody().asPrettyString();
		JsonPath js = new JsonPath(responseBody);
		
		String isbnID_Resp = js.get("[0].isbn");
		String aisle_Resp = js.get("[0].aisle");
		String isbn = isbnID_Resp + aisle_Resp;
		//System.out.println("ISBN: "+isbn);
		
		logger.info("Response_Body_AfterUpdate: "+responseBody);
		Assert.assertEquals(isbn, isbnID);
	
	}
	
	@Test(priority=4)
	void checkResponseTime_updateBook()
	{
		logger.info("***********  Checking Response Time **********");
		long responseTime = response.getTime(); // Getting Response Time 
		logger.info("Response_Time: "+responseTime);
		
		Assert.assertTrue(responseTime<6000);
		
	}
	
	@Test(priority=5)
	void checkContentType_updateBook()
	{
		logger.info("***********  Checking Content-Type **********");
		String contentType = response.header("Content-Type");
		logger.info("Content-Type: "+contentType);
		Assert.assertEquals(contentType, "application/json;charset=UTF-8");
	}
	
	@Test(priority=6)
	void checkserverType_updateBook()
	{
		logger.info("***********  Checking Server **********");
		String serverType = response.header("Server");
		logger.info("Server-Type: "+serverType);
		Assert.assertEquals(serverType, "Apache/2.4.18 (Ubuntu)");
	}
	
	@Test(priority=7)
	void checkStatusLine_updateBook()
	{
		logger.info("***********  Checking Connection **********");
		
		String statusLine = response.getStatusLine(); // Getting status Line
		logger.info("Status_Line: "+ statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	
	}
	
	@AfterClass
	void tearDown()
	{
		logger.info("*********  Finished TC_004_UpdateBook **********");
	}

	
}
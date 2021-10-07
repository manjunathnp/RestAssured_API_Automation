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

public class TC_005_DeleteBook extends TestBase
{
	RequestSpecification httpRequest;
	Response response, response_afterDel;
	
	final String isbn = RestUtils.isbnNumber();
	final String aisle = RestUtils.aisleNumber();
	final String author = RestUtils.authorName();
	final String isbnID = isbn.concat(aisle);

	
	@SuppressWarnings("unchecked")
	@BeforeClass
	public void deleteBook() throws InterruptedException
	{
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		httpRequest = RestAssured.given();
		

		// Add a header stating the Request body is a JSON
		JSONObject requestParams = new JSONObject();
		requestParams.put("name","Tester Testing Book");
		requestParams.put("isbn", isbn); 
		requestParams.put("aisle", aisle);
		requestParams.put("author", author);
		
		// Add the JSON to the body of the request
		httpRequest.header("Content-Type", "application/json");
		
		// Add the JSON to the body of the request
		httpRequest.body(requestParams.toJSONString());
		
		response = httpRequest.request(Method.POST, "/Library/Addbook.php");
		/*
		System.out.println(isbn);
		System.out.println(aisle);
		System.out.println(author);
		*/
		JSONObject requestParams_del = new JSONObject();
		requestParams_del.put("ID", isbn.concat(aisle));
		httpRequest.body(requestParams_del.toJSONString());
		response_afterDel = httpRequest.request(Method.DELETE, "/Library/DeleteBook.php");
		
	}
	
	@Test(priority=1)
	void checkStatusCode_deleteBook()
	{
		logger.info("********* Status_Code_Validation **********");
		int statusCode = response.getStatusCode(); // Getting status code
		logger.info("Status_Code: "+statusCode);
		Assert.assertEquals(statusCode, 200);
	}
	
	@Test(priority=2)
	void checkResposeBody_deleteBook()
	{
		logger.info("********* Response_Body_Validation **********");
		String responseBody_afterDel = response_afterDel.getBody().asPrettyString();
		JsonPath js = new JsonPath(responseBody_afterDel);
		
		System.out.println("Response_Body: \n"+responseBody_afterDel);
		
		String msg = js.get("msg");
		//System.out.println(msg);
		
		Assert.assertEquals(responseBody_afterDel.contains(msg), true);
		
	}
	
	@Test(priority = 3)
	void validateDeletedBook_deleteBook()
	{
		logger.info("********* Deleted_Book_Body_Validation **********");
		String responseBody_afterDel = response_afterDel.getBody().asPrettyString();
		JsonPath js = new JsonPath(responseBody_afterDel);
		
		//logger.info("Response_Body: \n"+responseBody_afterDel);
		
		String msg = js.get("msg");
		String expectedMsg = "book is successfully deleted";
		Assert.assertEquals(msg, expectedMsg);
			logger.info("Book Deletion Successful");
	}
	
	@Test(priority=4)
	void checkResponseTime_deleteBook()
	{
		logger.info("***********  Checking Response Time **********");
		long responseTime = response.getTime(); // Getting Response Time 
		logger.info("Response_Time: "+responseTime);
		
		Assert.assertTrue(responseTime<6000);
		
	}
	
	@Test(priority=5)
	void checkContentType_deleteBook()
	{
		logger.info("***********  Checking Content-Type **********");
		String contentType = response.header("Content-Type");
		logger.info("Content-Type: "+contentType);
		Assert.assertEquals(contentType, "application/json;charset=UTF-8");
	}
	
	@Test(priority=6)
	void checkserverType_deleteBook()
	{
		logger.info("***********  Checking Server **********");
		String serverType = response.header("Server");
		logger.info("Server-Type: "+serverType);
		Assert.assertEquals(serverType, "Apache/2.4.18 (Ubuntu)");
	}
	
	@Test(priority=7)
	void checkStatusLine_deleteBook()
	{
		logger.info("***********  Checking Connection **********");
		
		String statusLine = response.getStatusLine(); // Getting status Line
		logger.info("Status_Line: "+ statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	
	}
	
	@AfterClass
	void tearDown()
	{
		logger.info("*********  Finished TC_005_DeleteBook **********");
	}

}

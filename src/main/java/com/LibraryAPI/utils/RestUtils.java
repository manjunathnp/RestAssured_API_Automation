package com.LibraryAPI.utils;
import org.apache.commons.lang3.RandomStringUtils;

public class RestUtils 
{
	public static String isbnNumber()
	{
		String isbnVal = RandomStringUtils.randomNumeric(5);
		return isbnVal;
	}
	
	public static String aisleNumber()
	{
		String aisleVal = RandomStringUtils.randomNumeric(1);
		return aisleVal;
	}
	
	public static String authorName()
	{
		String author = RandomStringUtils.randomAlphabetic(5);
		return author;
	}
	
	public static String updatedAisleNum()
	{
		String updatedAisleVal = RandomStringUtils.randomNumeric(1);
		return updatedAisleVal;
	}
	
	
	public static String getISBN()
	{
		return (isbnNumber().toString()+aisleNumber().toString());
	}
	
	public static void main(String[] args) {
		String s1 = "123";
		String s2 = "456";
		System.out.println(s1.concat(s2));
		System.out.println(RestUtils.isbnNumber().concat(RestUtils.aisleNumber()));
		/*
		System.out.println(RestUtils.isbnNumber());
		System.out.println(RestUtils.aisleNumber());
		System.out.println(RestUtils.isbnNumber().concat(RestUtils.aisleNumber()));
		*/
		//System.out.println(RestUtils.getISBN().toString());
	}
	
	
}

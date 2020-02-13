package com.zero.test;


import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.zero.service.Menu;


public class MenuTest {
	Menu menu;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		menu = new Menu();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void checkFirstNameLengthTest()
	{
		assertEquals("This should return true just over minimum chars", new Boolean(true) , menu.checkFirstNameLength("22") );
		assertEquals("This should return false to many chars ", new Boolean(false) , menu.checkFirstNameLength("This should return false when the string is longer then 40 characters") );
		assertEquals("This should return false to few chars ", new Boolean(false) , menu.checkFirstNameLength("4") );
		
	} 
	  
	
	@Test
	public void checkLastNameLengthTest() {
		assertEquals("This should return true just over minimum chars", new Boolean(true) , menu.checkLastNameLength("22") );
		assertEquals("This should return false to many chars ", new Boolean(false) , menu.checkLastNameLength("This should return false when the string is longer then 40 characters") );
		assertEquals("This should return false to few chars ", new Boolean(false) , menu.checkLastNameLength("4") );
	}
	
	@Test
	public void checkPinTest()
	{
		assertEquals("This should return false to many digits", new Boolean(false), menu.checkPin(23344));
		assertEquals("This should return true enough digits", new Boolean(true), menu.checkPin(2344));
		assertEquals("This should return false not enough digits", new Boolean(false), menu.checkPin(234));
		
	}
	
	@Test
	public void checkUsernameLengthTest()
	{
		assertEquals("This should return true enough characters", new Boolean(true), menu.checkUsernameLength("valid username"));
		assertEquals("This should return false not enough characters", new Boolean(false), menu.checkUsernameLength("23"));
		assertEquals("This should return false to many characters", new Boolean(false), menu.checkUsernameLength("this has way to many characters to be valid"));
	}
	
	@Test 
	public void checkPasswordLengthTest()
	{
		assertEquals("This should return true enough characters", new Boolean(true), menu.checkPasswordLength("valid username"));
		assertEquals("This should return false not enough characters", new Boolean(false), menu.checkPasswordLength("!val"));
		assertEquals("This should return false to many characters", new Boolean(false), menu.checkPasswordLength("this has way to many characters to be valid"));
	}
	
	@Test
	public void checkPhoneNumberHyphenTest()
	{
		assertEquals("This should return true", new Boolean(true),  menu.checkPhoneNumberHyphen("234-234-2345"));
		assertEquals("This should return false", new Boolean(false),  menu.checkPhoneNumberHyphen("234423442345"));
		assertEquals("This should return false", new Boolean(false),  menu.checkPhoneNumberHyphen("234/234/2345"));
		assertEquals("This should return false", new Boolean(false),  menu.checkPhoneNumberHyphen("234+234+2345"));
		assertEquals("This should return false", new Boolean(false),  menu.checkPhoneNumberHyphen("234!234!2345"));
		assertEquals("This should return false", new Boolean(false),  menu.checkPhoneNumberHyphen("234s234f2345"));
		assertEquals("This should return false", new Boolean(false),  menu.checkPhoneNumberHyphen("234!234-2345"));
		assertEquals("This should return false", new Boolean(false),  menu.checkPhoneNumberHyphen("234-234!2345"));
		
	}
	@Test
	public void checkPhoneNumberFormatTest()
	{
		assertEquals("This should return true", new Boolean(true),  menu.checkPhoneNumberLength("234-234-2345"));
		assertEquals("This should return false", new Boolean(false),  menu.checkPhoneNumberLength("234423442"));
		assertEquals("This should return false", new Boolean(false),  menu.checkPhoneNumberLength("234-234-234523"));
		assertEquals("This should return true", new Boolean(true),  menu.checkPhoneNumberLength("shouldpass!!"));
	}
	@Test
	public void checkPhoneNumberIsNumericTest()
	{
		assertEquals("This should return true", new Boolean(true),  menu.checkPhoneNumberIsNumeric("234-234-2345"));
		assertEquals("This should return true", new Boolean(true),  menu.checkPhoneNumberIsNumeric("234423442545"));
		assertEquals("This should return false", new Boolean(true),  menu.checkPhoneNumberIsNumeric("234l900k9090"));
		assertEquals("This should return false", new Boolean(false),  menu.checkPhoneNumberIsNumeric("shouldpass!!"));
		assertEquals("This should return false", new Boolean(false),  menu.checkPhoneNumberIsNumeric("2f4-2d4-f345"));
		assertEquals("This should return false", new Boolean(false),  menu.checkPhoneNumberIsNumeric("234-2d4-f345"));
		assertEquals("This should return false", new Boolean(false),  menu.checkPhoneNumberIsNumeric("2f4-244-f345"));
		assertEquals("This should return false", new Boolean(false),  menu.checkPhoneNumberIsNumeric("2f4-2d4-2345"));
		assertEquals("This should return false", new Boolean(false),  menu.checkPhoneNumberIsNumeric("224-224-f345"));
		assertEquals("This should return false", new Boolean(false),  menu.checkPhoneNumberIsNumeric("2f4-224-2345"));
		assertEquals("This should return false", new Boolean(false),  menu.checkPhoneNumberIsNumeric("224-2d4-3345"));
	}
	
	
}

package com.zero.test;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.zero.pojo.CheckingAccount;
import com.zero.pojo.Account;
import com.zero.pojo.SavingsAccount;

public class AccountTest {
	
	private Account chris;
	private Account chris2;
	private Scanner scanner;
	private boolean firstTimeUser = false;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		chris = new CheckingAccount();
		chris2 = new SavingsAccount();
		scanner = new Scanner(System.in); 
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void authenticateTest()
	{
		assertEquals("This should return false ", new Boolean(true) , chris.authenticate("chris","true"));
	}
	/*
	 * @Test public void test() { String test,username,password;
	 * System.out.println("Welcome to my Java Banking Application"); do {
	 * System.out.println("Are you a first time user? y for yes and n for no"); test
	 * = scanner.nextLine(); if (test.equalsIgnoreCase("y")) {
	 * System.out.println("Please enter your username"); username =
	 * scanner.nextLine(); System.out.println("Please enter your password");
	 * password = scanner.nextLine(); chris.createUser(username, password);
	 * 
	 * firstTimeUser = true; } else if (test.equalsIgnoreCase("n")) {
	 * System.out.println("Please enter your username"); username =
	 * scanner.nextLine(); System.out.println("Please enter your password");
	 * password = scanner.nextLine();
	 * 
	 * 
	 * if (chris.authenticate(username, password) == true) {
	 * System.out.println("i did it"); } else if (chris.authenticate(username,
	 * password) == false) { System.out.println("failure"); }
	 * 
	 * firstTimeUser = true; } else { System.out.println("Invalid input"); }
	 * }while(!firstTimeUser);
	 * 
	 * 
	 * 
	 * }
	 */
	
	@Test
	public void retrieveBalanceTest() {
		
		((CheckingAccount) chris).deposit(50.0);
		((SavingsAccount) chris2).deposit(40.0);
		assertEquals("The Balance Should be equal to ", new Double(50.0) , ((CheckingAccount) chris).getBalance(), 0.01);
		assertEquals("The Balance Should be equal to ", new Double(40.0) , ((SavingsAccount) chris2).getBalance(), 0.01);
	}

}

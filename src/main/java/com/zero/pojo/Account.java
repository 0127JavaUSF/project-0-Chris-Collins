package com.zero.pojo;

import com.zero.user.profile;

public class Account implements profile{
	
	private String username = "chris";
	
	private String password = "true";
	
	private CheckingAccount checking;
	
	private SavingsAccount savings;
	

	public CheckingAccount getChecking() {
		return checking;
	}

	public SavingsAccount getSavings() {
		return savings;
	}
	
	
	public boolean authenticate(String username, String password)
	{
		
		if (this.password.equals(password) & this.username.equals(username))
		{
			System.out.println("Hello");
			return true;
		}
		else
			return false;
	}

	@Override
	public void createUser(String user, String password) {
		
		this.username = user;
		this.password = password;
		
	}

	@Override
	public void openCheckingAccount() {
		
		this.checking = new CheckingAccount();
	}

	@Override
	public void closeCheckingAccount() {
		this.checking = null;
		
	}

	@Override
	public void openSavingsAccount() {
		this.savings = new SavingsAccount();
		
	}

	@Override
	public void closeSavingsAccount() {
		this.savings = null;
		
	}
	

	
	

}

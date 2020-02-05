package com.zero.user;


public interface profile {
	
	public boolean authenticate(String user, String password);
	
	public void createUser(String user, String password);
	
	public void openCheckingAccount();
	
	public void closeCheckingAccount();
	
	public void openSavingsAccount();
	
	public void closeSavingsAccount();
	
	//public void login();

}

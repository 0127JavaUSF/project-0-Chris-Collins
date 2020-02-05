package com.zero.pojo;

public interface AccountService {
		
		
		
		public void deposit(double amount);
		
		public void withdraw(double amount);
		
		public void transferTo(double amount, AccountService account);
		
		public double getBalance();
		
		public void closeAccount();

		public void setBalance(double amount);

		

}


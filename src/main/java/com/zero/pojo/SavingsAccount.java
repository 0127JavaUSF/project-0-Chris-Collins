package com.zero.pojo;

public class SavingsAccount extends Account implements AccountService{
	
	private double balance;
	

	public void deposit(double amount) {
		this.balance = this.balance + amount;
		
	}

	public void withdraw(double amount) {
		this.balance = this.balance - amount;
		
	}

	public void transferTo(double amount, AccountService account) {
		
		double result = account.getBalance() + amount;
		account.setBalance(result);
	}

	public double getBalance() {
		return balance;
		
	}

	public void closeAccount() {
		
	}
	public void setBalance(double amount) {
		this.balance = amount;
		
	}


}

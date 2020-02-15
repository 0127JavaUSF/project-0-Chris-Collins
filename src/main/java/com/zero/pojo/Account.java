package com.zero.pojo;

import java.math.BigDecimal;

public class Account{
	public enum Account_Type
	{
		Checking, Savings
	}
	private int id;
	private Account_Type account_type;
	private BigDecimal balance;
	private float interest;
	



	public Account(int id, Account_Type account_type, BigDecimal balance, float interest) {
		super();
		this.id = id;
		this.account_type = account_type;
		this.balance = balance;
		this.interest = interest;
	}

	public String getAccount_Type()
	{
		String account_type = this.account_type.toString();
		return account_type;
	}

	public void setAccount_type(Account_Type account_type) {
		this.account_type = account_type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public BigDecimal getBalance() {
		return balance;
	}


	public void setBalance(BigDecimal amount) {
		this.balance = amount;
		
	}

	public float getInterest() {
		return interest;
	}

	public void setInterest(float interest) {
		this.interest = interest;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account_type == null) ? 0 : account_type.hashCode());
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + id;
		result = prime * result + Float.floatToIntBits(interest);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (account_type != other.account_type)
			return false;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (id != other.id)
			return false;
		if (Float.floatToIntBits(interest) != Float.floatToIntBits(other.interest))
			return false;
		return true;
	}

	public Account() {
		super();
	}

	
}

package com.zero.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.zero.pojo.Account;
import com.zero.pojo.Account.Account_Type;
import com.zero.pojo.User;
import com.zero.connection.ConnectionUtil;

public class AccountDao {
	
	private Account extractAccount(ResultSet result) throws SQLException {
		int id = result.getInt("id");
		BigDecimal balance = result.getBigDecimal("balance");
		float interest = result.getFloat("interest");
		Account_Type accountType = Account_Type.valueOf(result.getString("account_type"));
		return new Account(id, accountType, balance, interest);
	}
	
	public Account getAccount(int id) {
		try(Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT id, balance, interest, account_type FROM account " +
					"WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, id);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				id = result.getInt("id");
				BigDecimal balance = result.getBigDecimal("balance");
				float interest = result.getFloat("interest");
				Account_Type accountType = Account_Type.valueOf(result.getString("account_type"));
				return new Account(id, accountType, balance, interest);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	public BigDecimal getBalance(int id)
	{
		try(Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT balance FROM account " +
					"WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, id);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				BigDecimal balance = result.getBigDecimal("balance");
				return balance;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public float getInterest(int id)
	{
		try(Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT interest FROM account " +
					"WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, id);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				float interest = result.getFloat("interest");
				return interest;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return 0.0f;
	}
	
	public Account createAccount(User user, String accountType) {
		Account newAccount = new Account();
		try(Connection connection = ConnectionUtil.getConnection()) {
			connection.setAutoCommit(false);
			String sql = "INSERT INTO account (balance, interest, account_type) " +
					" VALUES (?, ?, ?) RETURNING *";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setBigDecimal(1, BigDecimal.valueOf(0.0));
			statement.setFloat(2, 0.02f);
			if(accountType == "c")
			{
				statement.setObject(3, "Checking", java.sql.Types.OTHER);
			}
			else if (accountType == "s")
			{
				statement.setObject(3, "Savings", java.sql.Types.OTHER);
			}
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				newAccount = extractAccount(result);
				String sql2 = "INSERT INTO users_account (user_id, account_id, account_owner) " +
						" VALUES (?, ?, ?) RETURNING *";
				PreparedStatement statement2 = connection.prepareStatement(sql2);
				
				statement2.setInt(1, user.getId());
				statement2.setInt(2, result.getInt("id"));
				statement2.setBoolean(3, true);
				
				statement2.executeQuery();
				System.out.println("Account has been created");
				connection.commit();
				return newAccount;
			}
			connection.rollback();
			return null;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void deposit(User user, int id, BigDecimal amount)
	{
		List<Integer> userAccounts = new ArrayList<>();
		boolean accountAccess = false;
		try(Connection connection = ConnectionUtil.getConnection()) {
			connection.setAutoCommit(false);
			String sql = "SELECT account_id FROM users_account " +
					"WHERE user_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, user.getId());
			ResultSet result = statement.executeQuery();
			while (result.next())
			{
				userAccounts.add(result.getInt("account_id"));
			}
			ListIterator<Integer> iter = userAccounts.listIterator();
			while(iter.hasNext())
			{
				if (iter.next().intValue() == id)
				{
					accountAccess = true;
				}
			}
			if(accountAccess)
			{
				String sql2 = "UPDATE account " + 
						"set balance = balance + ? " + 
						"where id = ?;";
				PreparedStatement statement2 = connection.prepareStatement(sql2);
				
				statement2.setBigDecimal(1, amount);
				statement2.setInt(2, id);
				
				statement2.executeUpdate();
				System.out.println("$" + amount + " has been deposited into account number: " + id);
				connection.commit();
			}
			else
			{
				System.out.println("You do not have access to this account");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void withdraw(User user, int id, BigDecimal amount)
	{
		List<Integer> userAccounts = new ArrayList<>();
		boolean accountAccess = false;
		try(Connection connection = ConnectionUtil.getConnection()) {
			connection.setAutoCommit(false);
			String sql = "SELECT account_id FROM users_account " +
					"WHERE user_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, user.getId());
			
			ResultSet result = statement.executeQuery();
			while (result.next())
			{
				userAccounts.add(result.getInt("account_id"));
			}
			ListIterator<Integer> iter = userAccounts.listIterator();
			while(iter.hasNext())
			{
				if (iter.next().intValue() == id)
				{
					accountAccess = true;
				}
			}
			if(accountAccess)
			{
				if((getBalance(id).floatValue() - amount.floatValue()) >= 0.0f)
				{
				String sql2 = "UPDATE account " + 
						"set balance = balance - ? " + 
						"where id = ?;";
				PreparedStatement statement2 = connection.prepareStatement(sql2);
				
				statement2.setBigDecimal(1, amount);
				statement2.setInt(2, id);
				
				statement2.executeUpdate();
				System.out.println("$"+ amount + " has been withdrawn fromn account number: " + id);
				connection.commit();
				}
				else {
					System.out.println("You do not have enough funds in your account to make this withdraw.");
				}
			}
			else
			{
				System.out.println("you do not have access to this account");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void transferTo(User user, int id, int destinationAccount,BigDecimal amount)
	{
		List<Integer> userAccounts = new ArrayList<>();
		boolean accountAccess = false;
		boolean ownAccount = false;
		try(Connection connection = ConnectionUtil.getConnection()) {
			connection.setAutoCommit(false);
			String sql = "SELECT account_id FROM users_account " +
					"WHERE user_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, user.getId());
			
			ResultSet result = statement.executeQuery();
			while (result.next())
			{
				userAccounts.add(result.getInt("account_id"));
			}
			ListIterator<Integer> iter = userAccounts.listIterator();
			while(iter.hasNext())
			{
				int next = iter.next();
				if (next == id)
				{
					accountAccess = true;
				}
				if(next == destinationAccount)
				{
					ownAccount = true;
				}
			}
			if(accountAccess && ownAccount)
			{
				if((getBalance(id).floatValue() - amount.floatValue()) >= 0.0f)
				{
				String sql2 = "UPDATE account " + 
						"set balance = balance - ? " + 
						"where id = ?;";
				PreparedStatement statement2 = connection.prepareStatement(sql2);
				
				statement2.setBigDecimal(1, amount);
				statement2.setInt(2, id);
				
				statement2.executeUpdate();
				
				String sql3 = "UPDATE account " + 
						"set balance = balance + ? " + 
						"where id = ?;";
				PreparedStatement statement3 = connection.prepareStatement(sql3);
				
				statement3.setBigDecimal(1, amount);
				statement3.setInt(2, destinationAccount);
				
				statement3.executeUpdate();
				System.out.println("$" + amount + " has been transfered to account number: " + destinationAccount + " from account number "
						+ id);
				connection.commit();
				
				}
				else {
					System.out.println("You do not have enough funds in your account to make this transfer.");
				}

			}
			else
			{
				System.out.println("you do not have access to this account");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void transferFrom(User user, int id, int givingAccount,BigDecimal amount)
	{
		List<Integer> userAccounts = new ArrayList<>();
		boolean accountAccess = false;
		boolean ownAccount = false;
		try(Connection connection = ConnectionUtil.getConnection()) {
			connection.setAutoCommit(false);
			String sql = "SELECT account_id FROM users_account " +
					"WHERE user_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, user.getId());
			
			ResultSet result = statement.executeQuery();
			while (result.next())
			{
				userAccounts.add(result.getInt("account_id"));
			}
			ListIterator<Integer> iter = userAccounts.listIterator();
			while(iter.hasNext())
			{
				int next = iter.next().intValue();
				if (next == givingAccount)
				{
					accountAccess = true;
				}
				if (next == id)
				{
					ownAccount = true;
				}
			}
			if(accountAccess && ownAccount)
			{
				if((getBalance(givingAccount).floatValue() - amount.floatValue()) >= 0.0f)
				{
				String sql2 = "UPDATE account " + 
						"set balance = balance - ? " + 
						"where id = ?;";
				PreparedStatement statement2 = connection.prepareStatement(sql2);
				
				statement2.setBigDecimal(1, amount);
				statement2.setInt(2, givingAccount);
				
				statement2.executeUpdate();
				
				String sql3 = "UPDATE account " + 
						"set balance = balance + ? " + 
						"where id = ?;";
				PreparedStatement statement3 = connection.prepareStatement(sql3);
				
				statement3.setBigDecimal(1, amount);
				statement3.setInt(2, id);
				
				statement3.executeUpdate();
				System.out.println("$" + amount + " has been transfered to account number: " + id + " from account number "
						+ givingAccount);
				connection.commit();
				
				}
				else {
					System.out.println("You do not have enough funds in your account to make this transfer.");
				}

			}
			else
			{
				System.out.println("you do not have access to this account");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void closeAccount(int accountId)
	{
		int relationshipId;
		try(Connection connection = ConnectionUtil.getConnection()) {
			boolean deletable = true;
			connection.setAutoCommit(false);
			String sql = "DELETE FROM account " +
					" WHERE id = ? ";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, accountId);
			
			if(getBalance(accountId).floatValue() > 0.0f)
			{
				System.out.println("Please empty your account of funds before you attempt to remove the account");
				deletable = false;
			}
			int result = 0;
			if(deletable == true)
			{
			result = statement.executeUpdate();
			}
			if(result == 1) {
				String sql2 = "SELECT id FROM users_account " +
				"WHERE account_id = ?";
				PreparedStatement statement2 = connection.prepareStatement(sql2);
				
				statement2.setInt(1, accountId);
				ResultSet result2 = statement2.executeQuery();
				
				while(result2.next())
				{
					relationshipId = result2.getInt("id");
					String sql3 = "DELETE FROM users_account " + 
					"WHERE id = ?";
					PreparedStatement statement3 = connection.prepareStatement(sql3);
					statement3.setInt(1, relationshipId);
					result = statement3.executeUpdate();
							
				}
				connection.commit();
				System.out.println("Account: " + accountId + " has now been closed.");
			}
			else
			{
				connection.rollback();
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}

package com.zero.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zero.connection.ConnectionUtil;
import com.zero.pojo.Account;
import com.zero.pojo.Account.Account_Type;
import com.zero.pojo.User;

public class UsersAccountsDao {
	
	private Account extractAccount(ResultSet result) throws SQLException {
		int id = result.getInt("id");
		BigDecimal balance = result.getBigDecimal("balance");
		float interest = result.getFloat("interest");
		Account_Type accountType = Account_Type.valueOf(result.getString("account_type"));
		return new Account(id, accountType, balance, interest);
	}
	
	public List<Account> getUserAccounts(int id) {
		List<Account> userAccounts = new ArrayList<>();
		try(Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT users_account.user_id, account.* FROM users_account " +
					"LEFT JOIN account ON users_account.account_id = account.id " + 
					"WHERE user_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, id);
			
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				userAccounts.add(extractAccount(result));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return userAccounts;		
	}
	
	public ArrayList<Integer> getAccountId(int id) {
		ArrayList<Integer> userAccounts = new ArrayList<>();
		try(Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT account_id FROM users_account " +
					"WHERE user_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, id);
			
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				int account_id = result.getInt("account_id");
				userAccounts.add(account_id);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return userAccounts;		
	}
	
	public void addJointUser(int newUserId, int id)
	{
		try(Connection connection = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO users_account (user_id, account_id) " +
					"values (?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, newUserId);
			statement.setInt(2, id);
			
			int result = statement.executeUpdate();
			
			if(result == 1) {
				System.out.println("User id: " + newUserId + " has been added as a joint user for this account");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void removeJointUser(int id, int userToRemove)
	{
		try(Connection  connection = ConnectionUtil.getConnection())
		{
			connection.setAutoCommit(false);
			String sql = "SELECT id FROM users_account " +
					"WHERE user_id = ? AND account_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, userToRemove);
			statement.setInt(2, id);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next())
			{
				int jointId = result.getInt("id");
				String sql2 = "DELETE FROM users_account " +
						"WHERE id = ?";
				PreparedStatement statement2 = connection.prepareStatement(sql2);
				
				statement2.setInt(1, jointId);
				
				int result2 = statement2.executeUpdate();
				if(result2 == 1)
				{
					System.out.println("The user id: " + userToRemove + " has been removed as a joint user");
					connection.commit();
				}
				else
				{
					connection.rollback();
				}
			}
			else
			{
				connection.rollback();
			}
		}catch(SQLException e) {
		e.printStackTrace();
	}
}
	

	public boolean isOwner(User user, int id)
	{
		try(Connection  connection = ConnectionUtil.getConnection())
		{
			String sql = "SELECT id FROM users_account " +
					"WHERE user_id = ? AND account_id = ? AND account_owner = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, user.getId());
			statement.setInt(2, id);
			statement.setBoolean(3, true);
			
			
			ResultSet result = statement.executeQuery();
			
			if(result.next())
			{
				return true;
			}
			else
			{
				return false;
			}
		}catch(SQLException e) {
		e.printStackTrace();
	}
		return false;
	}
}

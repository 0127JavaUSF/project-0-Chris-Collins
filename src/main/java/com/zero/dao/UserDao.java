package com.zero.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zero.connection.ConnectionUtil;
import com.zero.pojo.User;

public class UserDao {
	
	private User extractUser(ResultSet result) throws SQLException {
		int id = result.getInt("id");
		String username = result.getString("username");
		String password = result.getString("user_pass");
		String firstname = result.getString("first_name");
		String lastname = result.getString("last_name");
		int	pin = result.getInt("pin");
		String phoneNumber = result.getString("phonenumber");
		return new User(id, username, password, firstname, lastname, pin, phoneNumber);
	}
	
	public User getUser(int id) {
		try(Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT id, username, user_pass, first_name, last_name, pin, phonenumber FROM users " +
					"WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, id);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				id = result.getInt("id");
				String username = result.getString("username");
				String password = result.getString("user_pass");
				String firstname = result.getString("first_name");
				String lastname = result.getString("last_name");
				int	pin = result.getInt("pin");
				String phoneNumber = result.getString("phonenumber");
				return new User(id, username, password, firstname, lastname, pin, phoneNumber);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;		
	}
	public User getUser(String username) {
		try(Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT id, username, user_pass, first_name, last_name, pin, phonenumber FROM users " +
					"WHERE username = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, username);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				int id = result.getInt("id");
				username = result.getString("username");
				String password = result.getString("user_pass");
				String firstname = result.getString("first_name");
				String lastname = result.getString("last_name");
				int	pin = result.getInt("pin");
				String phoneNumber = result.getString("phonenumber");
				return new User(id, username, password, firstname, lastname, pin, phoneNumber);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	public User createUser(User user) {
		try(Connection connection = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO users (username, user_pass, first_name, last_name, pin, phonenumber) " +
					" VALUES (?, ?, ?, ?, ?, ?) RETURNING *";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getLastName());
			statement.setInt(5, user.getPin());
			statement.setString(6, user.getPhoneNumber());
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				return extractUser(result);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public boolean checkUsernameAvailability(String username)
	{
		try(Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT username FROM users " +
					"WHERE username = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, username);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				return true;
			}
			else return false;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean authenticate(String username, String password)
	{
		
		try(Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT username, user_pass FROM users " +
					"WHERE username = ? AND user_pass = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, username);
			statement.setString(2, password);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				String returned_username = result.getString("username");
				String returned_password = result.getString("user_pass");
				if(username.equals(returned_username) & password.equals(returned_password))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else return false;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public void changePassword(User user, String password)
	{
		try(Connection connection = ConnectionUtil.getConnection()) {
			String sql = "UPDATE users " +
					" SET user_pass = ? WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, password);
			statement.setInt(2, user.getId());
			
			statement.executeUpdate();
			
				System.out.println("Password updated");
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void changePhoneNumber(User user, String phoneNumber)
	{
		try(Connection connection = ConnectionUtil.getConnection()) {
			String sql = "UPDATE users " +
					" SET phonenumber = ? WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, phoneNumber);
			statement.setInt(2, user.getId());
			
			statement.executeUpdate();
			
				System.out.println("Phone Number updated");
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

}

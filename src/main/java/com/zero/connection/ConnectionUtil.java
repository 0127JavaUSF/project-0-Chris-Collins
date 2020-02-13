package com.zero.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
		public static Connection getConnection() {
			String url = System.getenv("JDBC_URL");
			String user = System.getenv("JDBC_ROLE");
			String password = System.getenv("JDBC_PASSWORD");
			try {
				return DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

package com.cit.project.bms.in;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
	static
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection()
	{
		Connection conn = null;
		
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_management_systemdb", "root", "Sagarkeerthi620");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

}

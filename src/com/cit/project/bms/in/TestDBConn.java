package com.cit.project.bms.in;

import java.sql.Connection;

public class TestDBConn {
	
		public static void main(String[] args) {
		
		Connection conn = DBConnectionManager.getConnection();
		
		if(conn != null)
		{
			System.out.println("Connection successful");
		}

	}


}

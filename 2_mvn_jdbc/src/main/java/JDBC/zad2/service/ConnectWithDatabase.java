package JDBC.zad2.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectWithDatabase {
	
	private Connection connection;
	private String url = "jdbc:hsqldb:hsql://localhost/workdb";
	
	public ConnectWithDatabase () {
	
		try {
			this.connection =  DriverManager.getConnection(url);
			} catch(SQLException exc) {
				exc.getMessage();
			}
	}
	
	public Connection getConnection () {
		return this.connection;
	}
	

}

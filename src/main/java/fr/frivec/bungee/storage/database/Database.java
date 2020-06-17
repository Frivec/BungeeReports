package fr.frivec.bungee.storage.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import fr.frivec.bungee.BungeeReports;
import fr.frivec.bungee.storage.Credentials;
import fr.frivec.core.logger.Logger.LogLevel;

public class Database {
	
	private Credentials credentials;
	private Connection connection;
	
	public Database(final Credentials credentials) {
		
		this.credentials = credentials;
		
	}
	
	public void connect() {
		
		try {
			
			if(this.connection != null || !this.connection.isClosed())
				
				return;
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			this.connection = DriverManager.getConnection(this.credentials.toMySqlURL());
		
		} catch (ClassNotFoundException e) {
			
			BungeeReports.log(LogLevel.SEVERE, "Class not found: oracle.jdbc.driver.OracleDriver");
			BungeeReports.log(LogLevel.SEVERE, "Cannot connect to the database");
			
		} catch (SQLException e) {
			
			BungeeReports.log(LogLevel.SEVERE, "Cannot connect to the database. Error due to credentials. Please see below.");
			e.printStackTrace();
			
		}
		
	}
	
	public void close() {
			
		try {
				
			if(this.connection != null && !this.connection.isClosed())
				
				this.connection.close();
				
		} catch (SQLException e) {
			
			BungeeReports.log(LogLevel.SEVERE, "Error. The connection to the database is already closed or is null.");
			
			e.printStackTrace();
		}
		
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public Credentials getCredentials() {
		return credentials;
	}

}

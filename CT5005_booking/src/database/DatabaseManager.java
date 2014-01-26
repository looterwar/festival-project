package database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class DatabaseManager {

	private static String url;
	private static String username;
	private static String password;
	private static Connection conn;
	
	/**
	 * Creates a new DatabaseManager object
	 * @param dataProperties String path to properties file which contains the database credentials
	 */
	public DatabaseManager(String dataProperties) {
		
		try {
			
			System.out.println("Loading Database Settings...");
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(dataProperties);
			prop.load(in);
			
			String driver = prop.getProperty("jdbc.driver");
			url = prop.getProperty("jdbc.url");
			username = prop.getProperty("jdbc.username");
			
			if (username.isEmpty()) {
				throw new Exception("Database username empty!");
			}
			
			password = prop.getProperty("jdbc.password");
			
			if (password.isEmpty()) {
				throw new Exception("Database password empty!");
			}
			
			if (driver.isEmpty()) {
				throw new Exception("Database driver not found!");
			} else {
				Class.forName(driver);
			}
			
		} catch (Exception ex) {
			
		}
	}
	
	/**
	 * Create a new connection to the database
	 * @throws SQLException
	 */
	public static void createConnection() throws SQLException {
		
		conn = DriverManager.getConnection(url, username, password);
		
	}
	
	/**
	 * Returns the connection object
	 * @return Connection object
	 */
	public static Connection getConnection() {
		return conn;
	}
	
	public static void print_results(ResultSet result) throws SQLException {
		
		ResultSetMetaData meta = result.getMetaData();
		
		System.out.println("-- Results --");
		final String FORMAT = "%-20s";
		
		for (int i = 1; i <= meta.getColumnCount(); i++) {
			
			System.out.format(FORMAT, meta.getColumnName(i));
		
		}
		
		while (result.next()) {
			
			System.out.println();
			
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				System.out.format(FORMAT, result.getString(i));
			}
			
		}
		
		System.out.println();
		
	}
	
}

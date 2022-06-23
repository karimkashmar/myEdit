package myEdit_Server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import models.User;
import myEdit_Database.DatabaseConnection;

public class Server {

	/*
	 * Static Properties, could be accessed from anywhere in this project
	 */

	// Database connection property
	public static DatabaseConnection dbConnection;
	public static Connection connection;
	
	// Custom object, which acts as a regular Socket, but more customizable since we need it as multi-threading, and should be accessed by separate threads (so should be static)
	public static ServerSocketConnection mainServer = null;

	public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException, InterruptedException {
		// Establish Connection to Database
		try {
			// 
			Class.forName("org.sqlite.JDBC");
			
			// 
			connection = DriverManager.getConnection("jdbc:sqlite:MyEditDB.db");
			dbConnection = new DatabaseConnection(connection);
			System.out.println("Connected to database!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Initiate the ServerSocketConnection constructor (Refer to class for more details)
		mainServer = new ServerSocketConnection();

		// Start accepting all connections to the port
		mainServer.acceptConnections();
	}
}

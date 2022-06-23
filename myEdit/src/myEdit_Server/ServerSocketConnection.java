package myEdit_Server;

import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketConnection {

	/*
	 * Private Properties
	 */

	// Define the port number that is used by the server 
	private static final int PORT = 12131;
	
	// Define the socket that the server will be using to listen to clients
	private ServerSocket serverSocket;
	
	public ServerSocketConnection() throws ClassNotFoundException {
	}
	
	// Function that starts listening to messages received
	public void acceptConnections() throws IOException {
		// Initiate the socket using the port number
		serverSocket = new ServerSocket(PORT);
		
		System.out.println("Server is online. Port #" + PORT + " is connected. Listening to messages...\n");
		
		// Keep listening to new messages, and when you receive one, send it to be processed in a separate thread
		while (true) {
			Socket newConnection = serverSocket.accept();
			
			// Custom object defined in the package (Refer to class for details)
			ServerMultiThread st = new ServerMultiThread(newConnection);
			
			new Thread(st).start();
		}
	}
}


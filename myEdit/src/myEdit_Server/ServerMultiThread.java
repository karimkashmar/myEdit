package myEdit_Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import models.Message;
import models.File;
import models.User;
import myEdit_Database.DatabaseConnection;

public class ServerMultiThread implements Runnable {

	// Define the socket that will be used to send and receive streams, which will be passed by the main class to be used in the thread
	private Socket socket;
	private DatabaseConnection dbConnection;

	// Constructor to assign the passed property and use it
	public ServerMultiThread(Socket socket) {
		this.socket = socket;
		dbConnection = Server.dbConnection;
	}

	public void run() {
		try {
			// Define and initiate the input stream to the socket
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

			// The data received in the stream can be casted into an object, since we know the kind of message sent (only one kind for now)
			Message cMessage = (Message) ois.readObject();

			switch(cMessage.MessageType) {
			case 1: // refers to "Registering a new user"
				System.out.println("Message 1 received, adding new user...");
				User newUser = (User) cMessage.Message;

				boolean addUserResponse = dbConnection.insertUser(newUser);

				if (addUserResponse) {
					System.out.println("User added successfully, sending response back to " + newUser.Username);
					oos.writeObject(new Message(2, "success"));
				}
				else {
					System.out.println("User was not added, sending response back");
					oos.writeObject(new Message(2, "failure"));
				}
				break;
			case 3: // Validate user
				System.out.println("Message 3 received, trying to login...");
				User currentUser = (User) cMessage.Message;

				User correctUser = dbConnection.getUser(currentUser.Username);
				String correctUserPassword;
				if (correctUser == null) {
					correctUserPassword = "";
				} else {
					correctUserPassword = correctUser.Password;
				}

				if (currentUser.Password.equals(correctUserPassword)) {
					System.out.println("User login successful!");
					oos.writeObject(new Message(4, correctUser));
				} else {
					System.out.println("User login failed!");
					oos.writeObject(new Message(4, new User("", "", "", "", "", false)));
				}

				break;
			case 5: // Get all files
				System.out.println("Message 5 received, getting files...");

				ArrayList<File> FilesToReturn = dbConnection.getFiles();

				oos.writeObject(new Message(6, FilesToReturn));

				break;
			case 7: // Create new file
				System.out.println("Message 7 received, creating new file...");

				SimpleEntry<String, File> FileToAdd = (SimpleEntry<String, File>) cMessage.Message;

				File newFile = FileToAdd.getValue();

				if (dbConnection.insertFile(newFile)) {
					oos.writeObject(new Message(8, "success"));
				} else {
					oos.writeObject(new Message(8, "failure"));
				}

				break;
			case 9: // Update file content
				System.out.println("Message 9 received, updating file...");

				File FileToUpdate = (File) cMessage.Message;

				if (dbConnection.updateFile(FileToUpdate)) {
					oos.writeObject(new Message(10, "success"));
				} else {
					oos.writeObject(new Message(10, "failure"));
				}

				break;
			case 11: // Delete file (or mark it for deletion)
				System.out.println("Message 11 received, deleting file...");

				String FileNameToDelete = (String) cMessage.Message;

				if (dbConnection.removeFile(FileNameToDelete)) {
					oos.writeObject(new Message(12, "success"));
				} else {
					oos.writeObject(new Message(12, "failure"));
				}

				break;
			default:
				System.out.println("Wrong message");
				break;
			}

			// Dispose the input/output stream once done
			ois.close();
			oos.close();

		}  catch (SocketException e) {
			System.out.println("User disconnected!");
		} catch (IOException | ClassNotFoundException e2) {
			e2.printStackTrace();
		}
		return;
	}
}

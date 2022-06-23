package models;

import java.io.Serializable;
import java.util.AbstractMap.SimpleEntry;

/*
 *  Message object being sent from the client to the server. 
 *  It has to implement Serializable if it is sent in the stream, 
 *  because the object gets serialized into a stream, 
 *  and then recovered on the server
 */

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/*
	 * 1 = Registering a new user
	 * 2 = Registration response
	 * 3 = Validate User
	 * 4 = Validate User response
	 * 5 = Get All Files
	 * 6 = All Files Response
	 * 7 = Create New File
	 * 8 = Create New File Response
	 * 9 = Update file content
	 * 10 = Update file response
	 * 11 = Delete file
	 * 12 = Delete file response
	 */
	public int MessageType;
	
	/*
	 * 1 -> User
	 * 2 -> String (success or failure)
	 * 3 -> User with only username/password
	 * 4 -> User
	 * 5 -> null
	 * 6 -> ArrayList<File>
	 * 7 -> SimpleEntry<String, File>
	 * 8 -> String (success or failure)
	 * 9 -> File
	 * 10 -> String (success or failure)
	 * 11 -> String (filename)
	 * 12 -> String (success or failure)
	 */
	public Object Message;

	// All properties needed in this specific message type needs to be defined here
	public Message(int messageType, Object message) {
		MessageType = messageType;
		Message = message;
	}

}

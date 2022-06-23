package myEdit_Client;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import models.Message;
import models.File;
import models.User;

import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;

public class Client {

	// Client interface for users
	
	/*
	 * Private Properties
	 */

	// Server is running locally on the PC, therefore localhost is used (127.0.0.1 would work too). If server is hosted on another system, use the correct IP address
	private static final String HOST = "localhost";

	private static Socket socket;

	private static ObjectOutputStream oos;
	private static ObjectInputStream ois;

	// Port number could be any unused port, but needs to match the port that the server is listening to
	private static final int PORT = 12131;

	// ID of the user using the application.
	private static User loggedInUser;

	private static SimpleDateFormat formatter;

	// UI Elements
	private static JPanel loginPanel, registerPanel;
	private static JFrame identificationFrame;
	private static JLabel userLbl, loginPasswordLbl, fNameLbl, lNameLbl, emailAddressLbl, usernameLbl, 
	registerPasswordLbl, adminLbl;
	private static JTextArea loginStatusMsg, registerStatusMsg;
	private static JTextField userTxt, fNameTxt, lNameTxt, emailAddressTxt, usernameTxt;
	private static JPasswordField loginPasswordTxt, registerPasswordTxt;
	private static JButton loginBtn, newUserBtn, backToLoginBtn, registerBtn;
	private static JCheckBox isAdminCheckBox;

	/*
	 * Main logic that will run on the client side
	 */
	public static void main(String[] args) {

		try {
			System.out.println("Welcome to My Edit!");

			// Initiate the socket that will interact with the server, using the host name/ip and the port number
			socket = new Socket(HOST, PORT);

			// Initiate the stream (bus) of data that is going out of the client
			oos = null;
			ois = null;

			loggedInUser = null;
			//LoggedInUser will store the currently signed in user to allow easy extraction of data
			
			formatter= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Date date = new Date(System.currentTimeMillis());
			System.out.println(formatter.format(date));

			InitializeUIElements();
		} catch (Exception e) { 
			System.out.println("Error happened!");
		}
	}

	private static void InitializeUIElements() {

		/*
		 * Login Panel		
		 */
		loginPanel = new JPanel();
		identificationFrame = new JFrame("Identification");
		identificationFrame.setSize(350,350);
		identificationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		identificationFrame.add(loginPanel);

		loginPanel.setLayout(null);

		userLbl = new JLabel("User");
		userLbl.setBounds(10,20,80,25);
		loginPanel.add(userLbl);

		userTxt = new JTextField(20);
		userTxt.setBounds(100,20,165,25);
		loginPanel.add(userTxt);

		loginPasswordLbl = new JLabel("Password");
		loginPasswordLbl.setBounds(10,50,80,25);
		loginPanel.add(loginPasswordLbl);

		loginPasswordTxt = new JPasswordField(20);
		loginPasswordTxt.setBounds(100,50,165,25);
		loginPanel.add(loginPasswordTxt);

		loginBtn = new JButton("Login");
		loginBtn.setBounds(10,80,100,25);
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginBtnPressed();
			}
		});
		loginPanel.add(loginBtn);

		newUserBtn = new JButton("New User");
		newUserBtn.setBounds(120,80,100,25);
		newUserBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewUserBtnPressed();
			}
		});
		loginPanel.add(newUserBtn);

		loginStatusMsg = new JTextArea();
		loginStatusMsg.setBounds(10,110, 200, 25);
		loginStatusMsg.setDisabledTextColor(Color.red);
		loginStatusMsg.setEnabled(false);
		loginStatusMsg.setBackground(new Color(238,238,238));
		loginPanel.add(loginStatusMsg);


		/*
		 * Register Panel		
		 */

		registerPanel = new JPanel();

		registerPanel.setVisible(false);
		registerPanel.setLayout(null);

		fNameLbl = new JLabel("First Name");
		fNameLbl.setBounds(10,20,100,25);
		registerPanel.add(fNameLbl);

		fNameTxt = new JTextField(20);
		fNameTxt.setBounds(120,20,165,25);
		registerPanel.add(fNameTxt);

		lNameLbl = new JLabel("Last Name");
		lNameLbl.setBounds(10,50,100,25);
		registerPanel.add(lNameLbl);

		lNameTxt = new JTextField(20);
		lNameTxt.setBounds(120,50,165,25);
		registerPanel.add(lNameTxt);

		emailAddressLbl = new JLabel("Email Address");
		emailAddressLbl.setBounds(10,80,100,25);
		registerPanel.add(emailAddressLbl);

		emailAddressTxt = new JTextField(20);
		emailAddressTxt.setBounds(120,80,165,25);
		registerPanel.add(emailAddressTxt);

		usernameLbl = new JLabel("Username");
		usernameLbl.setBounds(10,110,100,25);
		registerPanel.add(usernameLbl);

		usernameTxt = new JTextField(20);
		usernameTxt.setBounds(120,110,165,25);
		registerPanel.add(usernameTxt);

		registerPasswordLbl = new JLabel("Password");
		registerPasswordLbl.setBounds(10,140,100,25);
		registerPanel.add(registerPasswordLbl);

		registerPasswordTxt = new JPasswordField(20);
		registerPasswordTxt.setBounds(120,140,165,25);
		registerPanel.add(registerPasswordTxt);

		adminLbl = new JLabel("Is Admin?");
		adminLbl.setBounds(10,170,100,25);
		registerPanel.add(adminLbl);

		isAdminCheckBox = new JCheckBox();
		isAdminCheckBox.setBounds(120,170,25,25);
		registerPanel.add(isAdminCheckBox);

		backToLoginBtn = new JButton("Back to Login");
		backToLoginBtn.setBounds(10,200,120,25);
		backToLoginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BackToLoginBtnPressed();
			}
		});
		registerPanel.add(backToLoginBtn);

		registerBtn = new JButton("Register");
		registerBtn.setBounds(140,200,120,25);
		registerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterBtnPressed();
			}
		});
		registerPanel.add(registerBtn);

		registerStatusMsg = new JTextArea();
		registerStatusMsg.setBounds(10,230, 300, 40);
		registerStatusMsg.setEnabled(false);
		registerStatusMsg.setBackground(new Color(238,238,238));
		registerStatusMsg.setDisabledTextColor(Color.red);
		registerPanel.add(registerStatusMsg);

		identificationFrame.setLocationRelativeTo(null);
		identificationFrame.setVisible(true);
	}

	private static void LoginBtnPressed() {
		System.out.println("Trying to login...");
		loginStatusMsg.setText("");

		User currentUser = new User(null, null, null, userTxt.getText(), new String(loginPasswordTxt.getPassword()), false);

		if (currentUser.Username == null) {
			loginStatusMsg.setText("Username is required");
		} else if (currentUser.Username.length() > 12 || currentUser.Username.length() < 4 ) {
			loginStatusMsg.setText("Username should be between 4 and 12 characters");
		} else if (currentUser.Password == null) {
			loginStatusMsg.setText("Password is required");
		} else if (currentUser.Password.length() > 12 || currentUser.Password.length() < 4 ) {
			loginStatusMsg.setText("Password should be between 4 and 12 characters");
		} else {
			try {
				Message receivedMsg = SendMessage(3, currentUser);

				if (receivedMsg.MessageType == 4) {
					User responseUser = (User) receivedMsg.Message;
					System.out.println("Received response back, username is: " + responseUser.Username);

					if (responseUser.Username.length() != 0) {
						ShowToast("Login successful. Hello " + responseUser.FirstName, 500, 2);
						loggedInUser = responseUser;
						identificationFrame.setVisible(false);
						InitializeDashboardElements();
					} else {
						ShowToast("Login failed.", 500, 3);
						loginStatusMsg.setText("Failed to login");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private static void NewUserBtnPressed() {
		System.out.println("New User pressed!");
		identificationFrame.add(registerPanel);
		loginPanel.setVisible(false);
		registerPanel.setVisible(true);
		identificationFrame.remove(loginPanel);
	}
	private static void BackToLoginBtnPressed() {
		System.out.println("Back to login pressed!");
		identificationFrame.add(loginPanel);
		registerPanel.setVisible(false);
		loginPanel.setVisible(true);
		identificationFrame.remove(registerPanel);
	}
	private static void RegisterBtnPressed() {
		System.out.println("Register pressed!");
		registerStatusMsg.setText("");

		String passwordContent = new String(registerPasswordTxt.getPassword());

		User currentUser = new User(fNameTxt.getText(), lNameTxt.getText(), emailAddressTxt.getText(), usernameTxt.getText(), passwordContent, isAdminCheckBox.isSelected());

		if (currentUser.FirstName == null) {
			registerStatusMsg.setText("First name is required");
		} else if (currentUser.FirstName.length() > 20 || currentUser.FirstName.length() < 4 ) {
			registerStatusMsg.setText("First name should be between 4 and 20 characters");
		} else if (currentUser.LastName == null) {
			registerStatusMsg.setText("Last name is required");
		} else if (currentUser.LastName.length() > 20 || currentUser.LastName.length() < 1 ) {
			registerStatusMsg.setText("Last name should be between 1 and 20 characters");
		} else if (currentUser.EmailAddress == null) {
			registerStatusMsg.setText("Email is required");
		} else if (currentUser.EmailAddress.length() > 25 || currentUser.EmailAddress.length() < 8 ) {
			registerStatusMsg.setText("Email should be between 8 and 25 characters");
		} else if (currentUser.Username == null) {
			registerStatusMsg.setText("Username is required");
		} else if (currentUser.Username.length() > 12 || currentUser.Username.length() < 4 ) {
			registerStatusMsg.setText("Username should be between 4 and 12 characters");
		} else if (currentUser.Password == null) {
			registerStatusMsg.setText("Password is required");
		} else if (currentUser.Password.length() > 12 || currentUser.Password.length() < 4 ) {
			registerStatusMsg.setText("Password should be between 4 and 12 characters");
		} else {
			System.out.println("All fields are valid.");

			try {
				Message receivedMsg = SendMessage(1, currentUser);

				if (receivedMsg.MessageType == 2) {
					String response = (String) receivedMsg.Message;
					System.out.println("Received response back: " + response);

					if (response.contains("success")) {
						loggedInUser = currentUser;
						identificationFrame.setVisible(false);
						InitializeDashboardElements();
					} else {
						registerStatusMsg.setText("Failed to add new user");
					}
				}
				System.out.println("Sent new user registration.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	// Dashboard Frame starts here
	private static JPanel dashboardPanel, deleteConfirmationPopupPanel;
	private static JFrame dashboardFrame;
	private static JLabel newFileNameLbl, filesTableLbl, deleteMessageLbl;
	private static JTextArea filePreviewTextArea;
	private static JTextField newFileNameTxt;
	private static JButton createNewFileBtn, saveFileContentBtn, deleteSelectedFileBtn, refreshBtn;

	private static JTextArea newFileStatusMsg;

	private static ScrollableJTable filesTable; 
	private static String filesTableHeader[];
	private static DefaultTableModel filesTableDefaultModel;
	private static int filesTableSelectedRow, filesTableSelectedColumn;

	private static ArrayList<File> serverFiles;

	private static void InitializeDashboardElements() {
		serverFiles = new ArrayList<>();

		Message receivedMsg = SendMessage(5, null);

		if (receivedMsg.MessageType == 6) {
			// received success files
			serverFiles = (ArrayList<File>) receivedMsg.Message;


			/*
			 * Dashboard Panel		
			 */
			dashboardPanel = new JPanel();
			dashboardFrame = new JFrame("Dashboard - Hello " + loggedInUser.FirstName);
			dashboardFrame.setSize(800,600);
			dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			dashboardFrame.add(dashboardPanel);

			dashboardPanel.setLayout(null);

			newFileNameLbl= new JLabel("New File Name");
			newFileNameLbl.setBounds(10,20,120,25);
			dashboardPanel.add(newFileNameLbl);

			newFileNameTxt = new JTextField(20);
			newFileNameTxt.setBounds(150,20,165,25);
			dashboardPanel.add(newFileNameTxt);

			
			refreshBtn = new JButton("Refresh");
			refreshBtn.setBounds(75,60,100,25);
			refreshBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					RefreshBtnPressed();
				}
			});
			dashboardPanel.add(refreshBtn);
			
			createNewFileBtn = new JButton("Create File");
			createNewFileBtn.setBounds(330,20,100,25);
			if(loggedInUser.IsAdmin==false) createNewFileBtn.setEnabled(false);
			createNewFileBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					NewFileBtnPressed();
				}
			});
			dashboardPanel.add(createNewFileBtn);

			newFileStatusMsg = new JTextArea();
			newFileStatusMsg.setBounds(450, 20, 300, 25);
			newFileStatusMsg.setDisabledTextColor(Color.red);
			newFileStatusMsg.setEnabled(false);
			newFileStatusMsg.setBackground(new Color(238,238,238));
			dashboardPanel.add(newFileStatusMsg);


			filesTableLbl= new JLabel("All Files");
			filesTableLbl.setBounds(10,60,120,25);
			dashboardPanel.add(filesTableLbl);

			filesTable = new ScrollableJTable();
			filesTable.setBounds(10, 90, 700, 200);
			filesTableHeader = new String[] { "File Name", "Date Created", "Date Modified", "Size (in Bytes)"};
			filesTableDefaultModel = new DefaultTableModel(filesTableHeader, 0);
			filesTable.setModel(filesTableDefaultModel);

			dashboardPanel.add(filesTable);

			filePreviewTextArea = new JTextArea();
			filePreviewTextArea.setBounds(10,300, 700, 200);
			filePreviewTextArea.setForeground(Color.black);
			filePreviewTextArea.setBackground(Color.white);
			dashboardPanel.add(filePreviewTextArea);

			saveFileContentBtn = new JButton("Save");
			saveFileContentBtn.setBounds(10,520, 100, 25);
			saveFileContentBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SaveCurrentFile();
				}
			});

			dashboardPanel.add(saveFileContentBtn);

			deleteSelectedFileBtn = new JButton("Delete");
			deleteSelectedFileBtn.setBounds(600,520, 100, 25);
			if(loggedInUser.IsAdmin==false) deleteSelectedFileBtn.setEnabled(false);
			deleteSelectedFileBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DeleteSelectedFile();
				}
			});

			dashboardPanel.add(deleteSelectedFileBtn);

			dashboardFrame.setUndecorated(true);
			dashboardFrame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
			dashboardFrame.setLocationRelativeTo(null);
			dashboardFrame.setVisible(true);


			deleteConfirmationPopupPanel = new JPanel();
			deleteConfirmationPopupPanel.setSize(new Dimension(500, 100));
			deleteConfirmationPopupPanel.setLayout(null);
			deleteMessageLbl = new JLabel("");
			deleteMessageLbl.setVerticalAlignment(SwingConstants.CENTER);
			deleteMessageLbl.setBounds(10, 10, 480, 30);
			deleteMessageLbl.setHorizontalAlignment(SwingConstants.LEFT);
			deleteConfirmationPopupPanel.add(deleteMessageLbl);
			UIManager.put("OptionPane.minimumSize", new Dimension(500, 100));

			ReloadFiles();
		} 
	}
	private static void NewFileBtnPressed() {
		System.out.println("New File pressed!");
		newFileStatusMsg.setText("");

		if (newFileNameTxt.getText() == null) {
			newFileStatusMsg.setText("File name is required");
		} else if (newFileNameTxt.getText().length() > 25 || newFileNameTxt.getText().length() < 6 ) {
			newFileStatusMsg.setText("File name should be between 6 and 25 characters");
		} else {
			Date date = new Date(System.currentTimeMillis());
			File FileToCreate = new File(newFileNameTxt.getText(), "", loggedInUser.Username, formatter.format(date), formatter.format(date), false, "");

			SimpleEntry<String, File> newFile = new SimpleEntry<>(loggedInUser.Username, FileToCreate);
			Message receivedMsg = SendMessage(7, newFile);

			if (receivedMsg.MessageType == 8) {
				String response = (String) receivedMsg.Message;
				System.out.println("Received response back: " + response);

				if (response.contains("success")) {
					System.out.println("New file added: " + FileToCreate.FileName);

					newFileNameTxt.setText("");

					ReloadFiles();
				} else {
					newFileStatusMsg.setText("Failed to add new file");
				}
			}
		}
	}

	private static void RefreshBtnPressed() {
			boolean filesUpdated = false;
			filesTableDefaultModel.setRowCount(0);

			serverFiles = new ArrayList<>();

			filePreviewTextArea.setText("");

			Message receivedMsg = SendMessage(5, null);

			if (receivedMsg.MessageType == 6) {
				// received success files
				serverFiles = (ArrayList<File>) receivedMsg.Message;

				for (int i = 0; i<serverFiles.size(); i++) {
					try {
						Object[] objs = {
								serverFiles.get(i).FileName,
								serverFiles.get(i).DateCreated,
								serverFiles.get(i).DateModified,
								serverFiles.get(i).FileContent.getBytes("UTF-8").length + " B"};

						filesTableDefaultModel.addRow(objs);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}

			for (int i = 0; i<serverFiles.size(); i++) {
				File currentFile = serverFiles.get(i);
				System.out.println("Checking file #" + i);
				System.out.println(currentFile.IsRequestedToBeDeleted);
				System.out.println(currentFile.CreatedByUser);
				System.out.println(loggedInUser.Username);

				if (currentFile.IsRequestedToBeDeleted && currentFile.CreatedByUser.equalsIgnoreCase(loggedInUser.Username)) {
					System.out.println("Found a file requested to be deleted");

					deleteMessageLbl.setText(currentFile.RequestedToBeDeletedBy + " is requesting the deletion of " + currentFile.FileName + ". Do you want to delete it?");
					int res = JOptionPane.showConfirmDialog(null, deleteConfirmationPopupPanel, "Delete File",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.PLAIN_MESSAGE, null);
					if(res == 0) {
						System.out.println("Deleting file...");

						Message receivedMsg2 = SendMessage(11, currentFile.FileName);

						if (receivedMsg2.MessageType == 12) {
							String response = (String) receivedMsg2.Message;
							System.out.println("Received response back: " + response);

							if (response.contains("success")) {
								ShowToast("File deleted!", 750, 2);

								filesUpdated = true;
							} else {
								ShowToast("File not deleted", 750, 3);
								System.out.println("Failed to delete file");
							}
						}
					} else if (res == 1) {
						Date date = new Date(System.currentTimeMillis());
						File newFile = new File(currentFile.FileName, filePreviewTextArea.getText(), currentFile.CreatedByUser, currentFile.DateCreated, formatter.format(date), false, "");

						Message receivedMsg3 = SendMessage(9, newFile);

						if (receivedMsg3.MessageType == 10) {
							String response = (String) receivedMsg3.Message;
							System.out.println("Received response back: " + response);

							if (response.contains("success")) {
								filesUpdated = true;
							} else {
								System.out.println("Failed to update file");
								newFileStatusMsg.setText("File update failed");
							}
						}
					} else {
						System.out.println("Pressed Cancel");
					}

				}
			}

			if (filesUpdated) {
				ReloadFiles();
			}
		}

	
	public static void FileClicked(MouseEvent e) {
		System.out.println("File Pressed!");

		filesTableSelectedRow = filesTable.getSelectedRow();
		filesTableSelectedColumn = filesTable.getSelectedColumn();

		filePreviewTextArea.setText(serverFiles.get(filesTableSelectedRow).FileContent);

		//		System.out.println(filesTableSelectedRow+","+filesTableSelectedColumn);
	}

	private static void ReloadFiles() {
		boolean filesUpdated = false;
		filesTableDefaultModel.setRowCount(0);

		serverFiles = new ArrayList<>();

		filePreviewTextArea.setText("");

		Message receivedMsg = SendMessage(5, null);

		if (receivedMsg.MessageType == 6) {
			// received success files
			serverFiles = (ArrayList<File>) receivedMsg.Message;

			for (int i = 0; i<serverFiles.size(); i++) {
				try {
					Object[] objs = {
							serverFiles.get(i).FileName,
							serverFiles.get(i).DateCreated,
							serverFiles.get(i).DateModified,
							serverFiles.get(i).FileContent.getBytes("UTF-8").length + " B"};

					filesTableDefaultModel.addRow(objs);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}

		for (int i = 0; i<serverFiles.size(); i++) {
			File currentFile = serverFiles.get(i);
			System.out.println("Checking file #" + i);
			System.out.println(currentFile.IsRequestedToBeDeleted);
			System.out.println(currentFile.CreatedByUser);
			System.out.println(loggedInUser.Username);

			if (currentFile.IsRequestedToBeDeleted && currentFile.CreatedByUser.equalsIgnoreCase(loggedInUser.Username)) {
				System.out.println("Found a file requested to be deleted");

				deleteMessageLbl.setText(currentFile.RequestedToBeDeletedBy + " is requesting the deletion of " + currentFile.FileName + ". Do you want to delete it?");
				int res = JOptionPane.showConfirmDialog(null, deleteConfirmationPopupPanel, "Delete File",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE, null);
				if(res == 0) {
					System.out.println("Deleting file...");

					Message receivedMsg2 = SendMessage(11, currentFile.FileName);

					if (receivedMsg2.MessageType == 12) {
						String response = (String) receivedMsg2.Message;
						System.out.println("Received response back: " + response);

						if (response.contains("success")) {
							ShowToast("File deleted!", 750, 2);

							filesUpdated = true;
						} else {
							ShowToast("File not deleted", 750, 3);
							System.out.println("Failed to delete file");
						}
					}
				} else if (res == 1) {
					Date date = new Date(System.currentTimeMillis());
					File newFile = new File(currentFile.FileName, filePreviewTextArea.getText(), currentFile.CreatedByUser, currentFile.DateCreated, formatter.format(date), false, "");

					Message receivedMsg3 = SendMessage(9, newFile);

					if (receivedMsg3.MessageType == 10) {
						String response = (String) receivedMsg3.Message;
						System.out.println("Received response back: " + response);

						if (response.contains("success")) {
							filesUpdated = true;
						} else {
							System.out.println("Failed to update file");
							newFileStatusMsg.setText("File update failed");
						}
					}
				} else {
					System.out.println("Pressed Cancel");
				}

			}
		}

		if (filesUpdated) {
			ReloadFiles();
		}
	}

	private static void SaveCurrentFile() {
		System.out.println("Save pressed!");
		newFileStatusMsg.setText("");

		filesTableSelectedRow = filesTable.getSelectedRow();
		filesTableSelectedColumn = filesTable.getSelectedColumn();

		Date date = new Date(System.currentTimeMillis());

		File currentFile = serverFiles.get(filesTableSelectedRow);
		File newFile = new File(currentFile.FileName, filePreviewTextArea.getText(), currentFile.CreatedByUser, currentFile.DateCreated, formatter.format(date), false, "");

		Message receivedMsg = SendMessage(9, newFile);

		if (receivedMsg.MessageType == 10) {
			String response = (String) receivedMsg.Message;
			System.out.println("Received response back: " + response);

			if (response.contains("success")) {
				ReloadFiles();
			} else {
				System.out.println("Failed to update file");
				newFileStatusMsg.setText("File update failed");
			}
		}
	}	

	private static void DeleteSelectedFile() {
		System.out.println("Delete pressed!");
		newFileStatusMsg.setText("");

		filesTableSelectedRow = filesTable.getSelectedRow();
		filesTableSelectedColumn = filesTable.getSelectedColumn();

		Date date = new Date(System.currentTimeMillis());

		File currentFile = serverFiles.get(filesTableSelectedRow);
		
		if (currentFile.CreatedByUser.equalsIgnoreCase(loggedInUser.Username)) {
			System.out.println("User is file owner!");

			Message receivedMsg = SendMessage(11, currentFile.FileName);

			if (receivedMsg.MessageType == 12) {
				String response = (String) receivedMsg.Message;
				System.out.println("Received response back: " + response);

				if (response.contains("success")) {
					ShowToast("File deleted!", 750, 2);

					ReloadFiles();
				} else {
					ShowToast("File not deleted", 750, 3);
					System.out.println("Failed to delete file");
					newFileStatusMsg.setText("File delete failed");
				}
			}
		} else {
			File newFile = new File(currentFile.FileName, currentFile.FileContent, currentFile.CreatedByUser, currentFile.DateCreated, formatter.format(date), true, loggedInUser.Username);

			System.out.println("User is not file owner!");
			Message receivedMsg = SendMessage(9, newFile);

			if (receivedMsg.MessageType == 10) {
				String response = (String) receivedMsg.Message;
				System.out.println("Received response back: " + response);

				if (response.contains("success")) {
					ShowToast("File delete requested", 500, 2);
					ReloadFiles();
				} else {
					System.out.println("Failed to update file");
					newFileStatusMsg.setText("File update failed");
				}
			}
		}

	}	
	private static Message SendMessage(int MessageType, Object Message) {
		try {
			socket = new Socket(HOST, PORT);

			// Fetch the output stream from the socket to send data on it
			oos = new ObjectOutputStream(socket.getOutputStream());

			// Create the message object that should be sent in the stream, and feed it the properties attached to the content
			Message newMessage = new Message(MessageType, Message);

			// Broadcast the message on the stream
			oos.writeObject(newMessage);

			ois = new ObjectInputStream(socket.getInputStream());

			Message receivedMsg = (Message) ois.readObject();
			System.out.println("Received response back: " + receivedMsg.MessageType);

			// Close the output stream
			oos.close();

			// Close the socket (until a new message is written), important to dispose of variables
			socket.close();

			return receivedMsg;
		} catch (IOException | ClassNotFoundException e) {
			// Auto-generated catch block
			e.printStackTrace();
			return new Message(0, "General Error!");
		}
	}
	
	private static void ShowToast(String toast, int duration, int toastType) {
		// create a toast message
		/*
		 * toastType = 1 (info), 2 (success), 3 (error)
		 */
		Toaster toaster = new Toaster(toast, 20, 20, toastType);

		// call the method
		toaster.ShowToast(duration);
	}
}

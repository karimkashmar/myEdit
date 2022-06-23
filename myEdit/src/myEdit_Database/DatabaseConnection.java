package myEdit_Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.File;
import models.User;

public class DatabaseConnection {
	private Connection dbConnection;

	public DatabaseConnection(Connection dbConnection) {
		this.dbConnection = dbConnection;
	}

	public boolean insertUser(User user) {

		System.out.println("Insert user...");
		PreparedStatement ps = null;

		try {
			String sql = "INSERT INTO USER(FirstName, LastName, EmailAddress, Username, Password, IsAdmin) VALUES(?,?,?,?,?,?)";

			ps = dbConnection.prepareStatement(sql);

			ps.setString(1, user.FirstName);
			ps.setString(2, user.LastName);
			ps.setString(3, user.EmailAddress);
			ps.setString(4, user.Username);
			ps.setString(5, user.Password);
			ps.setString(6, user.IsAdmin?"1":"0");

			ps.execute();

			System.out.println("New user " + user.Username + " inserted!");

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public ArrayList<User> getUsers() {
		ArrayList<User> UsersToReturn = new ArrayList<>();
		System.out.println("Getting all users...");
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "Select * from user";

			ps = dbConnection.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				UsersToReturn.add(
						new User(
								rs.getString("FirstName"), 
								rs.getString("LastName"), 
								rs.getString("EmailAddress"), 
								rs.getString("Username"), 
								rs.getString("Password"), 
								rs.getString("IsAdmin").equalsIgnoreCase("1")? true:false));
			}

			return UsersToReturn;
		} catch (SQLException e) {
			e.printStackTrace();
			return UsersToReturn;
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public User getUser(String Username) {
		User UserToReturn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			System.out.println("Getting user...");

			String sql = "Select * from user Where Username=? limit 1";

			ps = dbConnection.prepareStatement(sql);

			ps.setString(1, Username);

			rs = ps.executeQuery();

			UserToReturn = new User(rs.getString("FirstName"), 
					rs.getString("LastName"), 
					rs.getString("EmailAddress"), 
					rs.getString("Username"), 
					rs.getString("Password"),
					rs.getString("IsAdmin").equalsIgnoreCase("1")? true:false);

			System.out.println("Name: " + UserToReturn.FirstName + " " + UserToReturn.LastName);

			return UserToReturn;

		} catch (SQLException e) {
			e.printStackTrace();
			return UserToReturn;
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/* 
	 * Not used functions
	 * */
	public void updateUser(User user) {

		System.out.println("Updating user...");
		PreparedStatement ps = null;

		try {
			String sql = "Update User set FirstName = '?', LastName = '?', EmailAddress = '?', Username='?', Password='?', IsAdmin='?' where Username=?";

			ps = dbConnection.prepareStatement(sql);

			ps.setString(1, user.FirstName);
			ps.setString(2, user.LastName);
			ps.setString(3, user.EmailAddress);
			ps.setString(4, user.Username);
			ps.setString(5, user.Password);
			ps.setString(6, user.IsAdmin?"1":"0");
			ps.setString(7, user.Username);

			ps.execute();

			System.out.println("Updated user " + user.Username);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void removeUser(String username) {
		System.out.println("Removing user...");
		PreparedStatement ps = null;

		try {
			String sql = "delete from user where Username=?";
			ps = dbConnection.prepareStatement(sql);
			ps.setString(1, username);
			ps.execute();
			System.out.println("User deleted!");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	public int getUserId(String Username) {
		int UserIdToReturn = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			System.out.println("Getting user id...");

			String sql = "Select * from user Where Username=? limit 1";

			ps = dbConnection.prepareStatement(sql);

			ps.setString(1, Username);

			rs = ps.executeQuery();

			UserIdToReturn = Integer.parseInt(rs.getString("Id"));

			System.out.println("Username: " + Username + ". Id: " + UserIdToReturn);

			return UserIdToReturn;

		} catch (SQLException e) {
			e.printStackTrace();
			return UserIdToReturn;
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/*
	 * End Not User
	 * */


	public boolean insertFile(File file) {

		System.out.println("Insert file...");
		PreparedStatement ps = null;

		try {
			String sql = "INSERT INTO FILE(FileName, FileContent, CreatedByUser, DateCreated, DateModified, IsRequestedToBeDeleted, RequestedToBeDeletedBy) VALUES(?,?,?,?,?,?,?)";

			ps = dbConnection.prepareStatement(sql);

			ps.setString(1, file.FileName);
			ps.setString(2, file.FileContent);
			ps.setString(3, file.CreatedByUser);
			ps.setString(4, file.DateCreated);
			ps.setString(5, file.DateModified);
			ps.setString(6, file.IsRequestedToBeDeleted?"1":"0");
			ps.setString(7, file.RequestedToBeDeletedBy);

			ps.execute();

			System.out.println("New file " + file.FileName + " inserted!");

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public ArrayList<File> getFiles() {
		ArrayList<File> FilesToReturn = new ArrayList<>();
		System.out.println("Getting all files...");
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "Select * from file";

			ps = dbConnection.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				FilesToReturn.add(
						new File(
								rs.getString("FileName"), 
								rs.getString("FileContent"), 
								rs.getString("CreatedByUser"), 
								rs.getString("DateCreated"),
								rs.getString("DateModified"),
								rs.getString("IsRequestedToBeDeleted").equalsIgnoreCase("1")? true:false,
								rs.getString("RequestedToBeDeletedBy")));
			}

			return FilesToReturn;
		} catch (SQLException e) {
			e.printStackTrace();
			return FilesToReturn;
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public File getFile(String FileName) {
		File FileToReturn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			System.out.println("Getting file...");

			String sql = "Select * from file Where FileName=? limit 1";

			ps = dbConnection.prepareStatement(sql);

			ps.setString(1, FileName);

			rs = ps.executeQuery();

			System.out.println("HERE: " + rs.getString("IsRequestedToBeDeleted"));
			FileToReturn = new File(rs.getString("FileName"), 
					rs.getString("FileContent"), 
					rs.getString("CreatedByUser"), 
					rs.getString("DateCreated"),
					rs.getString("DateModified"),
					rs.getString("IsRequestedToBeDeleted").equalsIgnoreCase("1")? true:false,
							rs.getString("RequestedToBeDeletedBy"));

			System.out.println("Name: " + FileToReturn.FileName);

			return FileToReturn;

		} catch (SQLException e) {
			e.printStackTrace();
			return FileToReturn;
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public boolean updateFile(File file) {

		System.out.println("Updating file...");
		PreparedStatement ps = null;

		try {
			String sql = "Update File set FileName = ?, FileContent = ?, CreatedByUser = ?, DateCreated=?, DateModified=?, IsRequestedToBeDeleted=?, RequestedToBeDeletedBy=? where FileName=?";

			ps = dbConnection.prepareStatement(sql);

			ps.setString(1, file.FileName);
			ps.setString(2, file.FileContent);
			ps.setString(3, file.CreatedByUser);
			ps.setString(4, file.DateCreated);
			ps.setString(5, file.DateModified);
			ps.setString(6, file.IsRequestedToBeDeleted?"1":"0");
			ps.setString(7, file.RequestedToBeDeletedBy);
			ps.setString(8, file.FileName);

			ps.execute();

			System.out.println("Updated file " + file.FileName);

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public boolean removeFile(String filename) {
		System.out.println("Removing file...");
		PreparedStatement ps = null;

		try {
			String sql = "delete from file where FileName=?";
			ps = dbConnection.prepareStatement(sql);
			ps.setString(1, filename);
			ps.execute();
			System.out.println("File deleted!");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}

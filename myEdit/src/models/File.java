package models;

import java.io.Serializable;

/*
 *  A file is an object with a collection of properties
 */

public class File implements Serializable {
	private static final long serialVersionUID = 1L;
	public String FileName;
	public String FileContent;
	public String CreatedByUser;
	public String DateCreated;
	public String DateModified;
	public boolean IsRequestedToBeDeleted;
	public String RequestedToBeDeletedBy;
	
	// All properties needed in this specific message type needs to be defined here
	public File(String FileName, String FileContent, String CreatedByUser, String DateCreated, String DateModified, boolean IsRequestedToBeDeleted, String RequestedToBeDeletedBy) {
		this.FileName = FileName;
		this.FileContent = FileContent;
		this.CreatedByUser = CreatedByUser;
		this.DateCreated = DateCreated;
		this.DateModified = DateModified;
		this.IsRequestedToBeDeleted = IsRequestedToBeDeleted;
		this.RequestedToBeDeletedBy = RequestedToBeDeletedBy;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public String getFileContent() {
		return FileContent;
	}

	public void setFileContent(String fileContent) {
		FileContent = fileContent;
	}

	public String getCreatedByUser() {
		return CreatedByUser;
	}

	public void setCreatedByUser(String createdByUser) {
		CreatedByUser = createdByUser;
	}

	public String getDateCreated() {
		return DateCreated;
	}

	public void setDateCreated(String dateCreated) {
		DateCreated = dateCreated;
	}

	public String getDateModified() {
		return DateModified;
	}

	public void setDateModified(String dateModified) {
		DateModified = dateModified;
	}

	public boolean isIsRequestedToBeDeleted() {
		return IsRequestedToBeDeleted;
	}

	public void setIsRequestedToBeDeleted(boolean isRequestedToBeDeleted) {
		IsRequestedToBeDeleted = isRequestedToBeDeleted;
	}

	public String getRequestedToBeDeletedBy() {
		return RequestedToBeDeletedBy;
	}

	public void setRequestedToBeDeletedBy(String requestedToBeDeletedBy) {
		RequestedToBeDeletedBy = requestedToBeDeletedBy;
	}
	

}

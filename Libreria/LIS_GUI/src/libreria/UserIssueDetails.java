
package libreria;

import java.io.Serializable;




 
public class UserIssueDetails implements Serializable{
    private String issuedBook;
    private String issueDate;
    private String returnDate;
    private boolean isOverdue;
    private boolean isNotif;
    private String dueDate;

    public UserIssueDetails(String issuedBook, String issueDate,String dueDate) {
        this.issuedBook = issuedBook;
        this.issueDate = issueDate;
        this.returnDate = "";
        this.setDueDate(dueDate);
    }
    
    
    public String getIssuedBook() {
        return issuedBook;
    }

    public void setIssuedBook(String issuedBook) {
        this.issuedBook = issuedBook;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }


	public String getDueDate() {
		return dueDate;
	}


	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}


	public boolean isOverdue() {
		return isOverdue;
	}


	public void setOverdue(boolean isOverdue) {
		this.isOverdue = isOverdue;
	}


	public boolean isNotif() {
		return isNotif;
	}


	public void setNotif(boolean isNotif) {
		this.isNotif = isNotif;
	}
    
    
}

package libreria;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class BookCopy implements Serializable{
    private String issueMember;
    private String issueDate;
    private String returnDate;
    private String dueDate;
    public BookCopy()
    {
    	this.issueMember ="";
        this.issueDate ="";
        this.setDueDate("");
        this.returnDate="";
    	
    }

    public BookCopy(String issueMember, String issueDate,String dueDate) {
        this.issueMember = issueMember;
        this.issueDate = issueDate;
        this.setDueDate(dueDate);
        this.returnDate="";
    }

    public String getIssueMember() {
        return issueMember;
    }

    public void setIssueMember(String issueMember) {
        this.issueMember = issueMember;
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
    
}

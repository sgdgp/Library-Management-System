package libreria;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Issue {
	
	@SuppressWarnings("unused")
	private String url1 = "jdbc:mysql://localhost:3306/";
    private static String url = "jdbc:mysql://localhost:3306/lis?useSSL=false";
    private static final String user = "root";
    private static final String password = "qwerty";
    
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public void issue(String ISBN, String username){
    	

    	
    	
        try {
            int flag = 0;
            Statement stmt = null;
            Statement stmt1 = null;
            Connection con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            stmt1 = con.createStatement();
            String add = "SELECT * FROM books WHERE ISBN = '" + ISBN + "'";

            ResultSet rs = stmt.executeQuery(add);
            try {
                if (rs.next()) {
                	 boolean isReserved = rs.getBoolean("isReserved");
                     int onShelf = rs.getInt("onShelf");
                    
                     
                     byte[] buf3 = rs.getBytes("issueStats");
                     ObjectInputStream o3 = new ObjectInputStream(new ByteArrayInputStream(buf3));
                     ArrayList<Date> issueStats = (ArrayList<Date>) o3.readObject();
                    
                    byte[] buf = rs.getBytes("copyDetails");
                    ObjectInputStream o = new ObjectInputStream(new ByteArrayInputStream(buf));
                    ArrayList<BookInfo> copyDetails = (ArrayList<BookInfo>) o.readObject();

                    byte[] buf1 = rs.getBytes("reserveList");
                    ObjectInputStream o1 = new ObjectInputStream(new ByteArrayInputStream(buf1));
                    ArrayList<String> reserveList = (ArrayList<String>) o1.readObject();

                    String mem = "SELECT * FROM users WHERE username = '" + username+"'";
                    ResultSet ms = stmt1.executeQuery(mem);
                    if (ms.next()) {
                    	
                    	int dur = ms.getInt("duration");
                        double fine = ms.getDouble("fine");
                        int bookLimit = ms.getInt("bookLimit");
                        byte[] buf2 = ms.getBytes("booksIssued");
                        ObjectInputStream o2 = new ObjectInputStream(new ByteArrayInputStream(buf2));
                        ArrayList<UserIssueDetails> booksIssued = (ArrayList<UserIssueDetails>) o2.readObject();
                        
        	            
        	            for(int i=0;i<booksIssued.size();i++){
        	            	if(booksIssued.get(i).getIssuedBook().equals(ISBN)){
        	            		PopUp pop = new PopUp("Book already issued by you!");
        	                    pop.setVisible(true);
        	                    return;
        	            	}
        	            		
        	            }
                        //if fine is not paid or max books issued
                        if (fine > 0) {
                            PopUp pop = new PopUp("Can't issue book! Fine due Rs." + fine);
                            pop.setVisible(true);
                            return;
                        }
                        int count = 0;
                        Iterator itr1 = booksIssued.iterator();
                        while (itr1.hasNext()) {
                            UserIssueDetails ism = (UserIssueDetails) itr1.next();
                            if (ism.getReturnDate().equals("")) {
                                count++;
                            }
                        }
                        if (count >= bookLimit) {
                            PopUp pop = new PopUp("Can't issue book! Book limit reached");
                            pop.setVisible(true);
                            return;
                        }

                        //for non-reserved books
                        if (isReserved == false) {
                            if (onShelf > 0) {
                                Iterator itr = copyDetails.iterator();
                                
                                while (itr.hasNext()) {
                                	
                                    BookInfo sb = (BookInfo) itr.next();
                                    if (!sb.isIsIssued()) {
                                        onShelf--;
                                        itr.remove();
                                        sb.setIsIssued(true);
                                        ArrayList<BookCopy> issuedMembers = sb.getIssuedMembers();
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                        String date = sdf.format(Calendar.getInstance().getTime());
                                        Calendar c = Calendar.getInstance();
                                        c.add(Calendar.MONTH, dur);
                                        String date1 = sdf.format(c.getTime());   
                                        BookCopy temp = new BookCopy(username, date,date1);
                                        issuedMembers.add(temp);
                                        sb.setIssuedMembers(issuedMembers);
                                        PopUp pop = new PopUp("Book Issued!");
                                        issueStats.add(Calendar.getInstance().getTime());
                                        pop.setVisible(true);
                                        UserIssueDetails memtemp = new UserIssueDetails(ISBN, date,date1);
                                        booksIssued.add(memtemp);
                                        copyDetails.add(sb);
                                        
                                        break;
                                    }
                                    
                                }

                            } else {
                                PopUp pop = new PopUp("Book not on shelf!");
                                pop.setVisible(true);
                            }
                        }

                        //if book is reserved
                        boolean member_exists = false;
                        if (isReserved == true) {
                            if (onShelf > 0) {
                                Iterator iter = reserveList.iterator();
                                while (iter.hasNext()) {
                                   String x = (String) iter.next();
                                    if (x.equals(username)) {
                                        member_exists = true;
                                        iter.remove();
                                        break;
                                    }
                                }

                                if (!member_exists) {
                                    PopUp pop = new PopUp("Book reserved! Can't be issued out!");
                                    pop.setVisible(true);
                                    return;
                                }

                                Iterator itr = copyDetails.iterator();

                                while (itr.hasNext()) {
                                    BookInfo sb = (BookInfo) itr.next();
                                    if (!sb.isIsIssued()) {
                                        onShelf--;
                                        itr.remove();
                                        sb.setIsIssued(true);
                                        ArrayList<BookCopy> issuedMembers = sb.getIssuedMembers();
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                        String date = sdf.format(Calendar.getInstance().getTime());
                                        Calendar c = Calendar.getInstance();
                                        c.add(Calendar.MONTH, dur);
                                        String date1 = sdf.format(c.getTime());   
                                        BookCopy temp = new BookCopy(username, date,date1);
                                        issuedMembers.add(temp);
                                        sb.setIssuedMembers(issuedMembers);
                                        if (reserveList.isEmpty()) {
                                            isReserved = false;
                                        }

                                        PopUp pop = new PopUp("Book Issued!");
                                        pop.setVisible(true);
                                        String sql="delete from rbList where ISBN='"+ISBN+"'";
                                        
                                        con.createStatement().executeUpdate("Set SQL_SAFE_UPDATES=0");
                                        con.createStatement().executeUpdate(sql);
                                        issueStats.add(Calendar.getInstance().getTime());
                                        UserIssueDetails memtemp = new UserIssueDetails(ISBN, date,date1);
                                        booksIssued.add(memtemp);
                                        copyDetails.add(sb);
                                        break;
                                    }
                                }

                            } else {
                                PopUp pop = new PopUp("Book still not on shelf!");
                                pop.setVisible(true);
                            }
                        }

                        add = "UPDATE books SET reserveList = " + "?"
                        		+ ", issueStats = " + "?"
                                + ", isReserved = " + isReserved
                                + ", copyDetails = " + "?"
                                + ", onShelf = " + onShelf
                                + " WHERE ISBN = '" + ISBN + "'";
                        PreparedStatement pstmt = con.prepareStatement(add);
//                        System.out.println(issueStats);
                        pstmt.setObject(2, issueStats);
                        pstmt.setObject(1, reserveList);
                        pstmt.setObject(3, copyDetails);
                        pstmt.executeUpdate();
                        //stmt.executeUpdate(add);

                        mem = "UPDATE users SET booksIssued = " + "?" + " WHERE username = '" + username+"'";
                        PreparedStatement pstmt1 = con.prepareStatement(mem);
                        //pstmt1 = con.prepareStatement(mem);
                        pstmt1.setObject(1, booksIssued);
                        pstmt1.executeUpdate();
                        //stmt.executeUpdate(mem);
                        ms.close();
                    } else {
                        PopUp pop = new PopUp("Inavalid Friend ID!");
                        pop.setVisible(true);
                    }
                    rs.close();
                } else {
                    PopUp pop = new PopUp("ISBN doesn't exist!");
                    pop.setVisible(true);
                }
            } catch (SQLException | IOException ex) {
//                Logger.getLogger(Issue.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
        } catch (SQLException ex) {
//            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

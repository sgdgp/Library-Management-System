
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sayan
 */
public class libraryfunc {

    @SuppressWarnings("unused")
	private String url1 = "jdbc:mysql://localhost:3306/";
    private static String url = "jdbc:mysql://localhost:3306/lis?useSSL=false";
    private static final String user = "root";
    private static final String password = "qwerty";

    public libraryfunc() {
//        try {
//            Statement stmt = null;
//            Connection con = DriverManager.getConnection(url1, user, password);
//            stmt = con.createStatement();
//            String add = "SELECT * FROM lis";
//            ResultSet rs = stmt.executeQuery(add);
//            rs.next();
//            url = "jdbc:mysql://" + rs.getString("url") + ":3306/lis";
//        } catch (SQLException ex) {
//
//        }
    }

    //private static ArrayList<Book> bookList;
    //private static ArrayList<Member> memberList;
//    public void statistics() {
//        try {
//			Connection con = DriverManager.getConnection(url+"lis", user, password);
//			Statement s = con.createStatement();
//			String sql = "Select * from books";
//			ResultSet rs = s.executeQuery(sql);
//			while(rs.next()){
//				String isbn = rs.getString("ISBN");
//				String a = rs.getString("issueStats");
//				System.out.println(isbn+"     "+a);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//    }

  
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void checkReserve(String ISBN) throws ParseException {
        try {
//            System.out.println("Check Reserve!");
            Statement stmt = null;
            Connection con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            String add = "SELECT * FROM books WHERE ISBN = '" + ISBN + "'";
            ResultSet rs = stmt.executeQuery(add);
            Date date = (Calendar.getInstance().getTime());
            if (rs.next()) {
                boolean isReserved = rs.getBoolean("isReserved");
                int onShelf = rs.getInt("onShelf");
                byte[] buf1 = rs.getBytes("reserveList");
                ObjectInputStream o1 = new ObjectInputStream(new ByteArrayInputStream(buf1));
                ArrayList<Integer> reservedList = (ArrayList<Integer>) o1.readObject();
                if (isReserved) {
                    if (onShelf > 0) {

                        byte[] buf = rs.getBytes("copyDetails");
                        ObjectInputStream o = new ObjectInputStream(new ByteArrayInputStream(buf));
                        ArrayList<BookInfo> copyDetails = (ArrayList<BookInfo>) o.readObject();
                        Iterator itr = copyDetails.iterator();
                        while (itr.hasNext()) {
                            BookInfo sb = (BookInfo) itr.next();
                            ArrayList<BookCopy> iss = (ArrayList<BookCopy>) sb.getIssuedMembers();
                            SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
                            for(int i=0;i<iss.size();++i)
                            {
                            	if (iss.get(i).getReturnDate().equals("")==false)
                            	{
                            	Date dd = sd.parse(iss.get(i).getReturnDate());
                            	if (dd.compareTo(date) < 0) {
                                    date = dd;
                            	}
                            }
                           
                                }
                            }
                        
                    }
                }
                Date date1 = Calendar.getInstance().getTime();
//                System.out.println(date.toString());
//                System.out.println(date1.toString());
                
                Calendar c1 = Calendar.getInstance();
                Calendar c2 = Calendar.getInstance();
                
                c1.setTime(date1);
                c2.setTime(date);
                long diff=date1.getTime()-date.getTime();
                if (diff >= 7 * 24 * 60 * 60 * 1000) {
                    reservedList.clear();
                    isReserved = false;
                    String sql="delete from rbList where ISBN='"+ISBN+"'";
                   
                    con.createStatement().executeUpdate("Set SQL_SAFE_UPDATES=0");
                    con.createStatement().executeUpdate(sql);
//                    System.out.println("Deleted in rblist");
                    
                    add = "UPDATE books SET reserveList = " + "?"
                            + ", isReserved = " + isReserved
                            + " WHERE ISBN = '" + ISBN + "'";
                    PreparedStatement pstmt = con.prepareStatement(add);
                    pstmt.setObject(1, reservedList);
                    pstmt.executeUpdate();
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(libraryfunc.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(libraryfunc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(libraryfunc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public void fine(String username ) {
        try {
//            System.out.println("fine");
            int flag = 0;
            double fine = 0.0;
            String issueDate = "";
            String returnDate = "";
            Statement stmt = null;
            Connection con = DriverManager.getConnection(url, user, password);
//            System.out.println("Success");
            stmt = con.createStatement();
            String add = "SELECT * FROM users WHERE username = '" + username+"'";
            ResultSet rs = stmt.executeQuery(add);
            rs.next();
            int duration = rs.getInt("duration");
            byte[] buf1 = rs.getBytes("booksIssued");
            ObjectInputStream o1 = new ObjectInputStream(new ByteArrayInputStream(buf1));
            ArrayList<UserIssueDetails> booksIssued = (ArrayList<UserIssueDetails>) o1.readObject();
            Iterator itr = booksIssued.iterator();
//            System.out.println(username);

            while (itr.hasNext()) {
                UserIssueDetails iss = (UserIssueDetails) itr.next();
                issueDate = iss.getIssueDate();
                returnDate = iss.getReturnDate();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                if (returnDate.equals("")) {
                    returnDate = sdf.format(Calendar.getInstance().getTime());
                }
                Date dIssue = sdf.parse(issueDate);
                Date dReturn = sdf.parse(returnDate);
                //System.out.println(dIssue.toString());
                //System.out.println(dReturn.toString());
                long diff = dReturn.getTime() - dIssue.getTime();
                long days = diff / (1000 * 60 * 60 * 24);
                if (days > duration * 30) {
                    fine = fine + days - duration * 30;
                    iss.setOverdue(true);
                }

            }
            add = "UPDATE users SET fine = ?,booksIssued=?" 
                    + " WHERE username = '" +username+"'";
            
            PreparedStatement pstmt = con.prepareStatement(add);
            pstmt.setDouble(1, fine);
            pstmt.setObject(2, booksIssued);
            pstmt.executeUpdate();
        } catch (Exception e) {
        }
    }

    @SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	public static void informReservedMembers(String ISBN){
        try {
            int flag = 0;
            Statement stmt = null;
            Connection con = DriverManager.getConnection(url, user, password);
//            System.out.println("Success");
            stmt = con.createStatement();
            String add = "SELECT * FROM books WHERE ISBN = '" + ISBN + "'";
            ResultSet rs = stmt.executeQuery(add);
            rs.next();
            boolean isReserved = rs.getBoolean("isReserved");
            int onShelf = rs.getInt("onShelf");
            byte[] buf1 = rs.getBytes("reserveList");
            ObjectInputStream o1 = new ObjectInputStream(new ByteArrayInputStream(buf1));
            ArrayList<Integer> reserveList = (ArrayList<Integer>) o1.readObject();
//            System.out.println(reserveList.size());
//            System.out.println("Success-info");
            Iterator itr = reserveList.iterator();
            while (itr.hasNext() && (onShelf > 0)) {
            	try{
//            		Connection com = ;
            		String sql = "INSERT INTO rbList (username,ISBN)" +
                            " VALUES(?,?)";
        			PreparedStatement st = con.prepareStatement(sql);
        			st.setString(1, (String) itr.next());
        			st.setString(2, ISBN);
        			st.executeUpdate();
//        			System.out.println("itr.next : "+itr.next());
            	}catch(Exception e){
//            		System.out.println("Exception???");
            	}
//                System.out.println("Book Available for member username = " +itr.next() );
            }
        } catch (SQLException ex) {
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void penalty() {

    }

//    @SuppressWarnings({ "unchecked", "unused", "rawtypes" })
//	public void issue(String ISBN, String username){
//    	
////    	try {
////			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lis", "root", "qwerty");
////			Statement st = con.createStatement();
////			
////		} catch (SQLException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//    	
//    	
//    	
//    	
//    	
//        try {
//            int flag = 0;
//            Statement stmt = null;
//            Statement stmt1 = null;
//            Connection con = DriverManager.getConnection(url, user, password);
//            stmt = con.createStatement();
//            stmt1 = con.createStatement();
//            String add = "SELECT * FROM books WHERE ISBN = '" + ISBN + "'";
//            ResultSet rs = stmt.executeQuery(add);
//            try {
//                if (rs.next()) {
//                	 boolean isReserved = rs.getBoolean("isReserved");
//                     int onShelf = rs.getInt("onShelf");
//                    
//                     
//                     byte[] buf3 = rs.getBytes("issueStats");
//                     ObjectInputStream o3 = new ObjectInputStream(new ByteArrayInputStream(buf3));
//                     ArrayList<Date> issueStats = (ArrayList<Date>) o3.readObject();
//                    
//                    byte[] buf = rs.getBytes("copyDetails");
//                    ObjectInputStream o = new ObjectInputStream(new ByteArrayInputStream(buf));
//                    ArrayList<BookInfo> copyDetails = (ArrayList<BookInfo>) o.readObject();
//
//                    byte[] buf1 = rs.getBytes("reserveList");
//                    ObjectInputStream o1 = new ObjectInputStream(new ByteArrayInputStream(buf1));
//                    ArrayList<String> reserveList = (ArrayList<String>) o1.readObject();
//
//                    String mem = "SELECT * FROM users WHERE username = '" + username+"'";
//                    ResultSet ms = stmt1.executeQuery(mem);
//                    if (ms.next()) {
//                    	
//                    	int dur = ms.getInt("duration");
//                        double fine = ms.getDouble("fine");
//                        int bookLimit = ms.getInt("bookLimit");
//                        byte[] buf2 = ms.getBytes("booksIssued");
//                        ObjectInputStream o2 = new ObjectInputStream(new ByteArrayInputStream(buf2));
//                        ArrayList<UserIssueDetails> booksIssued = (ArrayList<UserIssueDetails>) o2.readObject();
//
//                        //if fine is not paid or max books issued
//                        if (fine > 0) {
//                            PopUp pop = new PopUp("Can't issue book! Fine due Rs." + fine);
//                            pop.setVisible(true);
//                            return;
//                        }
//                        int count = 0;
//                        Iterator itr1 = booksIssued.iterator();
//                        while (itr1.hasNext()) {
//                            UserIssueDetails ism = (UserIssueDetails) itr1.next();
//                            if (ism.getReturnDate().equals("")) {
//                                count++;
//                            }
//                        }
//                        if (count >= bookLimit) {
//                            PopUp pop = new PopUp("Can't issue book! Book limit reached");
//                            pop.setVisible(true);
//                            return;
//                        }
//
//                        //for non-reserved books
//                        if (isReserved == false) {
//                            if (onShelf > 0) {
//                                Iterator itr = copyDetails.iterator();
//                                
//                                while (itr.hasNext()) {
//                                	
//                                    BookInfo sb = (BookInfo) itr.next();
//                                    if (!sb.isIsIssued()) {
//                                        onShelf--;
//                                        itr.remove();
//                                        sb.setIsIssued(true);
//                                        ArrayList<BookCopy> issuedMembers = sb.getIssuedMembers();
//                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                                        String date = sdf.format(Calendar.getInstance().getTime());
//                                        Calendar c = Calendar.getInstance();
//                                        c.add(Calendar.MONTH, dur);
//                                        String date1 = sdf.format(c.getTime());   
//                                        BookCopy temp = new BookCopy(username, date,date1);
//                                        issuedMembers.add(temp);
//                                        sb.setIssuedMembers(issuedMembers);
//                                        PopUp pop = new PopUp("Book Issued!");
//                                        issueStats.add(Calendar.getInstance().getTime());
//                                        pop.setVisible(true);
//                                        UserIssueDetails memtemp = new UserIssueDetails(ISBN, date,date1);
//                                        booksIssued.add(memtemp);
//                                        copyDetails.add(sb);
//                                        
//                                        break;
//                                    }
//                                    
//                                }
//
//                            } else {
//                                PopUp pop = new PopUp("Book not on shelf!");
//                                pop.setVisible(true);
//                            }
//                        }
//
//                        //if book is reserved
//                        boolean member_exists = false;
//                        if (isReserved == true) {
//                            if (onShelf > 0) {
//                                Iterator iter = reserveList.iterator();
//                                while (iter.hasNext()) {
//                                   String x = (String) iter.next();
//                                    if (x.equals(username)) {
//                                        member_exists = true;
//                                        iter.remove();
//                                        break;
//                                    }
//                                }
//
//                                if (!member_exists) {
//                                    PopUp pop = new PopUp("Book reserved! Can't be issued out!");
//                                    pop.setVisible(true);
//                                    return;
//                                }
//
//                                Iterator itr = copyDetails.iterator();
//
//                                while (itr.hasNext()) {
//                                    BookInfo sb = (BookInfo) itr.next();
//                                    if (!sb.isIsIssued()) {
//                                        onShelf--;
//                                        itr.remove();
//                                        sb.setIsIssued(true);
//                                        ArrayList<BookCopy> issuedMembers = sb.getIssuedMembers();
//                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                                        String date = sdf.format(Calendar.getInstance().getTime());
//                                        Calendar c = Calendar.getInstance();
//                                        c.add(Calendar.MONTH, dur);
//                                        String date1 = sdf.format(c.getTime());   
//                                        BookCopy temp = new BookCopy(username, date,date1);
//                                        issuedMembers.add(temp);
//                                        sb.setIssuedMembers(issuedMembers);
//                                        if (reserveList.isEmpty()) {
//                                            isReserved = false;
//                                        }
//
//                                        PopUp pop = new PopUp("Book Issued!");
//                                        pop.setVisible(true);
//                                        String sql="delete from rbList where ISBN='"+ISBN+"'";
//                                        
//                                        con.createStatement().executeUpdate("Set SQL_SAFE_UPDATES=0");
//                                        con.createStatement().executeUpdate(sql);
//                                        issueStats.add(Calendar.getInstance().getTime());
//                                        UserIssueDetails memtemp = new UserIssueDetails(ISBN, date,date1);
//                                        booksIssued.add(memtemp);
//                                        copyDetails.add(sb);
//                                        break;
//                                    }
//                                }
//
//                            } else {
//                                PopUp pop = new PopUp("Book still not on shelf!");
//                                pop.setVisible(true);
//                            }
//                        }
//
//                        add = "UPDATE books SET reserveList = " + "?"
//                        		+ ", issueStats = " + "?"
//                                + ", isReserved = " + isReserved
//                                + ", copyDetails = " + "?"
//                                + ", onShelf = " + onShelf
//                                + " WHERE ISBN = '" + ISBN + "'";
//                        PreparedStatement pstmt = con.prepareStatement(add);
////                        System.out.println(issueStats);
//                        pstmt.setObject(2, issueStats);
//                        pstmt.setObject(1, reserveList);
//                        pstmt.setObject(3, copyDetails);
//                        pstmt.executeUpdate();
//                        //stmt.executeUpdate(add);
//
//                        mem = "UPDATE users SET booksIssued = " + "?" + " WHERE username = '" + username+"'";
//                        PreparedStatement pstmt1 = con.prepareStatement(mem);
//                        //pstmt1 = con.prepareStatement(mem);
//                        pstmt1.setObject(1, booksIssued);
//                        pstmt1.executeUpdate();
//                        //stmt.executeUpdate(mem);
//                        ms.close();
//                    } else {
//                        PopUp pop = new PopUp("Inavalid Friend ID!");
//                        pop.setVisible(true);
//                    }
//                    rs.close();
//                } else {
//                    PopUp pop = new PopUp("ISBN doesn't exist!");
//                    pop.setVisible(true);
//                }
//            } catch (SQLException | IOException ex) {
//                Logger.getLogger(libraryfunc.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        } catch (SQLException ex) {
//            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }

    @SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public void returned(String ISBN, String username) {

        try {
            int ID = 0;
            int flag = 0;
            Statement stmt = null;
            Connection con = DriverManager.getConnection(url, user, password);
//            System.out.println("Success");
            stmt = con.createStatement();
            String add = "SELECT * FROM books WHERE ISBN = '" + ISBN + "'";
            ResultSet rs = stmt.executeQuery(add);
            if (rs.next()) {
                boolean isReserved = rs.getBoolean("isReserved");
                
//                System.out.println(isReserved);
                int onShelf = rs.getInt("onShelf");
                byte[] buf = rs.getBytes("copyDetails");
                ObjectInputStream o = new ObjectInputStream(new ByteArrayInputStream(buf));
                ArrayList<BookInfo> copyDetails = (ArrayList<BookInfo>) o.readObject();

                byte[] buf1 = rs.getBytes("reserveList");
                ObjectInputStream o1 = new ObjectInputStream(new ByteArrayInputStream(buf1));
                ArrayList<Integer> reserveList = (ArrayList<Integer>) o1.readObject();
//                Iterator itr1 = copyDetails.iterator();
//                while (itr1.hasNext()) {
//                    BookInfo sb = (BookInfo) itr1.next();
//                    if (sb.getIssuedMembers().size() > 0) {
//                        if (sb.getID() == subID) {
//                            username = sb.getIssuedMembers().get(sb.getIssuedMembers().size() - 1).getIssueMember();
//                        }
//                    }
//                }
//                System.out.println(ID);
                String mem = "SELECT * FROM users WHERE username = '" + username+"'";
                ResultSet ms = stmt.executeQuery(mem);
                if (ms.next()) {
                    double fine = ms.getDouble("fine");
                    int bookLimit = ms.getInt("bookLimit");
//                    byte[] buf2 = ms.getBytes("booksIssued");
//                    ObjectInputStream o2 = new ObjectInputStream(new ByteArrayInputStream(buf2));
//                    ArrayList<UserIssueDetails> booksIssued = (ArrayList<UserIssueDetails>) o2.readObject();
                    ArrayList<UserIssueDetails> booksIssued = ReturnWrapper.uid;
                    Iterator itr = copyDetails.iterator();
                    while (itr.hasNext() && flag == 0) {
                        BookInfo sb = (BookInfo) itr.next();
                        if ( sb.isIsIssued()) {
                            Iterator iter = sb.getIssuedMembers().iterator();
                            while (iter.hasNext()) {
                                BookCopy iss = (BookCopy) iter.next();
                                if (iss.getIssueMember().equals(username)) {
                                    iter.remove();
                                    itr.remove();
                                    flag = 1;
                                    sb.setIsIssued(false);
                                    onShelf++;
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                    String date = sdf.format(Calendar.getInstance().getTime());
                                    iss.setReturnDate(date);
                                    ArrayList<BookCopy> isbk = sb.getIssuedMembers();
                                    isbk.add(iss);
                                    sb.setIssuedMembers(isbk);
                                    copyDetails.add(sb);

                                    Iterator itrr = booksIssued.iterator();
                                    while (itrr.hasNext()) {
                                        UserIssueDetails memb = (UserIssueDetails) itrr.next();
                                        if (memb.getIssuedBook().equals(ISBN) ) {
                                            itrr.remove();
//                                            System.out.println("details removed");
//                                            memb.setReturnDate(date);
//                                            booksIssued.add(memb);
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }

                    // TODO inform reserved members
                    add = "UPDATE books SET reserveList = " + "?"
                            + ", isReserved = " + isReserved
                            + ", copyDetails = " + "?"
                            + ", onShelf = " + onShelf
                            + " WHERE ISBN = '" + ISBN + "'";
                    PreparedStatement pstmt = con.prepareStatement(add);
                    pstmt.setObject(1, reserveList);
                    pstmt.setObject(2, copyDetails);
                    pstmt.executeUpdate();
                    //stmt.executeUpdate(add);

                    mem = "UPDATE users SET booksIssued = " + "?" + " WHERE username = '" + username+"'";
                    pstmt = con.prepareStatement(mem);
                    pstmt.setObject(1, booksIssued);
                    pstmt.executeUpdate();
                    //stmt.executeUpdate(mem);
                    if (flag == 1) {
                        PopUp pop = new PopUp("Book returned!");
                        pop.setVisible(true);
                        if (isReserved == true) {
//                        	System.out.println("reach");
                            libraryfunc.informReservedMembers(ISBN);
                        }
                       
                    } else {
                        PopUp pop = new PopUp("Book ID invalid!");
                        pop.setVisible(true);
                    }
                    ms.close();
                } else {
                    PopUp pop = new PopUp("Book ID invalid!");
                    pop.setVisible(true);
                }

                rs.close();
            } else {
                PopUp pop = new PopUp("ISBN invalid!");
                pop.setVisible(true);
            }

        } catch (SQLException | IOException | ClassNotFoundException ex) {
           
        }

    }

    @SuppressWarnings({ "unchecked", "unused" })
	public void reserve(String ISBN, String username){
        try {
            int flag = 0;
            Statement stmt = null;
            Connection con = DriverManager.getConnection(url, user, password);
//            System.out.println("Success");
            stmt = con.createStatement();
            String add0 = "SELECT * FROM users WHERE username = '" + username + "'";
            ResultSet rs0 = stmt.executeQuery(add0);
            rs0.next();
            byte[] buf0 = rs0.getBytes("booksIssued");
            ObjectInputStream o0 = new ObjectInputStream(new ByteArrayInputStream(buf0));
            ArrayList<UserIssueDetails> bookList = (ArrayList<UserIssueDetails>) o0.readObject();
            for(int i=0;i<bookList.size();i++){
            	if(bookList.get(i).getIssuedBook().equals(ISBN)){
            		PopUp pop = new PopUp("Book already issued by you!");
                    pop.setVisible(true);
                    return;
            	}
            		
            }
            
            String add = "SELECT * FROM books WHERE ISBN = '" + ISBN + "'";
            ResultSet rs = stmt.executeQuery(add);
            rs.next();
            boolean isReserved = rs.getBoolean("isReserved");
            int onShelf=rs.getInt("onShelf");
            if(onShelf>0)
            {
            	PopUp pop = new PopUp("Book is available for issue.Cannot be reserved!");
                pop.setVisible(true);
                return;
            }
            isReserved = true;
//            System.out.println("is reserved set to true");
            byte[] buf = rs.getBytes("reserveList");
            ObjectInputStream o = new ObjectInputStream(new ByteArrayInputStream(buf));
            ArrayList<String> reserveList = (ArrayList<String>) o.readObject();
//            Iterator itr = reserveList.iterator();
//            while (itr.hasNext()) {
//                String sb = (String) itr.next();
//                if (sb.equals(username)) {
//                    flag = 1;
//                    break;
//                }
//            }
//            if (flag == 0) {
            if(reserveList.contains(username)){
            	PopUp pop = new PopUp("Book already reserved under this username!");
                pop.setVisible(true);
                return;
            }
            
            reserveList.add(username);
//            }
            rs.close();
//            System.out.println("isreserved =  "+isReserved);
//            System.out.println("Resrve list of books table updated");
            add = "UPDATE books SET reserveList = " + "?"
                    + ", isReserved = ?" 
                    + " WHERE ISBN = '" + ISBN + "'";
            PreparedStatement pstmt = con.prepareStatement(add);
            pstmt.setObject(1, reserveList);
            pstmt.setBoolean(2, isReserved);
            pstmt.executeUpdate();
//            System.out.println("Table updated");
            //stmt.executeUpdate(add);
            PopUp pop = new PopUp("Book Reserved!");
            pop.setVisible(true);
        } catch (SQLException ex) {
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

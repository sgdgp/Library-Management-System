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
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReturnAndInform {

	@SuppressWarnings("unused")
	private String url1 = "jdbc:mysql://localhost:3306/";
    private static String url = "jdbc:mysql://localhost:3306/lis?useSSL=false";
    private static final String user = "root";
    private static final String password = "qwerty";
	
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
		
		} catch (ClassNotFoundException e) {
			
		}
    }

	
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
//                            libraryfunc.informReservedMembers(ISBN);
                        	ReturnAndInform.informReservedMembers(ISBN);
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

}

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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reserver {
	
	@SuppressWarnings("unused")
	private String url1 = "jdbc:mysql://localhost:3306/";
    private static String url = "jdbc:mysql://localhost:3306/lis?useSSL=false";
    private static final String user = "root";
    private static final String password = "qwerty";

	 @SuppressWarnings({ "unchecked", "unused" })
		public void reserve(String ISBN, String username){
	        try {
	            int flag = 0;
	            Statement stmt = null;
	            Connection con = DriverManager.getConnection(url, user, password);
//	            System.out.println("Success");
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
//	            System.out.println("is reserved set to true");
	            byte[] buf = rs.getBytes("reserveList");
	            ObjectInputStream o = new ObjectInputStream(new ByteArrayInputStream(buf));
	            ArrayList<String> reserveList = (ArrayList<String>) o.readObject();
//	            Iterator itr = reserveList.iterator();
//	            while (itr.hasNext()) {
//	                String sb = (String) itr.next();
//	                if (sb.equals(username)) {
//	                    flag = 1;
//	                    break;
//	                }
//	            }
//	            if (flag == 0) {
	            if(reserveList.contains(username)){
	            	PopUp pop = new PopUp("Book already reserved under this username!");
	                pop.setVisible(true);
	                return;
	            }
	            
	            reserveList.add(username);
//	            }
	            rs.close();
//	            System.out.println("isreserved =  "+isReserved);
//	            System.out.println("Resrve list of books table updated");
	            add = "UPDATE books SET reserveList = " + "?"
	                    + ", isReserved = ?" 
	                    + " WHERE ISBN = '" + ISBN + "'";
	            PreparedStatement pstmt = con.prepareStatement(add);
	            pstmt.setObject(1, reserveList);
	            pstmt.setBoolean(2, isReserved);
	            pstmt.executeUpdate();
//	            System.out.println("Table updated");
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

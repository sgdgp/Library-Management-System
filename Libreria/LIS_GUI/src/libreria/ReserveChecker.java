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

public class ReserveChecker {
	
	@SuppressWarnings("unused")
	private String url1 = "jdbc:mysql://localhost:3306/";
    private static String url = "jdbc:mysql://localhost:3306/lis?useSSL=false";
    private static final String user = "root";
    private static final String password = "qwerty";

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

        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        }
    }

}

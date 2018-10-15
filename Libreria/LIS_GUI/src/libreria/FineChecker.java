package libreria;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class FineChecker {
	
	@SuppressWarnings("unused")
	private String url1 = "jdbc:mysql://localhost:3306/";
    private static String url = "jdbc:mysql://localhost:3306/lis?useSSL=false";
    private static final String user = "root";
    private static final String password = "qwerty";
	
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

}

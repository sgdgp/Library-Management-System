package libreria;

import java.sql.*;
import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;



//import com.sun.org.apache.xml.internal.security.Init;

/**
 *
 * @author sayan
 */
public class Initialize {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    @SuppressWarnings("unused")
	private static String url = "jdbc:mysql://127.0.0.1:3306/";
    private static final String user = "root";
    private static final String password = "qwerty";
    Statement stmt = null;
   	String query = null;
   	boolean dbExist = false;
	@SuppressWarnings("resource")
	public boolean init() {
//    	System.out.println("Init reached");
    	try {
        	try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("Exception 1");
				e.printStackTrace();
			}
        	Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/?useSSL=false", user, password);
        	
//        	String sql1 = "DROP DATABASE lis";
//        	stmt = con.createStatement();
//            stmt.executeUpdate(sql1);
            
            
        	ResultSet dbSet = con.getMetaData().getCatalogs();
        	while (dbSet.next()) {
        	  	String databaseName = dbSet.getString(1);
        		if(databaseName.equals("lis")){
        			dbExist = true;
        			break;
        		}
        	}
        	dbSet.close();
        	
//        	System.out.println("1");
            if(dbExist){
//        		System.out.println("Database exists");
        		con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lis?useSSL=false", user, password);
        		
        		Connection con1 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lis?useSSL=false","root","qwerty");
				ResultSet r1 = con1.createStatement().executeQuery("Select * from books where ISBN = 'b'");
				r1.next();
//				System.out.println("is reserved after initialize value :"+r1.getBoolean("isReserved"));
        		Statement s = con.createStatement();
        		
        		ResultSet r  = s.executeQuery("Select * from clerks ");
        		while(r.next()){
//        			System.out.println(r.getString("username"));
        		}
//        		System.out.println("exit if");
            }
        	else{
//        		System.out.println("reached else part");
        		query = "CREATE DATABASE lis";
        		stmt = con.createStatement();
        		stmt.executeUpdate(query);
        		
        		con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lis?useSSL=false", user, password);
        		
        		String sql = "CREATE TABLE books "
                        + "(ISBN VARCHAR(20), "
                        + " name VARCHAR(255), "
                        + " author VARCHAR(255), "
                        + " publisher VARCHAR(255), "
                        + " yearOfPurchase VARCHAR(255), "
                        + " rackNo VARCHAR(255), "
                        + " onShelf INTEGER, "
                        + " countID INTEGER, "
                        + " price VARCHAR(255), "
                        + " isReserved BOOLEAN, "
                        + " copyDetails LONGBLOB, "
                        + " reserveList LONGBLOB, "
                        + " delNotif BOOLEAN ,"
        				+ " issueStats LONGBLOB) ";
        		stmt = con.createStatement();
                stmt.executeUpdate(sql);
//                System.out.println("books table created");
                sql = "CREATE TABLE users "
                        + " (username VARCHAR(255), "
                        + " name VARCHAR(255), "
                        + " phoneNo VARCHAR(20), "
                        + " address VARCHAR(255), "
                        + " type VARCHAR(255), "
                        + " fine DOUBLE, "
                        + " bookLimit INTEGER, "
                        + " duration INTEGER, "
                        + " booksIssued LONGBLOB, "
                        + " password VARCHAR(12), "
                        + " overNotif BOOLEAN)";
                stmt = con.createStatement();
                stmt.executeUpdate(sql);
//                System.out.println("users table created");
                
                sql = "CREATE TABLE clerks "
                        + "(username VARCHAR(255), "
                        + " name VARCHAR(255), "
                        + " phoneNo VARCHAR(20), "
                        + " address VARCHAR(255), "
                        + " password VARCHAR(12))";
                stmt = con.createStatement();
                stmt.executeUpdate(sql);
//                System.out.println("clerks table created");
                
                sql = "create table rbList (username VARCHAR(255), ISBN VARCHAR(255))";
                stmt = con.createStatement();
                stmt.executeUpdate(sql);
                
                sql = "CREATE TABLE librarian "
                		+ " (username VARCHAR(255), "
                        + " name VARCHAR(255), "
                        + " phoneNo VARCHAR(20), "
                        + " address VARCHAR(255), "
                        + " password VARCHAR(12))";
                stmt = con.createStatement();
                stmt.executeUpdate(sql);
//                System.out.println("librarian table created");
                
                sql = "INSERT INTO librarian " +
                        "VALUES('librarian', 'Sumit','1234567890','abcd', 'password')";
                stmt = con.createStatement();
                stmt.executeUpdate(sql);
        	}
        	
//            System.out.println(query);
            
//            System.out.println("Success");	
            Timer t = new Timer();
            t.scheduleAtFixedRate(
                    new TimerTask() {
                        public void run() {
                            try {
//                                libraryfunc ls = new libraryfunc();
                                FineChecker F = new FineChecker();
                                Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lis?useSSL=false", user, password);
//                                System.out.println("Success");
                                Statement stmt = con.createStatement();
                                String add = "SELECT * FROM users ";
                                ResultSet rs = stmt.executeQuery(add);
                                while(rs.next()){
                                	String n = rs.getString("username");
//                                	ls.fine(n);
                                	F.fine(n);
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(Initialize.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }, 0, 3600000);
            Timer t1 = new Timer();
            t1.scheduleAtFixedRate(
                    new TimerTask() {
                        public void run() {
                            try {
//                                libraryfunc ls = new libraryfunc();
                                ReserveChecker R = new ReserveChecker();
                                Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lis?useSSL=false", user, password);
//                                System.out.println("Success");
                                Statement stmt = con.createStatement();
                                String add = "SELECT * FROM books";
                                ResultSet rs = stmt.executeQuery(add);
                                while (rs.next()) {
                                    String isbn = rs.getString("ISBN");
//                                    ls.checkReserve(isbn);
                                    R.checkReserve(isbn);
                                    
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(Initialize.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (ParseException ex) {
                                Logger.getLogger(Initialize.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }, 0, 3600000);
    	}catch(SQLException ex){
//    		ex.printStackTrace();
    		return false;
    	}
		return true;
    }
}
            
           
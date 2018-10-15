package libreria;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ImageIcon;

public class LibraryUser extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LibraryUser frame = new LibraryUser();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	String username;
	public LibraryUser(String name) {
		setResizable(false);
		username = name;
		setTitle("Library User Actions");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
				MainWindow x = new MainWindow();
				x.setVisible(true);
				x.setLocationRelativeTo(null);
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 471, 330);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 182, 193));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLibraryUserActions = new JLabel("Library User Actions");
		lblLibraryUserActions.setForeground(new Color(75, 0, 130));
		lblLibraryUserActions.setBackground(new Color(240, 240, 240));
		lblLibraryUserActions.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 24));
		lblLibraryUserActions.setBounds(80, 53, 288, 25);
		contentPane.add(lblLibraryUserActions);
		
		JButton btnIssueBook = new JButton("Issue Book");
		btnIssueBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					setVisible(false);
					OptionSelect frame = new OptionSelect(username);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					LastScreen.screen2 = frame;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnIssueBook.setBackground(new Color(0, 153, 102));
		btnIssueBook.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 12));
		btnIssueBook.setBounds(124, 123, 120, 23);
		contentPane.add(btnIssueBook);
		
		JButton btnReserveBook = new JButton("Reserve Book");
		btnReserveBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					setVisible(false);
					OptionSelect frame = new OptionSelect(username);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					LastScreen.screen2 = frame;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnReserveBook.setBackground(new Color(0, 153, 102));
		btnReserveBook.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 12));
		btnReserveBook.setBounds(124, 157, 120, 23);
		contentPane.add(btnReserveBook);
		
		JButton btnReturnBook = new JButton("Return Book");
		btnReturnBook.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent arg0) {
				try {
					setVisible(false);
					ReturnWrapper retWrap = new ReturnWrapper();
//					retWrap.username = username;
					ReturnWrapper.username = username;
				
					Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lis?useSSL=false", "root", "qwerty");
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("Select * from users where username='"+username+"'");
					rs.next();
					ReturnWrapper.uid = (ArrayList<UserIssueDetails>) (new ObjectInputStream(new ByteArrayInputStream(rs.getBytes("booksIssued")))).readObject() ;
					try{
						Connection con1 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lis?useSSL=false","root","qwerty");
						ResultSet r = con1.createStatement().executeQuery("Select * from books where ISBN = 'b'");
						r.next();
						
					}catch(Exception e){
						e.printStackTrace();
					}
					ReturnWindow d1=new ReturnWindow(username);
					d1.setVisible(true);
					d1.setLocationRelativeTo(null);
					LastScreen.screen2 = d1;
					
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnReturnBook.setBackground(new Color(0, 153, 102));
		btnReturnBook.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 12));
		btnReturnBook.setBounds(124, 191, 120, 23);
		contentPane.add(btnReturnBook);
		
		JButton btnSearchBook = new JButton("Search Book");
		btnSearchBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				try {
					SearchBook frame = new SearchBook(username);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					LastScreen.screen2 = frame;
				} catch (Exception ex) {
//					e.printStackTrace();
				}
			}
		});
		btnSearchBook.setBackground(new Color(0, 153, 102));
		btnSearchBook.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 12));
		btnSearchBook.setBounds(124, 89, 120, 23);
		contentPane.add(btnSearchBook);
		
		JButton btnPayFine = new JButton("Pay Fine");
		btnPayFine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double fine = 0;
//				setVisible(false);
				Connection con;
				try {
					con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lis?useSSL=false", "root", "qwerty");
				
				ResultSet r = con.createStatement().executeQuery("Select * from users where username='"+username+"'");
				r.next();
				fine=r.getDouble("fine");
				if(r.getDouble("fine")==0.0){
					JOptionPane.showMessageDialog(null, "No fine is charged yet");
					return;
				}
				byte[] buf0 = r.getBytes("booksIssued");
	            ObjectInputStream o0 = new ObjectInputStream(new ByteArrayInputStream(buf0));
	            ArrayList<UserIssueDetails> bookList = (ArrayList<UserIssueDetails>) o0.readObject();
	            for(int i=0;i<bookList.size();i++){
	            	
	            		PopUp pop = new PopUp("First you need to return all Books to pay fine!");
	                    pop.setVisible(true);
	                    return;
	            	
	            		
	            }
				Statement st = con.createStatement();

				String add = "UPDATE users SET fine = " +0
	                    + " WHERE username = '" +username+"'";
	            PreparedStatement pstmt = con.prepareStatement(add);
	            pstmt.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PopUp frame=new PopUp("Fine Paid of Rs."+fine);
				frame.setVisible(true);
//				setVisible(true);
			}
		});
		btnPayFine.setBackground(new Color(0, 153, 102));
		btnPayFine.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 12));
		btnPayFine.setBounds(124, 225, 120, 23);
		contentPane.add(btnPayFine);
		
		JButton btnNotifications = new JButton("Notifications");
		btnNotifications.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				UserNotificationOption frame = new UserNotificationOption(username);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);	
				LastScreen.screen2 = frame;
			}
		});
		btnNotifications.setBackground(new Color(0, 153, 102));
		btnNotifications.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 12));
		btnNotifications.setBounds(325, 257, 120, 23);
		contentPane.add(btnNotifications);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setIcon(new ImageIcon(LibraryUser.class.getResource("/libreria/logout.png")));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				MainWindow frame=new MainWindow();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
			}
		});
		btnLogout.setBackground(new Color(0, 153, 102));
		btnLogout.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		btnLogout.setBounds(280, 11, 144, 33);
		contentPane.add(btnLogout);
		
		JLabel lblNameOfUser = new JLabel("Name:"+name);
		lblNameOfUser.setForeground(new Color(51, 51, 255));
		lblNameOfUser.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 12));
		lblNameOfUser.setBounds(24, 11, 112, 31);
		contentPane.add(lblNameOfUser);
	}
}

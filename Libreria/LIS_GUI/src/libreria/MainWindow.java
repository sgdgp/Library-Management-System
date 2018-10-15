package libreria;

import java.awt.EventQueue;
import javax.swing.UIManager.*;

import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.WindowAdapter;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JPasswordField textFieldPassword;
	private JTextField textFieldUsername;
	private JLabel lblUsertype;
	private JComboBox<String> comboBoxUserType;

	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				splashTimer();
				try {
				    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				        if ("Nimbus".equals(info.getName())) {
				            UIManager.setLookAndFeel(info.getClassName());
				            break;
				        }
				    }
				} catch (Exception e) {
//				   System.out.println("Nimbus not available");
				}
				Initialize I = new Initialize();
				boolean flagInit = I.init();
//				System.out.println(flagInit);
//				if (flagInit)
//					JOptionPane.showMessageDialog(null, "Init done");
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
					frame.pack();
				    frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/*spalsh time setter*/
	
	public static void splashTimer(){

		try {
		    Thread.sleep(1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	}
	
	/*constructor for the main window */
	
	public MainWindow() {
		setResizable(false);
		setMinimumSize(new Dimension(500, 340));

		setTitle("LIBRERIA v1.0");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				String ObjButtons[] = {"Yes","No"};
		        int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","Libreria",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
		        if(PromptResult==JOptionPane.YES_OPTION)
		        {
		            System.exit(0);
		        }
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		setBounds(100, 100, 500, 340);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(152, 251, 152));
		contentPane.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.RED, Color.ORANGE, Color.DARK_GRAY, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("WELCOME");
		lblNewLabel.setForeground(new Color(51, 51, 102));
		lblNewLabel.setFont(new Font("Calisto MT", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel.setBounds(182, 11, 161, 31);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblPassword.setBounds(49, 131, 103, 25);
		contentPane.add(lblPassword);
		
		textFieldPassword = new JPasswordField();
		textFieldPassword.setBounds(170, 133, 241, 30);
		contentPane.add(textFieldPassword);
		textFieldPassword.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username :");
		lblUsername.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblUsername.setBounds(49, 97, 103, 25);
		contentPane.add(lblUsername);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setColumns(10);
		textFieldUsername.setBounds(170, 99, 241, 30);
		contentPane.add(textFieldUsername);
		
		lblUsertype = new JLabel("Type :");
		lblUsertype.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblUsertype.setBounds(49, 173, 103, 25);
		contentPane.add(lblUsertype);
		
		comboBoxUserType = new JComboBox<String>();
		comboBoxUserType.setBounds(170, 173, 241, 28);
		contentPane.add(comboBoxUserType);
		
		JButton btnProceed = new JButton("Proceed");
		btnProceed.setIcon(new ImageIcon(MainWindow.class.getResource("/libreria/proceed.png")));
		btnProceed.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		btnProceed.setBackground(new Color(102, 102, 153));
		btnProceed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = textFieldUsername.getText().trim();
				String password = String.valueOf(textFieldPassword.getPassword()).trim();
				
				String type = comboBoxUserType.getSelectedItem().toString().trim();
				
				boolean checkEmpty = true;//checkEmpty(username,password,type);
				if(type.equals("---Choose Type---"))
					checkEmpty = false;
					
				if(checkEmpty){
					Connection con;
					try {
						con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lis?useSSL=false", "root", "qwerty");
					
					Statement stmt = null;
					String query = null;
					switch(type){
					case "Librarian":
						query = "SELECT * FROM librarian";
						//query  = "SELECT password FROM librarian WHERE username="+username;
						stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery(query);
						int flag=0;
						while(rs.next()){
							
							if(rs.getString("password").equals(password) && rs.getString("username").equals(username)){
								flag = 1;
								try {
									LibrarianHomePage frame = new LibrarianHomePage();
									frame.setVisible(true);
									frame.setLocationRelativeTo(null);
									LastScreen.screen1 = frame;
									dispose();
									break;
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						if(flag==0)
							JOptionPane.showMessageDialog(null, "Wrong username and password combination");
						break;
					case "Clerk" :
						query = "SELECT * FROM clerks";
						//query  = "SELECT password FROM librarian WHERE username="+username;
						stmt = con.createStatement();
					    rs = stmt.executeQuery(query);
						flag=0;
					    while(rs.next()){
							
							if(rs.getString("password").equals(password) && rs.getString("username").equals(username)){
								flag=1;
								try {
									LibraryClerkScreen frame = new LibraryClerkScreen();
									frame.setVisible(true);
									frame.setLocationRelativeTo(null);
									LastScreen.screen1 = frame;
									dispose();
									break;
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						if(flag==0)
							JOptionPane.showMessageDialog(null, "Wrong username and password combination");
						break;
					case "User" :
						query = "SELECT * FROM users";
						stmt = con.createStatement();
					    rs = stmt.executeQuery(query);
					    flag=0;
					    while(rs.next()){
							
							if(rs.getString("password").equals(password) && rs.getString("username").equals(username)){
								flag=1;
								try {
									LibraryUser frame = new LibraryUser(username);
									frame.setVisible(true);
									frame.setLocationRelativeTo(null);
									LastScreen.screen1 = frame;
									dispose();
									
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						if(flag==0)
							JOptionPane.showMessageDialog(null, "Wrong username and password combination");
						break;
					}
				
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
				else{
					JOptionPane.showMessageDialog(null, "Choose a proper type");
				}
			}
		});
		btnProceed.setBounds(108, 257, 117, 30);
		contentPane.add(btnProceed);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.setIcon(new ImageIcon(MainWindow.class.getResource("/libreria/exit.png")));
		btnQuit.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		btnQuit.setBackground(new Color(102, 102, 153));
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ObjButtons[] = {"Yes","No"};
		        int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","Libreria",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
		        if(PromptResult==JOptionPane.YES_OPTION)
		        {
		            System.exit(0);
		        }
			}
		});
		btnQuit.setBounds(270, 257, 117, 30);
		contentPane.add(btnQuit);
		
		JLabel lblLibreriaLibrary = new JLabel("LIBRERIA ---- LIBRARY INFORMATION SYSTEM");
		lblLibreriaLibrary.setForeground(new Color(204, 51, 153));
		lblLibreriaLibrary.setFont(new Font("Candara", Font.BOLD, 18));
		lblLibreriaLibrary.setBounds(49, 54, 374, 32);
		contentPane.add(lblLibreriaLibrary);
		comboBoxUserType.addItem("---Choose Type---");
		comboBoxUserType.addItem("Librarian");
		comboBoxUserType.addItem("Clerk");
		comboBoxUserType.addItem("User");
	}
	
	
}

package libreria;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class CreateClerk extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldName;
	private JTextField textFieldUsername;
	private JTextField textFieldAddress;
	private JTextField textFieldPhone;
	private JPasswordField textFieldPassword;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					CreateClerk frame = new CreateClerk();
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
	public CreateClerk() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
				LastScreen.screen1.setVisible(true);
			}
		});
		setTitle("Create New Clerk");
		setBackground(new Color(138, 43, 226));
		setBounds(100, 100, 494, 324);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 228, 196));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setForeground(new Color(139, 0, 139));
		lblName.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblName.setBounds(78, 69, 91, 14);
		contentPane.add(lblName);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(new Color(139, 0, 139));
		lblUsername.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblUsername.setBounds(78, 102, 102, 14);
		contentPane.add(lblUsername);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setForeground(new Color(139, 0, 139));
		lblAddress.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblAddress.setBounds(78, 139, 101, 14);
		contentPane.add(lblAddress);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setForeground(new Color(139, 0, 139));
		lblPhoneNumber.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblPhoneNumber.setBounds(78, 172, 116, 14);
		contentPane.add(lblPhoneNumber);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(new Color(139, 0, 139));
		lblPassword.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblPassword.setBounds(78, 204, 100, 14);
		contentPane.add(lblPassword);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(203, 61, 209, 30);
		contentPane.add(textFieldName);
		textFieldName.setColumns(10);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(203, 95, 209, 30);
		contentPane.add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		textFieldAddress = new JTextField();
		textFieldAddress.setBounds(203, 132, 209, 30);
		contentPane.add(textFieldAddress);
		textFieldAddress.setColumns(10);
		
		textFieldPhone = new JTextField();
		textFieldPhone.setBounds(204, 165, 208, 30);
		contentPane.add(textFieldPhone);
		textFieldPhone.setColumns(10);
		
		textFieldPassword = new JPasswordField();
		textFieldPassword.setBounds(203, 198, 209, 30);
		contentPane.add(textFieldPassword);
		textFieldPassword.setColumns(10);
		
		JButton btnCreateUser = new JButton("Create Clerk");
		btnCreateUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = textFieldUsername.getText().trim();
				String password = String.valueOf(textFieldPassword.getPassword()).trim();
				String name = textFieldName.getText().trim();
				String address = textFieldAddress.getText().trim();
				String phone = textFieldPhone.getText().trim();
				boolean check = checkEntry(username,password,name,address,phone);
				if(check){
					addtoDatabase(username,name,address,phone,password);
					dispose();
					PopUp frame = new PopUp("Clerk created successfully");
					frame.setVisible(true);
					LastScreen.screen1.setVisible(true);

				}
				
				
				
			}
		});
		btnCreateUser.setBackground(new Color(255, 105, 180));
		btnCreateUser.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		btnCreateUser.setBounds(305, 251, 124, 23);
		contentPane.add(btnCreateUser);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				LastScreen.screen1.setVisible(true);
			}
		});
		btnBack.setBackground(new Color(255, 105, 180));
		btnBack.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		btnBack.setBounds(21, 251, 89, 23);
		contentPane.add(btnBack);
		
		JLabel lblCreateNewUser = new JLabel("Create New Clerk");
		lblCreateNewUser.setForeground(new Color(0, 0, 205));
		lblCreateNewUser.setFont(new Font("Segoe UI Semibold", Font.BOLD | Font.ITALIC, 18));
		lblCreateNewUser.setBounds(48, 11, 209, 34);
		contentPane.add(lblCreateNewUser);
	}
	public void addtoDatabase(String username,String name,String address,String phone,String password){
		Connection con;
		try {
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lis?useSSL=false", "root", "qwerty");
			String sql = "INSERT INTO clerks (username,name,phoneNo,address,password)" +
                    " VALUES(?,?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setString(1, username);
			stmt.setString(2, name);
			stmt.setString(3, phone);
			stmt.setString(4, address);
			stmt.setString(5, password);
			
//			Statement stmt = con.createStatement();
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			
//			e.printStackTrace();
		}
		
		
		
	}
	
	public boolean checkEntry(String username,String password,String name,String address,String phone){
		if(username.equals("")|| password.equals("")||name.equals("")||address.equals("")||phone.equals("")){
			JOptionPane.showMessageDialog(null,"Blank entries and invalid entries given!!!");
			return false;
		}
		if(!checkPhone(phone)){
			JOptionPane.showMessageDialog(null,"Phone number given as string!!!");
			return false;
		}
		
//		if(!checkPassword(password)){
//			JOptionPane.showMessageDialog(null,"Password not meeting requirements!!!");
//			return false;
//		}
		
		if(!checkUsername(username)){
			JOptionPane.showMessageDialog(null,"Username not unique!!!");
			return false;
		}
		return true;
	}
	public static boolean checkUsername(String x){
		Connection con;
		try {
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lis?useSSL=false","root","qwerty");
			ResultSet r = con.createStatement().executeQuery("Select * from users");
			while(r.next()){
				String a  = r.getString("username");
				if(a.equals(x))
					return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	public static boolean checkPhone(String x){
		try { 
	        Long.parseLong(x); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
		return true;
	}
//	public static boolean checkPassword(String x){
//		boolean a =false;
//		boolean b = false;
//		boolean c =false;
//		
//	}

	
	
}

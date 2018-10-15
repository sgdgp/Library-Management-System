package libreria;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class CreateUser extends JFrame {

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
//					CreateUser frame = new CreateUser();
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CreateUser() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
				LastScreen.screen1.setVisible(true);
			}
		});
		setTitle("Create New User");
		setBackground(new Color(138, 43, 226));
		setBounds(100, 100, 505, 365);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 228, 196));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setForeground(new Color(139, 0, 139));
		lblName.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblName.setBounds(78, 66, 91, 14);
		contentPane.add(lblName);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(new Color(139, 0, 139));
		lblUsername.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblUsername.setBounds(78, 97, 102, 14);
		contentPane.add(lblUsername);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setForeground(new Color(139, 0, 139));
		lblAddress.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblAddress.setBounds(78, 136, 101, 14);
		contentPane.add(lblAddress);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setForeground(new Color(139, 0, 139));
		lblPhoneNumber.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblPhoneNumber.setBounds(78, 172, 116, 14);
		contentPane.add(lblPhoneNumber);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(new Color(139, 0, 139));
		lblPassword.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblPassword.setBounds(78, 208, 100, 14);
		contentPane.add(lblPassword);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBackground(new Color(173, 216, 230));
		comboBox.setFont(new Font("Verdana", Font.BOLD, 12));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"UG Student", "PG Student", "Research Scholar", "Faculty"}));
		comboBox.setBounds(233, 247, 169, 28);
		contentPane.add(comboBox);
		
		JLabel lblChooseUserType = new JLabel("Choose User Type");
		lblChooseUserType.setForeground(new Color(139, 0, 139));
		lblChooseUserType.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblChooseUserType.setBounds(92, 254, 159, 14);
		contentPane.add(lblChooseUserType);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(203, 60, 209, 30);
		contentPane.add(textFieldName);
		textFieldName.setColumns(10);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(203, 95, 209, 30);
		contentPane.add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		textFieldAddress = new JTextField();
		textFieldAddress.setBounds(203, 129, 209, 30);
		contentPane.add(textFieldAddress);
		textFieldAddress.setColumns(10);
		
		textFieldPhone = new JTextField();
		textFieldPhone.setBounds(204, 165, 208, 30);
		contentPane.add(textFieldPhone);
		textFieldPhone.setColumns(10);
		
		textFieldPassword = new JPasswordField();
		textFieldPassword.setBounds(203, 200, 209, 30);
		contentPane.add(textFieldPassword);
		textFieldPassword.setColumns(10);
		
		JButton btnCreateUser = new JButton("Create User");
		btnCreateUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String username = textFieldUsername.getText().trim();
				String password = String.valueOf(textFieldPassword.getPassword()).trim();
				String name = textFieldName.getText().trim();
				String address = textFieldAddress.getText().trim();
				String phone = textFieldPhone.getText().trim();
				String type = comboBox.getSelectedItem().toString().trim();
				boolean check = checkEntry(username,password,name,address,phone,type);
				if(check){
					addtoDatabase(username,name,address,phone,password,type);
					dispose();
					PopUp frame = new PopUp("User created successfully");
					frame.setVisible(true);
					LastScreen.screen1.setVisible(true);
				}
			}
		});
		btnCreateUser.setBackground(new Color(255, 105, 180));
		btnCreateUser.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		btnCreateUser.setBounds(319, 302, 124, 23);
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
		btnBack.setBounds(35, 302, 89, 23);
		contentPane.add(btnBack);
		
		JLabel lblCreateNewUser = new JLabel("Create New User");
		lblCreateNewUser.setForeground(new Color(0, 0, 205));
		lblCreateNewUser.setFont(new Font("Segoe UI Semibold", Font.BOLD | Font.ITALIC, 18));
		lblCreateNewUser.setBounds(48, 11, 209, 34);
		contentPane.add(lblCreateNewUser);
	}
	
	public void addtoDatabase(String username,String name,String address,String phone,String password,String type){
		Connection con;
		try {
			
			int bl=0;
			int d =0;
			ArrayList<UserIssueDetails> rList = new ArrayList<UserIssueDetails>();
			
			switch(type){
			case "UG Student":
				bl=2;
				d = 1;
				break;
			case "PG Student":
				bl = 4;
				d = 1;
				break;
			case "Research Scholar" :
				bl = 6;
				d = 3;
				break;
			case "Faculty":
				bl = 10;
				d = 6;
				break;
			
			}
			
			//SQL QUERY
			
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lis?useSSL=false", "root", "qwerty");
			String sql = "INSERT INTO users (username,name,phoneNo,address,type,fine,bookLimit,duration,booksIssued,password,overNotif)" +
                    " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, name);
			stmt.setString(3, phone);
			stmt.setString(4, address);
			stmt.setString(5, type);
			stmt.setDouble(6, 0);
			
			stmt.setInt(7,bl);
			stmt.setInt(8, d);
			 
			stmt.setObject(9, rList);
			stmt.setString(10,password);
			stmt.setBoolean(11, false);
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			
//			e.printStackTrace();
		}
		
		
		
	}
	
	public boolean checkEntry(String username,String password,String name,String address,String phone,String type){
		if(username.equals("")|| password.equals("")||name.equals("")||address.equals("")||phone.equals("")||type.equals("---Choose Type---")){
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
		
		if(!checkUsername(username,type)){
			JOptionPane.showMessageDialog(null,"Username not unique!!!");
			return false;
		}
		return true;
	}
	public static boolean checkUsername(String x,String y){
		Connection con;
		try {
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lis?useSSL=false","root","qwerty");
			ResultSet r = con.createStatement().executeQuery("Select * from users");
			while(r.next()){
				String a  = r.getString("username");
				String t = r.getString("type");
				if(a.equals(x) && t.equals(y))
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

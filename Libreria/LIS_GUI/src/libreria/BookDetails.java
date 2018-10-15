package libreria;

import java.sql.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ImageIcon;

public class BookDetails extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldName;
	private JTextField textFieldAuthor;
	private JTextField textFieldISBN;
	private JTextField textFieldCopies;
	private JTextField textFieldCopiesIssued;
	private JTextField textFieldRackNo;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					BookDetails frame = new BookDetails();
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
	String username,ISBN;
	public BookDetails(String uname) {
		setResizable(false);
		username = uname;
		
		setTitle("Book Details");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
				LastScreen.screen2.setVisible(true);
			}
		});
		setBounds(100, 100, 538, 401);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(224, 255, 255));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblName.setBounds(40, 61, 46, 14);
		contentPane.add(lblName);
		
		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblAuthor.setBounds(40, 87, 46, 14);
		contentPane.add(lblAuthor);
		
		JLabel lblIsbnNumber = new JLabel("ISBN Number");
		lblIsbnNumber.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblIsbnNumber.setBounds(40, 112, 93, 14);
		contentPane.add(lblIsbnNumber);
		
		textFieldName = new JTextField();
		textFieldName.setEditable(false);
		textFieldName.setBounds(243, 58, 222, 22);
		contentPane.add(textFieldName);
		textFieldName.setColumns(10);
		
		textFieldAuthor = new JTextField();
		textFieldAuthor.setEditable(false);
		textFieldAuthor.setBounds(243, 84, 222, 22);
		contentPane.add(textFieldAuthor);
		textFieldAuthor.setColumns(10);
		
		textFieldISBN = new JTextField();
		textFieldISBN.setEditable(false);
		textFieldISBN.setBounds(243, 109, 222, 22);
		contentPane.add(textFieldISBN);
		textFieldISBN.setColumns(10);
		
		JLabel lblBookDetails = new JLabel("Book Details");
		lblBookDetails.setForeground(new Color(72, 61, 139));
		lblBookDetails.setFont(new Font("Segoe UI Semilight", Font.BOLD | Font.ITALIC, 18));
		lblBookDetails.setBounds(40, 11, 169, 31);
		contentPane.add(lblBookDetails);
		
		JButton btnBack = new JButton("Back");
		btnBack.setIcon(new ImageIcon(BookDetails.class.getResource("/libreria/back.png")));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				LastScreen.screen2.setVisible(true);
			}
		});
		btnBack.setBackground(new Color(176, 224, 230));
		btnBack.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		btnBack.setBounds(10, 328, 89, 30);
		contentPane.add(btnBack);
		
		JLabel lblNumberOfCopies = new JLabel("Number of Copies");
		lblNumberOfCopies.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblNumberOfCopies.setBounds(40, 137, 128, 14);
		contentPane.add(lblNumberOfCopies);
		
		JLabel lblNumberOfCopies_1 = new JLabel("Number of Copies available");
		lblNumberOfCopies_1.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblNumberOfCopies_1.setBounds(40, 162, 169, 14);
		contentPane.add(lblNumberOfCopies_1);
		
		JLabel lblRackNumber = new JLabel("Rack Number");
		lblRackNumber.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblRackNumber.setBounds(40, 187, 118, 14);
		contentPane.add(lblRackNumber);
		
		JButton btnIssueBook = new JButton("Issue Book");
		btnIssueBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				libraryfunc l = new libraryfunc();
//				l.issue(ISBN, username);
				
//				System.out.println("ISBN fetched = "+ISBN);
				
				ISBN = textFieldISBN.getText().trim();
				Issue I = new Issue();
				I.issue(ISBN, username);
				
//				l.returned(ISBN, "i");
			}
			
		});
		btnIssueBook.setBackground(new Color(176, 224, 230));
		btnIssueBook.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		btnIssueBook.setBounds(186, 328, 118, 30);
		contentPane.add(btnIssueBook);
		
		JButton btnReturnBook = new JButton("Reserve Book");
		btnReturnBook.setBackground(new Color(176, 224, 230));
		btnReturnBook.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		btnReturnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				libraryfunc l = new libraryfunc();
//				l.reserve(ISBN, username);
				ISBN = textFieldISBN.getText().trim();
				Reserver R = new Reserver();
				R.reserve(ISBN, username);
			}
		});
		btnReturnBook.setBounds(339, 328, 144, 30);
		contentPane.add(btnReturnBook);
		
		textFieldCopies = new JTextField();
		textFieldCopies.setEditable(false);
		textFieldCopies.setBounds(243, 134, 222, 22);
		contentPane.add(textFieldCopies);
		textFieldCopies.setColumns(10);
		
		textFieldCopiesIssued = new JTextField();
		textFieldCopiesIssued.setEditable(false);
		textFieldCopiesIssued.setBounds(243, 159, 222, 22);
		contentPane.add(textFieldCopiesIssued);
		textFieldCopiesIssued.setColumns(10);
		
		textFieldRackNo = new JTextField();
		textFieldRackNo.setEditable(false);
		textFieldRackNo.setBounds(243, 187, 222, 22);
		contentPane.add(textFieldRackNo);
		textFieldRackNo.setColumns(10);
		
		JButton btnBackToUser = new JButton("Back to User Home Screen");
		btnBackToUser.setIcon(new ImageIcon(BookDetails.class.getResource("/libreria/home.png")));
		btnBackToUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				LastScreen.screen1.setVisible(true);
			}
		});
		btnBackToUser.setBounds(10, 291, 222, 30);
		contentPane.add(btnBackToUser);
	}
	
	public void showParams(String name,String ISBN){
//		this.name = name;
		this.ISBN = ISBN;
		textFieldName.setText(name);
//		textFieldAuthor.setText(author);
		textFieldISBN.setText(ISBN);
		
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lis?useSSL=false", "root", "qwerty");
			
			Statement st = con.createStatement();
			String query = null;
			if(ISBN.compareTo("")!=0)
				query = "Select * from books where ISBN='"+ISBN+"'";
			else
				query = "Select * from books where name='"+name+"'";
			ResultSet r = st.executeQuery(query);
			while(r.next()){
				textFieldName.setText(r.getString("name"));
				textFieldAuthor.setText(r.getString("author"));
				textFieldISBN.setText(r.getString("ISBN"));
				textFieldCopies.setText(r.getString("countID"));
//				textFieldCopiesIssued.setText(r.getString("copyIssued"));
				textFieldRackNo.setText(r.getString("rackNo"));
				textFieldCopiesIssued.setText(r.getString("onShelf"));
				
				ISBN = textFieldISBN.getText().trim();
//				System.out.println("ISBN set");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

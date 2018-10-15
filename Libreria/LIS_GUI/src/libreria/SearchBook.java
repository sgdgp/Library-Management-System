package libreria;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class SearchBook extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldISBN;
	private JTextField textFieldName;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SearchBook frame = new SearchBook();
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
	public SearchBook(String uname) {
		setResizable(false);

		username = uname;
		setTitle("Search Book");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
				LastScreen.screen1.setVisible(true);
			}
		});
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(233, 150, 122));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = textFieldName.getText().trim();
//				String author = textFieldAuthor.getText().trim();
				String ISBN = textFieldISBN.getText().trim();
				
				try {
					BookDetails frame = new BookDetails(username);
					Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lis?useSSL=false", "root", "qwerty");
					Statement st = con.createStatement();
					String query = "Select count(*) from books where ISBN='"+ISBN+"' or name='"+name+"'";
					ResultSet r = st.executeQuery(query);
					r.next();
					if(r.getInt(1)!=0){
						setVisible(false);
						frame.setLocationRelativeTo(null);
						frame.setVisible(true);
						
						frame.showParams(name,ISBN);
					}
					else{
						JOptionPane.showMessageDialog(null, "No such book present");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		btnSearch.setBackground(new Color(119, 136, 153));
		btnSearch.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		btnSearch.setBounds(303, 205, 89, 23);
		contentPane.add(btnSearch);
		
		textFieldISBN = new JTextField();
		textFieldISBN.setBounds(171, 73, 231, 30);
		contentPane.add(textFieldISBN);
		textFieldISBN.setColumns(10);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(171, 112, 231, 30);
		contentPane.add(textFieldName);
		textFieldName.setColumns(10);
		
		JLabel lblIsbnNumber = new JLabel("ISBN Number");
		lblIsbnNumber.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblIsbnNumber.setBounds(30, 76, 123, 14);
		contentPane.add(lblIsbnNumber);
		
		JLabel lblNameOfThe = new JLabel("Name of the Book");
		lblNameOfThe.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblNameOfThe.setBounds(30, 115, 131, 14);
		contentPane.add(lblNameOfThe);
		
		JLabel lblSearchBook = new JLabel("Search Book");
		lblSearchBook.setForeground(new Color(51, 102, 204));
		lblSearchBook.setFont(new Font("Perpetua Titling MT", Font.BOLD | Font.ITALIC, 18));
		lblSearchBook.setBounds(132, 11, 159, 28);
		contentPane.add(lblSearchBook);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				LastScreen.screen1.setVisible(true);
			}
		});
		btnBack.setBackground(new Color(119, 136, 153));
		btnBack.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		btnBack.setBounds(50, 205, 89, 23);
		contentPane.add(btnBack);
	}
}

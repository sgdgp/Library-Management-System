package libreria;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.sql.*;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class UserReserveNotifications extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public UserReserveNotifications(String username) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
				LastScreen.screen2.setVisible(true);
			}
		});
		setBounds(100, 100, 398, 382);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 42, 362, 258);
		contentPane.add(scrollPane);
		
		table = new JTable();
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new String[] {"ISBN"});
		table.setModel(model);
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lis","root","qwerty");
			ResultSet r = con.createStatement().executeQuery("Select * from rbList where username = '"+username+"'");
			//String add = "SELECT * FROM users WHERE username = '" + username+"'";
			while(r.next()){
				model.addRow(new String[] {r.getString("ISBN")});
			}
		}
		catch(Exception e){
			
		}
		scrollPane.setViewportView(table);
		
		JLabel lblReservedBooks = new JLabel("RESERVED BOOKS THAT HAVE BEEN RETURNED");
		lblReservedBooks.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblReservedBooks.setBounds(26, 11, 346, 14);
		contentPane.add(lblReservedBooks);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				LastScreen.screen2.setVisible(true);
			}
		});
		btnBack.setBounds(150, 311, 89, 23);
		contentPane.add(btnBack);
	}
}

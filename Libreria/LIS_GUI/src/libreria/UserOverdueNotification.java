package libreria;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

@SuppressWarnings("serial")
public class UserOverdueNotification extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JButton btnBack;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("unchecked")
	public UserOverdueNotification(String username) {
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
		scrollPane.setBounds(10, 42, 362, 261);
		contentPane.add(scrollPane);
		
		table = new JTable();
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new String[] {"ISBN"});
		table.setModel(model);
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lis?useSSL=false","root","qwerty");
			ResultSet r = con.createStatement().executeQuery("Select * from users WHERE username = '" + username+"'");
			//String add = "SELECT * FROM users WHERE username = '" + username+"'";
			while(r.next()){
				byte[] buf1 = r.getBytes("booksIssued");
	            ObjectInputStream o1 = new ObjectInputStream(new ByteArrayInputStream(buf1));
	            ArrayList<UserIssueDetails> booksIssued = (ArrayList<UserIssueDetails>) o1.readObject();
	            for(int i=0;i<booksIssued.size();++i){
	            	if(booksIssued.get(i).isNotif()==true && booksIssued.get(i).isOverdue()==true){
	            		model.addRow(new String[]{booksIssued.get(i).getIssuedBook()});
	            	}
	            }
			}
		}
		catch(Exception e){
			
		}
		scrollPane.setViewportView(table);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				LastScreen.screen2.setVisible(true);
			}
		});
		btnBack.setBounds(136, 314, 89, 23);
		contentPane.add(btnBack);
		
		JLabel lblOverdueBookNotifications = new JLabel("OVERDUE BOOK NOTIFICATIONS");
		lblOverdueBookNotifications.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblOverdueBookNotifications.setBounds(102, 11, 224, 14);
		contentPane.add(lblOverdueBookNotifications);
	}
}

package libreria;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Window.Type;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.sql.*;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
public class ClerkNotification extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ClerkNotification frame = new ClerkNotification();
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
	public ClerkNotification() {
		setResizable(false);
		setType(Type.POPUP);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
				LastScreen.screen1.setVisible(true);
			}
		});
		setBounds(100, 100, 374, 353);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 44, 338, 221);
		contentPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lis?useSSL=false", "root", "qwerty");
			Statement st = con.createStatement();
			String query = "Select * from books where delNotif=true";
			ResultSet r = st.executeQuery(query);
			String a = "";
			while(r.next()){
				a += "Delete book with ISBN = "+r.getString("ISBN")+"\n";
			}
			textArea.setText(a);
			
			JLabel lblTasks = new JLabel("TASKS");
			lblTasks.setBounds(135, 19, 46, 14);
			contentPane.add(lblTasks);
			
			JButton btnBack = new JButton("Back");
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
					LastScreen.screen1.setVisible(true);
				}
			});
			btnBack.setBounds(135, 280, 89, 23);
			contentPane.add(btnBack);
		}catch(Exception e){
			
		}
	
	}
}

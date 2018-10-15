package libreria;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;

public class LibrarianHomePage extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LibrarianHomePage frame = new LibrarianHomePage();
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
	public LibrarianHomePage() {
		setResizable(false);
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
		setBounds(100, 100, 467, 325);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNameOfLibrarian = new JLabel("Librarian");
		lblNameOfLibrarian.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNameOfLibrarian.setBounds(45, 12, 155, 15);
		contentPane.add(lblNameOfLibrarian);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setIcon(new ImageIcon(LibrarianHomePage.class.getResource("/libreria/logout.png")));
		btnLogout.setBackground(new Color(153, 102, 153));
		btnLogout.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				MainWindow frame=new MainWindow();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
			}
		});
		btnLogout.setBounds(302, 7, 139, 33);
		contentPane.add(btnLogout);
		
		JLabel lblNewLabel = new JLabel("Operations that can be performed");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel.setBounds(74, 55, 309, 25);
		contentPane.add(lblNewLabel);
		
		JButton btnAddUser = new JButton("Add user");
		btnAddUser.setIcon(new ImageIcon(LibrarianHomePage.class.getResource("/libreria/add.png")));
		btnAddUser.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					setVisible(false);
					CreateUser frame = new CreateUser();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception ex) {
//					ex.printStackTrace();
				}
			}
		});
		btnAddUser.setBackground(new Color(153, 102, 153));
		btnAddUser.setBounds(45, 117, 139, 30);
		contentPane.add(btnAddUser);
		
		JButton btnRemoveUser = new JButton("Remove User");
		btnRemoveUser.setIcon(new ImageIcon(LibrarianHomePage.class.getResource("/libreria/delete.png")));
		btnRemoveUser.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		btnRemoveUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					setVisible(false);
					RemoveUser frame = new RemoveUser();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnRemoveUser.setBackground(new Color(153, 102, 153));
		btnRemoveUser.setBounds(278, 117, 149, 30);
		contentPane.add(btnRemoveUser);
		
		JButton btnAddClerk = new JButton("Add clerk");
		btnAddClerk.setIcon(new ImageIcon(LibrarianHomePage.class.getResource("/libreria/add.png")));
		btnAddClerk.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		btnAddClerk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					setVisible(false);
					CreateClerk frame = new CreateClerk();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception ex) {
//					e.printStackTrace();
				}
			}
		});
		btnAddClerk.setBackground(new Color(153, 102, 153));
		btnAddClerk.setBounds(45, 166, 139, 30);
		contentPane.add(btnAddClerk);
		
		JButton btnRemoveClerk = new JButton("Remove clerk");
		btnRemoveClerk.setIcon(new ImageIcon(LibrarianHomePage.class.getResource("/libreria/delete.png")));
		btnRemoveClerk.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		btnRemoveClerk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					setVisible(false);
					RemoveClerk frame = new RemoveClerk();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnRemoveClerk.setBackground(new Color(153, 102, 153));
		btnRemoveClerk.setBounds(278, 166, 149, 30);
		contentPane.add(btnRemoveClerk);
		
		JButton btnPrintNotifications = new JButton("Print notifications");
		btnPrintNotifications.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					setVisible(false);
					SendOverdueNotif frame = new SendOverdueNotif();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					LastScreen.screen2 = frame;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnPrintNotifications.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		btnPrintNotifications.setBackground(new Color(153, 102, 153));
		btnPrintNotifications.setBounds(45, 220, 155, 30);
		contentPane.add(btnPrintNotifications);
		
		JButton btnNewButton = new JButton("Get Statistics");
		btnNewButton.setBackground(new Color(153, 102, 153));
		btnNewButton.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IssueStat frame;
				try {
					setVisible(false);
					frame = new IssueStat();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				
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
		btnNewButton.setBounds(278, 220, 149, 30);
		contentPane.add(btnNewButton);
	}
}

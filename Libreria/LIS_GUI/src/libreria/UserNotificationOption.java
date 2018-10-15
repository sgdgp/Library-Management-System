package libreria;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class UserNotificationOption extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public UserNotificationOption(String username) {
		setResizable(false);
		setTitle("Notification Options");
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
		contentPane.setBackground(new Color(153, 204, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				LastScreen.screen1.setVisible(true);
			}
		});
		btnBack.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		btnBack.setBackground(new Color(255, 255, 102));
		btnBack.setBounds(39, 205, 89, 23);
		contentPane.add(btnBack);
		
		JButton btnReserveNotifications = new JButton("Reserve Notifications");
		btnReserveNotifications.setBackground(new Color(255, 153, 204));
		btnReserveNotifications.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
		btnReserveNotifications.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				UserReserveNotifications frame=new UserReserveNotifications(username);
				frame.setVisible(true);
				
			}
		});
		btnReserveNotifications.setBounds(109, 59, 216, 41);
		contentPane.add(btnReserveNotifications);
		
		JButton btnOverdueNotifications = new JButton("Overdue Notifications");
		btnOverdueNotifications.setBackground(new Color(255, 153, 204));
		btnOverdueNotifications.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
		btnOverdueNotifications.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				UserOverdueNotification frame=new UserOverdueNotification(username);
				frame.setVisible(true);
			}
		});
		btnOverdueNotifications.setBounds(109, 111, 216, 41);
		contentPane.add(btnOverdueNotifications);
	}

}

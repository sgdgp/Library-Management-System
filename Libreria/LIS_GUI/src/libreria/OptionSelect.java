package libreria;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class OptionSelect extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					OptionSelect frame = new OptionSelect();
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
	public OptionSelect(String username) {
		setResizable(false);
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
		contentPane.setBackground(new Color(255, 153, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SearchBook frame=new SearchBook(username);
				frame.setVisible(true);
			}
		});
		btnSearch.setBackground(new Color(204, 255, 153));
		btnSearch.setForeground(new Color(153, 51, 204));
		btnSearch.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
		btnSearch.setBounds(78, 83, 257, 52);
		contentPane.add(btnSearch);
		
		JButton btnViewCompleteBook = new JButton("View complete Book List");
		btnViewCompleteBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FullBookList frame;
				try {
					frame = new FullBookList(username);
					frame.setVisible(true);
					IssueWrapper.username = username;
					ReserveWrapper.username = username;
				} catch (ClassNotFoundException | SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		btnViewCompleteBook.setBackground(new Color(204, 255, 153));
		btnViewCompleteBook.setForeground(new Color(153, 51, 204));
		btnViewCompleteBook.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
		btnViewCompleteBook.setBounds(78, 146, 257, 52);
		contentPane.add(btnViewCompleteBook);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				LastScreen.screen1.setVisible(true);
			}
		});
		btnBack.setBackground(new Color(204, 255, 153));
		btnBack.setForeground(new Color(102, 153, 204));
		btnBack.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		btnBack.setBounds(161, 227, 89, 23);
		contentPane.add(btnBack);
		
		JLabel lblChooseYourAction = new JLabel("Choose your action");
		lblChooseYourAction.setForeground(new Color(0, 153, 153));
		lblChooseYourAction.setFont(new Font("Malgun Gothic Semilight", Font.BOLD | Font.ITALIC, 24));
		lblChooseYourAction.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseYourAction.setBounds(78, 22, 257, 50);
		contentPane.add(lblChooseYourAction);
	}
}

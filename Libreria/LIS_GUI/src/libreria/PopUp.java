package libreria;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;

public class PopUp extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public PopUp(String str) {
		setTitle("Message");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 452, 302);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 102));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea txtrGhjkl = new JTextArea();
		txtrGhjkl.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		txtrGhjkl.setText(str);
		txtrGhjkl.setForeground(new Color(0, 51, 204));
		txtrGhjkl.setBackground(new Color(255, 255, 153));
		txtrGhjkl.setEditable(false);
		txtrGhjkl.setBounds(10, 11, 416, 241);
		contentPane.add(txtrGhjkl);
	}
}

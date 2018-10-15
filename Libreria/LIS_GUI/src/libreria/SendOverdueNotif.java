package libreria;

import java.sql.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class SendOverdueNotif extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SendOverdueNotif frame = new SendOverdueNotif();
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
	public SendOverdueNotif() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
				LastScreen.screen1.setVisible(true);
			}
		});
		setBounds(100, 100, 404, 405);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 368, 285);
		contentPane.add(scrollPane);
		
		DefaultTableModel t = new DefaultTableModel() {

            boolean[] canEdit = new boolean[]{
                    false,false, true
            };

//            public boolean isCellEditable(int rowIndex, int columnIndex) {
//                return canEdit[columnIndex];
//            }
		};
		t.setColumnIdentifiers(new String[]{"Username","Book ISBN","Action"});
		table = new JTable();
		table.setModel(t);
		table.getColumn("Action").setCellRenderer(new ButtonRenderer());
		table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));
		
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lis","root","qwerty");
			Statement s = con.createStatement();
			String query = "select * from users where fine>0";
			ResultSet r = s.executeQuery(query);
//			ResultSet x= r;
//			x.next();
//			String[] u=new String[x.getInt(1)];
//			int counter = 0;
//			
//			while(r.next()){
//				u[counter]=r.getString("username");
//				t.addRow(new String[]{r.getString("username"),r.getString("fine"),"Send Overdue Notification"});
//				
//			}
			 
			while(r.next())
			{
				byte[] buf1 = r.getBytes("booksIssued");
	            ObjectInputStream o1 = new ObjectInputStream(new ByteArrayInputStream(buf1));
	            ArrayList<UserIssueDetails> booksIssued = (ArrayList<UserIssueDetails>) o1.readObject();
	            SendOverNotifWrapper.uid = booksIssued;
	            ArrayList<String> xs = new ArrayList<String>();
	            Iterator itr = booksIssued.iterator();
	            while(itr.hasNext())
	            {
	            	UserIssueDetails var=(UserIssueDetails) itr.next();
	            	if(var.isOverdue()==true && var.isNotif()==false)
	            	{
	            		String z = var.getIssuedBook();
	            		xs.add(z);
	            		t.addRow(new String[]{r.getString("username"),var.getIssuedBook(),"Send Overdue Notification"});
	            	}
	            }
				SendOverNotifWrapper.ISBN = xs;
			}
			//SendOverNotifWrapper.username=u;
		}catch(Exception e){
			
		}
		scrollPane.setViewportView(table);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				LastScreen.screen1.setVisible(true);
			}
		});
		btnBack.setBounds(149, 318, 89, 23);
		contentPane.add(btnBack);
		
	}
}

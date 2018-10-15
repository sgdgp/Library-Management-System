package libreria;
//Displays list of all books in library
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IssueStat extends JFrame {

	private JPanel contentPane;
	public String[][] data1= new String[100][100];
	
	private String url1 = "jdbc:mysql://localhost:3306/";
    private static String url = "jdbc:mysql://localhost:3306/lis";
    private static final String user = "root";
    private static final String password = "qwerty";
    private JTable table;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					DisplayAccountDetails frame = new DisplayAccountDetails();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public IssueStat() throws SQLException, IOException, ClassNotFoundException {
		setResizable(false);
		setTitle("Issue Statistics");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
				LastScreen.screen1.setVisible(true);
			}
		});
		setVisible(true);
		setBounds(100, 100, 584, 314);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 548, 212);
		getContentPane().add(scrollPane);
		
		DefaultTableModel t = new DefaultTableModel()
				{
			boolean canEdit[]={false,false,false,true};
				};
		String[] columnNames = {"ISBN Number of  Book","Name of Book", "Times issued in last 5 years", "Dispose"};
		t.setColumnIdentifiers(columnNames);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		 table.setModel(t);
		 
		 JButton btnBack = new JButton("Back");
		 btnBack.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		dispose();
				LastScreen.screen1.setVisible(true);
		 	}
		 });
		 btnBack.setBounds(239, 241, 89, 23);
		 getContentPane().add(btnBack);
		JPanel panel = new JPanel();
		Statement stmt = null;
        Connection con = DriverManager.getConnection(url, user, password);
        stmt = con.createStatement();
		String add = "SELECT * FROM books ";
        ResultSet rs = stmt.executeQuery(add);
        String name[]=new String[10000];
        String ISBN[]=new String[10000];
        
        int i=0;
        table.getColumn("Dispose").setCellRenderer(new ButtonRenderer());
		table.getColumn("Dispose").setCellEditor(new ButtonEditor(new JCheckBox()));
		
        while (rs.next())
        {
        	ISBN[i]=rs.getString("ISBN");
        	name[i]=rs.getString("name");
        	byte[] buf3 = rs.getBytes("issueStats");
            ObjectInputStream o3 = new ObjectInputStream(new ByteArrayInputStream(buf3));
            ArrayList<Date> issueStats = (ArrayList<Date>) o3.readObject();
            Date d1=Calendar.getInstance().getTime();
            int z=0;
            for(int k=0;k<issueStats.size();++k){
            	Calendar c1 =Calendar.getInstance();
            	Calendar c2 =Calendar.getInstance();
            	c1.setTime(d1);
            	c2.setTime(issueStats.get(k));
            	int diff=c1.get(Calendar.YEAR)-c2.get(Calendar.YEAR);
            	
            	if(diff<=5)
            	{
            		++z;
            	}
            }
        	String a[]={ISBN[i],name[i],Integer.toString(z),"Dispose"};
        	t.addRow(a);
        
        }
        DisposeNotifWrapper.ISBN = ISBN;
        

	
		}
	
	}



package libreria;

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

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ReturnWindow extends JFrame {

	@SuppressWarnings("unused")
	private JPanel contentPane;
	public String[][] data1= new String[100][100];
	
	@SuppressWarnings("unused")
	private String url1 = "jdbc:mysql://localhost:3306/";
    private static String url = "jdbc:mysql://localhost:3306/lis?useSSL=false";
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
	@SuppressWarnings("unchecked")
	public ReturnWindow(String username) throws SQLException, IOException, ClassNotFoundException {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
				LastScreen.screen1.setVisible(true);
			}
		});
		setVisible(true);
		setBounds(100, 100, 585, 300);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 36, 533, 169);
		getContentPane().add(scrollPane);
		
		DefaultTableModel t = new DefaultTableModel() {

            boolean[] canEdit = new boolean[]{
                    false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
};
		String[] columnNames = {"ISBN Number of  Book","Name of Book", "Date of Issue", "Due Date", "Actions available "};
		t.setColumnIdentifiers(columnNames);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		table.setModel(t);
		
		
		
		JLabel label = new JLabel(username);
		label.setBounds(47, 11, 102, 14);
		getContentPane().add(label);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				LastScreen.screen1.setVisible(true);
			}
		});
		btnBack.setBounds(248, 227, 89, 23);
		getContentPane().add(btnBack);
		 
		table.getColumn("Actions available ").setCellRenderer(new ButtonRenderer());
		table.getColumn("Actions available ").setCellEditor(new ButtonEditor(new JCheckBox()));
		
        Statement stmt = null;
        Statement stmt1 = null;
        Connection con = DriverManager.getConnection(url, user, password);
        stmt = con.createStatement();
        stmt1 = con.createStatement();
        String mem = "SELECT * FROM users WHERE username = '" + username+"'";
        ResultSet ms = stmt1.executeQuery(mem);
        if (ms.next()) {

//            double fine = ms.getDouble("fine");
            byte[] buf2 = ms.getBytes("booksIssued");
            ObjectInputStream o2 = new ObjectInputStream(new ByteArrayInputStream(buf2));
            ArrayList<UserIssueDetails> booksIssued = (ArrayList<UserIssueDetails>) o2.readObject();
            
            int i;
            double fine=(ms.getDouble("fine"));
            for(i=0;i<booksIssued.size();++i)
            {
            	data1[i][0]=booksIssued.get(i).getIssuedBook();
            	data1[i][2]=(String)booksIssued.get(i).getIssueDate();
            	data1[i][3]=(String)booksIssued.get(i).getDueDate();
            	
            	data1[i][4]="Return";
            	String add = "SELECT * FROM books WHERE ISBN = '" +booksIssued.get(i).getIssuedBook() + "'";
                ResultSet rs = stmt.executeQuery(add);
                rs.next();
//                System.out.println(booksIssued.get(i).getIssuedBook());
                data1[i][1]=rs.getString("name");
                String[] x={data1[i][0],data1[i][1],data1[i][2],data1[i][3],data1[i][4]};
            	t.addRow(x);
            }
            

            table.addMouseListener(new MouseAdapter() {
            	  public void mouseClicked(MouseEvent e) {
            	    if (e.getClickCount() == 2) {
            	      JTable target = (JTable)e.getSource();
            	      int row = target.getSelectedRow();
            	      int column = target.getSelectedColumn();
            	      // do some action if appropriate column
            	      if(column==4)
            	      {
//            	    	  libraryfunc l1=new libraryfunc();
//            	    	  l1.returned(booksIssued.get(row).getIssuedBook(), username);
            	    	  
            	    	  ReturnAndInform RI = new ReturnAndInform();
            	    	  RI.returned(booksIssued.get(row).getIssuedBook(), username);
            	      }
            	    }
            	  }
            	});
            JLabel lblFineDue = new JLabel("Fine Due="+fine);
    		lblFineDue.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
    		lblFineDue.setBounds(397, 11, 95, 14);
    		getContentPane().add(lblFineDue);
            
//    		table.setEnabled(false);

	
		}
	
	}
}


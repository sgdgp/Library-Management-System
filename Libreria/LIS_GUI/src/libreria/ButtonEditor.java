package libreria;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;


@SuppressWarnings("serial")
class ButtonEditor extends DefaultCellEditor {
  protected JButton button;

  private String label;
  @SuppressWarnings("unused")
private JTable tbl;
  private boolean isPushed;
  private int r;
  public ButtonEditor(JCheckBox checkBox) {
    super(checkBox);
    button = new JButton();
    button.setOpaque(true);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
      }
    });
  }

  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    if (isSelected) {
      button.setForeground(table.getSelectionForeground());
      button.setBackground(table.getSelectionBackground());
    } else {
      button.setForeground(table.getForeground());
      button.setBackground(table.getBackground());
    }
    label = (value == null) ? "" : value.toString();
    button.setText(label);
    isPushed = true;
    r = row;
    tbl = table;
    return button;
  }

  public Object getCellEditorValue() {
    if (isPushed) {          
      if(button.getText().equals("Return")){
    	  LastScreen.screen2.dispose();
//    	  libraryfunc l = new libraryfunc();
//    	  l.returned(ReturnWrapper.uid.get(r).getIssuedBook(), ReturnWrapper.username);
    	  
    	  
    	  ReturnAndInform RI = new ReturnAndInform();
    	  RI.returned(ReturnWrapper.uid.get(r).getIssuedBook(), ReturnWrapper.username);
    	  
    	  
    	  LastScreen .screen1.setVisible(true);
      }
      
      if(button.getText().equals("Issue")){
//    	  libraryfunc l = new libraryfunc();
//    	  l.issue(IssueWrapper.ISBN.get(r), IssueWrapper.username);
    	  
    	  Issue I = new Issue();
		  I.issue(IssueWrapper.ISBN.get(r), IssueWrapper.username);
      }
      
      if(button.getText().equals("Reserve")){
//    	  libraryfunc l = new libraryfunc();
//    	  l.reserve(ReserveWrapper.ISBN.get(r), ReserveWrapper.username);
    	  
    	  Reserver R = new Reserver();
    	  R.reserve(ReserveWrapper.ISBN.get(r), ReserveWrapper.username);
      }
      
      
      if(button.getText().equals("Dispose")){
    	try{
    		Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lis?useSSL=false","root","qwerty");
			con.createStatement().executeQuery("SET SQL_SAFE_UPDATES=0");
    		String query = "update books set delNotif=true where ISBN='"+DisposeNotifWrapper.ISBN[r]+"'";
    		Statement s = con.createStatement();
    		s.executeUpdate(query);
    	}catch(Exception e){
    	
    	}
    	
    	
     }
      if(button.getText().equals("Send Overdue Notification")){
      	try{
      		Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lis?useSSL=false","root","qwerty");
  			con.createStatement().executeQuery("SET SQL_SAFE_UPDATES=0");
      		for(int i=0;i<SendOverNotifWrapper.uid.size();i++){
      			String z = SendOverNotifWrapper.ISBN.get(r);
      			if(SendOverNotifWrapper.uid.get(i).getIssuedBook().equals(z)){
      				SendOverNotifWrapper.uid.get(i).setNotif(true);
      				SendOverNotifWrapper.uid.get(i).setOverdue(true);
      				
      			}
      		}
      		con.createStatement().executeQuery("SET SQL_SAFE_UPDATES=0");
			String sql = "update users set booksIssued = ?";
			
      		PreparedStatement s = con.prepareStatement(sql);
      		s.setObject(1, SendOverNotifWrapper.uid);
      		s.executeUpdate();
      	}catch(Exception e){
      	
      	}
      	
       } 
    }
    isPushed = false;
    return new String(label);
  }

  public boolean stopCellEditing() {
    isPushed = false;
    return super.stopCellEditing();
  }

  protected void fireEditingStopped() {
    super.fireEditingStopped();
  }
}



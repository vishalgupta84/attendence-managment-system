// package com.tutorialspoint.gui;
import java.io.*;
import java.util.*;
import java.util.Scanner;
// import java.util.Scanner;
// import java.lang.Thread;
// import java.util.Vector;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
import javax.swing.border.*;
import java.text.*;
import java.sql.*;
public class Assignment1Part2{
  private JFrame mainFrame;
 	private JLabel headerLabel,rollLabel,nameLabel,seatLabel;
 	private JLabel statusLabel;
 	private JPanel radioPanel,infoPanel,buttonPanel;
 	private JTextField roll,name,seat;
  public String r_number;
 	private JButton loginButton;
    // GridBagConstraints cs_rNum,cs_r;
  private ButtonGroup group=new ButtonGroup();
  Border highlightBorder = BorderFactory.createLineBorder(java.awt.Color.ORANGE);
	public Assignment1Part2(){
		prepareGUI(); 
    // showEvent();
	}
	// public static void main(String[] args) throws IOException {
	// 	Assignment1Part2 controlGUI=new Assignment1Part2();
	// 	// controlGUI.showEvent();
	// }
  // roll.setText("1234");
	private void prepareGUI(){
		mainFrame=new JFrame("main page");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(300,500);
		mainFrame.setLayout(new GridLayout(6,3,3,6));
		headerLabel=new JLabel("",JLabel.CENTER); 
    // headerLabel.setHorizontalAlignment(50);  
    // headerLabel.setVerticalAlignment(50);
  	radioPanel = new JPanel();
  	infoPanel=new JPanel();
  	buttonPanel=new JPanel();
    // buttonPanel.setPreferredSize(new Dimension(640, 480));
  	statusLabel=new JLabel();
  	GridBagConstraints cs = new GridBagConstraints();
    GridBagConstraints cs_rNum = new GridBagConstraints();
    GridBagConstraints cs_r = new GridBagConstraints();
  	cs.fill = GridBagConstraints.HORIZONTAL;

    // infoPanel.setBorder(new LineBorder(Color.GRAY));
    nameLabel = new JLabel("Name: ");
    cs.gridx = 0;          
    cs.gridy = 0;
    cs.gridwidth = 1;
    infoPanel.add(nameLabel, cs);

    name = new JTextField(20);
    cs.gridx = 1;
    cs.gridy = 0;
    cs.gridwidth = 2;
    infoPanel.add(name, cs);

    rollLabel = new JLabel("Roll N: ");
    cs.gridx = 0;
    cs.gridy = 1;
    cs.gridwidth = 1;
    infoPanel.add(rollLabel, cs);
    cs_rNum=cs;
    roll = new JTextField(20);
    cs.gridx = 1;
    cs.gridy = 1;
    cs.gridwidth = 2;
    infoPanel.add(roll,cs);
    seatLabel = new JLabel("Seat. : ");
    cs.gridx = 0;
    cs.gridy = 2;
    cs.gridwidth = 1;
    infoPanel.add(seatLabel, cs);
    seat = new JTextField(20);
    cs.gridx = 1;
    cs.gridy = 2;
    cs.gridwidth = 2;
    infoPanel.add(seat,cs);
 		loginButton = new JButton("Submit");

 		// infoPanel.add(loginButton);
 		buttonPanel.add(loginButton);
      	mainFrame.add(headerLabel);
      	mainFrame.add(radioPanel);
      	mainFrame.add(infoPanel);
      	mainFrame.add(buttonPanel);
      	mainFrame.add(statusLabel);
      	mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      	});
      	mainFrame.setVisible(true); 
	}
	public void showEvent(){
      headerLabel.setText("Main Page"); 
      JRadioButton StudentButton = new JRadioButton("Student");
      JRadioButton ProfessorButton = new JRadioButton("Professor");
      ProfessorButton.setActionCommand("Teacher");
      StudentButton.setActionCommand("Student");
      loginButton.setActionCommand("Submit");
      StudentButton.addActionListener(new ButtonClickListener()); 
      ProfessorButton.addActionListener(new ButtonClickListener());
      loginButton.addActionListener(new ButtonClickListener()); 
      StudentButton.setSelected(true);
      group.add(StudentButton);
      group.add(ProfessorButton);
      radioPanel.add(StudentButton);
      radioPanel.add(ProfessorButton);
      mainFrame.setVisible(true);
      name.requestFocusInWindow();  
   }
   public String getRollNumber(){
    // return "As";
    // System.out.println(roll.getText());
   	  return roll.getText().trim();
   }
   public String getName(){
   	  return name.getText();
   }
   public String getSeatNumber(){
      return seat.getText();
   }
   private class ButtonClickListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
         String command = e.getActionCommand();  
         
         if( command.equals( "Teacher" ))  {
            mainFrame.remove(infoPanel);
            mainFrame.remove(buttonPanel);
            mainFrame.revalidate();
            mainFrame.repaint();
         }
         else if( command.equals( "Student" ) )  {
            mainFrame.add(infoPanel);
            mainFrame.add(buttonPanel);
            mainFrame.revalidate();
            mainFrame.repaint();
         }
         else if(command.equals( "Submit" )){
         	String Name=getName();
          String Roll=getRollNumber();
          String Seat=getSeatNumber();
          // r_number=Roll;
         	statusLabel.setText(Name+Roll+Seat);
          // System.out.println(getName());

          // HandleStudentData studentData=new HandleStudentData(name,roll,seat);
          // try{
          //   Runtime rt = Runtime.getRuntime();
          //   Process p = rt.exec("javac HandleStudentData.java");
          //   Process pr = rt.exec("java HandleStudentData "+name+" "+roll+" "+seat);
          // }
          // catch(IOException ext){
          //   System.out.println("IO error in server thread");
          // }
         }  	
      }		
   }	
}
// class HandleStudentData{
//   private String name,roll,seat;
//   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
//    static final String DB_URL = "jdbc:mysql://localhost/EMP";

//    //  Database credentials
//    static final String USER = "root";
//    static final String PASS = "vodka";
   
//    // public
//   HandleStudentData(String name,String roll,String seat){
//     name=name;
//     roll=roll;
//     seat=seat;
//     System.out.println(name+roll+seat);
//     // static void main(String[] args) {
//    Connection conn = null;
//    Statement stmt = null;
//    try{
//       //STEP 2: Register JDBC driver
//       Class.forName("com.mysql.jdbc.Driver");

//       //STEP 3: Open a connection
//       System.out.println("Connecting to database...");
//       conn = DriverManager.getConnection(DB_URL,USER,PASS);

//       //STEP 4: Execute a query
//       System.out.println("Creating statement...");
//       stmt = conn.createStatement();
//       String sql;
//       sql = "SELECT id, name FROM attendenceInfo";
//       ResultSet rs = stmt.executeQuery(sql);

//       //STEP 5: Extract data from result set
//       while(rs.next()){
//          //Retrieve by column name
//          int id  = rs.getInt("id");
//          int age = rs.getInt("name");
//          // String first = rs.getString("first");
//          // String last = rs.getString("last");

//          //Display values
//          System.out.println("ID: " + id);
//          System.out.println(", Age: " + age);
//          // System.out.print(", First: " + first);
//          // System.out.println(", Last: " + last);
//       }
//       //STEP 6: Clean-up environment
//       rs.close();
//       stmt.close();
//       conn.close();
//    }catch(SQLException se){
//       //Handle errors for JDBC
//       se.printStackTrace();
//    }catch(Exception e){
//       //Handle errors for Class.forName
//       e.printStackTrace();
//    }finally{
//       //finally block used to close resources
//       try{
//          if(stmt!=null)
//             stmt.close();
//       }catch(SQLException se2){
//       }// nothing we can do
//       try{
//          if(conn!=null)
//             conn.close();
//       }catch(SQLException se){
//          se.printStackTrace();
//       }//end finally try
//    }//end try
//    System.out.println("Goodbye!");
//   // }
// // }
//     try{
//       PrintWriter writer = new PrintWriter("data.txt", "UTF-8");
//       writer.println(name+"\t"+roll+"\t"+seat);
//       writer.close();
//     }
//     catch(IOException ioe){

//     }
//   }
//   // public static void main(String[] args) {
   
// }
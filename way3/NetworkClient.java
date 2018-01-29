// 3rd strategy implementation of layout

import java.net.InetAddress;
import java.net.Socket;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.swing.border.*;
import java.text.*;
import java.sql.*;
import java.util.concurrent.*;


public class NetworkClient{
    public NetworkClient(){
        prepareGUI();               // method to prepare GUI for client to input their info.
    }
    static InetAddress address;     //localhost address of current client
    static Socket s1=null;
    static String line=null;
    static BufferedReader br=null;
    static BufferedReader is=null;
    static PrintWriter os=null;
    private JFrame mainFrame;
    private JLabel headerLabel,rollLabel,nameLabel,seatLabel;
    private JLabel statusLabel;
    private JPanel radioPanel,infoPanel,buttonPanel;
    private JTextField roll,name,seat;
    public String Name,Roll,Seat;
    private JButton loginButton;
    private ButtonGroup group=new ButtonGroup();
    Border highlightBorder = BorderFactory.createLineBorder(java.awt.Color.ORANGE);

    //method implementation which creates GUI for client like student and professor both to interact with 
    //server and put their requrest

    private void prepareGUI(){
        mainFrame=new JFrame("main page");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300,500);
        mainFrame.setLayout(new GridLayout(6,3,3,6));
        headerLabel=new JLabel("",JLabel.CENTER); 
        radioPanel = new JPanel();
        infoPanel=new JPanel();
        buttonPanel=new JPanel();
        statusLabel=new JLabel();
        GridBagConstraints cs = new GridBagConstraints();
        GridBagConstraints cs_rNum = new GridBagConstraints();
        GridBagConstraints cs_r = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
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
        // attendenceButton=new JButton("See Your Attendence Here");
        buttonPanel.add(loginButton);
        // buttonPanel.add(attendenceButton);
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

    //this method puts all the component of GUI which is needed for student interaction with server
    // and submit their information.

    public void showEvent(){
        headerLabel.setText("Main Page"); 
        JRadioButton StudentButton = new JRadioButton("Student");
        JRadioButton ProfessorButton = new JRadioButton("Professor");
        ProfessorButton.setActionCommand("Teacher");
        StudentButton.setActionCommand("Student");
        loginButton.setActionCommand("Submit");
        // attendenceButton.setActionCommand("Attendence");
        StudentButton.addActionListener(new ButtonClickListener()); 
        ProfessorButton.addActionListener(new ButtonClickListener());
        loginButton.addActionListener(new ButtonClickListener()); 
        // attendenceButton.addActionListener(new ButtonClickListener());
        StudentButton.setSelected(true);
        group.add(StudentButton);
        group.add(ProfessorButton);
        radioPanel.add(StudentButton);
        radioPanel.add(ProfessorButton);
        mainFrame.setVisible(true);
        name.requestFocusInWindow();  
   }
   //function to get roll number of student submitted
    public String getRollNumber(){
        return roll.getText().trim();
    }
    //function to get name of student submitted
    public String getName(){
        return name.getText();
    }
    //method to get seat of student submitted
    public String getSeatNumber(){
        return seat.getText();
    }

    //this class implements method to listen activity on buttons on client side GUI

    private class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();  
         
         if( command.equals( "Teacher" ))  {            //if the request is from professor to see all the seat arrangement 
            mainFrame.remove(infoPanel);                // of student
            mainFrame.remove(buttonPanel);
            os.println("request of Professor");
            os.flush();
            mainFrame.revalidate();
            mainFrame.repaint();
         }
         else if( command.equals( "Student" ) )  {      // if the request if from student
            mainFrame.add(infoPanel);
            mainFrame.add(buttonPanel);
            mainFrame.revalidate();
            mainFrame.repaint();
         }
         else if(command.equals( "Submit" )){           // if the information is submitted by student
            Name=getName();
            Roll=getRollNumber();
            Seat=getSeatNumber();
            statusLabel.setText("You have successfully submitted ");
            buttonPanel.remove(loginButton);
            mainFrame.remove(infoPanel);
            mainFrame.remove(radioPanel);
            os.println(Name+"\t"+Roll+"\t"+Seat);
            os.flush();
         }    
      }     
   }
    public static void main(String args[]) throws IOException{    
        
        //client socket trying to connect to server using socket binding

        try {
            address=InetAddress.getLocalHost(); 
            if(args.length==1) 
                s1=new Socket(args[0], 4445); 
            else
                s1=new Socket(address,4445); 
            try{
                PrintWriter writer = new PrintWriter("data.txt", "UTF-8");
            } 
            catch (IOException e) {
               // do something
            }
            br= new BufferedReader(new InputStreamReader(System.in));
            is=new BufferedReader(new InputStreamReader(s1.getInputStream()));
            os= new PrintWriter(s1.getOutputStream());
            NetworkClient student=new NetworkClient();
            student.showEvent();
        }
        catch (IOException e){
            e.printStackTrace();
            System.err.print("IO Exception");
        }

        //if the professor request for seat arrangement
        //server sends all the information of student currently occupying seats

        String response=null;
        try{
            line=is.readLine(); 
            while(line.compareTo("QUIT")!=0){
                    // os.println(line);
                    // os.flush();
                response=is.readLine();

                //response holds student information sent from server 
                
                String[] studentRollNumber=response.split("\\s+");
                line=is.readLine();
                long startTime=System.currentTimeMillis();
                //calling Gui method in Gui.java file to disaply layout for professor
                //it takes student info as argument.
                Gui gui=new Gui(studentRollNumber);
                gui.showGridLayoutDemo();
                long endTime=System.currentTimeMillis();
                System.out.println("execution time in building layout is "+(endTime - startTime)+" ms");
            }
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Socket read Error");
        }
        finally{

            is.close();os.close();br.close();s1.close();
            System.out.println("Connection Closed");

        }

    }
}

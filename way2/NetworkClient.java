
//strategy 2 implementation

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
        prepareGUI();
    }
    static InetAddress address;
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

    //constructor to initialize every component of client side gui 

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

    //method to show component on gui in which response is made

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
            //response holds student information sent from server 
            response=is.readLine();
            String[] studentRollNumber=response.split("\\s+");
            line=is.readLine();
            long startTime=System.currentTimeMillis();
            //calling ProGui method  to disaply layout for professor
            //it takes student info as argument.
            ProfGui gui=new ProfGui(studentRollNumber);
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

//class for creating thread and running them using Runnable interface implementation
class SingleThread implements Runnable{
    ImageIcon icon; 
    Image image ;  
    Image newimg ;
    String img,name,roll,seat;
    JLabel[] jls;
    int index; 
    public SingleThread(JLabel[] jls,int index,String img,String name,String roll,String seat){
        this.jls=jls;
        this.index=index;
        this.img=img;
        this.name=name;
        this.roll=roll;
        this.seat=seat;
        // System.out.println(img);
    }

    //set image and grid components info to particular student info and display

    public void run() {  
        icon = new ImageIcon(img); 
        image = icon.getImage(); // transform it 
        newimg = image.getScaledInstance(175, 175,  java.awt.Image.SCALE_SMOOTH);       // scale it the smooth way  
        icon = new ImageIcon(newimg);
        jls[index].setIcon(icon);
        String text="<html>"+name+"<br>Roll No:"+roll+"</br>"+"<br>Seat No:"+seat+"</br></html>"; 
        jls[index].setText(text);
        jls[index].setHorizontalTextPosition(SwingConstants.CENTER);
        jls[index].setVerticalTextPosition(SwingConstants.BOTTOM);
        jls[index].setForeground(Color.WHITE);
    }  
}

//prof gui for displaying grid layout of student info on the panel

class ProfGui {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JLabel msglabel;
    static int totalSeat=15;
    JLabel[] labels = new JLabel[totalSeat];
    String[] imagesFiles;

    //constructor to initialize everything
    public ProfGui(String[] imagesFiles){
        this.imagesFiles =  imagesFiles;
        // this.imagesFiles =  imagesFiles;
        ImageIcon icon; 
        Image image ;  
        Image newimg ; 
        icon = new ImageIcon("notFound.png"); 
        image = icon.getImage(); // transform it 
        newimg = image.getScaledInstance(175, 175,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        icon = new ImageIcon(newimg); 
        for(int i=0;i<labels.length;i++){
            labels[i] = new JLabel();
            labels[i].setIcon(icon);
            labels[i].setText("Not Found");
            labels[i].setHorizontalTextPosition(SwingConstants.CENTER);
            labels[i].setVerticalTextPosition(SwingConstants.BOTTOM);
            labels[i].setForeground(Color.WHITE);

        }
        prepareGUI();
    }
    //prepate panel gui
   private void prepareGUI(){
        mainFrame = new JFrame("Attendence Sheet");
        mainFrame.setSize(1500,1500);
        mainFrame.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent windowEvent){
              System.exit(0);
           }        
        });    
        controlPanel = new JPanel();
        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);  
   }
   //method to show grid layout formation
   public void showGridLayoutDemo(){      
      
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);
        panel.setSize(2000,2000);
        GridLayout layout = new GridLayout(3,5);
        layout.setHgap(10);
        layout.setVgap(10);

        panel.setLayout(layout);        
        ImageIcon icon; 
        Image image ;  
        Image newimg ; 
        int j=0;
        ExecutorService executor = Executors.newFixedThreadPool(totalSeat);//creating a pool of 15 threads  
        for (int i = 0; i < totalSeat; i++) {  
            Runnable worker;
            String name,roll,im,seat;
            if(j<imagesFiles.length/3&&(i+1)==Integer.parseInt(imagesFiles[j*3+2])){
              im="../images/"+imagesFiles[j*3]+".jpg";
              File f=new File(im);
              if(!f.exists())
                im="../images/notFound.png";
              name=imagesFiles[j*3+1];
              roll=imagesFiles[j*3];
              seat=imagesFiles[j*3+2];
              j++;
            }
            else{
              im="../images/notFound.png";
              name="Not Found";
              roll="";
              seat="";
            }
            
            worker= new SingleThread(labels,i,im,name,roll,seat);   //call single thread to display
            //calling execute method of ExecutorService             //each student info by one thread parallely 
            executor.execute(worker);                                        
        }  
        executor.shutdown();  
        while (!executor.isTerminated()) {   }  

        System.out.println("Finished all robot threads");  
        for(int i=0;i<totalSeat;i++)
          panel.add(labels[i]);
        controlPanel.add(panel);
        mainFrame.setVisible(true);   
   }
} 
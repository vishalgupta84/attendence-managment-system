//GUI of layout for professor to see seat arrangements 
import java.net.InetAddress;
import java.net.Socket;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.swing.border.*;
import java.text.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
public class Gui{
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;
   private JLabel msglabel;
   static int totalSeat=15;
   JLabel[] labels = new JLabel[totalSeat];
   String[] imagesFiles;
   
   //constructor to initialize everthing
   public Gui(String[] imagesFiles){
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
			// nameLabels[i]=new JLabel();
			labels[i].setText("");

			labels[i].setHorizontalTextPosition(SwingConstants.CENTER);
			labels[i].setVerticalTextPosition(SwingConstants.BOTTOM);
			labels[i].setForeground(Color.WHITE);
		}
		prepareGUI();
   }
   //method to prepare GUI
   private void prepareGUI(){
		mainFrame = new JFrame("Present Student");
		mainFrame.setSize(1500,1500);
		headerLabel=new JLabel("",JLabel.CENTER);
		mainFrame.addWindowListener(new WindowAdapter() {
		 public void windowClosing(WindowEvent windowEvent){
		    System.exit(0);
		 }        
		});    
		controlPanel = new JPanel();
		mainFrame.add(headerLabel);
		// mainFrame.add(controlPanel);
		// controlPanel.add(scrollPane);
		// headerLabel.setText("Student Attendence Page");
		mainFrame.setVisible(true);  
   }
   //method to create grid layout
   public void showGridLayoutDemo(){
   		// headerLabel.setText("Student Attendence Page"); 
		JPanel panel = new JPanel();
		panel.setBackground(Color.darkGray);
		panel.setSize(2000,2000);
		GridLayout layout = new GridLayout(3,5);
		layout.setHgap(10);
		layout.setVgap(10);

		panel.setLayout(layout);    
		JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(50, 30, 300, 50);    
		ImageIcon icon; 
		Image image ;  
		Image newimg ; 
		int j=0;
		ForkJoinPool pool = new ForkJoinPool();			//fork pool creation for multi thread
		for(int i=0;i<totalSeat;i++){					//to display student info
			String im;
			String name,roll,seat;
			// seat=Integer.parseInt(imagesFiles[j*3+2]);
			if(j<imagesFiles.length/3&&(i+1)==Integer.parseInt(imagesFiles[j*3+2])){
				im="../images/"+imagesFiles[j*3]+".jpg";
				File f=new File(im);
				if(!f.exists())							//check if student image exists or not
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
			ForkJoin obj=new ForkJoin(labels,i,im,name,roll,seat);			//method to call
			pool.invoke(obj);												//for displaying one student info
			panel.add(labels[i]);
			// panel.add(nameLabels[i]);
		}
		controlPanel.add(scrollPane);
		mainFrame.add(controlPanel);
		mainFrame.setVisible(true);  
   }

    // public static void main(String[] args) {
    //     String[] images=new String[2];
    //     images[0]="1";
    //     images[1]="2";
    //     Gui gui=new Gui(images);
    //     gui.showGridLayoutDemo();   
    // }
}

//class to display student info using fork join methhod recursively
class ForkJoin extends RecursiveAction{
	ImageIcon icon; 
	Image image ;  
	Image newimg ;
	String img,name,roll,seat;
	JLabel[] jls;
	// JLabel[] names;
	int index;
	public ForkJoin(JLabel[] jls,int index,String img,String name,String roll,String seat){
		this.jls=jls;
		this.index=index;
		this.img=img;
		this.name=name;
		this.roll=roll;
		this.seat=seat;
	}
	@Override
	protected void compute(){
		icon = new ImageIcon(img); 
		image = icon.getImage(); // transform it 
		newimg = image.getScaledInstance(175, 175,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		icon = new ImageIcon(newimg);
		jls[index].setIcon(icon);
		// names[index]=new JLabel("simran");
		String text="<html>"+name+"<br>Roll No:"+roll+"</br>"+"<br>Seat No:"+seat+"</br></html>";
		jls[index].setText(text);
		jls[index].setHorizontalTextPosition(SwingConstants.CENTER);
		jls[index].setVerticalTextPosition(SwingConstants.BOTTOM);
		jls[index].setForeground(Color.WHITE);
	}
}
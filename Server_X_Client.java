import java.io.*;
import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
public class Server_X_Client{
    static int totalSeat=100;                //maximum number of clients that server can handle
    public static int[]  information;
    public static String[]  names;
    //check if all seat is full or not
    public static boolean isFull(){
        for (int i=0;i<totalSeat;i++ ) {
            if(information[i]==0)
                return false;
        }
        return true;
    }
    //method to get all the information of student as string
    public static String getall(){
        String all="";
        for (int i=0;i<totalSeat ;i++ ) {
            if(information[i]!=0)
                all=all+information[i]+" "+names[i]+" "+(i+1)+" ";
        }
        return all;
    }

    //synchronized method to join information from multiple clients

    synchronized static void insertOutput(int roll,int seat,String name){
        // OutputData.add(d);
        information[seat-1]=roll;
        names[seat-1]=name;
        if(isFull()){
            System.out.println("All student has came and All seat are full");
            // ProfGui gui=new ProfGui(information);
            // gui.showGridLayoutDemo();
        }
        // System.out.println(roll+"\t"+seat);
    }
    public static void main(String args[]){
        // InetAddress address=InetAddress.getLocalHost();
        information=new int[totalSeat];
        names=new String[totalSeat];
        for (int i=0;i<totalSeat ;i++ ) {
            information[i]=0;
            names[i]=null;
        }
        Socket s=null;
        ServerSocket ss2=null;
        System.out.println("Server Listening......");
        // System.out.println(address);
        try{
            ss2 = new ServerSocket(4445);           // can also use static final PORT_NUM , when defined

        }
        catch(IOException e){
        e.printStackTrace();
        System.out.println("Server error");

        }

        while(true){
            try{
                s= ss2.accept();                    //accepts the new connection
                System.out.println("connection Established");
                ServerThread st=new ServerThread(s);
                st.start();                         //start the new thread for client
                st.join();

            }

            catch(Exception e){
                e.printStackTrace();
                System.out.println("Connection Error");

            }

        }

    }

}

//new tthread for new client

class ServerThread extends Thread{  

    String line=null;
    BufferedReader  is = null;
    PrintWriter os=null;
    Socket s=null;
    public ServerThread(Socket s){
        this.s=s;
    }
    public void run() {
        try{   
            is= new BufferedReader(new InputStreamReader(s.getInputStream()));
            os=new PrintWriter(s.getOutputStream());
        }
        catch(IOException e){
            System.out.println("IO error in server thread");
        }

        try {
            // PrintWriter writer = new PrintWriter("data.txt", "UTF-8");
            line=is.readLine();
            while(line.compareTo("QUIT")!=0){

                os.println(line);
                os.flush();
                //send all the students info when request is from professor
                if(line.equals("request of Professor")){
                    // System.out.println(line);
                    String allInfo=Server_X_Client.getall();
                    // System.out.println("server side info"+allInfo);
                    os.println(allInfo);
                    os.flush();
                }
                //get student info
                else{
                    String[] detail=line.split("\\s+");
                    int rollNumber=Integer.parseInt(detail[1]);
                    int seat=Integer.parseInt(detail[2]);
                    System.out.println("seat "+seat);
                    String name=detail[0];
                    Server_X_Client.insertOutput(rollNumber,seat,name);
                    // System.out.println(name);
                    // os.println("chill");
                    // os.flush();
                }
                line=is.readLine();
            }
        } catch (IOException e) {

            line=this.getName(); //reused String line for getting thread name
            System.out.println("IO Error/ Client "+line+" terminated abruptly");
        }
        catch(NullPointerException e){
            line=this.getName(); //reused String line for getting thread name
            System.out.println("Client "+line+" Closed");
        }
        //close the client connection
        finally{    
            try{
                System.out.println("Connection Closing..");
                if (is!=null){
                    is.close(); 
                    System.out.println(" Socket Input Stream Closed");
                }

                if(os!=null){
                    os.close();
                    System.out.println("Socket Out Closed");
                }
                if (s!=null){
                s.close();
                System.out.println("Socket Closed");
                }

                }
            catch(IOException ie){
                System.out.println("Socket Close Error");
            }
        }   //end finally
    }
}

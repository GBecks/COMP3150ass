/*

NAME:	Amit Balchan
ID:		00701069
COURSE:	COMP 3150 - Computer Networks
Group Project
DATE:	30th October, 2015

Descriptiopn: 	

*/

import java.io.*;
import java.net.*;
import java.util.*;

public class FTPServerTCP{
	public static void main(String [] args) throws Exception{
		String fromClient = null;	//String for receiving clients responses
		//The questions objects are stored in an array list
		ArrayList<User> ftpUsers = new ArrayList<User>();
		String str, str1 = null, str2 = null, str3 = null;
		try{
		//use BufferedReader to read input data- questions and answers to create the questions objects
			BufferedReader buffStr = new BufferedReader(new FileReader("users.dat"));
			while((str = buffStr.readLine()) != null){
				StringTokenizer st1 = new StringTokenizer(str,";, ");
				str1 = st1.nextToken();
				str2 = st1.nextToken();
				str3 = st1.nextToken();
				ftpUsers.add(new User(str1,str2,str3));
			}	
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
		
		//create server welcome socket		
		ServerSocket welcomeSocket = new ServerSocket(6789);
		System.out.println("Server running....");
		
		while(true){
			//to keep welcome socket open connection handed over to connection socket
		    Socket connectionSocket = welcomeSocket.accept();
			
			//obtaining client IP address
			InetAddress clientIPAddress = connectionSocket.getInetAddress();
			System.out.println(clientIPAddress.toString() + " connected");
			
			//create stream to receive data from client 
			BufferedReader inFromClient = 
			new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			
			//create stream to send data to client
			DataOutputStream  outToClient =
             new DataOutputStream(connectionSocket.getOutputStream());
			
			boolean login = false;
			while(login != true){
				//obtaining client credentials (Username and Password)
				fromClient = inFromClient.readLine();
				login = validateUser(fromClient,ftpUsers);
				if(login == false) outToClient.writeBytes("Invalid Username-password\n");
				else{
					outToClient.writeBytes("Successful Login\n");
					System.out.println(clientIPAddress + " Successful Login");
				}
			}
			
			boolean input;
			//once logged in by server ftp commands that can be performed
			while(login){
				input = false;
				fromClient = inFromClient.readLine();
				if(fromClient != null){
					System.out.println(clientIPAddress + "--" + fromClient + "!");
					//HELP command
					if(fromClient.compareTo("HELP") == 0){
						fromClient = inFromClient.readLine();
						helpMenu(outToClient);
						input = true;
					}
					//UPLOAD command
					if(fromClient.compareTo("UPLOAD") == 0){
						fromClient = inFromClient.readLine();
						saveFile(fromClient);
						outToClient.writeBytes("Upload successful!\n");
						outToClient.writeBytes("__end\n");
						input = true;
					}
					//DOWNLOAD command
					if(fromClient.compareTo("DOWNLOAD") == 0){
						fromClient = inFromClient.readLine();
						transferFile(fromClient);
						outToClient.writeBytes("Download successful!\n");
						outToClient.writeBytes("__end\n");
						input = true;
					}
					//Invalid command
					if(input == false){
						fromClient = inFromClient.readLine();
						outToClient.writeBytes("Invalid Command!\n");
						outToClient.writeBytes("__end\n");
					}
				}
				else login = false;
			}
			
			connectionSocket.close();
        }//end true
		
    }//end main
	
	public static boolean validateUser(String a, ArrayList<User> ftpu){
		StringTokenizer st2 = new StringTokenizer(a,";, ");
		String clientName, clientPassword;
		clientName = st2.nextToken();
		clientPassword = st2.nextToken();
		for(User u:ftpu){
			if((clientName.compareTo(u.getUserName()) == 0) && (clientPassword.compareTo(u.getPassword()) == 0)){  
				return true;
			}
		}
		return false;
	}
	
	public static void helpMenu(DataOutputStream out) throws Exception{
		BufferedReader buf = new BufferedReader(new FileReader("help.dat"));
		String line, doc = "";
		while((line = buf.readLine()) != null){
			out.writeBytes(line + "\n");
		}
		out.writeBytes("__end\n");
	}
	
	public static void saveFile(String a){
		System.out.println("Saving " + a);
		//Code for saving file on server goes here
	}
	
	public static void transferFile(String a){
		System.out.println("Sending " + a);
		//Code for sending file goes here 
	}
	
	
}//end class



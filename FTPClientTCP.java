/*

NAME:	Amit Balchan
ID:		00701069
COURSE:	COMP 3150 - Computer Networks
Group Project
DATE:	30th October, 2015

Descriptiopn: 

*/

import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;
class FTPClientTCP{

    public static void main(String argv[]) throws Exception
    {
        //Strings to hold user commands and responses from server
		String command;
		String response;
		//value used when user wants to quit program
		boolean exit = false;

        //input from user at keyboard
		BufferedReader inFromUser =
          new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("********************** FTPClient **********************\n\nEnter exit to terminate at any time\n");
		String str1 = null, str2 = null;
		System.out.print("\n__________________________________________________" +
		  "\n\nPlease enter your username(no spaces): ");
		str1 = inFromUser.readLine();
		if(str1.compareTo("exit") == 0){
			exit = true;
		}
		else{
			System.out.print("\nPlease enter your password(no spaces): ");
			str2 = inFromUser.readLine();
			if(str2.compareTo("exit") == 0){
				exit = true;
			}
			System.out.print("\n__________________________________________________");
        }
		
		while(exit == false){
			InetAddress IPAddress = InetAddress.getLocalHost();
			//Uses a local server. Delete for remote
			Socket clientSocket = new Socket(IPAddress,6789);//local
			//Socket clientSocket = new Socket("hostname", 6789);
			// hostname is the name of the computer that has the Server running
			
			//output Stream for sending commands to server
			DataOutputStream outToServer =
			  new DataOutputStream(clientSocket.getOutputStream());
		  
			//send username and password to server for validation
			outToServer.writeBytes(str1 + ";" + str2 + "\n");
		
			//input Stream for reading messages from server
			BufferedReader inFromServer =
			  new BufferedReader(new
			InputStreamReader(clientSocket.getInputStream()));

			//response from server's validation
			response = inFromServer.readLine();
			//continue asking for username-password combination until successful login
			while(response.compareTo("Invalid Username-password")==0 && exit!=true){
				System.out.println("\n\n" + response);
				System.out.print("\n__________________________________________________" +
				"\n\nPlease enter your username(no spaces): ");
				str1 = inFromUser.readLine();
				if(str1.compareTo("exit") == 0){
					exit = true;
				}
				else{
					System.out.print("\nPlease enter your password(no spaces): ");
					str2 = inFromUser.readLine();
					System.out.println("\n__________________________________________________");
					if(str2.compareTo("exit") == 0){
						exit = true;
					}
					else{
						outToServer.writeBytes(str1 + ";" + str2 + "\n");
						response = inFromServer.readLine();
					}
				}
			}
			
			//once logged in perform ftp commands until user exits 
			while(exit!=true){
				String buf = new String();
				String param = new String(" ");
				String fromUser = new String();
				System.out.print("\nFor a list of commands and description enter HELP\n\nCOMMAND: ");
				fromUser = inFromUser.readLine();
				Scanner sc = new Scanner(fromUser);
				command = sc.next();
				if(command.compareTo("exit")!=0){
					if(sc.hasNext()) param = sc.next();
					if(param.compareTo("exit")!=0){
						outToServer.writeBytes(command.toUpperCase() + '\n');
						outToServer.writeBytes(param + '\n');
						while((buf = inFromServer.readLine()).compareTo("__end")!=0){
							System.out.print(buf + "\n");
						}
					}
					else exit = true;
				}
				else exit = true;
			}
			clientSocket.close();

        }//end exit

    }//end main
}//end class

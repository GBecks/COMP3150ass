/*

NAME:	Amit Balchan
ID:		00701069
COURSE:	COMP 3150 - Computer Networks
Group Project
DATE:	30th October, 2015

Descriptiopn: 	

*/

import java.util.*;
import java.io.*;
import java.net.*;

public class User{
	private String userName;
	private String password;
	private String email;
	
	//constructor
	User(String a, String b , String c){
		this.userName = a;
		this.password = b;
		this.email = c;
	}
	
	//setters for changing only password and email
	public void setPassword(String a){
		this.password = a;
	}
	
	public void setEmail(String a){
		this.email = a;
	}
	
	//getters
	public String getUserName(){
		return this.userName;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public String getEmail(){
		return this.email;
	}
}
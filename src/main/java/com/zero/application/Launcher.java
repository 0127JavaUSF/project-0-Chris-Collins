package com.zero.application;

import com.zero.service.Menu;

//public class Launcher {
//
//	public static void main(String[] args) {
//		//initializes the loop flags
//		boolean firstTimeUser = false;
//		boolean loggedIn = false;
//		int response;
//		Menu teller = new Menu(); //creates a menu object to display the menus
//		teller.Welcome();
//		//this loop executes the login if the user is able to log in then they are able to proceed to user and account options
//		do {
//			firstTimeUser = teller.login(); //returns false if the user is unable to login and true on successful logins
//			
//		} while (!firstTimeUser); //only exits if login is true
//		System.out.println("Succesful Login");
//		//this loop displays the user options and account options when a account option is completed returns you to user options
//		do {
//			teller.userOptions();
//			response = teller.recieveInt(1,6);
//			loggedIn = teller.userAction(response);
//		} while (!loggedIn);//after logout the loop ends and the program ends
//		System.out.println("Bye!");
//
//	}
//
//}

public class Launcher {

	public static void main(String[] args) {
		//initializes the loop flags
		boolean firstTimeUser = false;
		boolean loggedIn = false;
		boolean changedUser = false;
		int response;
		Menu teller = new Menu(); //creates a menu object to display the menus
		do {
		
		teller.Welcome();
		//this loop executes the login if the user is able to log in then they are able to proceed to user and account options
		do {
			firstTimeUser = teller.login(); //returns false if the user is unable to login and true on successful logins
			
		} while (!firstTimeUser); //only exits if login is true
		System.out.println("Succesful Login");
		//this loop displays the user options and account options when a account option is completed returns you to user options
		do {
			teller.userOptions();
			response = teller.recieveInt(1,6);
			loggedIn = teller.userAction(response);
		} while (!loggedIn);//after logout the loop ends and the program ends
		}while(!changedUser);
		System.out.println("Bye!");

	}

}

package com.zero.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import com.zero.dao.AccountDao;
import com.zero.dao.UserDao;
import com.zero.dao.UsersAccountsDao;
import com.zero.pojo.Account;
import com.zero.pojo.User;


//Displays the menu options
public class Menu {
	//initializes the objects used to access accounts and display accounts
	private static Scanner scanner = new Scanner(System.in);
	private AccountDao accountDao = new AccountDao();
	private UserDao userDao = new UserDao();
	private UsersAccountsDao usersAccountsDao = new UsersAccountsDao();
	private static Account newAccount = new Account();
	private static User newUser;
	private static int actionedAccount;
	
	//displays the welcome screen
	public void Welcome()
	{
		System.out.println("Welcome to the Guy Fieri's credit union");
		System.out.println("Are you a new user?");
		System.out.println("Enter y for yes and n for no");
	}
	//runs through the login logic
	public boolean login()
	{
		String username,password,response,firstname,lastname,phoneNumber;
		int pin;
		boolean availability = true;
		boolean validation = false;
		response = scanner.next();
		//reads in if the user has an account or not
		if (response.equalsIgnoreCase("y")) {
			outerUser: do {
				System.out.println("Please create your username");
				username = scanner.next();
				if(checkUsernameLength(username) == false)
				{
					//goes back to the first while loop
					continue outerUser;
				}
				if (userDao.checkUsernameAvailability(username) == true)
				{
					System.out.println(username + " is already in use. please try another one");
				}
				else {
					availability = false;
				}
			}while(availability);
			do {
					System.out.println("Please create your password");
					password = scanner.next();
					if (!checkPasswordLength(password))
					{
						validation = false;
					}
					else
					{
						validation = true;
					}
					
			}while(!validation);
			do {
					System.out.println("Please enter your first name");
					firstname = scanner.next();
			
					if (!checkFirstNameLength(firstname))
					{
						
						validation = false;
					}
					else
					{
						validation = true;
					}
			}while(!validation);
			do {
					System.out.println("Please enter your last name");
					lastname = scanner.next();
					
					if (!checkLastNameLength(lastname))
					{
						validation = false;
					}
					else
					{
						validation = true;
					}
			}while(!validation);
			do {
					System.out.println("please create a pin");
						
					pin = scanner.nextInt();

					if (!checkPin(pin))
					{
						validation = false;
					}
					else
					{
						validation = true;
					}
			}while(!validation);
			do {
					System.out.println("Please enter your phone number");
					System.out.println("in ###-###-#### format");
					phoneNumber = scanner.next();
					if(!checkPhoneNumberLength(phoneNumber))
					{
						validation = false;
					}
					else if(!checkPhoneNumberHyphen(phoneNumber))
					{
						validation = false;
					}
					else if(!checkPhoneNumberIsNumeric(phoneNumber))
					{
						validation = false;
					}
					else
					{
						validation = true;
					}
			}while(!validation);
			
			newUser = new User(username, password, firstname,lastname,pin,phoneNumber);
			newUser = userDao.createUser(newUser);
			printUser(newUser);
			return true;

		} else if (response.equalsIgnoreCase("n")) 
		{
			boolean answer = false;
			do
			{
			System.out.println("Please enter your username");
			username = scanner.next();
			System.out.println("Please enter your password");
			password = scanner.next();
			if (userDao.authenticate(username, password)== true)
			{
				newUser =  userDao.getUser(username);
				answer = true;
			}
			else
			{
				System.out.println("Wrong username and password combination");
				answer = false;
			}
			}while(!answer);
			return userDao.authenticate(username, password);
		} else 
		{	
			System.out.println("Invalid input");
			return false;
		}
	}
	
	public void accountOptions()
	{
		System.out.println("Welcome to account options");
		System.out.println("1. Deposit");
		System.out.println("2. Withdraw");
		System.out.println("3. Transfer to");
		System.out.println("4. Transfer from");
		System.out.println("5. Check balance");
		System.out.println("6. Owner of account");
		System.out.println("7. Add joint user");
		System.out.println("8. Remove joint user");
		System.out.println("9. Close account");
		System.out.println("10. Return to the last menu");
		System.out.println("Select one of the available options");
		
	}
	public void userOptions()
	{
		System.out.println("Welcome to User Options");
		System.out.println("1. Create a Checking Account");
		System.out.println("2. Create a Savings Account");
		System.out.println("3. Change Password");
		System.out.println("4. Change Phone Number");
		System.out.println("5. Access accounts");
		System.out.println("6. log out");
	}

	public int recieveInt(int min, int max)
	{
		int selection = 0;
		do {
			selection = scanner.nextInt();
		if (selection > max || selection < min)
		{
			System.out.println("please enter a number between " + min + " and " + max);
		}
		}while(selection > max || selection < min);
		return selection;
	}
	public boolean userAction(int response)
	{
		if (response == 1)
		{
			newAccount = accountDao.createAccount(newUser, "c");
			printAccount(newAccount);
			return false;
		}
		else if (response == 2)
		{
			newAccount = accountDao.createAccount(newUser,"s");
			printAccount(newAccount);
			return false;
		}
		else if (response == 3)
		{
			String password;
			System.out.println("Enter your new password");
			password = scanner.next();
			userDao.changePassword(newUser, password);
			return false;
		}
		else if (response == 4)
		{
			boolean phone = false;
			String phonenumber;
			do {
			System.out.println("Enter your new phone number");
			System.out.println("in ###-###-#### format");
			phonenumber = scanner.next();
			if(!checkPhoneNumberLength(phonenumber))
			{
				phone = false;
			}
			else if(!checkPhoneNumberHyphen(phonenumber))
			{
				phone = false;
			}
			else if(!checkPhoneNumberIsNumeric(phonenumber))
			{
				phone = false;
			}
			else
			{
				phone = true;
			}
			}while(!phone);
			userDao.changePhoneNumber(newUser, phonenumber);
			return false;
		}
		else if (response == 5)
		{
			boolean allow = false;
			if (printAccounts(newUser))
			{
			System.out.println("Enter the id of the account you would like to access");
			 actionedAccount = scanner.nextInt();
			List<Account> userAccounts = usersAccountsDao.getUserAccounts(newUser.getId());
			ListIterator<Account> iter = userAccounts.listIterator();
			while(iter.hasNext()) {
			if (iter.next().getId() == actionedAccount)
			{
				allow = true;
			}

			}
			if(allow)
			{
				newAccount = accountDao.getAccount(actionedAccount);
				accountOptions();
				int action = recieveInt(1,10);
				accountAction(action);
			}else
			{
			System.out.println("You only have access to accounts you are an owner of");
			}
			}
			return false;
		}
		else if (response == 6)
		{
			return true;
		}
		else {
		return false;
		}
	}
	
	public void accountAction(int response)
	{
		if (response == 1)
		{
			System.out.println("Enter the amount you would like to deposit");
			double deposit = scanner.nextDouble();
			accountDao.deposit(newUser, actionedAccount, BigDecimal.valueOf(deposit));
		}
		else if (response == 2)
		{
			System.out.println("Enter the amount you would like to withdraw");
			double withdraw = scanner.nextDouble();
			accountDao.withdraw(newUser,actionedAccount, BigDecimal.valueOf(withdraw));
		}
		else if (response == 3)
		{
			System.out.println("Enter the account id of the acocunt you would like to transfer too");
			int destinationAccount = scanner.nextInt();
			System.out.println("Enter the amount you would like to transfer");
			double transfer = scanner.nextDouble();
			accountDao.transferTo(newUser, actionedAccount, destinationAccount, BigDecimal.valueOf(transfer));
		}
		else if (response == 4)
		{
			System.out.println("Enter the account id of the acocunt you would like to transfer from");
			int givingAccount = scanner.nextInt();
			System.out.println("Enter the amount you would like to transfer");
			double transfer = scanner.nextDouble();
			accountDao.transferFrom(newUser, actionedAccount, givingAccount, BigDecimal.valueOf(transfer));
		}
		else if (response == 5)
		{
			getBalance();
		}
		else if (response == 6)
		{
			boolean owner = false;
			owner = usersAccountsDao.isOwner(newUser, actionedAccount);
			if(owner)
			{
				System.out.println("You are the owner of this account");
			}
		}
		else if (response == 7)
		{
			boolean owner = false;
			System.out.println("Enter the id of the user you would like to add to the account");
			int newUserId = scanner.nextInt();
			owner = usersAccountsDao.isOwner(newUser, actionedAccount);
			if(owner == true)
			{
				usersAccountsDao.addJointUser(newUserId, actionedAccount);
			}
			else
			{
				System.out.println("You are not an owner of this account");
			}
		}
		else if (response == 8)
		{
			System.out.println("Enter the id of the user you would like to remove from the account");
			int userToRemove = scanner.nextInt();
			boolean owner = false;
			owner = usersAccountsDao.isOwner(newUser, actionedAccount);
			if(owner == true)
			{
				usersAccountsDao.removeJointUser(actionedAccount, userToRemove);
			}
			else
			{
				System.out.println("You are not an owner of this account");
			}
		}
		else if(response == 9)
		{
			boolean owner = false;
			owner = usersAccountsDao.isOwner(newUser, actionedAccount);
			if(owner == true)
			{
				accountDao.closeAccount(actionedAccount);
			}
			else
			{
				System.out.println("You are not an owner of this account");
			}
		}
		else if(response == 10)
		{
			System.out.println("Returning");
		}
	}
	
	private void getBalance()
	{
		
		BigDecimal balance = accountDao.getBalance(actionedAccount);
		System.out.println(balance);
		
	}
	
	private boolean printAccounts(User customer)
	{
		
		List<Account> userAccounts = usersAccountsDao.getUserAccounts(customer.getId());
		if(!userAccounts.isEmpty())
		{
		System.out.println("|   id   |Account Type |balance | interest |");
		userAccounts.forEach(a -> {
			System.out.printf("|%7d |%-11s  |%8.2f |%.0f%%       |%n",
					a.getId(), a.getAccount_Type(), a.getBalance() , (a.getInterest() * 100));			
		});
		return true;
		}
		else
		{
			System.out.println("No available accounts");
			return false;
		}
	}
	private void printAccount(Account account)
	{
		System.out.println("|   id   |Account Type |balance | interest |");
		System.out.printf("|%7d |%-11s  |%8.2f |%.0f%%       |%n",
					account.getId(), account.getAccount_Type(), account.getBalance() , (account.getInterest() * 100));			
	}
	private void printUser(User customer)
	{
		System.out.println("| id  |       username      |      first name     |      last name      |phone number |");
		System.out.printf("|%4d |%-20s |%-20s |%-20s |%-12s |%n",
		customer.getId(), customer.getUsername(), customer.getFirstName(), customer.getLastName(), customer.getPhoneNumber()); 
		
	}
	
	public boolean isNumeric(String num)
	{
	    try {
	        Integer.parseInt(num);
	    } catch (NumberFormatException e) {
	        return false;
	    }
	    return true;
	}
	public boolean checkUsernameLength(String username)
	{
		if(username.length() < 3 || username.length() > 20)
		{
			System.out.println("username does not meet length requirements ");
			return false;
		}
		else 
		{
			return true;
		}
	}
	public boolean checkPasswordLength(String password)
	{
		
		if (password.length() > 18 || password.length() < 6)
		{
			System.out.println("password does not meet length requirements");
			return false;
		}
		else
		{
			return true;
		}
	}
	public boolean checkFirstNameLength(String firstname)
	{

		if (firstname.length() > 40 || firstname.length() < 2)
		{
			System.out.println(firstname + " does not meet length requirements");
			return false;
		}
		else
		{
			return true;
		}
	}
	public boolean checkLastNameLength(String lastname)
	{
		if (lastname.length() > 40 || lastname.length() < 2)
		{
			System.out.println(lastname + " does not meet length requierments");
			return false;
		}
		else
		{
			return true;
		}
	}
	public boolean checkPin(int pin)
	{
		if (pin > 9999 || pin < 999)
		{
			System.out.println("invalid pin enter 4 numbers");
			return false;
		}
		else
		{
			return true;
		}
	}
	public boolean checkPhoneNumberLength(String phoneNumber)
	{
		if(phoneNumber.length() != 12)
		{
			System.out.println("invalid phone number provided");
			return  false;
		}
		else
		{
			return true;
		}
	}
	public boolean checkPhoneNumberHyphen(String phoneNumber)
	{
		if((phoneNumber.substring(3,4)).matches("-") == false || (phoneNumber.substring(7,8)).matches("-") == false)
		{
			System.out.println("invalid format");
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public boolean checkPhoneNumberIsNumeric(String phoneNumber)
	{
		if(isNumeric(phoneNumber.substring(0,3)) == false || isNumeric(phoneNumber.substring(4,7)) == false || isNumeric(phoneNumber.substring(8,12)) == false)
		{
			System.out.println("phone number must only contain digits");
			return false;
		}
		else
		{
			return true;
		}
	
	}
}

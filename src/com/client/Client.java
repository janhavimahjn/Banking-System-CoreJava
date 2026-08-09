package com.client;

import java.util.Scanner;

import com.data.Account;
import com.services.AccountServices;
import com.services.BusinessObjectCreator;

public class Client {

	public static void main(String[] args) {
		
		Scanner sc =new Scanner(System.in);
		
		AccountServices service = BusinessObjectCreator.getAccountService();
		
		int choice;
		
		do {
			System.out.println("\n==============================");
			System.out.println("BANKING MANAGEMENT SYSTEM");
			System.out.println("==============================");
			System.out.println("1. Create Account");
			System.out.println("2. Login");
			System.out.println("3. Deposit");
			System.out.println("4. Withdraw");
			System.out.println("5. Transfer Money");
			System.out.println("6. Check Balance");
			System.out.println("7. Mini Statement");
			System.out.println("8. Change Password");
			System.out.println("9. Close Account");
			System.out.println("10. Exit");
			
			System.out.println("Enter Choice : ");
			choice = sc.nextInt();
			
			switch(choice) {
			case 1: 
				Account account=new Account();
				
				System.out.println("Enter Account NUmber : ");
				account.setAccountNo(sc.nextInt());
				
				sc.nextLine();
				
				System.out.println("Enter Customer Name : ");
				account.setCustomerName(sc.nextLine());
				
				System.out.println("Enter Account Type (Savings/Current): ");
				account.setAccountType(sc.nextLine());
				
				System.out.println("Enter Mobile Number : ");
				account.setMobile(sc.nextLine());
				
				System.out.println("Enter Email : ");
				account.setEmail(sc.nextLine());
				
				System.out.println("Enter Address : ");
				account.setAddress(sc.nextLine());
				
				System.out.println("Create Password : ");
				account.setPassword(sc.nextLine());
				
				System.out.println("Enter Opening Balance : ");
				account.setBalance(sc.nextDouble());
				
				if(service.createAccount(account))
					System.out.println("Account created Successfully.");
				else 
					System.out.println("Account Creation Failed.");
				
				break;
			
			case 2:
				
				System.out.println("Enter Account Number : ");
				int accNo = sc.nextInt();
				
				sc.nextLine();
				
				System.out.println("Enter Password : ");
				String password = sc.nextLine();
				
				Account user = service.login(accNo,  password);
				
				if(user != null) {
					System.out.println("\nLogin Successful");
					
					System.out.println("Welcome : " + user.getCustomerName());
					System.out.println("Account No : " + user.getAccountNo());
					System.out.println("Balance : " + user.getBalance());
				} else {
					System.out.println("Invalid Account Number or Password");
				}
				break;
				
			case 3:
				System.out.print("Enter Account Number : ");
				int depAcc = sc.nextInt();
				
				System.out.print("Enter Amount : ");
				double depAmt = sc.nextDouble();
				
				double depBalance = service.deposit(depAcc,  depAmt);
				
				System.out.println("Updated Balance : " + depBalance);
				
				break;
			
			case 4:
				System.out.println("Enter Account Number : ");
				int wAcc = sc.nextInt();
				
				System.out.print("Enter Amount : ");
				double wAmt = sc.nextDouble();
				
				double bal = service.withdraw(wAcc, wAmt);
				
				System.out.println("Remaining Balance : " + bal);
				
				break;
				
			case 5:
				System.out.print("Enter Sender Account : ");
				int sender = sc.nextInt();
				
				System.out.print("Enter Receiver Account : ");
				int receiver = sc.nextInt();
				
				System.out.print("Enter Amount : ");
				double amount = sc.nextDouble();
				
				if(service.transferMoney(sender,  receiver,  amount))
					System.out.println("Money Transferred Successfully");
				else 
					System.out.println("Tranfer Failed");
				
				break;
				
			case 6:
				System.out.print("Enter Account Number : ");
				int checkAcc = sc.nextInt();
				
				System.out.println("Balance : " + service.checkBalance(checkAcc));
				
				break;
				
			case 7:
				System.out.print("Enter Account Number : ");
				int statementAcc = sc.nextInt();
				
				service.miniStatement(statementAcc);
				
				break;
				
			case 8:
				System.out.print("Enter Account Number : ");
				int changeAcc = sc.nextInt();
				
				sc.nextLine();
				
				System.out.print("Enter Old Password : ");
				String oldPassword = sc.nextLine();
				
				System.out.print("Enter New Password : ");
				String newPassword = sc.nextLine();
				
				if(service.changePassword(changeAcc, oldPassword, newPassword))
					System.out.println("Password Changed Successfully");
				else
					System.out.println("Password Change Failed");
				
				break;
				
			case 9:
				System.out.print("Enter Account Number : ");
				int closeAcc = sc.nextInt();
				
				if(service.closeAccount(closeAcc))
					System.out.println("Account Closed Successfully");
				else
					System.out.println("Unable to Close Account");
				
				break;
				
			case 10:
				System.out.println("Thank You for Using Banking System");
				break;
				
			default:
				System.out.println("Invalid Choice");
			}
		} while(choice != 10);
		
		sc.close();

	}

}

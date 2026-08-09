package com.valid;

public class AccountServiceValidation {
      
	public boolean validateName(String name) {
		if (name == null || name.trim().isEmpty()) {
			System.out.println("Name cannot be empty.");
			return false;
		}
		
		if (!name.matches("[A-Za-z]+")) {
			System.out.println("Name should contain only alphabets and spaces.");
			return false;
		}
		
		if (name.length() < 3 || name.length() > 30) {
			System.out.println("Name should be between 3 and 30 characters.");
			return false;
		}
		
		return true;
	}
	
	public boolean validateMobile(String mobile) {
		if (mobile == null) {
			System.out.println("Enter a valid 10-digit mobile number.");
			return false;
		}
		
		if (!mobile.matches("[6-9][0-9]{9}")) {
			System.out.println("Enter a valid 10-digit mobile number.");
			return false;
		}
		
		return true;
	}
	
	public boolean validateEmail(String email) {
		if (email == null || email.trim().isEmpty()) {
			System.out.println("Email cannot be empty.");
			return false;
		}
		
		String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		
		if (!email.matches(emailPattern)) {
			System.out.println("Invalid email format.");
			return false;
		}
		return true;
	}
	
	public boolean validatePassword(String password) {
		if (password == null || password.isEmpty()) {
			System.out.println("Password cannot be empty.");
			return false;
		}
		
		if (password.length() < 8) {
			System.out.println("Password must contain at least 8 characters.");
			return false;
		}
		
		if (!password.matches(".*[A-Z].*")) {
			System.out.println("Password must contain at least one uppercase letter.");
			return false;
		}
		
		if (!password.matches(".*[a-z].*")) {
			System.out.println("Password must contain at least one lowercase letter.");
			return false;
		}
		
		if (!password.matches(".*\\d.*")) {
			System.out.println("Password must contain at least one digit.");
			return false;
		}
		
		return true;
	}
	
	public boolean validateBalance(double balance) {
		if (balance < 1000) {
			System.out.println("Minimum opening balance should be 1000.");
			return false;
		}
		return true;
	}
	
	public boolean validateAmount(double amount) {
		if (amount <= 0) {
			System.out.println("Amount should be greater than zero.");
			return false;
		}
		return true;
	}
	
	public boolean validateAccountNumber(int accountNumber) {
		String acc = String.valueOf(accountNumber);
		
		if (!acc.matches("\\d{8,12}")) {
			System.out.println("Account number should contain 8 to 10 digits.");
			return false;
		}
		return true;
	}
}

package com.data;

public class Account {
     
	private int accountNo;
	private String customerName;
	private String accountType;
	private double balance;
	private String mobile;
	private String email;
	private String address;
	private String password;
	
	public Account() {
		
	}
	
	public Account(int accountNo, String customerName, String accountType,
            String mobile, String email, String address,
            String password, double balance) {
		super();
		this.accountNo = accountNo;
		this.customerName = customerName;
		this.accountType = accountType;
		this.balance = balance;
		this.mobile = mobile;
		this.email = email;
		this.address = address;
		this.password = password;
	}

	public int getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Account [accountNo=" + accountNo + ", customerName=" + customerName + ", accountType=" + accountType
				+ ", balance=" + balance + ", mobile=" + mobile + ", email=" + email + ", address=" + address
				+ ", password=" + password + ", getAccountNo()=" + getAccountNo() + ", getCustomerName()="
				+ getCustomerName() + ", getAccountType()=" + getAccountType() + ", getBalance()=" + getBalance()
				+ ", getMobile()=" + getMobile() + ", getEmail()=" + getEmail() + ", getAddress()=" + getAddress()
				+ ", getPassword()=" + getPassword() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
}

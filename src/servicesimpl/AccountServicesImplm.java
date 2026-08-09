package servicesimpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import com.data.Transaction;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.data.Account;
import com.services.AccountServices;
import com.util.DBConnection;

public class AccountServicesImplm implements AccountServices {

	@Override
	public boolean createAccount(Account account) {
		try {
			Connection con = DBConnection.getConnection();
			
			String sql = "INSERT INTO account VALUES(?,?,?,?,?,?,?,?)";
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1, account.getAccountNo());
			ps.setString(2, account.getCustomerName());
			ps.setString(3, account.getAccountType());
			ps.setString(4, account.getMobile());
			ps.setString(5, account.getEmail());
			ps.setString(6, account.getAddress());
			ps.setString(7, account.getPassword());
			ps.setDouble(8, account.getBalance());
			
			int row = ps.executeUpdate();
			return row > 0;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Account login(int accountNo, String password) {
		Account account = null;
		try {
			Connection con = DBConnection.getConnection();
			
			String sql = "SELECT * FROM account WHERE account_no=? AND password=?";
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1, accountNo);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				account = new Account();
				
				account.setAccountNo(rs.getInt("account_no"));
	            account.setCustomerName(rs.getString("customer_name"));
	            account.setAccountType(rs.getString("account_type"));
	            account.setMobile(rs.getString("mobile"));
	            account.setEmail(rs.getString("email"));
	            account.setAddress(rs.getString("address"));
	            account.setPassword(rs.getString("password"));
	            account.setBalance(rs.getDouble("balance"));

	            System.out.println("Login Successful");

	        } else {

	            System.out.println("Invalid Account Number or Password");

	        }
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		return account;
	}

	@Override
	public double deposit(int accountNo, double amount) {
		double balance = 0;
		
		try {
			Connection con = DBConnection.getConnection();
			
			String sql = "UPDATE account SET balance = balance + ? WHERE account_no = ?";
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setDouble(1, amount);
			ps.setInt(2, accountNo);
			
			int rows = ps.executeUpdate();
			
			if(rows > 0) {
				String transaction = "INSERT INTO transaction_history(account_no, transaction_type, amount) VALUES(?,?,?)";
				PreparedStatement ps1 = con.prepareStatement(transaction);
				
				ps1.setInt(1,  accountNo);
				ps1.setString(2, "Deposit");
				ps1.setDouble(3, amount);
				
				ps1.executeUpdate();
				
				balance = checkBalance(accountNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return balance;
	}

	@Override
	public double withdraw(int accountNo, double amount) {
		double balance = checkBalance(accountNo);
		
		if (balance < amount) {
			System.out.println("Insufficient Balance");
			return balance;
		}
		
		try {
			Connection con = DBConnection.getConnection();
			
			String sql = "UPDATE account SET balance = balance - ? WHERE account_no = ?";
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setDouble(1, amount);
			ps.setInt(2, accountNo);
			
			int rows = ps.executeUpdate();
			
			if (rows > 0) {
				String transaction = " INSERT INTO transaction_history(account_no, transaction_type, amount) VALUES(?,?,?)";
				
				PreparedStatement ps1 = con.prepareStatement(transaction);
				
				ps1.setInt(1,  accountNo);
				ps1.setString(2, "Withdraw");
				ps1.setDouble(3, amount);
				
				ps1.executeUpdate();
				
				balance = checkBalance(accountNo);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return balance;
	}

	@Override
	public boolean transferMoney(int sender, int receiver, double amount) {
		Connection con = null;
		
		try {
			con = DBConnection.getConnection();
			con.setAutoCommit(false);
			
			double senderBalance = checkBalance(sender);
			
			if (senderBalance < amount) {
				System.out.println("Insufficient Balance");
				return false;
			}
			
			PreparedStatement debit = con.prepareStatement("UPDATE account SET balance = balance - ? WHERE account_no=?");
			
			debit.setDouble(1, amount);
			debit.setInt(2, sender);
			
			debit.executeUpdate();
			
			PreparedStatement credit = con.prepareStatement("UPDATE account SET balance = balance + ? WHERE account_no=?");
			
			credit.setDouble(1, amount);
			credit.setInt(2, receiver);
			
			credit.executeUpdate();
			
			PreparedStatement ps1 = con.prepareStatement("INSERT INTO transaction_history(account_no, transaction_type, amount) VALUES(?,?,?)");
			
			ps1.setInt(1, sender);
			ps1.setString(2, "Transfer Sent");
			ps1.setDouble(3, amount);
			
			ps1.executeUpdate();
			
			PreparedStatement ps2 = con.prepareStatement("INSERT INTO transaction_history(account_no, transaction_type, amount) VALUES(?,?,?)");
			
			ps2.setInt(1, receiver);
			ps2.setString(2, "Transfer Received");
			ps2.setDouble(3, amount);
			
			ps2.executeUpdate();
			
			con.commit();
			
			return true;
		} catch (Exception e) {
			try {
				if (con != null)
					con.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public double checkBalance(int accountNo) {
		double balance = 0;
		try {
			Connection con = DBConnection.getConnection();
			
			String sql = " SELECT balance FROM account WHERE account_no=?";
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1, accountNo);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				balance = rs.getDouble("balance");
			}
		} catch (Exception e ) {
			e.printStackTrace();
		}
		return balance;
	}

	@Override
	public List<Transaction> miniStatement(int accountNo) {

	    List<Transaction> transactions = new ArrayList<>();

	    try {

	        Connection con = DBConnection.getConnection();

	        String sql =
	                "SELECT transaction_type, amount, transaction_date " +
	                "FROM transaction_history " +
	                "WHERE account_no = ? " +
	                "ORDER BY transaction_date DESC";

	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setInt(1, accountNo);

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {

	            Transaction transaction = new Transaction();

	            transaction.setTransactionType(
	                    rs.getString("transaction_type")
	            );

	            transaction.setAmount(
	                    rs.getDouble("amount")
	            );

	            transaction.setTransactionDate(
	                    rs.getDate("transaction_date")
	            );

	            transactions.add(transaction);
	        }

	        rs.close();
	        ps.close();
	        con.close();

	    } catch (Exception e) {

	        e.printStackTrace();
	    }

	    return transactions;
	}

	@Override
	public boolean changePassword(int accountNo, String oldPassword, String newPassword) {
		try {
			Connection con = DBConnection.getConnection();
			
			String check = "SELECT * FROM account WHERE account_no=? AND password=?";
			
			PreparedStatement ps = con.prepareStatement(check);
			
			ps.setInt(1, accountNo);
			ps.setString(2, oldPassword);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				String update ="UPDATE account SET password=? WHERE account_no=?";
				
				PreparedStatement ps1 = con.prepareStatement(update);
				
				ps1.setString(1, newPassword);
				ps1.setInt(2, accountNo);
				
				int rows = ps1.executeUpdate();
				
				return rows > 0;
			
			} else {
				System.out.println("Old Password is Incorrect");
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean closeAccount(int accountNo) {
		try {

	        Connection con = DBConnection.getConnection();

	        String sql = "DELETE FROM account WHERE account_no=?";

	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setInt(1, accountNo);

	        int rows = ps.executeUpdate();

	        if(rows > 0) {
	            System.out.println("Account closed successfully.");
	            return true;
	        }

	    } catch(Exception e) {
	        e.printStackTrace();
	    }
		return false;
	}
      
}

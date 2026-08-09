package com.client;

import java.awt.*;
import javax.swing.*;

import com.data.Account;
import com.services.AccountServices;

public class DashboardFrame extends JFrame {

    private Account account;
    private AccountServices service;

    private JLabel balanceLabel;

    public DashboardFrame(
            Account account,
            AccountServices service) {

        this.account = account;
        this.service = service;

        setTitle("Banking Management System - Dashboard");

        setSize(700, 550);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        setVisible(true);
    }

    private void createUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(
                new Color(240, 245, 250)
        );

        // ================= HEADER =================

        JPanel header =
                new JPanel(new GridLayout(2, 1));

        header.setBackground(
                new Color(25, 70, 120)
        );

        JLabel welcome =
                new JLabel(
                        "Welcome, " +
                        account.getCustomerName(),
                        SwingConstants.CENTER
                );

        welcome.setForeground(Color.WHITE);

        welcome.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        balanceLabel =
                new JLabel(
                        "Balance: ₹" +
                        account.getBalance(),
                        SwingConstants.CENTER
                );

        balanceLabel.setForeground(Color.WHITE);

        balanceLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        header.add(welcome);
        header.add(balanceLabel);

        mainPanel.add(
                header,
                BorderLayout.NORTH
        );

        // ================= BUTTON PANEL =================

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(
                                4,
                                2,
                                20,
                                20
                        )
                );

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        50,
                        30,
                        50
                )
        );

        buttonPanel.setBackground(
                new Color(240, 245, 250)
        );

        JButton deposit =
                new JButton("DEPOSIT");

        JButton withdraw =
                new JButton("WITHDRAW");

        JButton transfer =
                new JButton("TRANSFER MONEY");

        JButton balance =
                new JButton("CHECK BALANCE");

        JButton statement =
                new JButton("MINI STATEMENT");

        JButton password =
                new JButton("CHANGE PASSWORD");

        JButton close =
                new JButton("CLOSE ACCOUNT");

        JButton logout =
                new JButton("LOGOUT");

        buttonPanel.add(deposit);
        buttonPanel.add(withdraw);
        buttonPanel.add(transfer);
        buttonPanel.add(balance);
        buttonPanel.add(statement);
        buttonPanel.add(password);
        buttonPanel.add(close);
        buttonPanel.add(logout);

        mainPanel.add(
                buttonPanel,
                BorderLayout.CENTER
        );

        // ================= ACTIONS =================

        deposit.addActionListener(
                e -> depositMoney()
        );

        withdraw.addActionListener(
                e -> withdrawMoney()
        );

        transfer.addActionListener(
                e -> transferMoney()
        );

        balance.addActionListener(
                e -> checkBalance()
        );

        statement.addActionListener(
                e -> showStatement()
        );

        password.addActionListener(
                e -> changePassword()
        );

        close.addActionListener(
                e -> closeAccount()
        );

        logout.addActionListener(
                e -> logout()
        );

        add(mainPanel);
    }

    // =====================================================
    // DEPOSIT
    // =====================================================

    private void depositMoney() {

        String input =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Deposit Amount:"
                );

        if (input == null)
            return;

        try {

            double amount =
                    Double.parseDouble(input);

            if (amount <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Amount must be greater than 0."
                );

                return;
            }

            double newBalance =
                    service.deposit(
                            account.getAccountNo(),
                            amount
                    );

            account.setBalance(newBalance);

            updateBalance();

            JOptionPane.showMessageDialog(
                    this,
                    "Deposit Successful!\n\n" +
                    "Amount: ₹" + amount +
                    "\nNew Balance: ₹" + newBalance
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid amount."
            );
        }
    }

    // =====================================================
    // WITHDRAW
    // =====================================================

    private void withdrawMoney() {

        String input =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Withdrawal Amount:"
                );

        if (input == null)
            return;

        try {

            double amount =
                    Double.parseDouble(input);

            if (amount <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Amount must be greater than 0."
                );

                return;
            }

            if (amount > account.getBalance()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Insufficient Balance.",
                        "Withdrawal Failed",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            double newBalance =
                    service.withdraw(
                            account.getAccountNo(),
                            amount
                    );

            account.setBalance(newBalance);

            updateBalance();

            JOptionPane.showMessageDialog(
                    this,
                    "Withdrawal Successful!\n\n" +
                    "Amount: ₹" + amount +
                    "\nRemaining Balance: ₹" +
                    newBalance
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid amount."
            );
        }
    }

    // =====================================================
    // TRANSFER
    // =====================================================

    private void transferMoney() {

        JTextField receiverField =
                new JTextField();

        JTextField amountField =
                new JTextField();

        Object[] fields = {

                "Receiver Account Number:",
                receiverField,

                "Amount:",
                amountField
        };

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        fields,
                        "Transfer Money",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (result != JOptionPane.OK_OPTION)
            return;

        try {

            int receiver =
                    Integer.parseInt(
                            receiverField.getText()
                    );

            double amount =
                    Double.parseDouble(
                            amountField.getText()
                    );

            if (amount <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Amount must be greater than 0."
                );

                return;
            }

            if (amount > account.getBalance()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Insufficient Balance."
                );

                return;
            }

            boolean success =
                    service.transferMoney(
                            account.getAccountNo(),
                            receiver,
                            amount
                    );

            if (success) {

                account.setBalance(
                        account.getBalance() - amount
                );

                updateBalance();

                JOptionPane.showMessageDialog(
                        this,
                        "Money Transferred Successfully!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Transfer Failed.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter valid account number and amount."
            );
        }
    }

    // =====================================================
    // BALANCE
    // =====================================================

    private void checkBalance() {

        double balance =
                service.checkBalance(
                        account.getAccountNo()
                );

        account.setBalance(balance);

        updateBalance();

        JOptionPane.showMessageDialog(
                this,
                "Account Number: " +
                account.getAccountNo() +
                "\n\nAvailable Balance: ₹" +
                balance,
                "Balance Inquiry",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =====================================================
    // MINI STATEMENT
    // =====================================================

    private void showStatement() {

        java.util.List<com.data.Transaction> transactions =
                service.miniStatement(account.getAccountNo());

        String[] columns = {
                "Transaction Type",
                "Amount",
                "Date"
        };

        javax.swing.table.DefaultTableModel model =
                new javax.swing.table.DefaultTableModel(columns, 0) {

                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

        for (com.data.Transaction transaction : transactions) {

            Object[] row = {

                    transaction.getTransactionType(),

                    String.format(
                            "₹ %.2f",
                            transaction.getAmount()
                    ),

                    transaction.getTransactionDate()
            };

            model.addRow(row);
        }

        JTable table = new JTable(model);

        table.setRowHeight(30);

        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        table.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        // Column widths

        table.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(180);

        table.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(120);

        table.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(150);

        JScrollPane scrollPane =
                new JScrollPane(table);

        scrollPane.setPreferredSize(
                new Dimension(500, 300)
        );

        JPanel panel = new JPanel(
                new BorderLayout()
        );

        JLabel title =
                new JLabel(
                        "MINI STATEMENT",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        panel.add(
                title,
                BorderLayout.NORTH
        );

        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        JOptionPane.showMessageDialog(
                this,
                panel,
                "Transaction History",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    // =====================================================
    // CHANGE PASSWORD
    // =====================================================

    private void changePassword() {

        JPasswordField oldPassword =
                new JPasswordField();

        JPasswordField newPassword =
                new JPasswordField();

        Object[] fields = {

                "Old Password:",
                oldPassword,

                "New Password:",
                newPassword
        };

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        fields,
                        "Change Password",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (result != JOptionPane.OK_OPTION)
            return;

        boolean success =
                service.changePassword(
                        account.getAccountNo(),
                        new String(
                                oldPassword.getPassword()
                        ),
                        new String(
                                newPassword.getPassword()
                        )
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password Changed Successfully!"
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Old Password is Incorrect.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // CLOSE ACCOUNT
    // =====================================================

    private void closeAccount() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to close your account?",
                        "Close Account",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION)
            return;

        boolean success =
                service.closeAccount(
                        account.getAccountNo()
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Account Closed Successfully."
            );

            dispose();

            new LoginFrame();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to close account.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // LOGOUT
    // =====================================================

    private void logout() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice == JOptionPane.YES_OPTION) {

            dispose();

            new LoginFrame();
        }
    }

    // =====================================================
    // UPDATE BALANCE
    // =====================================================

    private void updateBalance() {

        balanceLabel.setText(
                "Balance: ₹" +
                String.format(
                        "%.2f",
                        account.getBalance()
                )
        );
    }
}
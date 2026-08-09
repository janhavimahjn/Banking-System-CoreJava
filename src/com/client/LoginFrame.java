package com.client;

import java.awt.*;
import javax.swing.*;

import com.data.Account;
import com.services.AccountServices;
import com.services.BusinessObjectCreator;

public class LoginFrame extends JFrame {

    private JTextField accountField;
    private JPasswordField passwordField;

    private AccountServices service;

    public LoginFrame() {

        service = BusinessObjectCreator.getAccountService();

        setTitle("Banking Management System - Login");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();

        setVisible(true);
    }

    private void createUI() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 245, 250));

        // ---------------- HEADER ----------------

        JLabel title = new JLabel(
                "BANKING MANAGEMENT SYSTEM",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(25, 70, 120));

        mainPanel.add(title, BorderLayout.NORTH);

        // ---------------- FORM ----------------

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 15));

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(40, 50, 20, 50)
        );

        formPanel.setBackground(new Color(240, 245, 250));

        JLabel accountLabel = new JLabel("Account Number:");
        JLabel passwordLabel = new JLabel("Password:");

        accountField = new JTextField();

        passwordField = new JPasswordField();

        JButton loginButton = new JButton("LOGIN");
        JButton registerButton = new JButton("CREATE ACCOUNT");

        formPanel.add(accountLabel);
        formPanel.add(accountField);

        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        formPanel.add(loginButton);
        formPanel.add(registerButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // ---------------- BUTTON ACTIONS ----------------

        loginButton.addActionListener(e -> login());

        registerButton.addActionListener(e -> createAccount());

        add(mainPanel);
    }

    // ================= LOGIN =================

    private void login() {

        try {

            int accountNo =
                    Integer.parseInt(accountField.getText().trim());

            String password =
                    new String(passwordField.getPassword());

            if (password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter password."
                );

                return;
            }

            Account account =
                    service.login(accountNo, password);

            if (account != null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login Successful!"
                );

                dispose();

                new DashboardFrame(account, service);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Account Number or Password",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Account Number must be numeric.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ================= CREATE ACCOUNT =================

    private void createAccount() {

        JTextField accountNo =
                new JTextField();

        JTextField name =
                new JTextField();

        JTextField accountType =
                new JTextField();

        JTextField mobile =
                new JTextField();

        JTextField email =
                new JTextField();

        JTextField address =
                new JTextField();

        JPasswordField password =
                new JPasswordField();

        JTextField balance =
                new JTextField();

        Object[] fields = {

                "Account Number:",
                accountNo,

                "Customer Name:",
                name,

                "Account Type:",
                accountType,

                "Mobile:",
                mobile,

                "Email:",
                email,

                "Address:",
                address,

                "Password:",
                password,

                "Opening Balance:",
                balance
        };

        int result = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Create New Account",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {

            try {

                Account account = new Account();

                account.setAccountNo(
                        Integer.parseInt(accountNo.getText())
                );

                account.setCustomerName(
                        name.getText()
                );

                account.setAccountType(
                        accountType.getText()
                );

                account.setMobile(
                        mobile.getText()
                );

                account.setEmail(
                        email.getText()
                );

                account.setAddress(
                        address.getText()
                );

                account.setPassword(
                        new String(password.getPassword())
                );

                account.setBalance(
                        Double.parseDouble(balance.getText())
                );

                boolean created =
                        service.createAccount(account);

                if (created) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Account Created Successfully!"
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Account Creation Failed.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter valid information.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new LoginFrame()
        );
    }
}
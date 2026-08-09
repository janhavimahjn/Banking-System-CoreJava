# Banking System

A desktop-based Banking Management System developed using **Core Java, Java Swing, JDBC, and Oracle Database**. The application provides a graphical user interface for customers to manage their bank accounts and perform basic banking operations.

## Features

- Customer account creation
- Customer login using account number and password
- Deposit money
- Withdraw money
- Transfer money between accounts
- Check account balance
- View mini statement
- Change password
- Close account
- Transaction history
- Input validation
- Oracle database connectivity using JDBC
- Swing-based graphical user interface

## Technologies Used

- **Java**
- **Core Java**
- **Java Swing**
- **JDBC**
- **Oracle Database**
- **Eclipse IDE**
- **Git & GitHub**

## Database Connection

Connection con = DriverManager.getConnection(
    "jdbc:oracle:thin:@localhost:1521/XEPDB1",
    "banking",
    "YOUR_PASSWORD"
);



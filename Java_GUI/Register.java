package com.divya.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends JFrame {
    JTextField name = new JTextField();
    JTextField phonenum = new JTextField();
    JTextField cardnum = new JTextField();
    JTextField expirydate = new JTextField();
    JTextField bank = new JTextField();
    JTextField organization = new JTextField();
    JButton submit = new JButton("Submit");
    MainFrame mainFrame = null;
    Register frame = this;

    SQL adduser;
    ResultSet rs;
    int userid;
    String sqlcode;

    public Register(SQL sqlo, MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        adduser = sqlo;

        // Set layout and panel
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new GridLayout(6, 2, 10, 10)); // Added gaps between fields

        // Adding fields to the panel
        jpanel.add(new JLabel("Name:"));        jpanel.add(name);
        jpanel.add(new JLabel("Phone Number:")); jpanel.add(phonenum);
        jpanel.add(new JLabel("Card Number:"));  jpanel.add(cardnum);
        jpanel.add(new JLabel("Expiry Date (YYYY-MM-DD):")); jpanel.add(expirydate);
        jpanel.add(new JLabel("Bank:"));         jpanel.add(bank);
        jpanel.add(new JLabel("Card Issue Organization:")); jpanel.add(organization);

        // Add background color to the panel and buttons
        jpanel.setBackground(new Color(230, 240, 255)); // Light blue background for panel
        submit.setBackground(new Color(144, 238, 144)); // Light green background for submit button
        submit.setForeground(Color.BLACK);

        // Set font for better visibility
        name.setFont(new Font("Arial", Font.PLAIN, 14));
        phonenum.setFont(new Font("Arial", Font.PLAIN, 14));
        cardnum.setFont(new Font("Arial", Font.PLAIN, 14));
        expirydate.setFont(new Font("Arial", Font.PLAIN, 14));
        bank.setFont(new Font("Arial", Font.PLAIN, 14));
        organization.setFont(new Font("Arial", Font.PLAIN, 14));

        // Add the panel to the frame
        this.add(jpanel, BorderLayout.CENTER);

        // Submit button
        submit.addActionListener(new RegisterListener());
        submit.setPreferredSize(new Dimension(20, 40));
        this.add(submit, BorderLayout.SOUTH);

        // Add padding to the window
        ((JComponent) this.getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Added padding
    }

    public static void invoke(SQL sqlo, MainFrame mainFrame) {
        JFrame register = new Register(sqlo, mainFrame);
        register.setVisible(true);
        register.setSize(500, 300); // Increased size of the window
        register.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        register.setLocationRelativeTo(null);
        register.setTitle("Register a New User");
    }

    class RegisterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            sqlcode = "select max(userid) from users;"; // Get the max userid from user table
            rs = adduser.QueryExchte(sqlcode);
            try {
                rs.next();
                userid = rs.getInt(1) + 1;
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            String Name = name.getText();
            String Pnum = phonenum.getText();
            String Cardnum = cardnum.getText();
            String Expirydate = expirydate.getText();
            String Bank = bank.getText();
            String Org = organization.getText();

            if (Name.trim().isEmpty() || Pnum.trim().isEmpty() || Cardnum.trim().isEmpty() || Expirydate.trim().isEmpty() || Bank.trim().isEmpty() || Org.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!isValidCard(Cardnum)) {
                JOptionPane.showMessageDialog(null, "The card number must be in the format XXXX XXXX XXXX XXXX.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!isValidDate(Expirydate)) {
                JOptionPane.showMessageDialog(null, "The expiry date format is incorrect. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                sqlcode = "insert into users values (" + userid + ", '" + Name + "', '" + Pnum + "');"; // Insert a new buyer into the user table
                adduser.WriteExcute(sqlcode);
                sqlcode = "insert into bankcard values ('" + Cardnum + "', '" + Expirydate + "', '" + Bank + "');";
                adduser.WriteExcute(sqlcode);
                sqlcode = "insert into creditcard values ('" + Cardnum + "', " + userid + ", '" + Org + "');";
                adduser.WriteExcute(sqlcode);
                sqlcode = "insert into Buyer values(" + userid + ");";
                adduser.WriteExcute(sqlcode);

                JOptionPane.showMessageDialog(null, "You have successfully registered. Your user ID is " + userid + ". Please keep it for future logins.", "Registration Successful", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/resources/success.png")));

                try {
                    mainFrame.setUserid(userid);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                mainFrame.setVisible(true);
                mainFrame.setAddAddressButtonEnable(true);
                frame.dispose();
            }
        }
    }

    // Check if the card number is valid (XXXX XXXX XXXX XXXX format)
    public boolean isValidCard(String cardnum) {
        String cardPattern = "\\d{4} \\d{4} \\d{4} \\d{4}";
        Pattern pattern = Pattern.compile(cardPattern);
        Matcher match = pattern.matcher(cardnum);
        return match.matches();
    }

    // Check if the expiry date is valid (YYYY-MM-DD format)
    public boolean isValidDate(String date) {
        String datePattern = "\\d{4}-\\d{2}-\\d{2}";
        Pattern pattern = Pattern.compile(datePattern);
        Matcher match = pattern.matcher(date);
        return match.matches();
    }
}


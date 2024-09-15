package com.divya.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {
    JTextField userid = new JTextField();
    JTextField phonenumber = new JTextField();
    JButton login = new JButton("Log in");
    JButton back = new JButton("Back");

    SQL loginsql;
    ResultSet rs;
    String sqlcode;
    String uid;
    String pnum;
    MainFrame mainFrame = null;
    Login frame = this;

    public Login(SQL sqlo, MainFrame mainFrame) {
        loginsql = sqlo;
        this.mainFrame = mainFrame;

        // Customize text fields
        userid.setFont(new Font("Arial", Font.PLAIN, 14));
        phonenumber.setFont(new Font("Arial", Font.PLAIN, 14));

        // Customize panel and components
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(2, 2, 10, 10));
        panel1.add(new JLabel("User ID: "));
        panel1.add(userid);
        panel1.add(new JLabel("Phone Number: "));
        panel1.add(phonenumber);

        // Set background colors
        panel1.setBackground(new Color(255, 255, 255)); // Pale mint color

        this.add(panel1, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 25, 25));

        // Customize button fonts and colors
        login.setFont(new Font("Arial", Font.BOLD, 14));
        back.setFont(new Font("Arial", Font.BOLD, 14));

        login.setBackground(new Color(144, 238, 144));  // Light green
        login.setForeground(Color.BLACK);

        back.setBackground(new Color(255, 182, 193));   // Light pink
        back.setForeground(Color.BLACK);

        buttonPanel.add(login);
        buttonPanel.add(back);
        buttonPanel.setBackground(new Color(255, 255 , 255));  // Alice blue

        login.addActionListener(new loginListener());
        back.addActionListener(new loginListener());
        this.add(buttonPanel, BorderLayout.SOUTH);

        // Slight padding around the components
        this.setPadding(15);
    }

    public static void invoke(SQL sqlo, MainFrame mainFrame) {
        JFrame login = new Login(sqlo, mainFrame);
        login.setTitle("User Login");
        login.setLocationRelativeTo(null);
        login.setSize(450, 200);  // Increased window size
        login.setVisible(true);
        login.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    class loginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == login) {
                uid = userid.getText();
                pnum = phonenumber.getText();
                if (uid.trim().isEmpty() & pnum.trim().isEmpty())
                    JOptionPane.showMessageDialog(null, "User ID and phone number cannot be blank", "Error", JOptionPane.ERROR_MESSAGE);
                else if (uid.trim().isEmpty())
                    JOptionPane.showMessageDialog(null, "You must input your user ID", "Error", JOptionPane.ERROR_MESSAGE);
                else if (pnum.trim().isEmpty())
                    JOptionPane.showMessageDialog(null, "You must input your phone number", "Error", JOptionPane.ERROR_MESSAGE);
                else if (!isBuyer(uid))
                    JOptionPane.showMessageDialog(null, "You have not registered. You must register before login", "Error", JOptionPane.ERROR_MESSAGE);
                else
                    try {
                        if (pnum.equals(getResult())) {
                            int userid = Integer.parseInt(uid);
                            JOptionPane.showMessageDialog(null, "You have logged in successfully", "Login Successful", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Login.class.getResource("success.png")));

                            mainFrame.setUserid(userid);
                            mainFrame.setAddAddressButtonEnable(true);
                            mainFrame.setVisible(true);
                            frame.dispose();
                        } else
                            JOptionPane.showMessageDialog(null, "The user ID or phone number is incorrect", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    } catch (NumberFormatException | HeadlessException | SQLException e1) {
                        e1.printStackTrace();
                    }
            } else if (e.getSource() == back) {
                mainFrame.setVisible(true);
                frame.dispose();
            }
        }
    }

    public String getResult() throws SQLException {
        sqlcode = "select phoneNumber from users where userid = " + uid;
        rs = loginsql.QueryExchte(sqlcode);
        rs.next();
        String pnumInDb = rs.getString(1);
        System.out.println("For user ID " + uid + " the phone number is " + pnumInDb);
        return pnumInDb;
    }

    public boolean isBuyer(String uid) {
        sqlcode = "select userid from buyer where userid = " + uid;
        rs = loginsql.QueryExchte(sqlcode);
        int rowCount = 0;
        try {
            rs.last();
            rowCount = rs.getRow();
            rs.first();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowCount != 0;
    }

    // Utility method to set padding for the window content
    private void setPadding(int padding) {
        ((JComponent) this.getContentPane()).setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
    }
}


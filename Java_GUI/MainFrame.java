package com.divya.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MainFrame extends JFrame {

    JButton loginButton = new JButton("Login");
    JButton registerButton = new JButton("Register");
    JButton searchProductButton = new JButton("Search Product");
    JButton buyButton = new JButton("View Shopping Cart and Purchase");
    JButton addAddressButton = new JButton("Add a new Address");
    JButton QuitButton = new JButton("Quit");
    JLabel noticeString = new JLabel("Please login or Register first");
    JLabel reminderString = new JLabel("");
    MainFrame mainFrame = this;
    SQL sql = null;
    int userid;

    public MainFrame() throws SQLException {
        // Initialize the SQL connection
        sql = new SQL();
        userid = 0;

        // Main panel configuration
        JPanel mainArc = new JPanel();
        mainArc.setLayout(new GridLayout(6, 1, 25, 0));
        mainArc.setSize(200, 400);

        // Pastel background for the main panel
        mainArc.setBackground(new Color(240, 248, 255)); // Light pastel blue
        mainArc.setBorder(BorderFactory.createEmptyBorder()); // White border (or no border)

        // Button customization with pastel colors
        stylePastelButton(loginButton, new Color(255, 182, 193), Color.DARK_GRAY);   // Light pink for Login
        stylePastelButton(registerButton, new Color(173, 216, 230), Color.DARK_GRAY); // Light blue for Register
        stylePastelButton(addAddressButton, new Color(144, 238, 144), Color.DARK_GRAY); // Light green for Add Address
        stylePastelButton(searchProductButton, new Color(255, 228, 181), Color.DARK_GRAY); // Light yellow for Search Product
        stylePastelButton(buyButton, new Color(255, 218, 185), Color.DARK_GRAY);   // Peach for Buy button
        stylePastelButton(QuitButton, new Color(255, 160, 122), Color.DARK_GRAY);   // Light coral for Quit button

        // Button Listeners
        MainButtonListener listener = new MainButtonListener(sql);
        loginButton.addActionListener(listener);
        registerButton.addActionListener(listener);
        addAddressButton.addActionListener(listener);
        addAddressButton.setEnabled(false);
        searchProductButton.addActionListener(listener);
        searchProductButton.setEnabled(false);
        buyButton.addActionListener(listener);
        buyButton.setEnabled(false);
        QuitButton.addActionListener(listener);

        // Add buttons to main panel
        mainArc.add(loginButton);
        mainArc.add(registerButton);
        mainArc.add(addAddressButton);
        mainArc.add(searchProductButton);
        mainArc.add(buyButton);
        mainArc.add(QuitButton);

        // Logo Panel
        JPanel logoPanel = new JPanel();
        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/resources/logo.png")));
        logoPanel.setBackground(new Color(255, 255, 255)); // White background for logo
        logoPanel.add(imageLabel);

        // Notice and reminder labels
        noticeString.setFont(new Font("Arial", Font.PLAIN, 16));
        reminderString.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel stringPanel = new JPanel();
        stringPanel.add(noticeString);
        stringPanel.setBackground(new Color(240, 248, 255)); // Light pastel blue for the notice

        JPanel stringPanel2 = new JPanel();
        stringPanel2.add(reminderString);
        stringPanel2.setBackground(new Color(240, 248, 255)); // Light pastel blue for the reminder

        // Right panel configuration
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(0, 1));
        rightPanel.setBackground(new Color(255, 255, 255)); // White background for right panel
        rightPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 0)); // No border
        rightPanel.add(logoPanel);
        rightPanel.add(stringPanel);
        rightPanel.add(stringPanel2);

        this.getContentPane().setBackground(new Color(240, 248, 255));
        
        // Add components to frame
        this.add(mainArc, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);
    }

    // Method to style buttons with pastel colors and bold text
    private void stylePastelButton(JButton button, Color bgColor, Color textColor) {
        button.setBackground(bgColor); // Pastel background color
        button.setForeground(textColor); // Text color
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Bold text
        button.setFocusPainted(false); // Remove focus highlight
        button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1)); // Subtle border
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public void setUserid(int id) throws SQLException {
        userid = id;
        noticeString.setText("You have logged in with user id " + id);
        setAddAddressButtonEnable(true);
        setLoginButtonEnable(false);
        setRegisterEnable(false);

        String sqlCode = "select * from address where userid = " + id;
        java.sql.ResultSet result = sql.QueryExchte(sqlCode);
        if (result.next()) {
            setSearchAndBuyButtonEnable(true);
        } else {
            reminderString.setText("Please add an address before shopping.");
        }
    }

    public void setAddAddressButtonEnable(boolean b) {
        addAddressButton.setEnabled(b);
    }

    public void setSearchAndBuyButtonEnable(boolean b) {
        reminderString.setText("Please enjoy your shopping!");
        searchProductButton.setEnabled(b);
        buyButton.setEnabled(b);
    }

    public void setLoginButtonEnable(boolean b) {
        loginButton.setEnabled(b);
    }

    public void setRegisterEnable(boolean b) {
        registerButton.setEnabled(b);
    }

    public static void main(String[] args) throws Exception {
        MainFrame frame = new MainFrame();
        frame.setTitle("E-Shopping");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);  // Larger window for better layout
        frame.setVisible(true);
    }

    public class MainButtonListener implements ActionListener {
        SQL sql = null;

        public MainButtonListener(SQL sqlpassed) {
            sql = sqlpassed;
        }

        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == loginButton) {
                Login.invoke(sql, mainFrame);
                mainFrame.setVisible(false);
            } else if (event.getSource() == registerButton) {
                Register.invoke(sql, mainFrame);
                mainFrame.setVisible(false);
            } else if (event.getSource() == addAddressButton) {
                AddAddress.invoke(userid, sql, mainFrame);
                mainFrame.setVisible(false);
            } else if (event.getSource() == searchProductButton) {
                SearchFrame.invoke(userid, sql, mainFrame);
                mainFrame.setVisible(false);
            } else if (event.getSource() == buyButton) {
                String sqlCode = "select P.name, S.addTime, S.quantity, P.pid, P.price from Save_To_Shopping_Cart S, Product P where S.pid = P.pid and S.userid = " + userid + ";";
                java.sql.ResultSet rs = sql.QueryExchte(sqlCode);
                int rowCount = 0;
                try {
                    rs.last();
                    rowCount = rs.getRow();
                    rs.first();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (rowCount == 0)
                    JOptionPane.showMessageDialog(null, "No product is added into the cart yet", "No Result", JOptionPane.OK_OPTION);
                else
                    try {
                        SetUpOrderFrame.invoke(userid, sql, rs, mainFrame);
                        mainFrame.setVisible(false);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            } else if (event.getSource() == QuitButton) {
                System.exit(0);
            }
        }
    }
}


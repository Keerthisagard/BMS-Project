package com.cit.project.bms.in;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -671807527452645677L;
	@SuppressWarnings("unused")
	private BookManagementSystemEXT parentFrame;
    private JTextField userIdField;
    private JPasswordField passwordField;

    public LoginPanel(BookManagementSystemEXT parentFrame) {
        this.parentFrame = parentFrame;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(300, 200)); 

        JLabel welcomeLabel = new JLabel("Welcome to Book Management System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(welcomeLabel);
        add(Box.createRigidArea(new Dimension(0, 10))); 

        add(createLabelAndTextField("User ID:", userIdField = new JTextField(15)));
        add(createLabelAndPasswordField("Password:", passwordField = new JPasswordField(15)));

        JButton loginButton = new JButton("Login");

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        StylishButtonStyle.applyStyle(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userIdField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

               
                boolean isAuthenticated = authenticateUser(userId, password);

                if (isAuthenticated) {
                    parentFrame.authenticateUser(userId);
                } else {
                    JOptionPane.showMessageDialog(LoginPanel.this, "Invalid user ID or password", "Error", JOptionPane.ERROR_MESSAGE);
                }

                passwordField.setText("");
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loginButton);

        add(buttonPanel);
    }

    private JPanel createLabelAndTextField(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(80, 25));

        panel.add(label);
        panel.add(textField);

        return panel;
    }

    private JPanel createLabelAndPasswordField(String labelText, JPasswordField passwordField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(80, 25));

        panel.add(label);
        panel.add(passwordField);

        return panel;
    }

    private boolean authenticateUser(String userId, String password) {
        return "BMS123".equals(userId) && "BMS123".equals(password);
    }

    private static class StylishButtonStyle {
        static void applyStyle(AbstractButton button) {
            button.setFont(new Font("Arial", Font.PLAIN, 16));
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(59, 89, 182));
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setOpaque(true);
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(40, 60, 150));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(59, 89, 182));
                }
            });
        }
    }
}

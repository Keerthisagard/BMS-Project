package com.cit.project.bms.in;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3864794979546969188L;
	@SuppressWarnings("unused")
	private BookManagementSystemEXT parentFrame;

    public MainMenuPanel(BookManagementSystemEXT parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Set a preferred size for smaller buttons
        Dimension buttonSize = new Dimension(120, 50);

        JLabel welcomeLabel = new JLabel("Main Menu");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(welcomeLabel);

        JButton addBookButton = createStyledButton("Add a Book", buttonSize);
        addBookButton.addActionListener(e -> parentFrame.showCard("AddBook"));

        JButton searchBookButton = createStyledButton("Search Book", buttonSize);
        searchBookButton.addActionListener(e -> parentFrame.showCard("SearchBook"));

        JButton displayBooksButton = createStyledButton("Books List", buttonSize);
        displayBooksButton.addActionListener(e -> parentFrame.showCard("DisplayAllBooks"));

        JButton issueButton = createStyledButton("Issue", buttonSize);
        issueButton.addActionListener(e -> parentFrame.showCard("Issue"));

        JButton returnButton = createStyledButton("Return", buttonSize);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.showReturnPanel(); 
            }
        });

        JButton exitButton = createStyledButton("Exit", buttonSize);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); 
            }
        });

        add(Box.createVerticalGlue()); 
        add(addBookButton);
        add(searchBookButton);
        add(displayBooksButton);
        add(issueButton);
        add(returnButton);
        add(exitButton);
        add(Box.createVerticalGlue());
    }

    private JButton createStyledButton(String text, Dimension size) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT); 
        button.setPreferredSize(size);
        button.setMaximumSize(size);
     // button.setBackground(new Color(52, 152, 219)); // Modern blue color
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Add a method to retrieve the current user ID
    public String getCurrentUserId() {
        // Assuming you have a way to get the current user ID
        // Replace this with the actual logic to get the user ID
        return "BMS123";
    }
}


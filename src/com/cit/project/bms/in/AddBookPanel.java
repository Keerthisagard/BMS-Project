package com.cit.project.bms.in;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3385906861346999292L;
	@SuppressWarnings("unused")
	private BookManagementSystemEXT parentFrame;
    private JTextField isbnField, titleField, categoryField, numberOfBooksField, authorNameField, phoneNumberField;
    private BookManagementSystemDAO bookDAO;

    public AddBookPanel(BookManagementSystemEXT parentFrame) {
        this.parentFrame = parentFrame;
        this.bookDAO = new BookManagementSystemDAO();
        setLayout(new GridLayout(7, 2, 10, 10));

        addLabelAndTextField("Book ISBN:", isbnField = new JTextField());
        addLabelAndTextField("Book Title:", titleField = new JTextField());
        addLabelAndTextField("Book Category:", categoryField = new JTextField());
        addLabelAndTextField("Number of Books:", numberOfBooksField = new JTextField());
        addLabelAndTextField("Author Name:", authorNameField = new JTextField());
        addLabelAndTextField("Phone Number:", phoneNumberField = new JTextField());

        JButton addButton = new JButton("Add Book");
        JButton backButton = new JButton("Back to Menu");

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        StylishButtonStyle.applyStyle(addButton);
        StylishButtonStyle.applyStyle(backButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (areFieldsEmpty()) {
                    showError("Please enter values in all fields.");
                } else {
                    addBook();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.showCard("MainMenu");
            }
        });

        add(addButton);
        add(backButton);
    }

    private void addLabelAndTextField(String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        add(label);
        add(textField);
    }

    private boolean areFieldsEmpty() {
        return isbnField.getText().isEmpty() ||
                titleField.getText().isEmpty() ||
                categoryField.getText().isEmpty() ||
                numberOfBooksField.getText().isEmpty() ||
                authorNameField.getText().isEmpty() ||
                phoneNumberField.getText().isEmpty();
    }

    private void addBook() {
        String isbn = isbnField.getText();
        String title = titleField.getText();
        String category = categoryField.getText();

        int numberOfBooks;
        try {
            numberOfBooks = Integer.parseInt(numberOfBooksField.getText());
        } catch (NumberFormatException ex) {
            showError("Please enter a valid number for Number of Books.");
            return;
        }

        String authorName = authorNameField.getText();
        String phoneNumber = phoneNumberField.getText();

        Book newBook = new Book(numberOfBooks, title, category, isbn);
        Author author = new Author(authorName, phoneNumber, isbn);

        boolean addedSuccessfully = bookDAO.create(newBook, author);

        if (addedSuccessfully) {
            clearTextFields();
            showMessage("Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            showError("Error adding the book. Please try again.");
        }
    }

    private void clearTextFields() {
        isbnField.setText("");
        titleField.setText("");
        categoryField.setText("");
        numberOfBooksField.setText("");
        authorNameField.setText("");
        phoneNumberField.setText("");
    }

    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    private void showError(String errorMessage) {
        showMessage(errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
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

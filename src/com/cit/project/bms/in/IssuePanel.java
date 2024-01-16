package com.cit.project.bms.in;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class IssuePanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6204154545364713202L;
	private BookManagementSystemEXT parent;
    private BookManagementSystemDAO dao;

    private JTextField usnField;
    private JTextField isbnField;
    private JButton issueButton;
    private JButton displayButton;
    private JTable issuedBooksTable;

    public IssuePanel(BookManagementSystemEXT parent, BookManagementSystemDAO dao) {
        this.parent = parent;
        this.dao = dao;

        setLayout(new BorderLayout());

        usnField = new JTextField();
        isbnField = new JTextField();
        issueButton = new JButton("Issue Book");
        displayButton = new JButton("Display Issued Books");
        issuedBooksTable = new JTable();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(new JLabel("Enter USN:"));
        inputPanel.add(usnField);
        inputPanel.add(new JLabel("Enter ISBN:"));
        inputPanel.add(isbnField);

        inputPanel.add(Box.createVerticalGlue());
        inputPanel.add(issueButton);
        inputPanel.add(new JPanel()); 
        inputPanel.add(displayButton);
        inputPanel.add(Box.createVerticalGlue());

        JButton backToMenuButton = new JButton("Back to Menu");
        backToMenuButton.addActionListener(e -> parent.showCard("MainMenu"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backToMenuButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(issuedBooksTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        issueButton.addActionListener(e -> issueBook());
        displayButton.addActionListener(e -> displayIssuedBooks());
    }

    private void issueBook() {
        String usn = usnField.getText();
        String isbn = isbnField.getText();

        if (usn.isEmpty() || isbn.isEmpty()) {
            showMessage("Please enter USN and ISBN.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!dao.isStudentExists(usn)) {
            showMessage("Student with USN " + usn + " does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Book book = dao.getBookByISBN(isbn);

        if (book == null) {
            showMessage("Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (book.getNo_of_books() <= 0) {
            showMessage("Book is not available for issue.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            dao.issueBook(usn, isbn);
            showMessage("Book issued successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearTextFields(); 
        }
    }

    private void displayIssuedBooks() {
        String usn = JOptionPane.showInputDialog(this, "Enter USN:", "Display Issued Books", JOptionPane.PLAIN_MESSAGE);

        if (usn == null || usn.isEmpty()) {
            showMessage("Please enter USN to display issued books.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if the student with the provided USN exists
        if (!dao.isStudentExists(usn)) {
            showMessage("Student with USN " + usn + " does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Issue> issuedBooks = dao.getBooksIssuedToStudent(usn);
        updateIssuedBooksTable(issuedBooks);
        clearTextFields();
    }

    private void updateIssuedBooksTable(List<Issue> issuedBooks) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Book Title");
        model.addColumn("Student Name");
        model.addColumn("Return Date");

        for (Issue issue : issuedBooks) {
            model.addRow(new Object[]{issue.getUsn(), issue.getIsbn(), issue.getReturn_date()});
        }

        issuedBooksTable.setModel(model);
    }

    private void clearTextFields() {
        usnField.setText("");
        isbnField.setText("");
    }

    private void showMessage(String message, String title, int messageType) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(parent, message, title, messageType);
        });
    }
}


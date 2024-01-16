package com.cit.project.bms.in;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ReturnPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3274430144614382062L;
	@SuppressWarnings("unused")
	private BookManagementSystemEXT parent;
    private BookManagementSystemDAO dao;
    private JButton backToMenuButton;
    private JTable returnBooksTable;

    public ReturnPanel(BookManagementSystemEXT parent, BookManagementSystemDAO dao) {
        this.parent = parent;
        this.dao = dao;

        setLayout(new BorderLayout());

        backToMenuButton = new JButton("Back to Menu");
        returnBooksTable = new JTable();

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backToMenuButton);

        add(new JScrollPane(returnBooksTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        backToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.showCard("MainMenu");
            }
        });

        displayReturnBooks();
    }

    private void displayReturnBooks() {
        List<Issue> returnBooks = dao.getReturnBooksForCurrentDate(); // Call the DAO method

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Book Title");
        model.addColumn("Student Name");
        model.addColumn("Return Date");

        if (returnBooks != null && !returnBooks.isEmpty()) {
            for (Issue returnBook : returnBooks) {
                model.addRow(new Object[]{returnBook.getUsn(), returnBook.getIsbn(), returnBook.getReturn_date()});
            }
        } else {
            model.addRow(new Object[]{"No books to return for the current date", "", ""});
        }

        returnBooksTable.setModel(model);
    }
    
    void setReturnPanelCardLayout(CardLayout cardLayout, JPanel cardPanel) {
        backToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "MainMenu");
            }
        });
    }
    
    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(() -> new ReturnPanel(null, null)); // Provide appropriate arguments
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


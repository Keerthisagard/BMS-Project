package com.cit.project.bms.in;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DisplayAllBooksPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3916994599666464831L;
	private BookManagementSystemDAO dao;
    private DefaultTableModel tableModel;
    private JPanel buttonPanel; 

    public DisplayAllBooksPanel(BookManagementSystemEXT parentFrame, BookManagementSystemDAO dao) {
        this.dao = dao;
        setLayout(new BorderLayout());
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        updateData();

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });

        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> parentFrame.showCard("MainMenu"));

        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
    public void updateData() {
        List<Book> allBooks = dao.getAllBooksWithAuthors();

        String[] columnNames = {"ISBN", "Title", "Category", "Number of Books", "Author", "Phone Number"};
        tableModel = new DefaultTableModel(columnNames, 0);

        for (Book book : allBooks) {
            Author author = book.getAuthor();
            Object[] rowData = {book.getIsbn(), book.getTitle(), book.getCategory(), book.getNo_of_books(), author.getAuthor_name(), author.getPhone_number()};
            tableModel.addRow(rowData);
        }

        JTable allBooksTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(allBooksTable);

        removeAll();
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH); 
        revalidate();
        repaint();
    }
}


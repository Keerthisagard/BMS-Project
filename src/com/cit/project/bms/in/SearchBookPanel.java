package com.cit.project.bms.in;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchBookPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8992190180797443741L;
	private BookManagementSystemDAO dao;
    @SuppressWarnings("unused")
	private BookManagementSystemEXT parentFrame;

    public SearchBookPanel(BookManagementSystemEXT parentFrame, BookManagementSystemDAO dao) {
        this.parentFrame = parentFrame;
        this.dao = dao;
        setLayout(new BorderLayout());

        JButton searchByTitleButton = createStyledButton("Search by Title");
        JButton searchByAuthorButton = createStyledButton("Search by Author Name");
        JButton searchByCategoryButton = createStyledButton("Search by Category");
        JButton backToMenuButton = createStyledButton("Back to Menu");

        searchByTitleButton.addActionListener(e -> searchByTitle());
        searchByAuthorButton.addActionListener(e -> searchByAuthor());
        searchByCategoryButton.addActionListener(e -> searchByCategory());
        backToMenuButton.addActionListener(e -> parentFrame.showCard("MainMenu"));

        JPanel searchButtonsPanel = new JPanel(new GridLayout(1, 3));
        searchButtonsPanel.add(searchByTitleButton);
        searchButtonsPanel.add(searchByAuthorButton);
        searchButtonsPanel.add(searchByCategoryButton);

        JPanel backToMenuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backToMenuPanel.add(backToMenuButton);

        add(searchButtonsPanel, BorderLayout.CENTER);
        add(backToMenuPanel, BorderLayout.SOUTH);
    }

    private void searchByTitle() {
        String bookTitle = getInput("Please enter the Book Title to Search:", "Search by Title");
        if (isValidInput(bookTitle)) {
            List<Book> searchResult = dao.getBooksByTitle(bookTitle);
            displaySearchResult(searchResult);
        }
    }

    private void searchByAuthor() {
        String authorName = getInput("Please enter the Author Name to Search:", "Search by Author");
        if (isValidInput(authorName)) {
            List<Book> searchResult = dao.getBooksByAuthorName(authorName);
            displaySearchResult(searchResult);
        }
    }

    private void searchByCategory() {
        String bookCategory = getInput("Please enter the Book Category to Search:", "Search by Category");
        if (isValidInput(bookCategory)) {
            List<Book> searchResult = dao.getBooksByCategory(bookCategory);
            displaySearchResult(searchResult);
        }
    }

    private boolean isValidInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            showMessage("Please enter the title, author name, or category.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void displaySearchResult(List<Book> searchResult) {
        if (searchResult != null && !searchResult.isEmpty()) {
            JFrame resultFrame = new JFrame("Search Results");
            resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            String[] columnNames = {"ISBN", "Title", "Category", "Number of Books", "Author Name", "Phone Number"};
            Object[][] rowData = new Object[searchResult.size()][columnNames.length];

            for (int i = 0; i < searchResult.size(); i++) {
                Book book = searchResult.get(i);
                Author author = book.getAuthor();

                rowData[i][0] = book.getIsbn();
                rowData[i][1] = book.getTitle();
                rowData[i][2] = book.getCategory();
                rowData[i][3] = book.getNo_of_books();
                rowData[i][4] = author.getAuthor_name();
                rowData[i][5] = author.getPhone_number();
            }

            JTable table = new JTable(rowData, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            resultFrame.add(scrollPane);

            resultFrame.pack();
            resultFrame.setLocationRelativeTo(this);
            resultFrame.setVisible(true);
        } else {
            showMessage("No books found.", "No Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private String getInput(String message, String title) {
        return JOptionPane.showInputDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(120, 50));
        button.setMaximumSize(new Dimension(120, 50));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}

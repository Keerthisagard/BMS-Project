package com.cit.project.bms.in;

import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class BookManagementSystemEXT extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7565488801755277958L;
	private CardLayout cardLayout;
    private JPanel cardPanel;
    private JTable searchResultsTable;
    private BookManagementSystemDAO dao;

    private String currentUserId;

    public BookManagementSystemEXT() {
        dao = new BookManagementSystemDAO();

        setTitle("Book Management System");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        addMainMenu();
        showLoginPanel();
        addAddBookPanel();
        addSearchBookPanel();
        addDisplayAllBooksPanel();
        addIssuePanel();
        addReturnPanel();

        add(cardPanel);
        
        setLocationRelativeTo(null);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void showCard(String cardName) {
        cardLayout.show(cardPanel, cardName);
    }

    private void showLoginPanel() {
        LoginPanel loginPanel = new LoginPanel(this);
        cardPanel.add(loginPanel, "Login");
        showCard("Login");
    }

    public void authenticateUser(String userId) {
        currentUserId = userId;
        addMainMenu();
    }

    public void showSearchOptionsDialog() {
        String[] options = {"Search by Title", "Search by Author", "Search by Category"};
        int choice = JOptionPane.showOptionDialog(null, "Select Search Option", "Search",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                searchByTitle();
                break;
            case 1:
                searchByAuthor();
                break;
            case 2:
                searchByCategory();
                break;
            default:
        }
    }

    private void searchByTitle() {
        String title = getInput("Enter Book Title:", "Search by Title");
        List<Book> searchResult = dao.getBooksByTitle(title);
        updateSearchResultsTable(searchResult);
    }

    private void searchByAuthor() {
        String author = getInput("Enter Author Name:", "Search by Author");
        List<Book> searchResult = dao.getBooksByAuthor(author);
        updateSearchResultsTable(searchResult);
    }

    private void searchByCategory() {
        String category = getInput("Enter Book Category:", "Search by Category");
        List<Book> searchResult = dao.getBooksByCategory(category);
        updateSearchResultsTable(searchResult);
    }

    private String getInput(String message, String title) {
        return JOptionPane.showInputDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    private void updateSearchResultsTable(List<Book> searchResult) {
        DefaultTableModel model = new DefaultTableModel();
        searchResultsTable.setModel(model);
    }

    private void addMainMenu() {
        MainMenuPanel mainMenuPanel = new MainMenuPanel(this);
        cardPanel.add(mainMenuPanel, "MainMenu");
        showCard("MainMenu");
    }

    private void addAddBookPanel() {
        AddBookPanel addBookPanel = new AddBookPanel(this);
        cardPanel.add(addBookPanel, "AddBook");
    }

    private void addSearchBookPanel() {
        SearchBookPanel searchBookPanel = new SearchBookPanel(this, dao);
        cardPanel.add(searchBookPanel, "SearchBook");
    }

    private void addDisplayAllBooksPanel() {
        DisplayAllBooksPanel displayAllBooksPanel = new DisplayAllBooksPanel(this, dao);
        cardPanel.add(displayAllBooksPanel, "DisplayAllBooks");
    }

    private void addIssuePanel() {
        IssuePanel issuePanel = new IssuePanel(this, dao);
        cardPanel.add(issuePanel, "Issue");
    }

    private void addReturnPanel() {
        ReturnPanel returnPanel = new ReturnPanel(this, dao);
        cardPanel.add(returnPanel, "Return");
        returnPanel.setReturnPanelCardLayout(cardLayout, cardPanel);
    }

    public void showReturnPanel() {
        addReturnPanel();
        showCard("Return");
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(() -> new BookManagementSystemEXT());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.cit.project.bms.in;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookManagementSystemDAO {
	
	private Connection conn;

	// insert book
	 public boolean create(Book b, Author a) {
	        String bookInsertSQL = "INSERT INTO Book(isbn, title, category, no_of_books) VALUES (?, ?, ?, ?)";
	        String authorInsertSQL = "INSERT INTO Author(isbn, author_name, phone_number) VALUES (?, ?, ?)";
	        int rows = 0;

	        try (Connection conn = DBConnectionManager.getConnection();
	             PreparedStatement bookps = conn.prepareStatement(bookInsertSQL);
	             PreparedStatement authorPs = conn.prepareStatement(authorInsertSQL)) {

	            conn.setAutoCommit(false);

	            if (isISBNExists(conn, b.getIsbn())) {
	                System.out.println("Error: Book with ISBN " + b.getIsbn() + " already exists.");
	                return false;
	            }

	            bookps.setString(1, b.getIsbn());
	            bookps.setString(2, b.getTitle());
	            bookps.setString(3, b.getCategory());
	            bookps.setInt(4, b.getNo_of_books());
	           

	            rows = bookps.executeUpdate();

	            Author a1 = b.getAuthor();
	            authorPs.setString(1, b.getIsbn());
	            authorPs.setString(2, a1.getAuthor_name());
	            authorPs.setString(3, a1.getPhone_number());
	            authorPs.executeUpdate();

	            conn.commit();

	        } catch (SQLException e) {
	            e.printStackTrace();
	            try {
	                conn.rollback();
	            } catch (SQLException rollbackException) {
	                rollbackException.printStackTrace();
	            }
	            return false;
	        }
	        return rows > 0;
	    }

	    private boolean isISBNExists(Connection conn, String isbn) throws SQLException {
	        String query = "SELECT COUNT(*) FROM Book WHERE isbn = ?";
	        try (PreparedStatement ps = conn.prepareStatement(query)) {
	            ps.setString(1, isbn);
	            try (ResultSet rs = ps.executeQuery()) {
	                return rs.next() && rs.getInt(1) > 0;
	            }
	        }
	    }
	


	// author info
	public boolean create(Author a)
	{
		String sql = "insert into Author(author_name,phone_number,isbn) values (?,?,?)";
		int rows = 0;
		try(Connection conn = DBConnectionManager.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, a.getAuthor_name());
			ps.setString(2, a.getPhone_number());
			ps.setString(3, a.getIsbn());
			rows = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows > 0;
	}

	// student details
	public boolean create(Student s)
	{
		String sql = "insert into Student(usn,name) values (?,?)";
		int rows = 0;
		try(Connection conn = DBConnectionManager.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, s.getUsn());
			ps.setString(2, s.getName());
			rows = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows > 0;
	}

	
		// issue details
	    public boolean create(Issue i) {
	        String sql = "INSERT INTO Issue_books(usn, issue_date, return_date,  isbn) VALUES (?, ?, ?, ?)";
	        int rows = 0;

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
        	ps.setString(1, i.getUsn());
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ps.setDate(3, Date.valueOf(LocalDate.now().plusDays(7)));
            ps.setString(4, i.getIsbn());
            rows = ps.executeUpdate();

        } catch (SQLException e) {
          
            e.printStackTrace(); 
        }

        return rows > 0;
    }



	
	    // Search by title
	    public List<Book> getBooksByTitle(String bookTitle) {
	        String sql = "SELECT b.isbn, b.title, b.category, b.no_of_books, a.author_name, a.phone_number " +
	        			 "FROM book b, author a " +
	        			 "WHERE b.isbn = a.isbn AND b.title LIKE ?";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + bookTitle + "%");
            ResultSet rs = ps.executeQuery();

            while (rs != null && rs.next()) {
                Author author = new Author(
                         rs.getString("author_name"),
                         rs.getString("phone_number"),
                         rs.getString("isbn")
                );

                Book book = new Book(
                          rs.getString("isbn"),
                          rs.getString("title"),
                          rs.getString("category"),
                          rs.getInt("no_of_books"),
                          author
                );

                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }


	    //Search by category
	    public List<Book> getBooksByCategory(String bookCategory) {
	        String sql = "SELECT b.isbn, b.title, b.category, b.no_of_books, a.author_name, a.phone_number " +
	                	 "FROM book b, author a " +
	                	 "WHERE b.isbn = a.isbn AND b.category LIKE ?";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + bookCategory + "%");
            ResultSet rs = ps.executeQuery();

            while (rs != null && rs.next()) {
                Author author = new Author(
                         rs.getString("author_name"),
                         rs.getString("phone_number"),
                         rs.getString("isbn")
                );

                Book book = new Book(
                          rs.getString("isbn"),
                          rs.getString("title"),
                          rs.getString("category"),
                          rs.getInt("no_of_books"),
                          author
                );

                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

	
	    //Search by author name
		public List<Book> getBooksByAuthorName(String authorName) {
		    String sql = "SELECT b.isbn, b.title, b.category, b.no_of_books, a.author_name, a.phone_number " +
		                 "FROM book b, author a " +
		                 "WHERE b.isbn = a.isbn AND a.author_name LIKE ?";
	    List<Book> books = new ArrayList<>();

	    try (Connection conn = DBConnectionManager.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, "%" + authorName + "%");
	        ResultSet rs = ps.executeQuery();

	        while (rs != null && rs.next()) {
	            Author author = new Author(
	            		 rs.getString("author_name"),
	                     rs.getString("phone_number"),
	                     rs.getString("isbn")
	            );

	            Book book = new Book(
	            		  rs.getString("isbn"),
	                      rs.getString("title"),
	                      rs.getString("category"),
	                      rs.getInt("no_of_books"),
	                      author
	            );

	            books.add(book);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return books;
	}
	
		//Search all books
		public List<Book> getAllBooksWithAuthors() {
		    String sql = "SELECT b.isbn, b.title, b.category, b.no_of_books, a.author_name, a.phone_number " +
		                 "FROM book b, author a " +
		                 "WHERE b.isbn = a.isbn";
	    List<Book> books = new ArrayList<>();

	    try (Connection conn = DBConnectionManager.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs != null && rs.next()) {
	            Author author = new Author(
	                    rs.getString("isbn"),  
	                    rs.getString("author_name"),
	                    rs.getString("phone_number")
	            );

	            Book book = new Book(
	                    rs.getString("isbn"),
	                    rs.getString("title"),
	                    rs.getString("category"),
	                    rs.getInt("no_of_books"),
	                    author
	            );

	            books.add(book);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return books;
	}
	
	
		// Search book based on student USN
		public List<Issue> getBooksIssuedToStudent(String usn) {
		    String sql = "SELECT b.title, s.name, i.return_date " +
		                 "FROM book b, student s, issue_books i " + 
		                 "WHERE b.isbn = i.isbn AND s.usn = i.usn AND s.usn = ?";
	                    
	    List<Issue> issuedBooksDetails = new ArrayList<>();

	    try (Connection conn = DBConnectionManager.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, usn);
	        ResultSet rs = ps.executeQuery();

	        while (rs != null && rs.next()) {
	            Issue is = new Issue(
	            		rs.getString("title"),
	                    rs.getString("name"),
	                    rs.getDate("return_date")
	               
	            );

	            issuedBooksDetails.add(is);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace(); 
	    }

	    return issuedBooksDetails;
	}
	
	
		// Show the return books based on current date
		  public List<Issue> getBooksToBeReturnedForCurrentDate() {
		        String sql = "SELECT b.title, s.name, i.return_date " +
		                 "FROM book b, student s, issue_books i " + 
		                 "WHERE b.isbn = i.isbn AND s.usn = i.usn AND i.return_date = CURRENT_DATE";
		                    
	      List<Issue> booksToBeReturned = new ArrayList<>();

	        try (Connection conn = DBConnectionManager.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ResultSet rs = ps.executeQuery();

	            while (rs != null && rs.next()) {
	                Issue issue = new Issue(
	                        rs.getString("title"),
	                        rs.getString("name"),
	                        rs.getDate("return_date")
	                );

	                booksToBeReturned.add(issue);
	            }

	        } catch (SQLException e) {
            e.printStackTrace();
	        }

	        return booksToBeReturned;
	    }
	}


   





	
	





	

	

	




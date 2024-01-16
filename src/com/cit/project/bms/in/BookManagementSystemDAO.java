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
	public boolean create(Book book, Author author) {
	    String bookInsertSQL = "INSERT INTO Book(isbn, title, category, no_of_books) VALUES (?, ?, ?, ?)";
	    String authorInsertSQL = "INSERT INTO Author(isbn, author_name, phone_number) VALUES (?, ?, ?)";
	    int rows = 0;

	    try (Connection conn = DBConnectionManager.getConnection()) {
	        if (conn == null) {
	            System.out.println("Error: Failed to establish a database connection.");
	            return false;
	        }

	        conn.setAutoCommit(false);

	        if (isISBNExists(conn, book.getIsbn())) {
	            System.out.println("Error: Book with ISBN " + book.getIsbn() + " already exists.");
	            return false;
	        }

	        try (PreparedStatement bookps = conn.prepareStatement(bookInsertSQL);
	             PreparedStatement authorPs = conn.prepareStatement(authorInsertSQL)) {

	            bookps.setString(1, book.getIsbn());
	            bookps.setString(2, book.getTitle());
	            bookps.setString(3, book.getCategory());
	            bookps.setInt(4, book.getNo_of_books());

	            rows = bookps.executeUpdate();

	            authorPs.setString(1, book.getIsbn());
	            authorPs.setString(2, author.getAuthor_name());
	            authorPs.setString(3, author.getPhone_number());
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
	    } catch (SQLException e) {
	        e.printStackTrace();
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
	
	   public List<Book> getBooksByAuthor(String authorName) {
		   List<Book> books = new ArrayList<>();

		   String sql = "SELECT * FROM Book WHERE author_name = ?";

		   try (Connection conn = DBConnectionManager.getConnection();
				   PreparedStatement ps = conn.prepareStatement(sql)) {

			   ps.setString(1, authorName);

			   try (ResultSet rs = ps.executeQuery()) {
				   while (rs.next()) {
					   	Book book = new Book(0, sql, sql, sql);
	                    book.setIsbn(rs.getString("isbn"));
	                    book.setTitle(rs.getString("title"));
	                    book.setCategory(rs.getString("category"));
	                    books.add(book);
	                }
	            }

	        	} catch (SQLException e) {
	        		e.printStackTrace();
	        	}

	        	return books;
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

		  public void issueBook(String usn, String isbn) {
		        String sql = "INSERT INTO issue_books (usn, isbn, issue_date, return_date) VALUES (?, ?, CURRENT_DATE, CURRENT_DATE + INTERVAL 7 DAY)";

		        try (Connection conn = DBConnectionManager.getConnection();
		             PreparedStatement ps = conn.prepareStatement(sql)) {

		            ps.setString(1, usn);
		            ps.setString(2, isbn);

		            ps.executeUpdate();

		        } catch (SQLException e) {
		            e.printStackTrace();
		            System.err.println("Error issuing the book: " + e.getMessage());
		        }
		    }


		  public Book getBookByISBN(String isbn) {
			  String sql = "SELECT b.isbn, b.title, b.category, b.no_of_books, " +
		                 "a.author_name, a.phone_number " +
		                 "FROM book b " +
		                 "LEFT JOIN author a ON b.isbn = a.isbn " +
		                 "WHERE b.isbn = ?";
		        Book book = null;

		        try (Connection conn = DBConnectionManager.getConnection();
		             PreparedStatement ps = conn.prepareStatement(sql)) {

		            ps.setString(1, isbn);
		            ResultSet rs = ps.executeQuery();

		            if (rs.next()) {
		                book = new Book(rs);
		            }

		        } catch (SQLException e) {
		            e.printStackTrace();
		            System.err.println("Error getting book by ISBN: " + e.getMessage());
		        }

		        return book;
		    	}


		  	public List<Issue> getIssuedBooks() {
		  		List<Issue> issuedBooks = new ArrayList<>();
		      
		  		String sql = "SELECT b.title, s.name AS student_name, i.return_date " +
		                   "FROM book b " +
		                   "JOIN issue_books i ON b.isbn = i.isbn " +
		                   "JOIN student s ON i.usn = s.usn";

		  		try (Connection conn = DBConnectionManager.getConnection();
		           PreparedStatement ps = conn.prepareStatement(sql);
		           ResultSet rs = ps.executeQuery()) {

		          while (rs.next()) {
		              String bookTitle = rs.getString("title");
		              String studentName = rs.getString("student_name");
		              Date returnDate = rs.getDate("return_date");

		              Issue issue = new Issue(bookTitle, studentName, returnDate);
		              issuedBooks.add(issue);
		          }

		  		} catch (SQLException e) {
		          e.printStackTrace();
		  		}

		  		return issuedBooks;
		  	}
		  
		  
		  	public List<Issue> getReturnBooksForCurrentDate() {
		        List<Issue> returnBooks = new ArrayList<>();

		        String sql = "SELECT b.title, s.name AS student_name, i.return_date " +
		                "FROM book b " +
		                "JOIN issue_books i ON b.isbn = i.isbn " +
		                "JOIN student s ON i.usn = s.usn " +
		                "WHERE i.return_date = CURRENT_DATE";

		        try (Connection conn = DBConnectionManager.getConnection();
		             PreparedStatement ps = conn.prepareStatement(sql);
		             ResultSet rs = ps.executeQuery()) {

		            while (rs.next()) {
		                String bookTitle = rs.getString("title");
		                String studentName = rs.getString("student_name");
		                Date returnDate = rs.getDate("return_date");

		                Issue returnBook = new Issue(bookTitle, studentName, returnDate);
		                returnBooks.add(returnBook);
		            }

		        } catch (SQLException e) {
		            e.printStackTrace();
		           
		        }

		        return returnBooks;
		    }
		  
		  	public boolean isStudentExists(String usn) {
		        try (Connection conn =  DBConnectionManager.getConnection();
		             PreparedStatement pr = conn.prepareStatement("SELECT COUNT(*) FROM student WHERE usn = ?")) {

		            pr.setString(1, usn);

		            try (ResultSet resultSet = pr.executeQuery()) {
		                if (resultSet.next()) {
		                    int count = resultSet.getInt(1);
		                    return count > 0;
		                }
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }

		        return false;
		    }
		}
		
		

	



		
   





	
	





	

	

	




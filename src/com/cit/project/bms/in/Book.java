package com.cit.project.bms.in;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Book {
	
		private String isbn; // Primary Key
	    private String title;
	    private String category;
	    private int no_of_books;
	    
	    private Author author;
		
		public Book(String isbn, String title, String category, int no_of_books, Author author) {
			super();
			this.isbn = isbn;
			this.title = title;
			this.category = category;
			this.no_of_books = no_of_books;
			this.author = author;
		}
		
		
		public Book(int no_of_books, String title, String category, String isbn) {
			super();
			this.isbn = isbn;
			this.title = title;
			this.category = category;
			this.no_of_books = no_of_books;
		}

		
		 public Book(ResultSet resultSet) throws SQLException {
		        this.isbn = resultSet.getString("isbn");
		        this.title = resultSet.getString("title");
		        this.category = resultSet.getString("category");
		        this.no_of_books = resultSet.getInt("no_of_books");

		        // Assuming that the Author details are present in the ResultSet
		        this.author = new Author(
		            resultSet.getString("author_name"),
		            resultSet.getString("phone_number"),
		            resultSet.getString("isbn")
		        );
		 }

		
		public String getIsbn() {
			return isbn;
		}
		public void setIsbn(String isbn) {
			this.isbn = isbn;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public int getno_of_books() {
			return no_of_books;
		}
		
		public int getNo_of_books() {
			return no_of_books;
		}
		public void setNo_of_books(int no_of_books) {
			this.no_of_books = no_of_books;
		}
		public Author getAuthor() {
			return author;
		}
		public void setAuthor(Author author) {
			this.author = author;
		}
		
		@Override
		public String toString() {
		    StringBuilder sb = new StringBuilder();
		  //  sb.append("\n");
		    sb.append("\n"+"Book Details:\n");
		    sb.append("ISBN: ").append(isbn).append("\n");
		    sb.append("Title: ").append(title).append("\n");
		    sb.append("Category: ").append(category).append("\n");
		    sb.append("Number of Books: ").append(no_of_books).append("\n");

		    if (author != null) {
		        sb.append("\n" + "Author Details:\n");
		        sb.append("Name: ").append(author.getAuthor_name()).append("\n");
		        sb.append("Phone Number: ").append(author.getPhone_number()).append("\n");
		        sb.append("Author ISBN: ").append(author.getIsbn()).append("\n");
		    } else {
		        sb.append("No author information available.\n");
		    }

		    return sb.toString();
		}


}
	




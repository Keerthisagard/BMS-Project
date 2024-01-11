package com.cit.project.bms.in;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Issue {
	 
			
		    private String usn;
			private Date issue_date;
			private Date return_date;
		    private String isbn;
		    
		    private Book book;
		    private Student student;
			
		    public Issue( String usn, Date issue_date, Date return_date, String isbn) {
			super();
			this.usn = usn;
			this.issue_date = Date.valueOf(LocalDate.now());
			this.return_date = Date.valueOf(LocalDate.now().plusDays(7));
			this.isbn = isbn;
			
		}
		
		
		public Issue(String usn, String isbn) {
			this.usn = usn;
			this.isbn = isbn;
			
		}
		
		
		public Issue(String usn, String isbn, Date return_date) {
			this.usn = usn;
			this.isbn = isbn;
			this.return_date = return_date;
			
		}

	
		public Issue(Book book, Student student, Date return_date) {
			this.book = book;
			this.student = student;
			this.return_date = return_date;
			
		}

		public String getUsn() {
			return usn;
		}

		public void setUsn(String usn) {
			this.usn = usn;
		}

		public Date getIssue_date() {
			return issue_date;
		}

		public void setIssue_date(Date issue_date) {
			this.issue_date = issue_date;
		}

		public Date getReturn_date() {
			return return_date;
		}

		public void setReturn_date(Date return_date) {
			this.return_date = return_date;
		}

		public String getIsbn() {
			return isbn;
		}

		public void setIsbn(String isbn) {
			this.isbn = isbn;
		}

		public Book getBook() {
			return book;
		}

		public void setBook(Book book) {
			this.book = book;
		}

		public Student getStudent() {
			return student;
		}

		public void setStudent(Student student) {
			this.student = student;
		}


		 @Override
		    public String toString() {
		        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");

		        String formattedReturnDate = dateFormat.format(return_date);

		        return String.format("Issue Details:\nReturn Date: %s\nBook: %s\nStudent Name: %s\n",
		                formattedReturnDate, usn, isbn);
		    }

	}

package com.cit.project.bms.in;

public class Author {
	 private String author_name;
	 private String phone_number;
	 private String isbn;
	
	 
	 public Author(String author_name, String phone_number, String isbn) {
		super();
		this.author_name = author_name;
		this.phone_number = phone_number;
		this.isbn = isbn;
		
	}
	
	public String getAuthor_name() {
		return author_name;
	}
	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Author Details:\n");
	    sb.append("Name: ").append(author_name).append("\n");
	    sb.append("Phone Number: ").append(phone_number).append("\n");
	    sb.append("ISBN: ").append(isbn).append("\n");
	    return sb.toString();
	}

}

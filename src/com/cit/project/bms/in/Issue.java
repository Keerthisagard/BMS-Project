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

    public Issue(String usn, String isbn) {
        this.usn = usn;
        this.isbn = isbn;
        this.issue_date = Date.valueOf(LocalDate.now());
        this.return_date = Date.valueOf(LocalDate.now().plusDays(7));
    }

    public Issue(String usn, String isbn, Date return_date) {
        this.usn = usn;
        this.isbn = isbn;
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
    
   


    public String getBookTitle() {
        // Assuming you have a getTitle() method in your Book class
        return (book != null) ? book.getIsbn() : "N/A";
    }

    public String getBookDetails() {
        // Customize this part based on available methods or attributes in your Book class
        return (book != null) ? book.toString() : "N/A";
    }

    // Additional method to get student name
    public String getStudentName() {
        // Assuming you have a getName() method in your Student class
        return (student != null) ? student.getUsn() : "N/A";
    }
    

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        String formattedReturnDate = dateFormat.format(return_date);

        return String.format("Issue Details:\nReturn Date: %s\nBook: %s\nStudent Name: %s\n",
                formattedReturnDate, usn, isbn);
    }
}

package com.cit.project.bms.in;

import java.util.List;
import java.util.Scanner;

public class BMSDemo {
public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		String choice = "";
		BookManagementSystemDAO dao = new BookManagementSystemDAO();
		
		do
		{
			System.out.println("Please select your choice");
			System.out.println("1.Add a Book");
			System.out.println("2.Search a Book based on Book Title");
			System.out.println("3.Search Books based on Category");
			System.out.println("4.Search Books based on Author");
			System.out.println("5.List All Books along with author information");
			System.out.println("6.Issue Book to Student");
			System.out.println("7.List Books issued to Student based on USN number");
			System.out.println("8.List books which are to be returned for current date");
			System.out.println("9.Exit");
			
			choice = sc.nextLine();
			
			switch(choice) {
			case "1":
			    System.out.println("Please enter the Book ISBN");
			    String isbn = sc.nextLine();
			    System.out.println("Please enter the Book Title");
			    String title = sc.nextLine();
			    System.out.println("Please enter the Book Category");
			    String category = sc.nextLine();
			    int numberOfBooks;
                while (true) {
                    System.out.println("Please enter Number of books adding");
                    try {
                        numberOfBooks = Integer.parseInt(sc.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid integer for Number of books");
                    }
                }

			    System.out.println("Please enter the Book Author name");
			    String authorName = sc.nextLine();
			    System.out.println("Please enter the Book Author phone number");
			    String phoneNumber = sc.nextLine();

			    Author author = new Author(authorName, phoneNumber, isbn);
			    Book book = new Book(isbn, title, category, numberOfBooks, author);

			    if (dao.create(book, author)) {
			        System.out.println("The Book " + title + " is added successfully");
			    }
		break;
		
			case "2" : 
				System.out.println("Please enter the Book Title to Search");
				String BookTitle = sc.nextLine(); //sc.nextLine();
				List<Book> SearchResult = dao.getBooksByTitle(BookTitle);
				if (SearchResult != null && !SearchResult.isEmpty()) {
				    // Books found, print the list
				    System.out.println("Books found for the title: " + BookTitle);
				    for (Book book1 : SearchResult) {
				        System.out.println(book1);
				    }
				} 
				else
				{
				    System.out.println("No books found for the title: " + BookTitle);
				}
		break;
			
			case "3" :  
				System.out.println("Please enter the Book Category to Search");
				String BookCategory = sc.nextLine(); //sc.nextLine();
				List<Book> SearchCategory = dao.getBooksByCategory(BookCategory);
				if (SearchCategory != null && !SearchCategory.isEmpty()) {
				    System.out.println("Books found for the category: " + BookCategory);
				    for (Book book1 : SearchCategory) {
				        System.out.println(book1);
				    }
				} 
				else 
				{
				    System.out.println("No books found for the category: " + BookCategory);
				}
		break;
		
			case "4" :  
				System.out.println("Please enter the Book Author name to Search");
				String BookAuthor = sc.nextLine(); //sc.nextLine();
				List<Book> SearchAuthor = dao.getBooksByAuthorName(BookAuthor);
				if (SearchAuthor != null && !SearchAuthor.isEmpty()) {
				    System.out.println("Books found for the author: " + BookAuthor);
				    for (Book book1 : SearchAuthor) {
				        System.out.println(book1);
				    }
				} 
				else
				{
				    System.out.println("No books found for the author: " + BookAuthor);
				}

		break;
		
			case "5" : 
				List<Book> list = dao.getAllBooksWithAuthors();
				for(Book s : list)
				{
					System.out.println(s);
				}
		break;
		
			case "6" : 
				System.out.println("Please enter the USN of the Student ");
				String usn = sc.nextLine(); 
				System.out.println("Please enter the ISBN of the Book to be Issued ");
				String IssueIsbn = sc.nextLine();
				if(dao.create(new Issue(usn, IssueIsbn))) 
				{
					System.out.println("Successfully issued the book to " + usn);
				}
			
		break;	
		
			case "7" : 
				System.out.println("Please enter the USN of the student ");
				String StudentUsn = sc.nextLine();
				List<Issue> IssuedBook = dao.getBooksIssuedToStudent(StudentUsn);
				if (IssuedBook != null && !IssuedBook.isEmpty()) {
				    System.out.println("Books issued to the student with USN " + StudentUsn);
				    for (Issue issuedBook : IssuedBook) {
				        System.out.println(issuedBook);
				    }
				} 
				else 
				{
				    System.out.println("No books issued for the student with USN " + StudentUsn);
				}

		break;
		
			case "8" :
				List<Issue> booksToBeReturned = dao.getBooksToBeReturnedForCurrentDate();

				if (booksToBeReturned != null && !booksToBeReturned.isEmpty()) {
				    System.out.println("Books to be returned on the current date:");
				    for (Issue issue : booksToBeReturned) {
				        System.out.println(issue);
				    }
				} else {
				    System.out.println("No books to be returned on the current date.");
				}
		break;
		
			case "9" : 
				System.out.println("Exiting application");
				break;
			default:
				System.out.println("invalid choice");
				break;
			}
			
		}while(!choice.equals("9"));
			
			sc.close();
			}
	}



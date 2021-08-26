package com.bookapp.main;

import java.util.List;
import java.util.Scanner;

import com.bookapp.dao.BookInter;
import com.bookapp.exception.AuthorNotFoundException;
import com.bookapp.exception.BookNotFoundException;
import com.bookapp.exception.CategoryNotFoundException;
import com.bookapp.exception.PriceBookNotFoundException;
import com.bookapp.bean.Book;
import com.bookapp.dao.BookImpl;

public class Client {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		BookInter bookObject = new BookImpl();
		int select = 0;

		System.out.println("Enter Number 1 to Add Book Details\n" + "Enter Number 2 to get book by Author  \n"
				+ "Enter Number 3 to get book by Category \n" + "Enter Number 4 to get book by Price \n"
				+ "Enter Number 5 to get book by Book Id \n" + "Enter Number 6 to get book by Delete Book\n"
				+ "Enter Number 7 to get book by Update Book\n" + "Enter Number 8 to get All Books \n");
		select = sc.nextInt();

		switch (select) {

		case 1:
			Book book = new Book();
			System.out.println("Enter Title :");
			String title = sc.next();
			book.setTitle(title);

			System.out.println("Enter Author : ");
			String author = sc.next();
			book.setAuthor(author);

			System.out.println("Enter Category : ");
			String category = sc.next();
			book.setCategory(category);

			System.out.println("Enter Price : ");
			int price = sc.nextInt();
			book.setPrice(price);

			System.out.println("Enter Bood Id : ");
			int bookId = sc.nextInt();
			book.setBookId(bookId);

			bookObject.addBook(book);
			
			System.out.println("Book Added");
			break;
		case 2:
			System.out.println("Enter Author");
			String bookAuthor = sc.next();
			// System.out.println();
			try {
				List<Book> bookByAuthor = bookObject.getBookByAuthor(bookAuthor);
				if (bookByAuthor != null) {
					System.out.println(bookByAuthor);
				}
			} catch (AuthorNotFoundException e) {
				System.out.println(e.getMessage());
			}
			break;
		case 3:
			System.out.println("Enter Category : ");
			String bookCategory = sc.next();
			try {
				List<Book> bookByCategory = bookObject.getBookByCategory(bookCategory);
				if (bookByCategory != null) {
					System.out.println(bookByCategory);
				}
			} catch (CategoryNotFoundException e) {
				System.out.println(e.getMessage());
			}
			break;
		case 4:
			System.out.println("Enter Price : ");
			double bookPrice = sc.nextInt();
			try {
				List<Book> bookByPrice = bookObject.getBookByPrice(bookPrice);
				if (bookByPrice != null) {
					System.out.println(bookByPrice);
				}
			} catch (PriceBookNotFoundException e) {
				System.out.println(e.getMessage());
			}

			break;
		case 5:
			System.out.println("Enter Book Id");
			int bookId1 = sc.nextInt();

			try {
				// System.out.println(bookObject.getBookById(bookId1)+ "");
				Book bookById = bookObject.getBookById(bookId1);
				if (bookById != null) {
					System.out.println(bookById);
				}
			} catch (BookNotFoundException e) {
				System.out.println(e.getMessage());
			}
			break;
		case 6:
			System.out.println("Enter id to Delete Book");
			int bookId2 = sc.nextInt();
			try {
				boolean idValue = bookObject.deleteBook(bookId2);
				if (idValue != false) {
					System.out.println("Book Deleted");
				}
			} catch (BookNotFoundException e) {
				System.out.println(e.getMessage());
			}
			break;
		case 7:
			System.out.println("Upadte Book");
			System.out.println("Enter Book Id : ");
			int id1 = sc.nextInt();
			System.out.println("Enter Book Price : ");
			double bookPrice2 = sc.nextDouble();

			try {
				Book bookById = bookObject.getBookById(id1);
				if (bookById != null) {

					int checkId = bookObject.updateBook(id1, bookPrice2);
					if (checkId != 0) {
						System.out.println("Book Price is Updated");
					}

				}
			} catch (BookNotFoundException e) {
				System.out.println(e.getMessage());
			}
			break;
		case 8:
			System.out.println("All Books");
			System.out.println(bookObject.getAllBooks());
			break;
		}
		sc.close();
	}
}
package com.bookapp.dao;

import java.util.List;

import com.bookapp.bean.Book;
import com.bookapp.exception.AuthorNotFoundException;
import com.bookapp.exception.BookNotFoundException;
import com.bookapp.exception.CategoryNotFoundException;
import com.bookapp.exception.PriceBookNotFoundException;

public interface BookInter {

	// called by admin
	void addBook(Book book);
	boolean deleteBook(int bookid) throws BookNotFoundException;
	Book getBookById(int bookid) throws BookNotFoundException;
	int updateBook(int bookid, double price) throws BookNotFoundException;

	// called by customer
	List<Book> getAllBooks();
	List<Book> getBookByAuthor(String author) throws AuthorNotFoundException;
	List<Book> getBookByCategory(String category) throws CategoryNotFoundException;
	List<Book> getBookByPrice(double bookPrice) throws PriceBookNotFoundException;
	

}

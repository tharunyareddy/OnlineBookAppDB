package com.bookapp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.bookapp.bean.Book;
import com.bookapp.exception.AuthorNotFoundException;
import com.bookapp.exception.BookNotFoundException;
import com.bookapp.exception.CategoryNotFoundException;
import com.bookapp.exception.PriceBookNotFoundException;

public class BookImpl implements BookInter {

	@Override
	public void addBook(Book book) {

		Connection connection = null;
		PreparedStatement st = null;

		try {
			String sql = "insert into book values(?,?,?,?,?)";
			connection = ModelDao.openConnection();
			st = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			st.setString(1, book.getTitle());
			st.setString(2, book.getAuthor());
			st.setString(3, book.getCategory());
			st.setDouble(4, book.getPrice());
			st.setInt(5, book.getBookId());
			st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ModelDao.closeConnection();
			}
		}
	}

	@Override
	public boolean deleteBook(int bookid) throws BookNotFoundException {
		boolean deleteBook = true;
		String deleteQuery = "delete from book where bookid = ?";
		Connection connection = ModelDao.openConnection();
		PreparedStatement st = null;
		try {
			st = connection.prepareStatement(deleteQuery, ResultSet.CONCUR_UPDATABLE,
					ResultSet.TYPE_SCROLL_INSENSITIVE);
			st.setInt(1, bookid);
			int idValue = st.executeUpdate();
			// System.out.println(idValue);
			if (idValue == 0) {
				deleteBook = false;
				throw new BookNotFoundException("Book Id is not Available,Invaild Book Id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ModelDao.closeConnection();
		}
		return deleteBook;
	}

	@Override
	public Book getBookById(int bookid) throws BookNotFoundException {
		String idSelectQuery = "select * from book where bookid = ?";
		Connection connection = ModelDao.openConnection();
		PreparedStatement st = null;
		Book book = new Book();
		try {
			st = connection.prepareStatement(idSelectQuery, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			st.setInt(1, bookid);
			ResultSet rs = st.executeQuery();
			if (rs.next() == false) {
				throw new BookNotFoundException("Book is Not Found");
			}
			while (rs.next()) {
				book.setTitle(rs.getString(1));
				book.setAuthor(rs.getString(2));
				book.setCategory(rs.getString(3));
				book.setPrice(rs.getInt(4));
				book.setBookId(rs.getInt(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ModelDao.closeConnection();
		}

		return book;
	}

	public int updateBook(int bookid, double price) throws BookNotFoundException {

		Connection connection = null;
		PreparedStatement st = null;
		int rs = 0;
		try {
			String updateQuery = "update book set price=? where bookid =?";
			connection = ModelDao.openConnection();
			st = connection.prepareStatement(updateQuery, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			st.setInt(2, bookid);
			st.setDouble(1, price);
			rs = st.executeUpdate();
			if (rs == 0) {
				throw new BookNotFoundException("Book Not found,Invalid Book Id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (BookNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ModelDao.closeConnection();
		}
		return rs;
	}

	@Override
	public List<Book> getAllBooks() {
		List<Book> allBooks = new ArrayList<>();
		String allselectQuery = "select * from book";
		Connection connection = ModelDao.openConnection();
		PreparedStatement st = null;

		try {

			st = connection.prepareStatement(allselectQuery, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Book book = new Book();
				book.setTitle(rs.getString(1));
				book.setAuthor(rs.getString(2));
				book.setCategory(rs.getString(3));
				book.setPrice(rs.getInt(4));
				book.setBookId(rs.getInt(5));
				allBooks.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				st.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
			ModelDao.closeConnection();
		}

		return allBooks;
	}

	@Override
	public List<Book> getBookByAuthor(String author) throws AuthorNotFoundException {
		List<Book> bookByAuthor = new ArrayList<>();
		String authorSelectQuery = "select * from book where author = ?";
		Connection connection = ModelDao.openConnection();
		PreparedStatement st = null;

		try {
			st = connection.prepareStatement(authorSelectQuery, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			st.setString(1, author);
			ResultSet rs = st.executeQuery();
			if (rs.next() == false) {

				throw new AuthorNotFoundException("Author Not Found");
			}
			rs.beforeFirst();
			while (rs.next()) {
				Book book = new Book();
				book.setTitle(rs.getString(1));
				book.setAuthor(rs.getString(2));
				book.setCategory(rs.getString(3));
				book.setPrice(rs.getInt(4));
				book.setBookId(rs.getInt(5));
				bookByAuthor.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				st.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
			ModelDao.closeConnection();
		}

		return bookByAuthor;
	}

	@Override
	public List<Book> getBookByCategory(String category) throws CategoryNotFoundException {

		List<Book> bookByCategory = new ArrayList<>();
		String categorySelectQuery = "select * from book where category = ?";
		Connection connection = ModelDao.openConnection();
		PreparedStatement st = null;

		try {
			st = connection.prepareStatement(categorySelectQuery, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			st.setString(1, category);

			ResultSet rs = st.executeQuery();

			if (rs.next() == false) {
				throw new CategoryNotFoundException("Category Not Found");
			}
			rs.beforeFirst();
			while (rs.next()) {
				Book book = new Book();
				book.setTitle(rs.getString(1));
				book.setAuthor(rs.getString(2));
				book.setCategory(rs.getString(3));
				book.setPrice(rs.getInt(4));
				book.setBookId(rs.getInt(5));
				bookByCategory.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ModelDao.closeConnection();
		}
		return bookByCategory;
	}

	@Override
	public List<Book> getBookByPrice(double bookPrice) throws PriceBookNotFoundException {
		List<Book> bookByPrice = new ArrayList<>();
		String priceSelectQuery = "select * from book where price >= ?";
		Connection connection = ModelDao.openConnection();
		PreparedStatement st = null;

		try {
			st = connection.prepareStatement(priceSelectQuery, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			st.setDouble(1, bookPrice);
			ResultSet rs = st.executeQuery();
			if (rs.next() == false) {
				throw new PriceBookNotFoundException("Book Not Found with this price");
			}
			rs.beforeFirst();
			while (rs.next()) {
				Book book = new Book();
				book.setTitle(rs.getString(1));
				book.setAuthor(rs.getString(2));
				book.setCategory(rs.getString(3));
				book.setPrice(rs.getInt(4));
				book.setBookId(rs.getInt(5));
				bookByPrice.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				st.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
			ModelDao.closeConnection();
		}
		return bookByPrice;
	}

}

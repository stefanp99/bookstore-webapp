package com.webservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.sql.*;

import com.book.Book;
import com.book.DateComparator;
import com.book.NameComparator;
import com.book.PriceComparator;

public class BookRepository {
	Connection con = null;
	public BookRepository() {
		String sql_url = "jdbc:postgresql://localhost:5432/library";
		String username = "postgres";
		String password = "000";
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(sql_url, username, password);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	public List<Book> getBooks(){
		List<Book> books = new ArrayList<>();
		String query = "select * from books";
		try {
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		while (rs.next()) {
			Book b = new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
			books.add(b);
		}
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return books;
	}
	
	public Book getBook(String param, String value) {
		Book b = new Book();
		String query = "select * from books where lower(" + param + ") like " + "'%" + value + "%'";
		try {
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		if (rs.next()) {
			b = new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5)); //rs.getString(1) - isbn, rs.getString(2) - title
		}
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return b;
	}
	
	public List<Book> findBooks(String param, String value) {
		List<Book> books = new ArrayList<>();
		String query = "select * from books where lower(" + param + ") like " + "'%" + value + "%'";
		// SELECT * FROM books WHERE title LIKE '%ore%';
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				Book b = new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5)); //rs.getString(1) - isbn, rs.getString(2) - title
				books.add(b);
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return books;
	}
	
	public void addBook(String isbn, String title, String author, int price, String published_date) {
		List<Book> books = new ArrayList<>();
		String query = "select * from books";
		try {
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		while (rs.next()) {
			Book b = new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5)); //rs.getString(1) - isbn, rs.getString(2) - title
			books.add(b);
		}
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		boolean found = false;
		
		for (Book b:books) {
			if (b.getISBN().equals(isbn)) {
				found = true;
				updateBook(isbn, title, author, price, published_date);
			}
		}
		
		if (!found) {
			query = "insert into books values (?, ?, ?, ?, ?)";
			try {
				PreparedStatement st = con.prepareStatement(query); //pentru data manipulation e diferit
				st.setString(1, isbn);
				st.setString(2, title);
				st.setString(3, author);
				st.setInt(4, price);
				st.setString(5, published_date);
				st.executeUpdate();
			}
			catch (Exception e) {
				System.out.println(e);
			}
		}
}

	public void updateBook(String isbn, String title, String author, int price, String published_date) {
		List<Book> books = new ArrayList<>();
		String query = "select * from books";
		try {
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		while (rs.next()) {
			Book b = new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5)); //rs.getString(1) - isbn, rs.getString(2) - title
			books.add(b);
		}
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		query = "update books set title=?, author=?, price=?, published_date=? where isbn='"+isbn+"'";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, title);
			ps.setString(2, author);
			ps.setInt(3, price);
			ps.setString(4, published_date);
			ps.executeUpdate();
			}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	public void removeBook(String isbn) {
		String query = "delete from books where isbn = ?";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, isbn);
			ps.executeUpdate();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public List<Book> sortByPrice(String order){
		List<Book> books = new ArrayList<>();
		books = getBooks();
		
		if (order.equals("asc")) {
			Collections.sort(books, new PriceComparator());
			return books;
		}
		
		Collections.sort(books, new PriceComparator().reversed());
		return books;
	}
	
	public List<Book> sortByDate(String order){
		List<Book> books = new ArrayList<>();
		books = getBooks();
		
		if (order.equals("asc")) {
			Collections.sort(books, new DateComparator());
			return books;
		}
		
		Collections.sort(books, new DateComparator().reversed());
		return books;
	}
	
	public List<Book> sortByName(String order){
		List<Book> books = new ArrayList<>();
		books = getBooks();
		
		if(order.equals("asc")) {
			Collections.sort(books, new NameComparator());
			return books;
		}
		
		Collections.sort(books, new NameComparator().reversed());
		return books;
	}
}

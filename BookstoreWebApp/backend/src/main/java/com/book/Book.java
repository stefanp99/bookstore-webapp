package com.book;


public class Book {
	private String isbn;
    private String title;
    private String author;
    private int price;
    private String published_date;
    
    public Book(){
    	
    }
    
	public Book(String isbn, String title, String author, int price, String published_date) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.price = price;
		this.published_date = published_date;
	}
	
	public Book(Book b) {
		this.isbn = b.isbn;
		this.title = b.title;
		this.author = b.author;
		this.price = b.price;
		this.published_date = b.published_date;
	}
	
    public String getISBN(){
    	return isbn;
    }
    
    public String getTitle(){
    	return title;
    }
    
    public String getAuthor(){
    	return author;
    }
    
    public int getPrice(){
    	return price;
    }
    
    public String getDate(){
    	return published_date;
    }
    
    public void setISBN(String isbn) {
    	this.isbn = isbn;
    }
    
    public void setTitle(String title) {
    	this.title = title;
    }
    
    public void setAuthor(String author) {
    	this.author = author;
    }
    
    public void setPrice(int price) {
    	this.price = price;
    }
    
    public void setDate(String published_date) {
    	this.published_date = published_date;
    }
    
}



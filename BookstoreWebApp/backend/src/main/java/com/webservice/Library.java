package com.webservice;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.book.Book;

@Path("/library")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Library {
	
	BookRepository repo = new BookRepository();
@GET
@Path("/books")
	public List<Book> getBooks() {
		return repo.getBooks();
	}
	
@GET
@Path("/search/{param}/{value}")
	public List<Book> findBooks(@PathParam("param") String param, @PathParam("value") String value) {
		return repo.findBooks(param, value);
	}

@PUT
@Path("/book/{isbn}")
	public Book addBook(@PathParam("isbn") String isbn, @QueryParam("title") String title, @QueryParam("author") String author, @QueryParam("price") int price, @QueryParam("date") String published_date) {
		System.out.println(published_date);
		repo.addBook(isbn, title, author, price, published_date);
    	return repo.getBook("isbn", isbn);
    }

@DELETE
@Path("/book/{isbn}")
	public Book removeBook(@PathParam("isbn") String isbn) {
		Book b = repo.getBook("isbn", isbn);
		repo.removeBook(isbn);
		return b;
}

@GET
@Path("/sort/price/{order}")
	public List<Book> sortPrice(@PathParam("order") String order){
	return repo.sortByPrice(order);
}

@GET
@Path("/sort/date/{order}")
	public List<Book> sortDate(@PathParam("order") String order){
	return repo.sortByDate(order);
}

@GET
@Path("/sort/title/{order}")
	public List<Book> sortName(@PathParam("order") String order){
	return repo.sortByName(order);
}

}




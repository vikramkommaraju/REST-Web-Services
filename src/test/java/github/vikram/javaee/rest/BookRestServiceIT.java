package github.vikram.javaee.rest;

import static org.junit.Assert.*;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBException;

import org.junit.Test;

public class BookRestServiceIT {
	 
	  private static URI uri = UriBuilder.
	               fromUri("http://localhost/rest-1.0/rs/book").port(8080).build();
	  private static Client client = ClientBuilder.newClient();
	  
	  @Test
	  public void shouldNotCreateANullBook() throws JAXBException {
	 
	    // POSTs a Null Book
	    Response response = client.target(uri).request().post(Entity.entity(null , 
	                                                          MediaType.APPLICATION_XML));
	    assertEquals(Response.Status.BAD_REQUEST.toString(), response.getStatusInfo().toString());
	  }
	 
	  @Test
	  public void shouldNotFindTheBookID() throws JAXBException {
	 
	    // GETs a Book with an unknown ID
	    Response response = client.target(uri).path("unknownID").request().get();
	    assertEquals(Response.Status.NOT_FOUND.toString(), response.getStatusInfo().toString());
	  }
	 
	  @Test
	  public void shouldCreateAndDeleteABook() throws JAXBException {
	 
	    Book book = new Book("H2G2", 12.5F, "Science book", "1-84023-742-2", 354, false);
	 
	    // POSTs a Book
	    Response response = client.target(uri)
	    						  .request()
	    						  .post(Entity.entity(book, MediaType.APPLICATION_XML));
	    
	    assertEquals(Response.Status.CREATED.toString(), response.getStatusInfo().toString());
	    URI bookURI = response.getLocation();
	 
	    // With the location, GETs the Book
	    response = client.target(bookURI).request().get();
	    book = response.readEntity(Book.class);
	    assertEquals(Response.Status.OK.toString(), response.getStatusInfo().toString());
	    assertEquals("H2G2", book.getTitle());
	 
	    // Gets the book id and DELETEs it
	    String bookId = bookURI.toString().split("/")[6];
	    response = client.target(uri).path(bookId).request(). delete ();
	    assertEquals(Response.Status.NO_CONTENT.toString(), response.getStatusInfo().toString());
	 
	    // GETs the Book and checks it has been deleted
	    response = client.target(bookURI).request(). get ();
	    assertEquals(Response.Status.NOT_FOUND.toString(), response.getStatusInfo().toString());
	 
	  }
	}
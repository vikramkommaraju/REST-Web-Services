package github.vikram.javaee.rest;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@NamedQuery(name = Book.FIND_ALL, query = "SELECT b from Book b")
public class Book {
	
	public static final String FIND_ALL = "Book.FindAllBooks";
	private static ValidatorFactory vf;
	private static Validator validator;

	@Id @GeneratedValue 
	private String id;
	
	@NotNull @Size(min = 4, max = 50)
	@Column(nullable=false)
	private String title;
	
	private Float price;
	
	@Size(min=10, max=2000)
	@Column(length=2000)
	private String description;
	
	private String isbn;
	
	private Integer nbOfPage;
	
	private Boolean illustrations;
	
	
	public Book() {
	
	}
	
	public Book(String title, Float price, String description,
			String isbn, Integer nbOfPage, Boolean illustrations) {
		super();
		this.title = title;
		this.price = price;
		this.description = description;
		this.isbn = isbn;
		this.nbOfPage = nbOfPage;
		this.illustrations = illustrations;
	}

	
	public void printViolations(Set<ConstraintViolation<Book>> violations) {
		
		
		for(ConstraintViolation<Book> v : violations) {
			System.out.println("####################");
			System.out.println("Violation Property: " + v.getPropertyPath());
			System.out.println("Violation Message: " + v.getMessage());
			System.out.println("Violation Bean: " + v.getRootBean().getClass());
			System.out.println("####################");
		}
		
	}

	@PrePersist
	public void validateBook(){
		vf = Validation.buildDefaultValidatorFactory();
		validator = vf.getValidator();
		System.out.println("Need to prepersist the book: " + this);
		Set<ConstraintViolation<Book>> violations = validator.validate(this);
		
		if(violations.size() > 0) {
			System.out.println("Validation of book failed in prepersist to DB!");
			printViolations(violations);
		}
		
		vf.close();
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", price=" + price
				+ ", description=" + description + ", isbn=" + isbn
				+ ", nbOfPage=" + nbOfPage + ", illustrations=" + illustrations
				+ "]";
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Float getPrice() {
		return price;
	}


	public void setPrice(Float price) {
		this.price = price;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getIsbn() {
		return isbn;
	}


	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}


	public Integer getNbOfPage() {
		return nbOfPage;
	}


	public void setNbOfPage(Integer nbOfPage) {
		this.nbOfPage = nbOfPage;
	}


	public Boolean getIllustrations() {
		return illustrations;
	}


	public void setIllustrations(Boolean illustrations) {
		this.illustrations = illustrations;
	}

	
	
	
	

}

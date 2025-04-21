package lab.bookshop.domain.Item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B")
@Getter
@Setter
public class Book extends Item {

	private String author;
	private String isbn;

	//== 생성 메소드 ==//
	public static Book createBook(String name, int price, int stockQuantity, String author, String isbn) {
		Book book = new Book();
		book.setName(name);
		book.setPrice(price);
		book.setStockQuantity(stockQuantity);
		book.setAuthor(author);
		book.setIsbn(isbn);
		return book;
	}
}

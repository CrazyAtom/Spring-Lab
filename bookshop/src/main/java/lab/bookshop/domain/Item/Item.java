package lab.bookshop.domain.Item;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import lab.bookshop.domain.Category;
import lab.bookshop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Item {

	@Id
	@GeneratedValue
	@Column(name = "item_id")
	private Long id;

	private String name; // 이름
	private int price; // 가격
	private int stockQuantity; // 재고수량

	@ManyToMany(mappedBy = "items")
	private List<Category> categories = new ArrayList<>();

	//== 비즈니스 로직 ==//

	/**
	 * stock 증가
	 */
	public void addStock(int quantity) {
		this.stockQuantity += quantity;
	}

	/**
	 * stock 감소
	 */
	public void removeStock(int quantity) {
		int restStock = this.stockQuantity -= quantity;
		if (restStock < 0) {
			throw new NotEnoughStockException("need more stock");
		}
		this.stockQuantity = restStock;
	}
}

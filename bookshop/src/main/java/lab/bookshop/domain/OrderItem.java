package lab.bookshop.domain;

import static jakarta.persistence.FetchType.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lab.bookshop.domain.Item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

	@Id
	@GeneratedValue
	@Column(name = "order_item_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	@JsonIgnore
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	private int orderPrice; // 주문 가격
	private int count; // 주문 수량

	//==생성 메서드==//
	public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setOrderPrice(orderPrice);
		orderItem.setCount(count);

		item.removeStock(count);
		return orderItem;
	}

	//==비즈니스 로직==//
	public void cancel() {
		getItem().addStock(count);
	}

	//==조회 로직==//

	/**
	 * 주문상품 전체 가격 조회
	 */
	public int getTotalPrice() {
		return getOrderPrice() * getCount();
	}
}

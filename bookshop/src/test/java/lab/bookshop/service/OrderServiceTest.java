package lab.bookshop.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import lab.bookshop.domain.Address;
import lab.bookshop.domain.Item.Book;
import lab.bookshop.domain.Item.Item;
import lab.bookshop.domain.Member;
import lab.bookshop.domain.Order;
import lab.bookshop.domain.OrderStatus;
import lab.bookshop.exception.NotEnoughStockException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

	@Autowired
	MemberService memberService;
	@Autowired
	OrderService orderService;
	@Autowired
	ItemService itemService;

	@Test
	public void 상품주문() throws Exception {
		//given
		Member member = createMember("회원1", new Address("서울", "강가", "123-123"));
		Item item = createBook("JPA", 10000, 10);
		int orderCount = 2;

		//when
		Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

		//then
		Order getOrder = orderService.findOrder(orderId);

		assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
		assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
		assertEquals(10000 * 2, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다.");
		assertEquals(8, item.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");
	}

	@Test
	public void 주문취소() throws Exception {
		//given
		Member member = createMember("회원1", new Address("서울", "강가", "123-123"));
		Item item = createBook("JPA", 10000, 10);
		int orderCount = 2;

		Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

		//when
		orderService.cancelOrder(orderId);

		//then
		Order getOrder = orderService.findOrder(orderId);
		assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문 취소시 상태는 CANCEL이다.");
		assertEquals(10, item.getStockQuantity(), "주문이 취소된 상품은 그만큼 재고가 증가해야 한다.");
	}

	@Test
	public void 상품주문_재고수량초과() throws Exception {
		//given
		Member member = createMember("회원1", new Address("서울", "강가", "123-123"));
		Item item = createBook("JPA", 10000, 10);

		//when
		int orderCount = 11;

		//then
		assertThrows(NotEnoughStockException.class, () -> {
			orderService.order(member.getId(), item.getId(), orderCount);
		}, "재고 수량 부족 예외가 발생해야 합니다.");
	}

	private Member createMember(String name, Address address) {
		Member member = Member.createMember(name, address);
		memberService.join(member);
		return member;
	}

	private Book createBook(String name, int price, int stockQuantity) {
		Book book = new Book();
		book.setName(name);
		book.setPrice(price);
		book.setStockQuantity(stockQuantity);
		itemService.saveItem(book);
		return book;
	}
}

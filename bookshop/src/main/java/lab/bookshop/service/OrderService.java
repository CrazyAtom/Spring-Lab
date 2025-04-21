package lab.bookshop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lab.bookshop.domain.Delivery;
import lab.bookshop.domain.Item.Item;
import lab.bookshop.domain.Member;
import lab.bookshop.domain.Order;
import lab.bookshop.domain.OrderItem;
import lab.bookshop.domain.OrderSearch;
import lab.bookshop.repository.ItemRepository;
import lab.bookshop.repository.MemberRepository;
import lab.bookshop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;

	/**
	 * 주문
	 */
	@Transactional
	public Long order(Long memberId, Long itemId, int count) {

		// 엔터티 조회
		Member member = memberRepository.findOne(memberId);
		Item item = itemRepository.findOne(itemId);

		// 배송정보 생성
		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());

		// 주문상품 생성
		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

		// 주문 생성
		Order order = Order.createOrder(member, delivery, orderItem);

		// 주문 저장
		orderRepository.save(order);

		return order.getId();
	}

	/**
	 * 주문 취소
	 */
	@Transactional
	public void cancelOrder(Long orderId) {
		// 주문 엔터티 조회
		Order order = orderRepository.fineOne(orderId);
		// 주문 취소
		order.cancel();
	}

	public Order findOrder(Long orderId) {
		return orderRepository.fineOne(orderId);
	}

	/**
	 * 주문 검색
	 */
	public List<Order> findOrders(OrderSearch orderSearch) {
		return orderRepository.findAll(orderSearch);
	}
}

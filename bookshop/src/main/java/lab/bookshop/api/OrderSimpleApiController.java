package lab.bookshop.api;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lab.bookshop.domain.Address;
import lab.bookshop.domain.Order;
import lab.bookshop.domain.OrderSearch;
import lab.bookshop.domain.OrderStatus;
import lab.bookshop.repository.OrderRepository;
import lab.bookshop.repository.order.simplequery.OrderSimpleQueryDto;
import lab.bookshop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

	private final OrderRepository orderRepository;
	private final OrderSimpleQueryRepository orderSimpleQueryRepository;

	@GetMapping("/api/v1/simple-orders")
	public List<Order> ordersV1() {
		List<Order> all = orderRepository.findAllByString(new OrderSearch());
		for (Order order : all) {
			// Lazy 강제 초기화
			order.getMember().getName();
			order.getDelivery().getAddress();
		}
		return all;
	}

	@GetMapping("/api/v2/simple-orders")
	public List<SimpleOrderDto> ordersV2() {
		List<Order> orders = orderRepository.findAllByString(new OrderSearch());

		List<SimpleOrderDto> collect = orders.stream()
			.map(o -> new SimpleOrderDto(o))
			.toList();

		return collect;
	}

	@GetMapping("/api/v3/simple-orders")
	public List<SimpleOrderDto> ordersV3() {
		List<Order> orders = orderRepository.findAllWithMemberDelivery();

		List<SimpleOrderDto> collect = orders.stream()
			.map(o -> new SimpleOrderDto(o))
			.toList();

		return collect;
	}

	@GetMapping("/api/v4/simple-orders")
	public List<OrderSimpleQueryDto> ordersV4() {
		return orderSimpleQueryRepository.findOrderDtos();
	}

	@Data
	private class SimpleOrderDto {
		private Long orderId;
		private String name;
		private LocalDateTime orderDate;
		private OrderStatus status;
		private Address address;

		public SimpleOrderDto(Order order) {
			orderId = order.getId();
			name = order.getMember().getName();
			orderDate = order.getOrderDate();
			status = order.getStatus();
			address = order.getDelivery().getAddress();
		}
	}
}

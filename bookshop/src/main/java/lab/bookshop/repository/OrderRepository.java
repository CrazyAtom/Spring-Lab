package lab.bookshop.repository;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import lab.bookshop.domain.Order;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

	private final EntityManager em;

	public void save(Order order) {
		em.persist(order);
	}

	public Order fineOne(Long id) {
		return em.find(Order.class, id);
	}

	// TODO
	// public List<Order> findAll(OrderSearch orderSearch) {
	//
	// }
}

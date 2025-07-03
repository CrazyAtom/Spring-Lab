package lab.bookshop.repository.order.simplequery;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

	private final EntityManager em;

	/**
	 * DTO로 직접 조회
	 * - JPA에서 제공하는 생성자 표현식을 사용하여 DTO를 직접 조회
	 */
	public List<OrderSimpleQueryDto> findOrderDtos() {
		return em.createQuery(
				"select new lab.bookshop.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address)"
					+
					" from Order o" +
					" join o.member m" +
					" join o.delivery d", OrderSimpleQueryDto.class)
			.getResultList();
	}
}

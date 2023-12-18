package ecommerce.mobile.repository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ecommerce.mobile.entity.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

public interface LoggerRepository extends JpaRepository<Logger, Integer> {
	@Transactional
	@Query
	default List<Logger> findWithFilters(Integer status, String method, Integer userId, String companyName,
			String message, String agent, String result, String params, String body, String endpoint, String createdAt,
			String updatedAt, Pageable pageable, EntityManager entityManager) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Logger> cq = cb.createQuery(Logger.class);
		Root<Logger> logger = cq.from(Logger.class);
		List<Predicate> predicates = new ArrayList<>();
		List<Order> orders = new ArrayList<>();

		if (method != null) {
			predicates.add(cb.like(cb.lower(logger.get("method")), "%" + method.toLowerCase() + "%"));
		}
		if (message != null) {
			predicates.add(cb.like(cb.lower(logger.get("message")), "%" + message.toLowerCase() + "%"));
		}
		if (agent != null) {
			predicates.add(cb.like(cb.lower(logger.get("agent")), "%" + agent.toLowerCase() + "%"));
		}
		if (result != null) {
			predicates.add(cb.like(cb.lower(logger.get("result")), "%" + result.toLowerCase() + "%"));
		}
		if (params != null) {
			predicates.add(cb.like(cb.lower(logger.get("params")), "%" + params.toLowerCase() + "%"));
		}
		if (body != null) {
			predicates.add(cb.like(cb.lower(logger.get("body")), "%" + body.toLowerCase() + "%"));
		}
		if (endpoint != null) {
			predicates.add(cb.like(cb.lower(logger.get("endpoint")), "%" + endpoint.toLowerCase() + "%"));
		}

		if (status != null) {
			predicates.add(cb.equal(logger.get("status"), status));
		}
		if (userId != null) {
			predicates.add(cb.equal(logger.get("user").get("id"), userId));
		}
		if (companyName != null) {
			predicates.add(cb.like(cb.lower(logger.get("company").get("name")), "%" + companyName.toLowerCase() + "%"));
		}
		if (createdAt != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy:HH:mm:ss");
			String createdAtStr = createdAt.formatted(formatter);
			predicates.add(cb.like(logger.get("createdAt"), createdAtStr + "%"));
		}
		if (updatedAt != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy:HH:mm:ss");
			String createdAtStr = createdAt.formatted(formatter);
			predicates.add(cb.like(logger.get("updatedAt"), createdAtStr + "%"));
		}
		if (pageable.getSort() != null) {
			for (Sort.Order order : pageable.getSort()) {

				orders.add(order.isAscending() ? cb.asc(logger.get(order.getProperty()))
						: cb.desc(logger.get(order.getProperty())));
			}
			cq.orderBy(orders);
		}
		cq.where(predicates.toArray(new Predicate[0]));

		return entityManager.createQuery(cq).setFirstResult((int) pageable.getOffset())
				.setMaxResults(pageable.getPageSize()).getResultList();
	}
}

package ecommerce.mobile.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ecommerce.mobile.entity.Invoice;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
	@Transactional
	@Query
	default List<Invoice> findWithFilters(String note, Boolean isPaid, String email, String userName,
			String companyName, Integer status, Pageable pageable, EntityManager entityManager) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Invoice> cq = cb.createQuery(Invoice.class);
		Root<Invoice> invoice = cq.from(Invoice.class);
		List<Predicate> predicates = new ArrayList<>();
		List<Order> orders = new ArrayList<>();

		if (note != null) {
			predicates.add(cb.like(cb.lower(invoice.get("name")), "%" + note.toLowerCase() + "%"));
		}
		if (note != null) {
			predicates.add(cb.like(cb.lower(invoice.get("name")), "%" + note.toLowerCase() + "%"));
		}

		if (email != null) {
			predicates.add(cb.equal(invoice.get("user").get("email"), email));
		}
		if (userName != null) {
			predicates.add(cb.like(cb.lower(invoice.get("user").get("name")), "%" + userName.toLowerCase() + "%"));
		}

		if (companyName != null) {
			predicates.add(cb.equal(invoice.get("company").get("name"), companyName));
		}

		predicates.add(cb.equal(invoice.get("status"), status));

		if (pageable.getSort() != null) {
			for (Sort.Order order : pageable.getSort()) {

				orders.add(order.isAscending() ? cb.asc(invoice.get(order.getProperty()))
						: cb.desc(invoice.get(order.getProperty())));
			}
			cq.orderBy(orders);
		}
		cq.where(predicates.toArray(new Predicate[0]));

		return entityManager.createQuery(cq).setFirstResult((int) pageable.getOffset())
				.setMaxResults(pageable.getPageSize()).getResultList();
	}
}

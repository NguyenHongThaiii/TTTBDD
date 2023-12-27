package ecommerce.mobile.repository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ecommerce.mobile.entity.Invoice;
import ecommerce.mobile.seriveimp.UserServiceImp;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
	@Query
	default List<Invoice> findWithFilters(String note, Boolean isPaid, String emailUser, String username,
			String phoneGuest, String guestname, String companyName, Integer status, String createdAt, String updatedAt,
			Pageable pageable, EntityManager entityManager) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Invoice> cq = cb.createQuery(Invoice.class);
		Root<Invoice> invoice = cq.from(Invoice.class);
		List<Predicate> predicates = new ArrayList<>();
		List<Order> orders = new ArrayList<>();

		if (note != null) {
			predicates.add(cb.like(cb.lower(invoice.get("name")), "%" + note.toLowerCase() + "%"));
		}
		if (isPaid != null) {
			predicates.add(cb.equal(invoice.get("isPaid"), isPaid));
		}
		if (emailUser != null) {
			predicates.add(cb.like(cb.lower(invoice.get("user").get("email")), "%" + emailUser.toLowerCase() + "%"));
		}
		if (username != null) {
			predicates.add(cb.like(cb.lower(invoice.get("user").get("name")), "%" + username.toLowerCase() + "%"));
		}
		if (phoneGuest != null) {
			predicates.add(cb.like(cb.lower(invoice.get("guest").get("phone")), "%" + phoneGuest.toLowerCase() + "%"));
		}
		if (guestname != null) {
			predicates.add(cb.like(cb.lower(invoice.get("guest").get("name")), "%" + guestname.toLowerCase() + "%"));
		}
		if (companyName != null) {
			predicates.add(cb.equal(invoice.get("company").get("name"), companyName));
		}
		if (status != null) {
			predicates.add(cb.equal(invoice.get("status"), status));
		}
		if (createdAt != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy:HH:mm:ss");
			String createdAtStr = createdAt.formatted(formatter);
			predicates.add(cb.like(invoice.get("createdAt"), createdAtStr + "%"));
		}
		if (updatedAt != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy:HH:mm:ss");
			String createdAtStr = createdAt.formatted(formatter);
			predicates.add(cb.like(invoice.get("updatedAt"), createdAtStr + "%"));
		}
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

	Optional<Invoice> findByKey(String key);

	@Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM Invoice i WHERE i.key = :key")
	Boolean existsByQrKey(@Param("key") String key);
}

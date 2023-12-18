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

import ecommerce.mobile.entity.Customer;
import ecommerce.mobile.seriveimp.UserServiceImp;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	Optional<Customer> findByName(String name);

	Boolean existsByName(String name);

	Optional<Customer> findByEmail(String email);

	Boolean existsByEmail(String email);

	Optional<Customer> findByPhone(String phone);

	Boolean existsByPhone(String phone);

	static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

	@Transactional
	@Query
	default List<Customer> findWithFilters(String name, String email, String phone, Integer status, String createdAt,
			String updatedAt, String companyName, Pageable pageable, EntityManager entityManager) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
		Root<Customer> customer = cq.from(Customer.class);
		List<Predicate> predicates = new ArrayList<>();
		List<Order> orders = new ArrayList<>();

		if (name != null) {
			predicates.add(cb.like(cb.lower(customer.get("name")), "%" + name.toLowerCase() + "%"));
		}
		if (companyName != null) {
			predicates
					.add(cb.like(cb.lower(customer.get("company").get("name")), "%" + companyName.toLowerCase() + "%"));
		}
		if (email != null) {
			predicates.add(cb.like(cb.lower(customer.get("email")), "%" + email.toLowerCase() + "%"));
		}
		if (phone != null) {
			predicates.add(cb.like(cb.lower(customer.get("phone")), "%" + phone.toLowerCase() + "%"));
		}
		if (createdAt != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy:HH:mm:ss");
			String createdAtStr = createdAt.formatted(formatter);
			predicates.add(cb.like(customer.get("createdAt"), createdAtStr + "%"));
		}
		if (updatedAt != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy:HH:mm:ss");
			String createdAtStr = createdAt.formatted(formatter);
			predicates.add(cb.like(customer.get("updatedAt"), createdAtStr + "%"));
		}
		if (status != null) {
			predicates.add(cb.equal(customer.get("status"), status));
		}

		if (pageable.getSort() != null) {
			for (Sort.Order order : pageable.getSort()) {
				orders.add(order.isAscending() ? cb.asc(customer.get(order.getProperty()))
						: cb.desc(customer.get(order.getProperty())));
			}
			cq.orderBy(orders);
		}
		cq.where(predicates.toArray(new Predicate[0]));

		return entityManager.createQuery(cq).setFirstResult((int) pageable.getOffset())
				.setMaxResults(pageable.getPageSize()).getResultList();
	}
}

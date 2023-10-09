package ecommerce.mobile.repository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ecommerce.mobile.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface UserRepository extends JpaRepository<User, Integer> {
	@Query
	default List<User> findWithFilters(String name, String email, String phone, String gender, String role,
			Integer status, String createdAt, String updatedAt, String companyName, Pageable pageable,
			EntityManager entityManager) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);

		Root<User> user = cq.from(User.class);
		List<Predicate> predicates = new ArrayList<>();

		if (name != null) {
			predicates.add(cb.like(cb.lower(user.get("name")), "%" + name.toLowerCase() + "%"));
		}

		if (email != null) {
			predicates.add(cb.like(cb.lower(user.get("email")), "%" + email.toLowerCase() + "%"));
		}
		if (gender != null) {
			predicates.add(cb.like(cb.lower(user.get("gender")), "%" + gender.toLowerCase() + "%"));
		}
		if (phone != null) {
			predicates.add(cb.like(cb.lower(user.get("phone")), "%" + phone.toLowerCase() + "%"));
		}
		if (role != null) {
			predicates.add(cb.equal(user.get("role").get("name"), role));
		}
		if (status != null) {
			predicates.add(cb.equal(user.get("status"), status));
		}
		if (companyName != null) {
			predicates.add(cb.like(cb.lower(user.get("company").get("name")), "%" + companyName.toLowerCase() + "%"));
		}
		if (createdAt != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String createdAtStr = createdAt.formatted(formatter);
			predicates.add(cb.like(user.get("createdAt"), createdAtStr + "%"));
		}
		if (updatedAt != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String createdAtStr = createdAt.formatted(formatter);
			predicates.add(cb.like(user.get("updatedAt"), createdAtStr + "%"));
		}
		if (pageable.getSort() != null) {
			List<Order> orders = new ArrayList<>();
			for (Sort.Order order : pageable.getSort()) {
				orders.add(order.isAscending() ? cb.asc(user.get(order.getProperty()))
						: cb.desc(user.get(order.getProperty())));
			}
			cq.orderBy(orders);
		}
		cq.where(predicates.toArray(new Predicate[0]));

		return entityManager.createQuery(cq).setFirstResult((int) pageable.getOffset())
				.setMaxResults(pageable.getPageSize()).getResultList();
	}

	Optional<User> findByEmail(String email);

	Boolean existsByEmail(String email);

	Boolean existsByPhone(String phone);

	Boolean existsByNameAndIdNot(String name, Integer id);

	Optional<User> findByName(String name);

	Optional<User> findByPhone(String phone);

	Boolean existsByName(String email);

}

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

import ecommerce.mobile.entity.Company;
import ecommerce.mobile.seriveimp.UserServiceImp;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
	Optional<Company> findByKeyCompany(String key);

	Optional<Company> findByName(String name);

	Boolean existsByName(String name);

	static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

	@Transactional
	@Query
	default List<Company> findWithFilters(String name, Integer status, String createdAt, String updatedAt,
			Pageable pageable, EntityManager entityManager) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Company> cq = cb.createQuery(Company.class);
		Root<Company> company = cq.from(Company.class);
		List<Predicate> predicates = new ArrayList<>();
		List<Order> orders = new ArrayList<>();

		if (name != null) {
			predicates.add(cb.like(cb.lower(company.get("name")), "%" + name.toLowerCase() + "%"));
		}
		if (createdAt != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String createdAtStr = createdAt.formatted(formatter);
			predicates.add(cb.like(company.get("createdAt"), createdAtStr + "%"));
		}
		if (updatedAt != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String createdAtStr = createdAt.formatted(formatter);
			predicates.add(cb.like(company.get("createdAt"), createdAtStr + "%"));
		}

		predicates.add(cb.equal(company.get("status"), status));

		if (pageable.getSort() != null) {
			for (Sort.Order order : pageable.getSort()) {
				orders.add(order.isAscending() ? cb.asc(company.get(order.getProperty()))
						: cb.desc(company.get(order.getProperty())));
			}
			cq.orderBy(orders);
		}
		cq.where(predicates.toArray(new Predicate[0]));

		return entityManager.createQuery(cq).setFirstResult((int) pageable.getOffset())
				.setMaxResults(pageable.getPageSize()).getResultList();
	}
}

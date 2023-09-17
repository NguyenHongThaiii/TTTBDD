package ecommerce.mobile.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ecommerce.mobile.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	Optional<Product> findByName(String name);

	Optional<Product> findByIdAndCompanyId(Integer id, Integer companyId);

	Boolean existsByName(String name);

	@Transactional
	@Query
	default List<Product> findWithFilters(Integer status, String name, Integer priceMin, Integer priceMax, Integer type,
			String companyName, Pageable pageable, EntityManager entityManager) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> cq = cb.createQuery(Product.class);
		Root<Product> product = cq.from(Product.class);
		List<Predicate> predicates = new ArrayList<>();
		List<Order> orders = new ArrayList<>();

		if (name != null) {
			predicates.add(cb.like(cb.lower(product.get("name")), "%" + name.toLowerCase() + "%"));
		}

		if (priceMin != null && priceMax != null) {
			predicates.add(cb.between(product.get("price"), priceMin, priceMax));
		}

		if (type != null) {
			predicates.add(cb.equal(product.get("type"), type));
		}
		if (companyName != null) {
			predicates
					.add(cb.like(cb.lower(product.get("company").get("name")), "%" + companyName.toLowerCase() + "%"));
		}

		predicates.add(cb.equal(product.get("status"), status));

		if (pageable.getSort() != null) {
			for (Sort.Order order : pageable.getSort()) {

				orders.add(order.isAscending() ? cb.asc(product.get(order.getProperty()))
						: cb.desc(product.get(order.getProperty())));
			}
			cq.orderBy(orders);
		}
		cq.where(predicates.toArray(new Predicate[0]));

		return entityManager.createQuery(cq).setFirstResult((int) pageable.getOffset())
				.setMaxResults(pageable.getPageSize()).getResultList();
	}
}

package ecommerce.mobile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.mobile.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}

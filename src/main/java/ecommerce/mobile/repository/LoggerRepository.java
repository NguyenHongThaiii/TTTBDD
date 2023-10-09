package ecommerce.mobile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.mobile.entity.Logger;

public interface LoggerRepository extends JpaRepository<Logger, Integer> {

}

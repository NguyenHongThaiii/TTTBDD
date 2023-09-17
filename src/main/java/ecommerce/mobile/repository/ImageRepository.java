package ecommerce.mobile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import ecommerce.mobile.entity.Image;
import jakarta.transaction.Transactional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
	List<Image> findAllImageByProductId(Integer productId);

	@Transactional
	@Modifying
	@Query("DELETE FROM Image i WHERE i.product.id = :productId")
	void deleteAllImageByProductId(Integer productId);
}

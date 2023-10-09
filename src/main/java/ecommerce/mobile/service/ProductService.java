package ecommerce.mobile.service;

import java.io.IOException;
import java.util.List;

import ecommerce.mobile.payload.ProductCreateDTO;
import ecommerce.mobile.payload.ProductDTO;
import ecommerce.mobile.payload.ProductDeleteDTO;
import ecommerce.mobile.payload.ProductUpdateDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface ProductService {
	ProductDTO createProduct(ProductCreateDTO productCreateDto, HttpServletRequest request) throws IOException;

	ProductDTO getProductById(int id);

	void deleteProduct(ProductDeleteDTO productDeleteDto, HttpServletRequest request) throws IOException;

	ProductDTO updateProduct(ProductUpdateDTO productUpdateDto, HttpServletRequest request) throws IOException;

	List<ProductDTO> getListProducts(int limit, int page, Integer status, String name, String description, Integer type,
			Integer priceMin, Integer priceMax, String companyName, String createdAt, String updatedAt, String sortBy);
}

package ecommerce.mobile.service;

import java.io.IOException;
import java.util.List;

import ecommerce.mobile.payload.ProductCreateDTO;
import ecommerce.mobile.payload.ProductDTO;
import ecommerce.mobile.payload.ProductDeleteDTO;
import ecommerce.mobile.payload.ProductUpdateDTO;

public interface ProductService {
	ProductDTO createProduct(ProductCreateDTO productCreateDto) throws IOException;

	ProductDTO getProductById(int id);

	void deleteProduct(ProductDeleteDTO productDeleteDto) throws IOException;

	ProductDTO updateProduct(ProductUpdateDTO productUpdateDto) throws IOException;

	List<ProductDTO> getListProducts(int limit, int page, int status, String name, String description, Integer type,
			Integer priceMin, Integer priceMax, String companyName, String sortBy);
}

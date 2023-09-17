package ecommerce.mobile.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.mobile.payload.ProductCreateDTO;
import ecommerce.mobile.payload.ProductDTO;
import ecommerce.mobile.payload.ProductDeleteDTO;
import ecommerce.mobile.payload.ProductUpdateDTO;
import ecommerce.mobile.service.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
	ProductService productService;

	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}

	@GetMapping("")
	public ResponseEntity<List<ProductDTO>> getListProducts(@RequestParam(defaultValue = "5") int limit,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "1") int status,
			@RequestParam(required = false) String name, @RequestParam(required = false) String description,
			@RequestParam(required = false) Integer type, @RequestParam(required = false) Integer priceMin,
			@RequestParam(required = false) Integer priceMax, @RequestParam(required = false) String companyName,
			@RequestParam(required = false, defaultValue = "") String sortBy) {
		List<ProductDTO> listproducts = productService.getListProducts(limit, page, status, name, description, type,
				priceMin, priceMax, companyName, sortBy);
		return new ResponseEntity<>(listproducts, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MOD')")
	@PostMapping("")
	public ResponseEntity<ProductDTO> createProduct(@Valid @ModelAttribute ProductCreateDTO productCreateDto)
			throws IOException {
		ProductDTO product = productService.createProduct(productCreateDto);
		return new ResponseEntity<ProductDTO>(product, HttpStatus.CREATED);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@PatchMapping("")
	public ResponseEntity<ProductDTO> updateProduct(@Valid @ModelAttribute ProductUpdateDTO productUpdateDto)
			throws IOException {
		ProductDTO productDto = productService.updateProduct(productUpdateDto);
		return new ResponseEntity<ProductDTO>(productDto, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable(name = "id") int id) {
		ProductDTO product = productService.getProductById(id);

		return new ResponseEntity<>(product, HttpStatus.OK);

	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("")
	public ResponseEntity<String> deleteProduct(@Valid @RequestBody ProductDeleteDTO productDeleteDto)
			throws IOException {
		productService.deleteProduct(productDeleteDto);
		return new ResponseEntity<String>("Delete successfully", HttpStatus.OK);
	}
}

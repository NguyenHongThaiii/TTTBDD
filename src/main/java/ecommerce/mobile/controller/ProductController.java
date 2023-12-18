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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Tag(name = "CRUD REST APIs Products")
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
	ProductService productService;

	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Get all products")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@GetMapping("")
	public ResponseEntity<List<ProductDTO>> getListProducts(@RequestParam(defaultValue = "20") Integer limit,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(required = false) Integer status,
			@RequestParam(required = false) String name, @RequestParam(required = false) String description,
			@RequestParam(required = false) Integer type, @RequestParam(required = false) Integer priceMin,
			@RequestParam(required = false) String createdAt, @RequestParam(required = false) String updatedAt,

			@RequestParam(required = false) Integer priceMax, @RequestParam(required = false) String companyName,
			@RequestParam(required = false, defaultValue = "") String sortBy) {
		List<ProductDTO> listproducts = productService.getListProducts(limit, page, status, name, description, type,
				priceMin, priceMax, companyName, createdAt, updatedAt, sortBy);
		return new ResponseEntity<>(listproducts, HttpStatus.OK);
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Create product ")
	@ApiResponse(responseCode = "201", description = "Http status 201 CREAED")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@PostMapping("")
	public ResponseEntity<ProductDTO> createProduct(@Valid @ModelAttribute ProductCreateDTO productCreateDto,
			HttpServletRequest request) throws IOException {
		ProductDTO product = productService.createProduct(productCreateDto, request);
		return new ResponseEntity<ProductDTO>(product, HttpStatus.CREATED);
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Update product by id")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@PatchMapping("")
	public ResponseEntity<ProductDTO> updateProduct(@Valid @ModelAttribute ProductUpdateDTO productUpdateDto,
			HttpServletRequest request) throws IOException {
		ProductDTO productDto = productService.updateProduct(productUpdateDto, request);
		return new ResponseEntity<ProductDTO>(productDto, HttpStatus.OK);
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Get product by id")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable(name = "id") int id) {
		ProductDTO product = productService.getProductById(id);

		return new ResponseEntity<>(product, HttpStatus.OK);

	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Delete product by id")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@DeleteMapping("")
	public ResponseEntity<String> deleteProduct(@Valid @RequestBody ProductDeleteDTO productDeleteDto,
			HttpServletRequest request) throws IOException {
		productService.deleteProduct(productDeleteDto, request);
		return new ResponseEntity<String>("Delete successfully", HttpStatus.OK);
	}
}

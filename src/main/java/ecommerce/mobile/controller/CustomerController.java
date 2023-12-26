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

import ecommerce.mobile.payload.CustomerCreateDTO;
import ecommerce.mobile.payload.CustomerDTO;
import ecommerce.mobile.payload.CustomerUpdateDTO;
import ecommerce.mobile.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Tag(name = "CRUD REST APIs Customers")
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
	private CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Get all Customers")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@GetMapping("")
	public ResponseEntity<List<CustomerDTO>> getAllCustomers(@RequestParam(defaultValue = "20") Integer limit,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(required = false) Integer status,
			@RequestParam(required = false) String name, @RequestParam(required = false) String email,
			@RequestParam(required = false) String phone, @RequestParam(required = false) String companyName,
			@RequestParam(required = false) String createdAt, @RequestParam(required = false) String updatedAt,
			@RequestParam(required = false) String sortBy) {
		List<CustomerDTO> listCustomerDTO = customerService.getAllCustomers(limit, page, name, email, phone,
				companyName, status, createdAt, updatedAt, sortBy);
		return ResponseEntity.ok(listCustomerDTO);
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Get Customer by id")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable(name = "id") Integer id) {
		CustomerDTO CustomerDTO = customerService.getCustomerById(id);
		return ResponseEntity.ok(CustomerDTO);
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Create Customer (only admin)")
	@ApiResponse(responseCode = "200", description = "Http status 201 CREATED")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@PostMapping("")
	public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerCreateDTO cs,
			HttpServletRequest request) throws IOException {
		CustomerDTO CustomerDTO = customerService.createCustomer(cs, request);
		return new ResponseEntity<CustomerDTO>(CustomerDTO, HttpStatus.CREATED);
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Update Customer by id")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@PatchMapping("")
	public ResponseEntity<CustomerDTO> updateCustomerById(@Valid @RequestBody CustomerUpdateDTO cs,
			HttpServletRequest request) throws IOException {
		CustomerDTO CustomerDTO = customerService.updateCustomerById(cs, request);
		return new ResponseEntity<CustomerDTO>(CustomerDTO, HttpStatus.OK);
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Delete Customer by id")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCustomerById(@PathVariable(name = "id") Integer id, HttpServletRequest request)
			throws IOException {
		customerService.delelteCustomerById(id, request);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

}

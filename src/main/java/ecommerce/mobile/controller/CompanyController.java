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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.mobile.payload.CompanyCreateDTO;
import ecommerce.mobile.payload.CompanyDTO;
import ecommerce.mobile.payload.CompanyUpdateDTO;
import ecommerce.mobile.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/companies")
@Tag(name = "CRUD REST APIs Companies")
public class CompanyController {
	private CompanyService companyService;

	public CompanyController(CompanyService companyService) {
		super();
		this.companyService = companyService;
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Get all companies")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("")
	public ResponseEntity<List<CompanyDTO>> getAllCompanies(@RequestParam(defaultValue = "20") Integer limit,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(required = false) Integer status,
			@RequestParam(required = false) String name, @RequestParam(required = false) String createdAt,
			@RequestParam(required = false) String updatedAt, @RequestParam(required = false) String sortBy) {
		List<CompanyDTO> listCompanyDto = companyService.getListCompanies(limit, page, status, name, createdAt,
				updatedAt, sortBy);
		return ResponseEntity.ok(listCompanyDto);
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Get company by id")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@GetMapping("id/{id}")
	public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable(name = "id") Integer id) {
		CompanyDTO companyDto = companyService.getById(id);
		return ResponseEntity.ok(companyDto);
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Get company by name")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@GetMapping("/{name}")
	public ResponseEntity<CompanyDTO> getCompanyByName(@PathVariable(name = "name") String name) {
		CompanyDTO companyDto = companyService.getByName(name);
		return ResponseEntity.ok(companyDto);
	}

	@Operation(summary = "Get company by key")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@GetMapping("/{key}")
	public ResponseEntity<CompanyDTO> getCompanyByKey(@PathVariable(name = "name") String key) {
		CompanyDTO companyDto = companyService.getByKey(key);
		return ResponseEntity.ok(companyDto);
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Create company (only admin)")
	@ApiResponse(responseCode = "200", description = "Http status 201 CREATED")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping("")
	public ResponseEntity<CompanyDTO> createCompany(@Valid @ModelAttribute CompanyCreateDTO cpr,
			HttpServletRequest request) throws IOException {
		CompanyDTO companyDto = companyService.createCompany(cpr, request);
		return new ResponseEntity<CompanyDTO>(companyDto, HttpStatus.CREATED);
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Update company by id")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD')")
	@PatchMapping("")
	public ResponseEntity<CompanyDTO> updateCompanyById(@Valid @ModelAttribute CompanyUpdateDTO cpu,
			HttpServletRequest request) throws IOException {
		CompanyDTO companyDto = companyService.updateCompany(cpu, request);
		return new ResponseEntity<CompanyDTO>(companyDto, HttpStatus.OK);
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Delete company by id")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCompanyById(@PathVariable(name = "id") Integer id, HttpServletRequest request)
			throws IOException {
		companyService.deleteById(id, request);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

}

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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {
	private CompanyService companyService;

	public CompanyController(CompanyService companyService) {
		super();
		this.companyService = companyService;
	}

	@GetMapping("")
	public ResponseEntity<List<CompanyDTO>> getAllCompanies(@RequestParam(defaultValue = "5") Integer limit,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "1") Integer status,
			@RequestParam(defaultValue = "") String name, @RequestParam(required = false) String createdAt,
			@RequestParam(required = false) String updatedAt, @RequestParam(required = false) String sortBy) {
		List<CompanyDTO> listCompanyDto = companyService.getListCompanies(limit, page, status, name, createdAt,
				updatedAt, sortBy);
		return ResponseEntity.ok(listCompanyDto);
	}

	@GetMapping("id/{id}")
	public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable(name = "id") Integer id) {
		CompanyDTO companyDto = companyService.getById(id);
		return ResponseEntity.ok(companyDto);
	}

	@GetMapping("/{name}")
	public ResponseEntity<CompanyDTO> getCompanyByName(@PathVariable(name = "name") String name) {
		CompanyDTO companyDto = companyService.getByName(name);
		return ResponseEntity.ok(companyDto);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MOD')")
	@PostMapping("")
	public ResponseEntity<CompanyDTO> createCompany(@Valid @ModelAttribute CompanyCreateDTO cpr) throws IOException {
		CompanyDTO companyDto = companyService.createCompany(cpr);
		return new ResponseEntity<CompanyDTO>(companyDto, HttpStatus.CREATED);
	}

	@PatchMapping("/{id}")
	@PostMapping("")
	public ResponseEntity<CompanyDTO> updateCompanyById(@Valid @ModelAttribute CompanyUpdateDTO cpu)
			throws IOException {
		CompanyDTO companyDto = companyService.updateCompany(cpu);
		return new ResponseEntity<CompanyDTO>(companyDto, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCompanyById(@PathVariable(name = "id") Integer id) throws IOException {
		companyService.deleteById(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

}

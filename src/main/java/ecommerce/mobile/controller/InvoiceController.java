package ecommerce.mobile.controller;

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

import ecommerce.mobile.payload.InvoiceCreateDTO;
import ecommerce.mobile.payload.InvoiceDTO;
import ecommerce.mobile.payload.InvoiceUpdateDTO;
import ecommerce.mobile.service.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/invoices")
@Tag(name = "CRUD REST APIs Invoices")

public class InvoiceController {

	private InvoiceService invoiceService;

	public InvoiceController(InvoiceService invoiceService) {
		super();
		this.invoiceService = invoiceService;
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Create invoice")
	@ApiResponse(responseCode = "201", description = "Http status 201 CREATED")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@PostMapping("")
	public ResponseEntity<InvoiceDTO> createInvoice(@Valid @ModelAttribute InvoiceCreateDTO invoiceCreateDto,
			HttpServletRequest request) {
		InvoiceDTO invoiceDto = invoiceService.createInvoice(invoiceCreateDto, request);

		return new ResponseEntity<InvoiceDTO>(invoiceDto, HttpStatus.CREATED);
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Get invoice by id")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@GetMapping("/id/{id}")
	public ResponseEntity<InvoiceDTO> getById(@PathVariable(name = "id") int id) {
		InvoiceDTO invoiceDto = invoiceService.getById(id);

		return new ResponseEntity<InvoiceDTO>(invoiceDto, HttpStatus.OK);
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Get invoice by key qr")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@GetMapping("/{key}")
	public ResponseEntity<InvoiceDTO> getByKeyQR(@PathVariable(name = "key") String key) {
		InvoiceDTO invoiceDto = invoiceService.getByKey(key);

		return new ResponseEntity<InvoiceDTO>(invoiceDto, HttpStatus.OK);
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Update invoice by id")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@PatchMapping("")
	public ResponseEntity<InvoiceDTO> updateInvoiceById(@Valid @RequestBody InvoiceUpdateDTO invoiceUpdateDto,
			HttpServletRequest request) {
		InvoiceDTO invoiceDto = invoiceService.updateInvoice(invoiceUpdateDto, request);
		return new ResponseEntity<InvoiceDTO>(invoiceDto, HttpStatus.OK);
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Get all invoices ")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@GetMapping("")
	public ResponseEntity<List<InvoiceDTO>> getALlInvoices(@RequestParam(defaultValue = "20") Integer limit,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(required = false) Integer status,
			@RequestParam(required = false) String createdAt, @RequestParam(required = false) String updatedAt,
			@RequestParam(required = false) String note, @RequestParam(required = false) Boolean isPaid,
			@RequestParam(required = false) String emailUser, @RequestParam(required = false) String username,
			@RequestParam(required = false) String phoneGuest, @RequestParam(required = false) String guestname,
			@RequestParam(required = false) String companyName, @RequestParam(required = false) String sortBy) {
		List<InvoiceDTO> listInvoiceDto = invoiceService.getListInvoices(limit, page, status, note, isPaid, emailUser,
				username, phoneGuest, guestname, companyName, createdAt, updatedAt, sortBy);
		return new ResponseEntity<List<InvoiceDTO>>(listInvoiceDto, HttpStatus.OK);
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Delete invoice by id")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletById(@PathVariable(name = "id") int id, HttpServletRequest request) {
		invoiceService.deletInvoice(id, request);

		return new ResponseEntity<String>("Ok", HttpStatus.OK);
	}
}

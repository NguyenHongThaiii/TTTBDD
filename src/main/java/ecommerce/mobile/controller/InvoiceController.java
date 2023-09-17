package ecommerce.mobile.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {

	private InvoiceService invoiceService;

	public InvoiceController(InvoiceService invoiceService) {
		super();
		this.invoiceService = invoiceService;
	}

	@PostMapping("")
	public ResponseEntity<InvoiceDTO> createInvoice(@Valid @RequestBody InvoiceCreateDTO invoiceCreateDto) {
		InvoiceDTO invoiceDto = invoiceService.createInvoice(invoiceCreateDto);

		return new ResponseEntity<InvoiceDTO>(invoiceDto, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<InvoiceDTO> getById(@PathVariable(name = "id") int id) {
		InvoiceDTO invoiceDto = invoiceService.getById(id);

		return new ResponseEntity<InvoiceDTO>(invoiceDto, HttpStatus.OK);
	}

	@PatchMapping("")
	public ResponseEntity<InvoiceDTO> updateInvoiceById(@Valid @RequestBody InvoiceUpdateDTO invoiceUpdateDto) {
		InvoiceDTO invoiceDto = invoiceService.updateInvoice(invoiceUpdateDto);
		return new ResponseEntity<InvoiceDTO>(invoiceDto, HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<List<InvoiceDTO>> getALlInvoices(@RequestParam(defaultValue = "5") Integer limit,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "1") Integer status,
			@RequestParam(required = false) String createdAt, @RequestParam(required = false) String updatedAt,
			@RequestParam(required = false) String note, @RequestParam(required = false) Boolean isPaid,
			@RequestParam(required = false) String email, @RequestParam(required = false) String username,
			@RequestParam(required = false) String companyName, @RequestParam(required = false) String sortBy) {
		List<InvoiceDTO> listInvoiceDto = invoiceService.getListInvoices(limit, page, status, note, isPaid, email,
				username, companyName, sortBy);
		return new ResponseEntity<List<InvoiceDTO>>(listInvoiceDto, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletById(@PathVariable(name = "id") int id) {
		invoiceService.deletInvoice(id);

		return new ResponseEntity<String>("Ok", HttpStatus.OK);
	}
}

package ecommerce.mobile.service;

import java.util.List;

import ecommerce.mobile.payload.InvoiceCreateDTO;
import ecommerce.mobile.payload.InvoiceDTO;
import ecommerce.mobile.payload.InvoiceUpdateDTO;

public interface InvoiceService {
	InvoiceDTO createInvoice(InvoiceCreateDTO invoiceCreateDto);

	InvoiceDTO updateInvoice(InvoiceUpdateDTO invoiceUpdateDto);

	InvoiceDTO getById(Integer id);

	void deletInvoice(Integer id);

	List<InvoiceDTO> getListInvoices(int limit, int page, int status, String note, Boolean isPaid, String email,
			String userName, String companyName, String sortBy);
}

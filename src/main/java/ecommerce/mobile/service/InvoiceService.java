package ecommerce.mobile.service;

import java.util.List;

import ecommerce.mobile.payload.InvoiceCreateDTO;
import ecommerce.mobile.payload.InvoiceDTO;
import ecommerce.mobile.payload.InvoiceUpdateDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface InvoiceService {
	InvoiceDTO createInvoice(InvoiceCreateDTO invoiceCreateDto, HttpServletRequest request);

	InvoiceDTO updateInvoice(InvoiceUpdateDTO invoiceUpdateDto, HttpServletRequest request);

	InvoiceDTO getById(Integer id);

	void deletInvoice(Integer id, HttpServletRequest request);

	InvoiceDTO getByKey(String key);

	List<InvoiceDTO> getListInvoices(int limit, int page, Integer status, String note, Boolean isPaid, String emailUser,
			String username, String phoneGuest, String guestname, String companyName, String createdAt,
			String updatedAt, String sortBy);
}

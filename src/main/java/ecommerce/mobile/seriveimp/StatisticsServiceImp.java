package ecommerce.mobile.seriveimp;

import java.util.List;

import org.springframework.stereotype.Service;

import ecommerce.mobile.entity.Company;
import ecommerce.mobile.exception.ResourceNotFoundException;
import ecommerce.mobile.payload.InvoiceDTO;
import ecommerce.mobile.payload.StatisticsByDateDTO;
import ecommerce.mobile.repository.CompanyRepository;
import ecommerce.mobile.service.InvoiceService;
import ecommerce.mobile.service.StatisticsService;

@Service
public class StatisticsServiceImp implements StatisticsService {
	private CompanyRepository companyRepository;
	private InvoiceService invoiceService;

	public StatisticsServiceImp(CompanyRepository companyRepository, InvoiceService invoiceService) {
		super();
		this.companyRepository = companyRepository;
		this.invoiceService = invoiceService;
	}

	@Override
	public Double statisticsSalesByDate(StatisticsByDateDTO sbd) {

		Company company = companyRepository.findById(sbd.getCompanyId())
				.orElseThrow(() -> new ResourceNotFoundException("Company", "id", sbd.getCompanyId()));
		List<InvoiceDTO> listInvoice = invoiceService.getListInvoices(1000000000, 1, null, null, null, null, null, null,
				null, company.getName(), sbd.getDate(), null, null);

		Double total = 0.0;

		for (InvoiceDTO invoice : listInvoice) {
			total += invoice.getTotalPrice();
		}

		return total;
	}

}

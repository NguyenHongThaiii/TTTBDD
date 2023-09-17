package ecommerce.mobile.service;

import java.io.IOException;
import java.util.List;

import ecommerce.mobile.payload.CompanyCreateDTO;
import ecommerce.mobile.payload.CompanyDTO;
import ecommerce.mobile.payload.CompanyUpdateDTO;

public interface CompanyService {
	List<CompanyDTO> getListCompanies(Integer limit, Integer page, Integer status, String name, String createdAt,
			String updatedAt, String sortBy);

	CompanyDTO createCompany(CompanyCreateDTO cpr) throws IOException;

	CompanyDTO updateCompany(CompanyUpdateDTO cpu) throws IOException;

	CompanyDTO getById(Integer companyId);

	CompanyDTO getByName(String name);

	void deleteById(Integer companyId);
}

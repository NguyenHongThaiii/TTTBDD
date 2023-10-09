package ecommerce.mobile.service;

import java.io.IOException;
import java.util.List;

import ecommerce.mobile.payload.CompanyCreateDTO;
import ecommerce.mobile.payload.CompanyDTO;
import ecommerce.mobile.payload.CompanyUpdateDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface CompanyService {
	List<CompanyDTO> getListCompanies(Integer limit, Integer page, Integer status, String name, String createdAt,
			String updatedAt, String sortBy);

	CompanyDTO createCompany(CompanyCreateDTO cpr, HttpServletRequest request) throws IOException;

	CompanyDTO updateCompany(CompanyUpdateDTO cpu, HttpServletRequest request) throws IOException;

	CompanyDTO getById(Integer companyId);

	CompanyDTO getByName(String name);

	CompanyDTO getByKey(String key);

	void deleteById(Integer companyId, HttpServletRequest request);
}

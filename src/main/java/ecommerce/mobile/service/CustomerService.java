package ecommerce.mobile.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import ecommerce.mobile.payload.CustomerCreateDTO;
import ecommerce.mobile.payload.CustomerDTO;
import ecommerce.mobile.payload.CustomerUpdateDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface CustomerService {
	List<CustomerDTO> getAllCustomers(int limit, int page, String name, String email, String phone, String companyName,
			Integer status, String createdAt, String updatedAt, String sortBy);

	CustomerDTO createCustomer(CustomerCreateDTO cs, HttpServletRequest request) throws JsonProcessingException;

	CustomerDTO updateCustomerById(CustomerUpdateDTO cs, HttpServletRequest request);

	CustomerDTO getCustomerById(Integer id);

	void delelteCustomerById(Integer id, HttpServletRequest request);

}

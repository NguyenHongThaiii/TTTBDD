package ecommerce.mobile.seriveimp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ecommerce.mobile.constant.SortField;
import ecommerce.mobile.entity.Company;
import ecommerce.mobile.entity.Customer;
import ecommerce.mobile.exception.AppGlobalException;
import ecommerce.mobile.exception.ResourceNotFoundException;
import ecommerce.mobile.payload.CustomerCreateDTO;
import ecommerce.mobile.payload.CustomerDTO;
import ecommerce.mobile.payload.CustomerUpdateDTO;
import ecommerce.mobile.repository.CompanyRepository;
import ecommerce.mobile.repository.CustomerRepository;
import ecommerce.mobile.service.CustomerService;
import ecommerce.mobile.service.LoggerService;
import ecommerce.mobile.utils.MapperUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class CustomerServiceImp implements CustomerService {
	@PersistenceContext
	private EntityManager entityManager;
	private CompanyRepository companyRepository;
	private CustomerRepository customerRepository;
	private LoggerService loggerService;
	private ObjectMapper objectMapper;
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

	public CustomerServiceImp(EntityManager entityManager, CompanyRepository companyRepository,
			CustomerRepository customerRepository, LoggerService loggerService, ObjectMapper objectMapper) {
		super();
		this.entityManager = entityManager;
		this.companyRepository = companyRepository;
		this.customerRepository = customerRepository;
		this.loggerService = loggerService;
		this.objectMapper = objectMapper;
	}

	@Override
	public List<CustomerDTO> getAllCustomers(int limit, int page, String name, String email, String phone,
			String companyName, Integer status, String createdAt, String updatedAt, String sortBy) {
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.UPDATEDAT,
				SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC, SortField.UPDATEDATDESC,
				SortField.CREATEDATDESC);
		Pageable pageable = PageRequest.of(page - 1, limit);
		List<String> sortByList = new ArrayList<String>();
		List<CustomerDTO> listCustomerDto;
		List<Customer> listCustomer;
		List<Sort.Order> sortOrders = new ArrayList<>();

		if (!StringUtils.isEmpty(sortBy))
			sortByList = Arrays.asList(sortBy.split(","));

		for (String sb : sortByList) {
			boolean isDescending = sb.endsWith("Desc");

			if (isDescending && !StringUtils.isEmpty(sortBy))
				sb = sb.substring(0, sb.length() - 4).trim();

			for (SortField sortField : validSortFields) {
				if (sortField.toString().equals(sb)) {
					sortOrders.add(isDescending ? Sort.Order.desc(sb) : Sort.Order.asc(sb));
					break;
				}
			}
		}
		if (!sortOrders.isEmpty())
			pageable = PageRequest.of(page - 1, limit, Sort.by(sortOrders));
		listCustomer = customerRepository.findWithFilters(name, email, phone, status, createdAt, updatedAt, companyName,
				pageable, entityManager);
		logger.info(listCustomer.size() + "");
		listCustomerDto = listCustomer.stream().map(customer -> {
			CustomerDTO customerDto = MapperUtils.mapToDTO(customer, CustomerDTO.class);
			return customerDto;
		}).collect(Collectors.toList());

		return listCustomerDto;
	}

	@Override
	public CustomerDTO createCustomer(CustomerCreateDTO cs, HttpServletRequest request) {
		try {
			Company company = companyRepository.findByName(cs.getCompanyName())
					.orElseThrow(() -> new ResourceNotFoundException("Company", "name", cs.getCompanyName()));
			if (customerRepository.existsByEmail(cs.getEmail()))
				throw new AppGlobalException(HttpStatus.BAD_REQUEST, "Email is existing");
			if (customerRepository.existsByPhone(cs.getPhone()))
				throw new AppGlobalException(HttpStatus.BAD_REQUEST, "Phone is existing");
			Customer customer = MapperUtils.mapToEntity(cs, Customer.class);
			customer.setStatus(cs.getStatus());
			customer.setCompany(company);

			customerRepository.save(customer);

			CustomerDTO customerDto = MapperUtils.mapToDTO(customer, CustomerDTO.class);
			loggerService.logInfor(request, "Create Customer", "SUCCESSFULLY", objectMapper.writeValueAsString(cs));
			return customerDto;
		} catch (Exception e) {
			try {
				loggerService.logError(request, "Create Customer", "FAILED", objectMapper.writeValueAsString(cs));
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
			}
			throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@Override
	public CustomerDTO updateCustomerById(CustomerUpdateDTO cs, HttpServletRequest request) {
		try {
			Customer customer = customerRepository.findById(cs.getId())
					.orElseThrow(() -> new ResourceNotFoundException("Customer", "id", cs.getId()));
			if (customerRepository.existsByEmail(cs.getEmail()))
				throw new AppGlobalException(HttpStatus.BAD_REQUEST, "Email is existing");
			if (customerRepository.existsByPhone(cs.getPhone()))
				throw new AppGlobalException(HttpStatus.BAD_REQUEST, "Phone is existing");

			if (cs.getEmail() != null)
				customer.setEmail(cs.getEmail());
			if (cs.getName() != null)
				customer.setEmail(cs.getEmail());
			if (cs.getPhone() != null)
				customer.setPhone(cs.getPhone());
			if (cs.getStatus() != null)
				customer.setStatus(cs.getStatus());

			customerRepository.save(customer);
			CustomerDTO customerDto = MapperUtils.mapToDTO(customer, CustomerDTO.class);
			loggerService.logInfor(request, "Update Customer", "SUCCESSFULLY", objectMapper.writeValueAsString(cs));

			return customerDto;
		} catch (Exception e) {
			try {
				loggerService.logError(request, "Update Customer", "FAILED", objectMapper.writeValueAsString(cs));
			} catch (JsonProcessingException e1) {
				throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
			}

			throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@Override
	public void delelteCustomerById(Integer id, HttpServletRequest request) {
		try {
			Customer customer = customerRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

			customer.setStatus(2);
			customerRepository.save(customer);
			loggerService.logInfor(request, "Delete Customer", "SUCCESSFULLY", "{\"id\": " + "\"" + id + "\"" + "}");

		} catch (Exception e) {
			loggerService.logError(request, "Delete Customer", "FAILED", "{\"id\": " + "\"" + id + "\"" + "}");
			throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@Override
	public CustomerDTO getCustomerById(Integer id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
		CustomerDTO customerDto = MapperUtils.mapToDTO(customer, CustomerDTO.class);
		return customerDto;
	}

}

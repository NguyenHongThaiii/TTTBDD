package ecommerce.mobile.seriveimp;

import java.io.IOException;
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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ecommerce.mobile.constant.SortField;
import ecommerce.mobile.entity.Company;
import ecommerce.mobile.entity.Customer;
import ecommerce.mobile.entity.Image;
import ecommerce.mobile.entity.Invoice;
import ecommerce.mobile.entity.Order;
import ecommerce.mobile.entity.Product;
import ecommerce.mobile.entity.User;
import ecommerce.mobile.exception.AppGlobalException;
import ecommerce.mobile.exception.ResourceNotFoundException;
import ecommerce.mobile.payload.InvoiceCreateDTO;
import ecommerce.mobile.payload.InvoiceDTO;
import ecommerce.mobile.payload.InvoiceUpdateDTO;
import ecommerce.mobile.payload.OrderCreateDTO;
import ecommerce.mobile.payload.OrderDTO;
import ecommerce.mobile.payload.OrderUpdateDTO;
import ecommerce.mobile.repository.CompanyRepository;
import ecommerce.mobile.repository.CustomerRepository;
import ecommerce.mobile.repository.ImageRepository;
import ecommerce.mobile.repository.InvoiceRepository;
import ecommerce.mobile.repository.OrderRepository;
import ecommerce.mobile.repository.ProductRepository;
import ecommerce.mobile.repository.UserRepository;
import ecommerce.mobile.service.CloudinaryService;
import ecommerce.mobile.service.EmailService;
import ecommerce.mobile.service.InvoiceService;
import ecommerce.mobile.service.LoggerService;
import ecommerce.mobile.utils.MapperUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
@Transactional

public class InvoiceServiceImp implements InvoiceService {
	@PersistenceContext
	private EntityManager entityManager;
	private ProductRepository productRepository;
	private UserRepository userRepository;
	private CustomerRepository customerRepository;
	private OrderRepository orderRepository;
	private CompanyRepository companyRepository;
	private EmailService emailService;
	private ImageRepository imageRepository;
	private CloudinaryService cloudinaryService;
	private InvoiceRepository invoiceRepository;
	private LoggerService loggerService;
	private final ObjectMapper objectMapper;
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
	private final String path = "mobile/Invoices";

	public InvoiceServiceImp(EntityManager entityManager, ProductRepository productRepository,
			UserRepository userRepository, CustomerRepository customerRepository, OrderRepository orderRepository,
			CompanyRepository companyRepository, EmailService emailService, ImageRepository imageRepository,
			CloudinaryService cloudinaryService, InvoiceRepository invoiceRepository, LoggerService loggerService,
			ObjectMapper objectMapper) {
		super();
		this.entityManager = entityManager;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
		this.customerRepository = customerRepository;
		this.orderRepository = orderRepository;
		this.companyRepository = companyRepository;
		this.emailService = emailService;
		this.imageRepository = imageRepository;
		this.cloudinaryService = cloudinaryService;
		this.invoiceRepository = invoiceRepository;
		this.loggerService = loggerService;
		this.objectMapper = objectMapper;
	}

	@Transactional
	@Override
	public InvoiceDTO createInvoice(InvoiceCreateDTO invoiceCreateDto, HttpServletRequest request) {
		String url = null;
		InvoiceDTO invoiceDto = null;
		try {
			User user = userRepository.findByEmail(invoiceCreateDto.getEmailUser())
					.orElseThrow(() -> new ResourceNotFoundException("User", "email", invoiceCreateDto.getEmailUser()));
			Customer customer = customerRepository.findByPhone(invoiceCreateDto.getPhoneGuest()).orElseThrow(
					() -> new ResourceNotFoundException("Customer", "phone", invoiceCreateDto.getPhoneGuest()));
			Company company = companyRepository.findByName(invoiceCreateDto.getCompanyName()).orElseThrow(
					() -> new ResourceNotFoundException("Company", "name", invoiceCreateDto.getCompanyName()));
			if (invoiceRepository.existsByQrKey(invoiceCreateDto.getKey()))
				throw new AppGlobalException(HttpStatus.BAD_REQUEST, "Key's already exists");
			Invoice invoice = new Invoice();
			Integer totalQuantity = 0;
			invoice.setCompany(company);
			invoice.setUser(user);
			invoice.setCustomer(customer);
			invoice.setIsPaid(invoiceCreateDto.getIsPaid());
			invoice.setMethod(invoiceCreateDto.getMethod());
			invoice.setNote(invoiceCreateDto.getNote());
			invoice.setStatus(1);
			invoice.setKey(invoiceCreateDto.getKey());
			invoice.setTax(invoiceCreateDto.getTax());
			invoice.setTotalPrice(invoiceCreateDto.getTotalPrice());
			invoice.setAddress(invoiceCreateDto.getAddress());
			invoiceRepository.save(invoice);

			url = cloudinaryService.uploadImage(invoiceCreateDto.getImage(), path, "image");
			Image image = new Image();
			image.setImage(url);
			image.setInvoice(invoice);
			imageRepository.save(image);

			List<OrderCreateDTO> orderDtos = objectMapper.readValue(invoiceCreateDto.getListOrders(),
					new TypeReference<List<OrderCreateDTO>>() {
					});
			List<Order> listOrder = new ArrayList<>();
			List<OrderDTO> listOrderDto = new ArrayList<>();
			invoiceDto = MapperUtils.mapToDTO(invoice, InvoiceDTO.class);

			for (OrderCreateDTO orderDto : orderDtos) {
				Product product = productRepository.findByIdAndCompanyId(orderDto.getProductId(), company.getId())
						.orElseThrow(() -> new ResourceNotFoundException("Product", "id", orderDto.getProductId()));
				if (orderDto.getQuantity() > product.getStock())
					throw new AppGlobalException(HttpStatus.BAD_REQUEST, "Quantity must be less than stock product");
				Order order = new Order();
				order.setCompany(company);
				order.setQuantity(orderDto.getQuantity());
				order.setStatus(1);
				order.setInvoice(invoice);
				order.setUser(user);
				order.setCustomer(customer);
				order.setProduct(product);
				totalQuantity += orderDto.getQuantity();
				listOrder.add(order);
			}
			orderRepository.saveAll(listOrder);
			listOrder.forEach((order) -> {
				OrderDTO orderDto = MapperUtils.mapToDTO(order, OrderDTO.class);
				orderDto.setCompanyName(company.getName());
				orderDto.setInvoiceId(invoice.getId());
				orderDto.setProductId(order.getProduct().getId());
				orderDto.setStatus(1);
				orderDto.setUserId(user.getId());
				orderDto.setGuestId(customer.getId());
				listOrderDto.add(orderDto);
			});
			invoiceDto.setOrders(listOrderDto);
			invoiceDto.setCompanyName(company.getName());
			invoiceDto.setQuantity(totalQuantity);
			invoiceDto.setEmailUser(user.getEmail());
			invoiceDto.setEmailGuest(customer.getEmail());
			invoiceDto.setKey(invoice.getKey());
			invoiceDto.setImage(url);
			loggerService.logInfor(request, "Create Invoice", "SUCCESSFULLY",
					objectMapper.writeValueAsString(invoiceCreateDto));
			return invoiceDto;

		} catch (JsonMappingException e) {

			try {
				cloudinaryService.removeImageFromCloudinary(url, path);
				loggerService.logError(request, "Create Invoice", "FAILED",
						objectMapper.writeValueAsString(invoiceCreateDto));
				throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

			} catch (IOException e1) {
			}

		} catch (JsonProcessingException e) {
			try {
				cloudinaryService.removeImageFromCloudinary(url, path);
				loggerService.logError(request, "Create Invoice", "FAILED",
						objectMapper.writeValueAsString(invoiceCreateDto));
				throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

			} catch (IOException e1) {
				e1.printStackTrace();
			}
			throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error creating the invoice JsonProcessingException");

		} catch (IOException e) {

			try {
				cloudinaryService.removeImageFromCloudinary(url, path);
				loggerService.logError(request, "Create Invoice", "FAILED",
						objectMapper.writeValueAsString(invoiceCreateDto));
				throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

			} catch (IOException e1) {

			}
			throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating the invoice IOException");

		}
		return invoiceDto;

	}

	@Transactional
	@Override
	public InvoiceDTO updateInvoice(InvoiceUpdateDTO invoiceUpdateDto, HttpServletRequest request) {
		try {
			Invoice invoice = invoiceRepository.findById(invoiceUpdateDto.getId())
					.orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", invoiceUpdateDto.getId()));

			if (invoiceUpdateDto.getIsPaid() != null)
				invoice.setIsPaid(invoiceUpdateDto.getIsPaid());
			if (invoiceUpdateDto.getMethod() != null)
				invoice.setMethod(invoiceUpdateDto.getMethod());
			if (invoiceUpdateDto.getNote() != null)
				invoice.setNote(invoiceUpdateDto.getNote());
			if (invoiceUpdateDto.getStatus() != null)
				invoice.setStatus(invoiceUpdateDto.getStatus());
			if (invoiceUpdateDto.getTax() != null)
				invoice.setTax(invoiceUpdateDto.getTax());
			if (invoiceUpdateDto.getTotalPrice() != null)
				invoice.setTotalPrice(invoiceUpdateDto.getTotalPrice());
			if (invoiceUpdateDto.getAddress() != null)
				invoice.setAddress(invoiceUpdateDto.getAddress());

			Integer totalQuantity = 0;
			List<Order> listOrder = new ArrayList<>();
			List<OrderDTO> listOrderDto = new ArrayList<>();
			InvoiceDTO invoiceDto = MapperUtils.mapToDTO(invoice, InvoiceDTO.class);
			if (invoiceUpdateDto.getListOrders() != null) {
				List<OrderUpdateDTO> orderUpdateDto = objectMapper.readValue(invoiceUpdateDto.getListOrders(),
						new TypeReference<List<OrderUpdateDTO>>() {
						});
				for (OrderUpdateDTO orderUpdate : orderUpdateDto) {
					Order order = orderRepository.findById(orderUpdate.getId())
							.orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderUpdate.getId()));
					if (orderUpdate.getQuantity() > order.getProduct().getStock())
						throw new AppGlobalException(HttpStatus.BAD_REQUEST,
								"Quantity must be less than stock product");
					if (orderUpdate.getQuantity() != null)
						order.setQuantity(orderUpdate.getQuantity());
					totalQuantity += order.getQuantity();
					listOrder.add(order);
				}

				listOrder.forEach((order) -> {
					OrderDTO orderDto = MapperUtils.mapToDTO(order, OrderDTO.class);
					orderDto.setCompanyName(order.getCompany().getName());
					orderDto.setInvoiceId(invoice.getId());
					orderDto.setProductId(order.getProduct().getId());
					orderDto.setStatus(order.getStatus());
					orderDto.setUserId(order.getUser().getId());
					orderDto.setGuestId(order.getCustomer().getId());
					listOrderDto.add(orderDto);
				});
			} else {
				for (Order order : invoice.getOrders()) {
					OrderDTO orderDto = MapperUtils.mapToDTO(order, OrderDTO.class);
					orderDto.setCompanyName(order.getCompany().getName());
					orderDto.setInvoiceId(invoice.getId());
					orderDto.setProductId(order.getProduct().getId());
					orderDto.setStatus(order.getStatus());
					orderDto.setUserId(order.getUser().getId());
					orderDto.setGuestId(order.getCustomer().getId());
					totalQuantity += order.getQuantity();
					listOrderDto.add(orderDto);
				}

			}

			invoiceDto.setOrders(listOrderDto);
			invoiceDto.setCompanyName(invoice.getCompany().getName());
			invoiceDto.setQuantity(totalQuantity);
			invoiceDto.setEmailUser(invoice.getUser().getEmail());
			invoiceDto.setEmailGuest(invoice.getCustomer().getEmail());
			invoiceDto.setKey(invoice.getKey());
			invoice.setOrders(listOrder);
			invoiceRepository.save(invoice);
			loggerService.logInfor(request, "Update Invoice", "SUCCESSFULLY",
					objectMapper.writeValueAsString(invoiceUpdateDto));
			return invoiceDto;

		} catch (JsonMappingException e) {

			e.printStackTrace();
			try {
				loggerService.logError(request, "Update Invoice", "FAILED",
						objectMapper.writeValueAsString(invoiceUpdateDto));
				throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

			} catch (JsonProcessingException e1) {

				e1.printStackTrace();
			}
			throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating the invoice");

		} catch (JsonProcessingException e) {

			e.printStackTrace();
			try {
				loggerService.logError(request, "Update Invoice", "FAILED",
						objectMapper.writeValueAsString(invoiceUpdateDto));
				throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

			} catch (JsonProcessingException e1) {

				e1.printStackTrace();
			}
			throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating the invoice");

		}
	}

	@Override
	public InvoiceDTO getById(Integer id) {

		Invoice invoice = invoiceRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id));
		Integer totalQuantity = 0;

		List<OrderDTO> listOrderDto = new ArrayList<>();
		InvoiceDTO invoiceDto = MapperUtils.mapToDTO(invoice, InvoiceDTO.class);

		for (Order order : invoice.getOrders()) {
			OrderDTO orderDto = MapperUtils.mapToDTO(order, OrderDTO.class);
			orderDto.setCompanyName(order.getCompany().getName());
			orderDto.setInvoiceId(id);
			orderDto.setProductId(order.getProduct().getId());
			orderDto.setUserId(order.getUser().getId());
			orderDto.setGuestId(order.getCustomer().getId());
			totalQuantity += order.getQuantity();
			listOrderDto.add(orderDto);
		}

		invoiceDto.setOrders(listOrderDto);
		invoiceDto.setCompanyName(invoice.getCompany().getName());
		invoiceDto.setEmailUser(invoice.getUser().getEmail());
		invoiceDto.setEmailGuest(invoice.getCustomer().getEmail());
		invoiceDto.setQuantity(totalQuantity);
		invoiceDto.setKey(invoice.getKey());
		return invoiceDto;
	}

	@Override
	public List<InvoiceDTO> getListInvoices(int limit, int page, Integer status, String note, Boolean isPaid,
			String emailUser, String username, String phoneGuest, String guestname, String companyName,
			String createdAt, String updatedAt, String sortBy) {
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.UPDATEDAT,
				SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC, SortField.UPDATEDATDESC,
				SortField.CREATEDATDESC);
		Pageable pageable = PageRequest.of(page - 1, limit);
		List<String> sortByList = new ArrayList<String>();
		List<InvoiceDTO> listInvoiceDto;
		List<Invoice> listInvoices;
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
		listInvoices = invoiceRepository.findWithFilters(note, isPaid, emailUser, username, phoneGuest, guestname,
				companyName, status, createdAt, updatedAt, pageable, entityManager);
		listInvoiceDto = listInvoices.stream().map(invoice -> {
			InvoiceDTO invoiceDto = MapperUtils.mapToDTO(invoice, InvoiceDTO.class);
			Integer totalQuantity = 0;
			List<OrderDTO> listOrderDto = new ArrayList<>();

			for (Order order : invoice.getOrders()) {
				OrderDTO orderDto = MapperUtils.mapToDTO(order, OrderDTO.class);
				orderDto.setCompanyName(order.getCompany().getName());
				orderDto.setInvoiceId(invoice.getId());
				orderDto.setProductId(order.getProduct().getId());
				orderDto.setUserId(order.getUser().getId());
				orderDto.setGuestId(order.getCustomer().getId());
				totalQuantity += order.getQuantity();
				listOrderDto.add(orderDto);
			}
			invoiceDto.setOrders(listOrderDto);
			invoiceDto.setCompanyName(invoice.getCompany().getName());
			invoiceDto.setEmailUser(invoice.getUser().getEmail());
			invoiceDto.setEmailGuest(invoice.getCustomer().getEmail());
			invoiceDto.setQuantity(totalQuantity);
			invoiceDto.setKey(invoice.getKey());
			return invoiceDto;
		}).collect(Collectors.toList());

		return listInvoiceDto;
	}

	@Transactional
	@Override
	public void deletInvoice(Integer id, HttpServletRequest request) {
		try {
			Invoice invoice = invoiceRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id));
			List<Order> listOrder = new ArrayList<>();
			for (Order order : invoice.getOrders()) {
				order.setStatus(2);
				listOrder.add(order);
			}

			invoice.setStatus(2);
			invoiceRepository.save(invoice);
			loggerService.logInfor(request, "Delte Invoice", "SUCCESSFULLY", "{\"id\": " + "\"" + id + "\"" + "}");
		} catch (Exception e) {
			loggerService.logError(request, "Delte Invoice", "FAILED", "{\"id\": " + "\"" + id + "\"" + "}");
		}
	}

	@Override
	public InvoiceDTO getByKey(String key) {
		logger.info(key);
		Invoice invoice = invoiceRepository.findByKey(key)
				.orElseThrow(() -> new ResourceNotFoundException("Invoice", "key", key));
		Integer totalQuantity = 0;

		List<OrderDTO> listOrderDto = new ArrayList<>();
		InvoiceDTO invoiceDto = MapperUtils.mapToDTO(invoice, InvoiceDTO.class);

		for (Order order : invoice.getOrders()) {
			OrderDTO orderDto = MapperUtils.mapToDTO(order, OrderDTO.class);
			orderDto.setCompanyName(order.getCompany().getName());
			orderDto.setInvoiceId(invoice.getId());
			orderDto.setProductId(order.getProduct().getId());
			orderDto.setUserId(order.getUser().getId());
			orderDto.setGuestId(order.getCustomer().getId());
			totalQuantity += order.getQuantity();
			listOrderDto.add(orderDto);
		}

		invoiceDto.setOrders(listOrderDto);
		invoiceDto.setCompanyName(invoice.getCompany().getName());
		invoiceDto.setEmailUser(invoice.getUser().getEmail());
		invoiceDto.setEmailGuest(invoice.getCustomer().getEmail());
		invoiceDto.setQuantity(totalQuantity);
		invoiceDto.setKey(invoice.getKey());
		return invoiceDto;
	}

}

package ecommerce.mobile.seriveimp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ecommerce.mobile.constant.SortField;
import ecommerce.mobile.entity.Company;
import ecommerce.mobile.entity.Image;
import ecommerce.mobile.entity.Product;
import ecommerce.mobile.exception.AppGlobalException;
import ecommerce.mobile.exception.ResourceNotFoundException;
import ecommerce.mobile.payload.ProductCreateDTO;
import ecommerce.mobile.payload.ProductDTO;
import ecommerce.mobile.payload.ProductDeleteDTO;
import ecommerce.mobile.payload.ProductUpdateDTO;
import ecommerce.mobile.repository.CompanyRepository;
import ecommerce.mobile.repository.ImageRepository;
import ecommerce.mobile.repository.ProductRepository;
import ecommerce.mobile.service.CloudinaryService;
import ecommerce.mobile.service.LoggerService;
import ecommerce.mobile.service.ProductService;
import ecommerce.mobile.utils.MapperUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProductServiceImp implements ProductService {
	private ProductRepository productRepository;
	@PersistenceContext
	private EntityManager entityManager;
	private CloudinaryService cloudinaryService;
	private CompanyRepository companyRepository;
	private ImageRepository imageRepository;
	private LoggerService loggerService;
	private final ObjectMapper objectMapper;
	final String path = "mobile/Products";

	public ProductServiceImp(ProductRepository productRepository, EntityManager entityManager,
			CloudinaryService cloudinaryService, CompanyRepository companyRepository, ImageRepository imageRepository,
			LoggerService loggerService, ObjectMapper objectMapper) {
		super();
		this.productRepository = productRepository;
		this.entityManager = entityManager;
		this.cloudinaryService = cloudinaryService;
		this.companyRepository = companyRepository;
		this.imageRepository = imageRepository;
		this.loggerService = loggerService;
		this.objectMapper = objectMapper;
	}

	@Override
	public ProductDTO createProduct(ProductCreateDTO productCreateDto, HttpServletRequest request) throws IOException {
		try {
			Company company = companyRepository.findByName(productCreateDto.getCompanyName()).orElseThrow(
					() -> new ResourceNotFoundException("Company", "name", productCreateDto.getCompanyName()));

			List<Image> listImages = new ArrayList<>();
			List<String> images = new ArrayList<>();

			Product product = new Product();
			product.setName(productCreateDto.getName());
			product.setDescription(productCreateDto.getDescription());
			product.setPrice(productCreateDto.getPrice());
			product.setType(productCreateDto.getType());
			product.setStatus(productCreateDto.getStatus());
			product.setCompany(company);
			productRepository.save(product);
			cloudinaryService.uploadImages(images, productCreateDto.getListImageFile(), path, "image");
			images.forEach(image -> {
				Image imageItem = new Image();
				imageItem.setImage(image);
				imageItem.setProduct(product);
				listImages.add(imageItem);
			});

			imageRepository.saveAll(listImages);

			ProductDTO productDto = MapperUtils.mapToDTO(product, ProductDTO.class);

			listImages.forEach(image -> productDto.getListImage().add(image.getImage()));
			productDto.setNameCompany(company.getName());
			loggerService.logInfor(request, "Create Product", "SUCCESSFULLY",
					objectMapper.writeValueAsString(productCreateDto));
			return productDto;
		} catch (Exception e) {
			loggerService.logError(request, "Create Product", "FAILED",
					objectMapper.writeValueAsString(productCreateDto));
			throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@Override
	public ProductDTO updateProduct(ProductUpdateDTO productUpdateDto, HttpServletRequest request) throws IOException {
		try {

			Product product = productRepository.findById(productUpdateDto.getId())
					.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productUpdateDto.getId()));

			String desc = productUpdateDto.getDescription();
			Integer price = productUpdateDto.getPrice();
			String name = productUpdateDto.getName();
			Integer status = productUpdateDto.getStatus();
			Integer type = productUpdateDto.getType();

			if (desc != null)
				product.setDescription(desc);
			if (price != null)
				product.setPrice(price);
			if (name != null)
				product.setName(name);
			if (status != null)
				product.setStatus(status);
			if (type != null)
				product.setType(type);
			if (productUpdateDto.getListImageFile() != null) {
				List<String> images = new ArrayList<>();
				List<Image> listImages = new ArrayList<>();
				List<Image> listEntityImages = imageRepository.findAllImageByProductId(productUpdateDto.getId());
				if (listEntityImages != null)
					for (Image temp : listEntityImages) {
						cloudinaryService.removeImageFromCloudinary(temp.getImage(), path);
					}

				cloudinaryService.uploadImages(images, productUpdateDto.getListImageFile(), path, "image");
				images.forEach(imageTemp -> {
					Image imageItem = new Image();
					imageItem.setImage(imageTemp);
					imageItem.setProduct(product);
					listImages.add(imageItem);
				});
				imageRepository.deleteAllImageByProductId(product.getId());
				imageRepository.saveAll(listImages);
				product.setImages(listImages);

			}

			productRepository.save(product);

			ProductDTO productDto = MapperUtils.mapToDTO(product, ProductDTO.class);
			if (product.getImages() != null)
				product.getImages().forEach(image -> productDto.getListImage().add(image.getImage()));
			productDto.setNameCompany(product.getCompany().getName());
			loggerService.logInfor(request, "Update Product", "SUCCESSFULLY",
					objectMapper.writeValueAsString(productUpdateDto));
			return productDto;
		} catch (Exception e) {
			loggerService.logError(request, "Update Product", "FAILED",
					objectMapper.writeValueAsString(productUpdateDto));
			throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@Override
	public ProductDTO getProductById(int id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", id + ""));
		ProductDTO productDto = MapperUtils.mapToDTO(product, ProductDTO.class);

		if (product.getImages() != null)
			product.getImages().forEach(image -> productDto.getListImage().add(image.getImage()));

		return productDto;
	}

	@Override
	public void deleteProduct(ProductDeleteDTO productDeleteDto, HttpServletRequest request) throws IOException {
		try {
			Product product = productRepository.findById(productDeleteDto.getProductId()).orElseThrow(
					() -> new ResourceNotFoundException("Product", "id", productDeleteDto.getProductId() + ""));
			product.setStatus(2);
			productRepository.save(product);
			loggerService.logInfor(request, "Delete Product", "SUCCESSFULLY",
					objectMapper.writeValueAsString(productDeleteDto));
		} catch (Exception e) {
			throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@Override
	public List<ProductDTO> getListProducts(int limit, int page, Integer status, String name, String description,
			Integer type, Integer priceMin, Integer priceMax, String companyName, String createdAt, String updatedAt,
			String sortBy) {
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.UPDATEDAT,
				SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC, SortField.UPDATEDATDESC,
				SortField.CREATEDATDESC);
		Pageable pageable = PageRequest.of(page - 1, limit);
		List<String> sortByList = new ArrayList<String>();
		List<Product> productList = null;
		List<Sort.Order> sortOrders = new ArrayList<>();
		List<ProductDTO> listProductDto;

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

		productList = productRepository.findWithFilters(status, name, priceMin, priceMax, type, companyName, createdAt,
				updatedAt, pageable, entityManager);
		listProductDto = productList.stream().map(product -> {
			ProductDTO pdto = MapperUtils.mapToDTO(product, ProductDTO.class);
			if (product.getImages() != null)
				product.getImages().forEach(image -> pdto.getListImage().add(image.getImage()));
			return pdto;
		}).collect(Collectors.toList());

		return listProductDto;
	}

}

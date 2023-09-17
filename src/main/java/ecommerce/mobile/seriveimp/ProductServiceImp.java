package ecommerce.mobile.seriveimp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ecommerce.mobile.constant.SortField;
import ecommerce.mobile.entity.Company;
import ecommerce.mobile.entity.Image;
import ecommerce.mobile.entity.Product;
import ecommerce.mobile.exception.ResourceNotFoundException;
import ecommerce.mobile.payload.ProductCreateDTO;
import ecommerce.mobile.payload.ProductDTO;
import ecommerce.mobile.payload.ProductDeleteDTO;
import ecommerce.mobile.payload.ProductUpdateDTO;
import ecommerce.mobile.repository.CompanyRepository;
import ecommerce.mobile.repository.ImageRepository;
import ecommerce.mobile.repository.ProductRepository;
import ecommerce.mobile.service.CloudinaryService;
import ecommerce.mobile.service.ProductService;
import ecommerce.mobile.utils.MapperUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ProductServiceImp implements ProductService {
	private ProductRepository productRepository;
	@PersistenceContext
	private EntityManager entityManager;
	private CloudinaryService cloudinaryService;
	private CompanyRepository companyRepository;
	private ImageRepository imageRepository;
	final String path = "mobile/Products";

	public ProductServiceImp(ProductRepository productRepository, EntityManager entityManager,
			CloudinaryService cloudinaryService, CompanyRepository companyRepository, ImageRepository imageRepository) {
		super();
		this.productRepository = productRepository;
		this.entityManager = entityManager;
		this.cloudinaryService = cloudinaryService;
		this.companyRepository = companyRepository;
		this.imageRepository = imageRepository;
	}

	@Override
	public ProductDTO createProduct(ProductCreateDTO productCreateDto) throws IOException {
		Company company = companyRepository.findByName(productCreateDto.getCompanyName())
				.orElseThrow(() -> new ResourceNotFoundException("Company", "name", productCreateDto.getCompanyName()));

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
		productDto.setCompanyId(company.getId());
		return productDto;
	}

	@Override
	public ProductDTO updateProduct(ProductUpdateDTO productUpdateDto) throws IOException {

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
				for (Image temp : listEntityImages)
					cloudinaryService.removeImageFromCloudinary(temp.getImage(), path);

			cloudinaryService.uploadImages(images, productUpdateDto.getListImageFile(), path, "image");
			images.forEach(imageTemp -> {
				Image imageItem = new Image();
				imageItem.setImage(imageTemp);
				imageItem.setProduct(product);
				listImages.add(imageItem);
			});
			imageRepository.deleteAllImageByProductId(product.getId());
			product.setImages(listEntityImages);
		}

		productRepository.save(product);

		ProductDTO productDto = MapperUtils.mapToDTO(product, ProductDTO.class);
		if (product.getImages() != null)
			product.getImages().forEach(image -> productDto.getListImage().add(image.getImage()));
		productDto.setCompanyId(product.getCompany().getId());

		return productDto;
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
	public void deleteProduct(ProductDeleteDTO productDeleteDto) throws IOException {
		Product product = productRepository.findById(productDeleteDto.getProductId()).orElseThrow(
				() -> new ResourceNotFoundException("Product", "id", productDeleteDto.getProductId() + ""));
		product.setStatus(2);
		productRepository.save(product);
	}

	@Override
	public List<ProductDTO> getListProducts(int limit, int page, int status, String name, String description,
			Integer type, Integer priceMin, Integer priceMax, String companyName, String sortBy) {
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

		productList = productRepository.findWithFilters(status, name, priceMin, priceMax, type, companyName, pageable,
				entityManager);
		listProductDto = productList.stream().map(product -> {
			ProductDTO pdto = MapperUtils.mapToDTO(product, ProductDTO.class);
			if (product.getImages() != null)
				product.getImages().forEach(image -> pdto.getListImage().add(image.getImage()));
			return pdto;
		}).collect(Collectors.toList());

		return listProductDto;
	}

}

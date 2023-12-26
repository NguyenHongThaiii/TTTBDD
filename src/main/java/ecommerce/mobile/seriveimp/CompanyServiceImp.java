package ecommerce.mobile.seriveimp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ecommerce.mobile.constant.SortField;
import ecommerce.mobile.entity.Company;
import ecommerce.mobile.entity.Image;
import ecommerce.mobile.entity.Logo;
import ecommerce.mobile.exception.AppGlobalException;
import ecommerce.mobile.exception.ResourceNotFoundException;
import ecommerce.mobile.payload.CompanyCreateDTO;
import ecommerce.mobile.payload.CompanyDTO;
import ecommerce.mobile.payload.CompanyUpdateDTO;
import ecommerce.mobile.repository.CompanyRepository;
import ecommerce.mobile.repository.ImageRepository;
import ecommerce.mobile.repository.LogoRepository;
import ecommerce.mobile.service.CloudinaryService;
import ecommerce.mobile.service.CompanyService;
import ecommerce.mobile.service.LoggerService;
import ecommerce.mobile.utils.MapperUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class CompanyServiceImp implements CompanyService {
	private CompanyRepository companyRepository;
	private ImageRepository imageRepository;
	private LogoRepository logoRepository;
	private CloudinaryService cloudinaryService;
	@PersistenceContext
	private EntityManager entityManager;
	private LoggerService loggerService;
	private final String path = "mobile/Companies";
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
	private final ObjectMapper objectMapper;

	public CompanyServiceImp(CompanyRepository companyRepository, ImageRepository imageRepository,
			LogoRepository logoRepository, CloudinaryService cloudinaryService, EntityManager entityManager,
			LoggerService loggerService, ObjectMapper objectMapper) {
		super();
		this.companyRepository = companyRepository;
		this.imageRepository = imageRepository;
		this.logoRepository = logoRepository;
		this.cloudinaryService = cloudinaryService;
		this.entityManager = entityManager;
		this.loggerService = loggerService;
		this.objectMapper = objectMapper;
	}

	@Override
	public List<CompanyDTO> getListCompanies(Integer limit, Integer page, Integer status, String name, String createdAt,
			String updatedAt, String sortBy) {

		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.UPDATEDAT,
				SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC, SortField.UPDATEDATDESC,
				SortField.CREATEDATDESC);
		Pageable pageable = PageRequest.of(page - 1, limit);
		List<String> sortByList = new ArrayList<String>();
		List<Company> companyList = new ArrayList<>();
		List<Sort.Order> sortOrders = new ArrayList<>();
		List<CompanyDTO> listCompanyDto;

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

		companyList = companyRepository.findWithFilters(name, status, createdAt, updatedAt, pageable, entityManager);
		listCompanyDto = companyList.stream().map(company -> {
			CompanyDTO cpdto = MapperUtils.mapToDTO(company, CompanyDTO.class);
			cpdto.setLogo(company.getLogo().getLogo().getImage());

			return cpdto;
		}).collect(Collectors.toList());

		return listCompanyDto;
	}

	@Override
	public CompanyDTO createCompany(CompanyCreateDTO cpr, HttpServletRequest request) throws IOException {
		try {
			if (companyRepository.existsByName(cpr.getName()))
				throw new AppGlobalException(HttpStatus.BAD_REQUEST, "Company is existing");
			if (companyRepository.existsByEmail(cpr.getEmail()))
				throw new AppGlobalException(HttpStatus.BAD_REQUEST, "Email is existing");
			if (companyRepository.existsByPhone(cpr.getPhone()))
				throw new AppGlobalException(HttpStatus.BAD_REQUEST, "Phone is existing");

//			CompanyDTO temp = MapperUtils.mapToDTO(cpr, CompanyDTO.class);
			String url = cloudinaryService.uploadImage(cpr.getLogo(), path, "image");
			String uniqueKey = UUID.randomUUID().toString();

			Company company = new Company();
			company.setKeyCompany(uniqueKey);
			company.setEmail(cpr.getEmail());
			company.setAddress(cpr.getAddress());
			company.setName(cpr.getName());
			company.setPhone(cpr.getPhone());
			company.setStatus(cpr.getStatus());
			logger.info(company.getEmail());
			companyRepository.save(company);

			Logo logo = new Logo();
			logo.setStatus(1);
			logo.setCompany(company);
			logoRepository.save(logo);

			Image image = new Image();
			image.setImage(url);
			image.setStatus(1);
			image.setLogo(logo);
			image.setCompany(company);
			imageRepository.save(image);

			CompanyDTO companyDTO = MapperUtils.mapToDTO(company, CompanyDTO.class);
			companyDTO.setLogo(url);
			cpr.setLogo(null);
			loggerService.logInfor(request, "Create Invoice", "SUCCESSFULLY", objectMapper.writeValueAsString(cpr));
			return companyDTO;
		} catch (Exception e) {
			cpr.setLogo(null);
			loggerService.logError(request, "Create Invoice", "FAILED", objectMapper.writeValueAsString(cpr));

			throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@Override
	public CompanyDTO updateCompany(CompanyUpdateDTO cpu, HttpServletRequest request) throws IOException {
		try {
			Company company = companyRepository.findById(cpu.getId())
					.orElseThrow(() -> new ResourceNotFoundException("Company", "id", cpu.getId()));
			if (cpu.getName() != null)
				company.setName(cpu.getName());

			if (cpu.getPhone() != null)
				company.setPhone(cpu.getPhone());

			if (cpu.getEmail() != null)
				company.setEmail(cpu.getEmail());

			if (cpu.getAddress() != null)
				company.setAddress(cpu.getAddress());

			if (cpu.getLogo() != null) {
				cloudinaryService.removeImageFromCloudinary(company.getLogo().getLogo().getImage(), path);
				String url = cloudinaryService.uploadImage(cpu.getLogo(), path, "image");
				Logo logo = company.getLogo();
				logo.setStatus(1);
				logo.setCompany(company);
				logo.getLogo().setImage(url);
				logoRepository.save(logo);

				company.setLogo(logo);
			}
			if (cpu.getStatus() != null)
				company.setStatus(cpu.getStatus());
			logger.info(company.getId() + "");
			companyRepository.save(company);

			CompanyDTO companyDto = MapperUtils.mapToDTO(company, CompanyDTO.class);
			companyDto.setLogo(company.getLogo().getLogo().getImage());
			cpu.setLogo(null);
			loggerService.logInfor(request, "Update Invoice", "SUCCESSFULLY", objectMapper.writeValueAsString(cpu));

			return companyDto;
		} catch (Exception e) {
			cpu.setLogo(null);

			loggerService.logError(request, "Update Invoice", "FAILED", objectMapper.writeValueAsString(cpu));

			throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@Override
	public CompanyDTO getById(Integer companyId) {
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Company", "id", companyId));

		CompanyDTO companyDto = MapperUtils.mapToDTO(company, CompanyDTO.class);
		companyDto.setLogo(company.getLogo().getLogo().getImage());
		return companyDto;
	}

	@Override
	public void deleteById(Integer companyId, HttpServletRequest request) {
		try {
			Company company = companyRepository.findById(companyId)
					.orElseThrow(() -> new ResourceNotFoundException("Company", "id", companyId));
			company.getLogo().setStatus(2);
			company.getLogo().getLogo().setStatus(2);
			company.setStatus(2);
			companyRepository.save(company);
			loggerService.logInfor(request, "Update Invoice", "SUCCESSFULLY",
					"{\"id\": " + "\"" + companyId + "\"" + "}");

		} catch (Exception e) {
			loggerService.logError(request, "Update Invoice", "FAILED", "{\"id\": " + "\"" + companyId + "\"" + "}");
			throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

		}
	}

	@Override
	public CompanyDTO getByName(String name) {
		Company company = companyRepository.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("Company", "name", name));

		CompanyDTO companyDto = MapperUtils.mapToDTO(company, CompanyDTO.class);
		companyDto.setLogo(company.getLogo().getLogo().getImage());
		return companyDto;
	}

	@Override
	public CompanyDTO getByKey(String key) {
		Company company = companyRepository.findByKeyCompany(key)
				.orElseThrow(() -> new ResourceNotFoundException("Company", "keyCompany", key));

		CompanyDTO companyDto = MapperUtils.mapToDTO(company, CompanyDTO.class);
		companyDto.setLogo(company.getLogo().getLogo().getImage());
		return companyDto;
	}

}

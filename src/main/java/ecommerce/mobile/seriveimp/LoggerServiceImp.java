package ecommerce.mobile.seriveimp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ecommerce.mobile.constant.SortField;
import ecommerce.mobile.entity.Company;
import ecommerce.mobile.entity.Logger;
import ecommerce.mobile.entity.User;
import ecommerce.mobile.payload.LoggerCreateDTO;
import ecommerce.mobile.payload.LoggerDTO;
import ecommerce.mobile.repository.LoggerRepository;
import ecommerce.mobile.repository.UserRepository;
import ecommerce.mobile.service.LoggerService;
import ecommerce.mobile.utils.MapperUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class LoggerServiceImp implements LoggerService {
	@PersistenceContext
	private EntityManager entityManager;
	private LoggerRepository loggerRepository;
	private UserRepository userRepository;
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoggerServiceImp.class);

	public LoggerServiceImp(EntityManager entityManager, LoggerRepository loggerRepository,
			UserRepository userRepository) {
		super();
		this.entityManager = entityManager;
		this.loggerRepository = loggerRepository;
		this.userRepository = userRepository;
	}

	@Override
	public void logError(HttpServletRequest request, String message, String result, User user, Company company) {

		Logger log = new Logger();
		log.setAgent(request.getHeader("User-Agent"));
//		log.setBody(this.getJsonBody(request));
		log.setEndpoint(request.getRequestURI());
		log.setMessage(message);
		log.setMethod(request.getMethod());
		log.setParams(this.getParamsAsJson(request));
		log.setResult(result);
		log.setUser(user);
		log.setCompany(company);

		loggerRepository.save(log);
	}

	@Override
	public void logInfor(HttpServletRequest request, String message, String result, User user, Company company) {

		Logger log = new Logger();
		log.setAgent(request.getHeader("User-Agent"));
//		log.setBody(this.getJsonBody(request));
		log.setEndpoint(request.getRequestURI());
		log.setMessage(message);
		log.setMethod(request.getMethod());
		log.setParams(this.getParamsAsJson(request));
		log.setResult(result);
		log.setUser(user);
		log.setCompany(company);

		loggerRepository.save(log);
	}

	@Override
	public void logError(HttpServletRequest request, String message, String result) {
		User user = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("jwt")) {
					user = userRepository.findByEmail(cookie.getValue()).orElse(null);
					break;
				}

			}
		}

		Logger log = new Logger();
		log.setAgent(request.getHeader("User-Agent"));
//		log.setBody(this.getJsonBody(request));
		log.setEndpoint(request.getRequestURI());
		log.setMessage(message);
		log.setMethod(request.getMethod());
		log.setParams(this.getParamsAsJson(request));
		log.setResult(result);
		if (user != null) {
			log.setUser(user);
			log.setCompany(user.getCompany());
		}

		loggerRepository.save(log);
	}

	@Override
	public void logInfor(HttpServletRequest request, String message, String result) {
		User user = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("jwt")) {
					user = userRepository.findByEmail(cookie.getValue()).orElse(null);
					break;
				}

			}
		}

		Logger log = new Logger();
		log.setAgent(request.getHeader("User-Agent"));
//		log.setBody(this.getJsonBody(request));
		log.setEndpoint(request.getRequestURI());
		log.setMessage(message);
		log.setMethod(request.getMethod());
		log.setParams(this.getParamsAsJson(request));
		log.setResult(result);
		if (user != null) {
			log.setUser(user);
			log.setCompany(user.getCompany());
		}
		loggerRepository.save(log);
	}

	@Override
	public void logError(HttpServletRequest request, String message, String result, String bodyJson) {
		User user = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("jwt")) {
					user = userRepository.findByEmail(cookie.getValue()).orElse(null);
					break;
				}

			}
		}

		Logger log = new Logger();
		log.setAgent(request.getHeader("User-Agent"));
		log.setBody(bodyJson);
		log.setEndpoint(request.getRequestURI());
		log.setMessage(message);
		log.setMethod(request.getMethod());
		log.setParams(this.getParamsAsJson(request));
		log.setResult(result);
		if (user != null) {
			log.setUser(user);
			log.setCompany(user.getCompany());
		}
		loggerRepository.save(log);
	}

	@Override
	public void logInfor(HttpServletRequest request, String message, String result, String bodyJson) {
		User user = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("jwt")) {
					user = userRepository.findByEmail(cookie.getValue()).orElse(null);
					break;
				}

			}
		}

		Logger log = new Logger();
		log.setAgent(request.getHeader("User-Agent"));
		log.setBody(bodyJson);
		log.setEndpoint(request.getRequestURI());
		log.setMessage(message);
		log.setMethod(request.getMethod());
		log.setParams(this.getParamsAsJson(request));
		log.setResult(result);
		if (user != null) {
			log.setUser(user);
			log.setCompany(user.getCompany());
		}
		loggerRepository.save(log);
	}

	@Override
	public void logInfor(HttpServletRequest request, String message, String result, String bodyJson, User user,
			Company company) {

		Logger log = new Logger();
		log.setAgent(request.getHeader("User-Agent"));
		log.setBody(bodyJson);
		log.setEndpoint(request.getRequestURI());
		log.setMessage(message);
		log.setMethod(request.getMethod());
		log.setParams(this.getParamsAsJson(request));
		log.setResult(result);
		log.setUser(user);
		log.setCompany(company);

		loggerRepository.save(log);
	}

	@Override
	public LoggerCreateDTO createLoggerCreateDto(String method, User user, String message, String agent, String result,
			String params, String body, String endpoint) {

		return new LoggerCreateDTO(method, user.getId(), message, agent, result, params, body, endpoint);
	}

	@Override
	public String getJsonBody(HttpServletRequest request) {
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		try (BufferedReader bufferedReader = new BufferedReader(request.getReader())) {
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (IOException e) {
			logger.error("Error reading request body", e);
		}

		return stringBuilder.length() > 0 ? stringBuilder.toString() : null;
	}

	@Override
	public <T> T getJsonObject(HttpServletRequest request, Class<T> clazz) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(request.getReader(), clazz);
	}

	@Override
	public String getParamsAsJson(HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, Object> jsonMap = new HashMap<>();

		for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
			String paramName = entry.getKey();
			String[] paramValues = entry.getValue();

			if (paramValues.length == 1) {
				jsonMap.put(paramName, paramValues[0]);
			} else {
				jsonMap.put(paramName, paramValues);
			}
		}

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonString = objectMapper.writeValueAsString(jsonMap);

			return jsonString;

		} catch (Exception e) {
			e.printStackTrace();
			return "{}";
		}
	}

	@Override
	public List<LoggerDTO> getAllLoggers(Integer limit, Integer page, Integer status, String method, Integer userId,
			String companyName, String message, String agent, String result, String params, String body,
			String endpoint, String createdAt, String updatedAt, String sortBy) {
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.UPDATEDAT,
				SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC, SortField.UPDATEDATDESC,
				SortField.CREATEDATDESC);
		Pageable pageable = PageRequest.of(page - 1, limit);
		List<String> sortByList = new ArrayList<String>();
		List<Logger> loggerList = null;
		List<Sort.Order> sortOrders = new ArrayList<>();
		List<LoggerDTO> listLoggerDto;

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

		loggerList = loggerRepository.findWithFilters(status, method, userId, companyName, message, agent, result,
				params, body, endpoint, createdAt, updatedAt, pageable, entityManager);
		listLoggerDto = loggerList.stream().map(logger -> {
			LoggerDTO loggerDto = MapperUtils.mapToDTO(logger, LoggerDTO.class);
			if (logger.getCompany() != null)
				loggerDto.setCompanyName(logger.getCompany().getName());

			return loggerDto;
		}).collect(Collectors.toList());

		return listLoggerDto;

	}

}

package ecommerce.mobile.service;

import java.io.IOException;
import java.util.List;

import ecommerce.mobile.entity.Company;
import ecommerce.mobile.entity.User;
import ecommerce.mobile.payload.LoggerCreateDTO;
import ecommerce.mobile.payload.LoggerDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface LoggerService {
	void logError(HttpServletRequest request, String message, String result, User user, Company company);

	void logError(HttpServletRequest request, String message, String result);

	void logError(HttpServletRequest request, String message, String result, String bodyJson);

	void logInfor(HttpServletRequest request, String message, String result, User user, Company company);

	void logInfor(HttpServletRequest request, String message, String result);

	void logInfor(HttpServletRequest request, String message, String result, String bodyJson);

	void logInfor(HttpServletRequest request, String message, String result, String bodyJson, User user,
			Company company);

	LoggerCreateDTO createLoggerCreateDto(String method, User user, String message, String agent, String result,
			String params, String body, String endpoint);

	String getJsonBody(HttpServletRequest request) throws IOException;

	<T> T getJsonObject(HttpServletRequest request, Class<T> clazz) throws IOException;

	String getParamsAsJson(HttpServletRequest request);

	List<LoggerDTO> getAllLoggers(Integer limit, Integer page, Integer status, String method, Integer userId,
			String companyName, String message, String agent, String result, String params, String body, String endpoint,
			String createdAt, String updatedAt, String sortBy);

}

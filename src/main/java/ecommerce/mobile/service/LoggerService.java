package ecommerce.mobile.service;

import java.io.IOException;

import ecommerce.mobile.payload.LoggerCreateDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface LoggerService {
	void logError(HttpServletRequest request, String message, String result, Integer userId);

	void logError(HttpServletRequest request, String message, String result);

	void logError(HttpServletRequest request, String message, String result, String bodyJson);

	void logInfor(HttpServletRequest request, String message, String result, Integer userId);

	void logInfor(HttpServletRequest request, String message, String result);

	void logInfor(HttpServletRequest request, String message, String result, String bodyJson);

	void logInfor(HttpServletRequest request, String message, String result, String bodyJson, Integer userId);

	LoggerCreateDTO createLoggerCreateDto(String method, Integer userId, String message, String agent, String result,
			String params, String body, String endpoint);

	String getJsonBody(HttpServletRequest request) throws IOException;

	<T> T getJsonObject(HttpServletRequest request, Class<T> clazz) throws IOException;

	String getParamsAsJson(HttpServletRequest request);
}

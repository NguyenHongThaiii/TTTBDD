package ecommerce.mobile.security;

import java.io.IOException;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ecommerce.mobile.exception.ErrorDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
//	private final ObjectMapper objectMapper;
//
//	public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
//		this.objectMapper = objectMapper;
//	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		ObjectMapper mapper = new ObjectMapper();
		ErrorDetails errorDetails = new ErrorDetails(new Date(), authException.getMessage(), request.getServletPath());
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(mapper.writeValueAsString(errorDetails));

//		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());

	}

}

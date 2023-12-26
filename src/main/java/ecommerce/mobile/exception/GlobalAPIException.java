package ecommerce.mobile.exception;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ecommerce.mobile.entity.User;
import ecommerce.mobile.repository.UserRepository;
import ecommerce.mobile.seriveimp.UserServiceImp;
import ecommerce.mobile.service.LoggerService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalAPIException extends ResponseEntityExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy:HH:mm:ss");
	@Autowired
	private LoggerService loggerService;
	@Autowired
	private UserRepository userRepository;

	@Override
	public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		int beginIndex = 0; // Calculate or obtain dynamically
		int endIndex = ex.getMessage().length();
		ErrorDetails errorDetails = null;
		if (endIndex > 255) {
			errorDetails = new ErrorDetails(LocalDateTime.now().format(formatter),
					ex.getMessage().substring(beginIndex, 254), request.getDescription(false));
		} else {
			errorDetails = new ErrorDetails(LocalDateTime.now().format(formatter),
					ex.getMessage().substring(beginIndex, endIndex), request.getDescription(false));
		}

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	// handle specific exceptions
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
			WebRequest webRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
		int beginIndex = 0; // Calculate or obtain dynamically
		int endIndex = exception.getMessage().length();
		ErrorDetails errorDetails = null;
		User user = this.handleLoggerError(request, response, false);
		if (endIndex > 255) {
			errorDetails = new ErrorDetails(LocalDateTime.now().format(formatter),
					exception.getMessage().substring(beginIndex, 254), webRequest.getDescription(false));
			loggerService.logError(request, exception.getMessage().substring(0, 255), "FAILED", user,
					user.getCompany());

		} else {
			errorDetails = new ErrorDetails(LocalDateTime.now().format(formatter),
					exception.getMessage().substring(beginIndex, endIndex), webRequest.getDescription(false));
			loggerService.logError(request, exception.getMessage().substring(0, endIndex), "FAILED", user,
					user.getCompany());

		}
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	// access denied exceptions
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException exception,
			WebRequest webRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
		int beginIndex = 0; // Calculate or obtain dynamically
		int endIndex = exception.getMessage().length();
		ErrorDetails errorDetails = null;
		User user = this.handleLoggerError(request, response, false);
		if (endIndex > 255) {
			errorDetails = new ErrorDetails(LocalDateTime.now().format(formatter),
					exception.getMessage().substring(beginIndex, 254), webRequest.getDescription(false));
			loggerService.logError(request, exception.getMessage().substring(0, 255), "FAILED", user,
					user.getCompany());

		} else {
			errorDetails = new ErrorDetails(LocalDateTime.now().format(formatter),
					exception.getMessage().substring(beginIndex, endIndex), webRequest.getDescription(false));
			loggerService.logError(request, exception.getMessage().substring(0, endIndex), "FAILED", user,
					user.getCompany());

		}
		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
	}

	// Cafe exceptions
	@ExceptionHandler(AppGlobalException.class)
	public ResponseEntity<ErrorDetails> handleBlogAPIException(AppGlobalException exception, WebRequest webRequest,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		int beginIndex = 0; // Calculate or obtain dynamically
		int endIndex = exception.getMessage().length();
		ErrorDetails errorDetails = null;
		User user = this.handleLoggerError(request, response, false);
		if (endIndex > 255) {
			errorDetails = new ErrorDetails(LocalDateTime.now().format(formatter),
					exception.getMessage().substring(beginIndex, 254), webRequest.getDescription(false));
			loggerService.logError(request, exception.getMessage().substring(0, 255), "FAILED", user,
					user.getCompany());

		} else {
			errorDetails = new ErrorDetails(LocalDateTime.now().format(formatter),
					exception.getMessage().substring(beginIndex, endIndex), webRequest.getDescription(false));
			loggerService.logError(request, exception.getMessage().substring(0, endIndex), "FAILED", user,
					user.getCompany());

		}
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

//	UsernameNotFoundException 
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleAccessDenieddException(UsernameNotFoundException exception,
			WebRequest webRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
		int beginIndex = 0; // Calculate or obtain dynamically
		int endIndex = exception.getMessage().length();
		ErrorDetails errorDetails = null;
		User user = this.handleLoggerError(request, response, false);
		if (endIndex > 255) {
			errorDetails = new ErrorDetails(LocalDateTime.now().format(formatter),
					exception.getMessage().substring(beginIndex, 254), webRequest.getDescription(false));
			loggerService.logError(request, exception.getMessage().substring(0, 255), "FAILED", user,
					user.getCompany());

		} else {
			errorDetails = new ErrorDetails(LocalDateTime.now().format(formatter),
					exception.getMessage().substring(beginIndex, endIndex), webRequest.getDescription(false));
			loggerService.logError(request, exception.getMessage().substring(0, endIndex), "FAILED", user,
					user.getCompany());

		}
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	// global exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webRequest,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		int beginIndex = 0; // Calculate or obtain dynamically
		int endIndex = exception.getMessage().length();
		ErrorDetails errorDetails = null;
		User user = this.handleLoggerError(request, response, false);
		if (endIndex > 255) {
			errorDetails = new ErrorDetails(LocalDateTime.now().format(formatter),
					exception.getMessage().substring(beginIndex, 254), webRequest.getDescription(false));
			loggerService.logError(request, exception.getMessage().substring(0, 255), "FAILED", user,
					user.getCompany());

		} else {
			errorDetails = new ErrorDetails(LocalDateTime.now().format(formatter),
					exception.getMessage().substring(beginIndex, endIndex), webRequest.getDescription(false));
			loggerService.logError(request, exception.getMessage().substring(0, endIndex), "FAILED", user,
					user.getCompany());

		}
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

//	BadCredentialsException 
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorDetails> handleAccessDeniedException(BadCredentialsException exception,
			WebRequest webRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
		int beginIndex = 0; // Calculate or obtain dynamically
		int endIndex = exception.getMessage().length();
		ErrorDetails errorDetails = null;
		User user = this.handleLoggerError(request, response, false);
		if (endIndex > 255) {
			errorDetails = new ErrorDetails(LocalDateTime.now().format(formatter),
					exception.getMessage().substring(beginIndex, 254), webRequest.getDescription(false));
			loggerService.logError(request, exception.getMessage().substring(0, 255), "FAILED", user,
					user.getCompany());

		} else {
			errorDetails = new ErrorDetails(LocalDateTime.now().format(formatter),
					exception.getMessage().substring(beginIndex, endIndex), webRequest.getDescription(false));
			loggerService.logError(request, exception.getMessage().substring(0, endIndex), "FAILED", user,
					user.getCompany());

		}
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	// argument not valid exceptions
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			int beginIndex = 0; // Calculate or obtain dynamically
			int endIndex = ex.getMessage().length();
			String fieldName = ((FieldError) error).getField();
			String message = null;
			if (endIndex > 255) {
				message = error.getDefaultMessage().substring(beginIndex, 254);
				;
			} else {
				message = error.getDefaultMessage();
			}
			errors.put(fieldName, message);
		});
		errors.put("timestamp", LocalDateTime.now().format(formatter));
		if (request instanceof ServletWebRequest) {
			ServletWebRequest servletWebRequest = (ServletWebRequest) request;
			HttpServletRequest httpServletRequest = servletWebRequest.getRequest();
			HttpServletResponse httpServletResponse = servletWebRequest.getResponse();
			String endpoint = httpServletRequest.getRequestURI();
			errors.put("endpoint", endpoint);
			User user = this.handleLoggerError(httpServletRequest, httpServletResponse, true);
			if (ex.getMessage().length() > 255) {
				loggerService.logError(httpServletRequest, ex.getMessage().substring(0, 255), "FAILED", user,
						user.getCompany());
			} else {
				loggerService.logError(httpServletRequest, ex.getMessage().substring(0, ex.getMessage().length()),
						"FAILED", user, user.getCompany());
			}

		}
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		Map<String, String> errors = new HashMap<>();
		if (request instanceof ServletWebRequest) {
			ServletWebRequest servletWebRequest = (ServletWebRequest) request;
			HttpServletRequest httpServletRequest = servletWebRequest.getRequest();
			HttpServletResponse httpServletResponse = servletWebRequest.getResponse();

			String endpoint = httpServletRequest.getRequestURI();
			errors.put("endpoint", endpoint);

			User user = this.handleLoggerError(httpServletRequest, httpServletResponse, true);
			if (ex.getMessage().length() > 255) {
				loggerService.logError(httpServletRequest, ex.getMessage().substring(0, 255), "FAILED", user,
						user.getCompany());

			} else {
				loggerService.logError(httpServletRequest, ex.getMessage().substring(0, ex.getMessage().length()),
						"FAILED", user, user.getCompany());
			}

		}
		if (ex.getMessage().length() > 255) {
			errors.put("messsage", ex.getMessage().substring(0, 255));

		} else {
			errors.put("messsage", ex.getMessage().substring(0, ex.getMessage().length()));

		}
		errors.put("messsage", ex.getMessage().substring(0, 255));
		errors.put("timestamp", LocalDateTime.now().format(formatter));

		return new ResponseEntity<>(errors, status);
	}

	public User handleLoggerError(HttpServletRequest request, HttpServletResponse response, Boolean clearJwt) {
		User user = null;
		String email = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("jwt")) {
					email = cookie.getValue();
					if (clearJwt) {
						cookie.setMaxAge(0);
						cookie.setPath("/");
						cookie.getAttribute("jwt");
						response.addCookie(cookie);
					}
					break;
				}

			}
		}
		if (email != null)
			user = userRepository.findByEmail(email).orElse(null);

		return user;
	}
}

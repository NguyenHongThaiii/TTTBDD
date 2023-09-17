package ecommerce.mobile.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalAPIException extends ResponseEntityExceptionHandler {

	// handle specific exceptions
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
			WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	// Cafe exceptions
	@ExceptionHandler(AppGlobalException.class)
	public ResponseEntity<ErrorDetails> handleBlogAPIException(AppGlobalException exception, WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
				webRequest.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	// global exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

//
////	UsernameNotFoundException 
//	@ExceptionHandler(UsernameNotFoundException.class)
//	public ResponseEntity<ErrorDetails> handleAccessDeniedException(UsernameNotFoundException exception,
//			WebRequest webRequest) {
//		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
//				webRequest.getDescription(false));
//
//		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//	}
//
////	BadCredentialsException 
//	@ExceptionHandler(BadCredentialsException.class)
//	public ResponseEntity<ErrorDetails> handleAccessDeniedException(BadCredentialsException exception,
//			WebRequest webRequest) {
//		ErrorDetails errorDetails = new ErrorDetails(new Date(), "User not found", webRequest.getDescription(false));
//
//		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//	}
//
	// argument not valid exceptions
//	@Override
//	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//		Map<String, String> errors = new HashMap<>();
//		ex.getBindingResult().getAllErrors().forEach((error) -> {
//			String fieldName = ((FieldError) error).getField();
//			String message = error.getDefaultMessage();
//			errors.put(fieldName, message);
//		});
//
//		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//	}
//
//	// access denied exceptions
//	@ExceptionHandler(AccessDeniedException.class)
//	public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException exception,
//			WebRequest webRequest) {
//		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
//				webRequest.getDescription(false));
//
//		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
//	}
//
//	// insufficient exceptions
//	@ExceptionHandler(InsufficientAuthenticationException.class)
//	public ResponseEntity<ErrorDetails> handleAccessDeniedException(InsufficientAuthenticationException exception,
//			WebRequest webRequest) {
//
//		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
//				webRequest.getDescription(false));
//		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
//	}
//
//	// signature exceptions
//	@ExceptionHandler(SignatureException.class)
//	public ResponseEntity<ErrorDetails> handleAccessDeniedException(SignatureException exception,
//			WebRequest webRequest) {
//
//		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
//				webRequest.getDescription(false));
//		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
//	}

}

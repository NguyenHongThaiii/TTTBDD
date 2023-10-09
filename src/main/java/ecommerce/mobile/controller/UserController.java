package ecommerce.mobile.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.mobile.payload.ForgotPasswordDTO;
import ecommerce.mobile.payload.LoginDTO;
import ecommerce.mobile.payload.RegisterDTO;
import ecommerce.mobile.payload.ResetPasswordDTO;
import ecommerce.mobile.payload.UserDTO;
import ecommerce.mobile.payload.UserUpdateDTO;
import ecommerce.mobile.payload.ValidateOtpDTO;
import ecommerce.mobile.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Tag(name = "CRUD REST APIs Users")

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
	private UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Get all users")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getListUsers(@RequestParam(defaultValue = "20") int limit,
			@RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String name,
			@RequestParam(required = false) String email, @RequestParam(required = false) Integer status,
			@RequestParam(required = false) String createdAt, @RequestParam(required = false) String updatedAt,
			@RequestParam(required = false) String phone, @RequestParam(required = false) String role,
			@RequestParam(required = false) String gender, @RequestParam(required = false) String companyName,
			@RequestParam(required = false, defaultValue = "") String sortBy, HttpServletRequest request) {
		List<UserDTO> listUserDto = userService.getListUser(limit, page, name, email, phone, gender, role, companyName,
				status, createdAt, updatedAt, sortBy, request);
		return ResponseEntity.ok(listUserDto);
	}

	@Operation(summary = "Login user")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PostMapping(value = { "/login", "/signin" })
	public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginDTO loginDto, HttpServletRequest request,
			HttpServletResponse response) {
		UserDTO user = userService.login(loginDto, request, response);
		return ResponseEntity.ok(user);
	}

	@Operation(summary = "Create user")
	@ApiResponse(responseCode = "201", description = "Http status 201 CREATED")
	@PostMapping(value = { "/register", "/signup" })
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody RegisterDTO regsiterDto, HttpServletRequest request) {
		UserDTO reg = userService.createUser(regsiterDto, request);

		return new ResponseEntity<UserDTO>(reg, HttpStatus.CREATED);
	}

	@Operation(summary = "Validate register")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PostMapping(value = { "/validateRegister", })
	public ResponseEntity<UserDTO> validateRegister(@Valid @RequestBody ValidateOtpDTO validate) {
		UserDTO reg = userService.validateRegister(validate);
		return new ResponseEntity<>(reg, HttpStatus.CREATED);
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Get user by id")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@GetMapping("users/id/{id}")
	public ResponseEntity<UserDTO> getUserById(@Valid @PathVariable(name = "id") int id) {
		UserDTO userDto = userService.getUserById(id);
		return ResponseEntity.ok(userDto);
	}

	@Operation(summary = "Get user by email")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@GetMapping("users/{email}")
	public ResponseEntity<UserDTO> getUserByEmail(@Valid @PathVariable(name = "email") String email) {
		UserDTO userDto = userService.getUserByEmail(email);
		return ResponseEntity.ok(userDto);
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Delete user by id")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable(name = "id") int id, HttpServletRequest request)
			throws java.io.IOException {
		userService.deleteUserById(id, request);
		return ResponseEntity.ok("Delete Successfully");
	}

	@Operation(summary = "Validate reset pasword")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PostMapping(value = { "/validateReset", })
	public ResponseEntity<String> validateReset(@Valid @RequestBody ValidateOtpDTO validate) {
		userService.handleValidateResetPassword(validate);
		return ResponseEntity.ok("Ok");
	}

	@Operation(summary = "Forgot password")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PostMapping(value = { "/forgotPassword", })
	public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDto)
			throws MessagingException {
		userService.forgotPassword(forgotPasswordDto.getEmail());
		return ResponseEntity.ok("Ok");
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Update user by id")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@PatchMapping("/users")
	public ResponseEntity<UserDTO> updateUser(@ModelAttribute UserUpdateDTO userUpdateDto, HttpServletRequest request)
			throws IOException {
		UserDTO userDto = userService.updateUser(userUpdateDto, request);
		return new ResponseEntity<>(userDto, HttpStatus.CREATED);
	}

	@Operation(summary = "Reset pasword user")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PostMapping("/resetPassword")
	public ResponseEntity<String> resetPassword(@Valid @RequestBody(required = true) ResetPasswordDTO reset) {
		userService.handleResetPassword(reset);
		return ResponseEntity.ok("Ok");
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Change password user")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@PostMapping("/changePassword")
	public ResponseEntity<String> changePassword(@Valid @RequestBody(required = true) ResetPasswordDTO reset,
			HttpServletRequest request) {
		userService.changePassword(reset, request);
		return ResponseEntity.ok("Ok");
	}
}

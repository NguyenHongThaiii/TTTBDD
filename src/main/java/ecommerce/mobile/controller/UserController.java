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
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
	private UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getListUsers(@RequestParam(defaultValue = "5") int limit,
			@RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String name,
			@RequestParam(required = false) String email, @RequestParam(defaultValue = "1") int status,
			@RequestParam(required = false) String createdAt, @RequestParam(required = false) String updatedAt,
			@RequestParam(required = false) String phone, @RequestParam(required = false) String role,
			@RequestParam(required = false) String companyName,
			@RequestParam(required = false, defaultValue = "") String sortBy) {
		List<UserDTO> listUserDto = userService.getListUser(limit, page, name, email, phone, role, companyName, status,
				createdAt, updatedAt, sortBy);
		return ResponseEntity.ok(listUserDto);
	}

	@PostMapping(value = { "/login", "/signin" })
	public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginDTO loginDto, HttpServletResponse response) {
		UserDTO user = userService.login(loginDto, response);
		return ResponseEntity.ok(user);
	}

	@PostMapping(value = { "/register", "/signup" })
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody RegisterDTO regsiterDto) {
		UserDTO reg = userService.createUser(regsiterDto);

		return new ResponseEntity<UserDTO>(reg, HttpStatus.CREATED);
	}

	@PostMapping(value = { "/validateRegister", })
	public ResponseEntity<UserDTO> validateRegister(@Valid @RequestBody ValidateOtpDTO validate) {
		UserDTO reg = userService.validateRegister(validate);
		return new ResponseEntity<>(reg, HttpStatus.CREATED);
	}

	@GetMapping("users/id/{id}")
	public ResponseEntity<UserDTO> getUserById(@Valid @PathVariable(name = "id") int id) {
		UserDTO userDto = userService.getUserById(id);
		return ResponseEntity.ok(userDto);
	}

	@GetMapping("users/{email}")
	public ResponseEntity<UserDTO> getUserByEmail(@Valid @PathVariable(name = "email") String email) {
		UserDTO userDto = userService.getUserByEmail(email);
		return ResponseEntity.ok(userDto);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable(name = "id") int id) throws java.io.IOException {
		userService.deleteUserById(id);
		return ResponseEntity.ok("Delete Successfully");
	}

	@PostMapping(value = { "/validateReset", })
	public ResponseEntity<String> validateReset(@Valid @RequestBody ValidateOtpDTO validate) {
		userService.handleValidateResetPassword(validate);
		return ResponseEntity.ok("Ok");
	}

	@PostMapping(value = { "/forgotPassword", })
	public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDto)
			throws MessagingException {
		userService.forgotPassword(forgotPasswordDto.getEmail());
		return ResponseEntity.ok("Ok");
	}

	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@PatchMapping("/users")
	public ResponseEntity<UserDTO> updateUser(@Valid @ModelAttribute UserUpdateDTO userUpdateDto) throws IOException {
		UserDTO userDto = userService.updateUser(userUpdateDto);
		return new ResponseEntity<>(userDto, HttpStatus.CREATED);
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<String> resetPassword(@Valid @RequestBody(required = true) ResetPasswordDTO reset) {
		userService.handleResetPassword(reset);
		return ResponseEntity.ok("Ok");
	}
}

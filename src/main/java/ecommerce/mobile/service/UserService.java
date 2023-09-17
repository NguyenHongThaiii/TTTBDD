package ecommerce.mobile.service;

import java.io.IOException;
import java.util.List;

import ecommerce.mobile.payload.LoginDTO;
import ecommerce.mobile.payload.RegisterDTO;
import ecommerce.mobile.payload.ResetPasswordDTO;
import ecommerce.mobile.payload.UserDTO;
import ecommerce.mobile.payload.UserUpdateDTO;
import ecommerce.mobile.payload.ValidateOtpDTO;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

	UserDTO login(LoginDTO loginDto, HttpServletResponse response);

	UserDTO createUser(RegisterDTO registerDto);

	void forgotPassword(String email) throws MessagingException;

	UserDTO updateUser(UserUpdateDTO userUpdateDto) throws IOException;

	UserDTO validateRegister(ValidateOtpDTO validateDto);

	void handleValidateResetPassword(ValidateOtpDTO validateOtpDto);

	Boolean validateOtp(String otp, String key);

	UserDTO getUserById(int id);

	UserDTO getUserByEmail(String email);

	void handleResetPassword(ResetPasswordDTO reset);

	void deleteUserById(Integer id) throws IOException;

	List<UserDTO> getListUser(int limit, int page, String name, String email, String phone, String role,
			String companyName, Integer status, String createdAt, String updatedAt, String sortBy);

}

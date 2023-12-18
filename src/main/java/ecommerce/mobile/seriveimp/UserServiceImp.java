package ecommerce.mobile.seriveimp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ecommerce.mobile.constant.SortField;
import ecommerce.mobile.entity.Company;
import ecommerce.mobile.entity.Image;
import ecommerce.mobile.entity.Role;
import ecommerce.mobile.entity.User;
import ecommerce.mobile.exception.AppGlobalException;
import ecommerce.mobile.exception.ResourceNotFoundException;
import ecommerce.mobile.payload.LoginDTO;
import ecommerce.mobile.payload.RegisterDTO;
import ecommerce.mobile.payload.ResetPasswordDTO;
import ecommerce.mobile.payload.UserDTO;
import ecommerce.mobile.payload.UserUpdateDTO;
import ecommerce.mobile.payload.ValidateOtpDTO;
import ecommerce.mobile.repository.CompanyRepository;
import ecommerce.mobile.repository.ImageRepository;
import ecommerce.mobile.repository.RoleRepository;
import ecommerce.mobile.repository.UserRepository;
import ecommerce.mobile.service.CloudinaryService;
import ecommerce.mobile.service.EmailService;
import ecommerce.mobile.service.LoggerService;
import ecommerce.mobile.service.OTPService;
import ecommerce.mobile.service.UserService;
import ecommerce.mobile.utils.MapperUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImp implements UserService {
	private UserRepository userRepository;
	private CloudinaryService cloudinaryService;
	private CompanyRepository companyRepository;
	@PersistenceContext
	private EntityManager entityManager;
	private LoggerService loggerService;
	private AuthenticationManager authenticationManager;
	private PasswordEncoder passwordEncoder;
	private RoleRepository roleRepository;
	private OTPService otpService;
	private EmailService emailService;
	private ImageRepository imageRepository;
	private final ObjectMapper objectMapper;

	private final String path = "mobile/Users";

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

	public UserServiceImp(UserRepository userRepository, CloudinaryService cloudinaryService,
			CompanyRepository companyRepository, EntityManager entityManager, LoggerService loggerService,
			AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RoleRepository roleRepository,
			OTPService otpService, EmailService emailService, ImageRepository imageRepository,
			ObjectMapper objectMapper) {
		super();
		this.userRepository = userRepository;
		this.cloudinaryService = cloudinaryService;
		this.companyRepository = companyRepository;
		this.entityManager = entityManager;
		this.loggerService = loggerService;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
		this.otpService = otpService;
		this.emailService = emailService;
		this.imageRepository = imageRepository;
		this.objectMapper = objectMapper;
	}

	@Override
	public List<UserDTO> getListUser(int limit, int page, String name, String email, String phone, String gender,
			String role, String companyName, Integer status, String createdAt, String updatedAt, String sortBy,
			HttpServletRequest request) {
		try {
			List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.UPDATEDAT,
					SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC, SortField.UPDATEDATDESC,
					SortField.CREATEDATDESC);
			Pageable pageable = PageRequest.of(page - 1, limit);
			List<String> sortByList = new ArrayList<String>();
			List<UserDTO> listUserDto;
			List<User> listUser;
			List<Sort.Order> sortOrders = new ArrayList<>();

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

			listUser = userRepository.findWithFilters(name, email, phone, gender, role, status, createdAt, updatedAt,
					companyName, pageable, entityManager);

			listUserDto = listUser.stream().map(user -> {
				UserDTO userDto = MapperUtils.mapToDTO(user, UserDTO.class);
				if (user.getAvatar() != null)
					userDto.setImage(user.getAvatar().getImage());

				user.getRole().forEach(r -> userDto.getRoles().add(r.getName()));

				return userDto;
			}).collect(Collectors.toList());

			return listUserDto;
		} catch (Exception e) {

			throw new AppGlobalException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Override
	public UserDTO login(LoginDTO loginDto, HttpServletRequest request, HttpServletResponse response) {

		try {
			User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(
					() -> new AppGlobalException(HttpStatus.BAD_REQUEST, "Email or password is not correct."));
			if (user.getStatus() == 3)
				throw new AppGlobalException(HttpStatus.BAD_REQUEST, "Your account is blocked.");

			if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
				throw new AppGlobalException(HttpStatus.BAD_REQUEST, "Email or password is not correct.");
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			UserDTO userDto = MapperUtils.mapToDTO(user, UserDTO.class);
			if (user.getAvatar() != null)
				userDto.setImage(user.getAvatar().getImage());
			else
				userDto.setImage(null);
			if (user.getWallpaper() != null)
				userDto.setWallpaper(user.getWallpaper());
			else
				userDto.setWallpaper(null);

			user.getRole().forEach(role -> {
				userDto.getRoles().add(role.getName());
			});
			Cookie cookie = new Cookie("jwt", user.getEmail());
			cookie.setHttpOnly(false);
			cookie.setSecure(false);
			cookie.setMaxAge(7 * 24 * 60 * 999999999);
			cookie.setPath("/");
			response.addCookie(cookie);
			loggerService.logInfor(request, "Login User", "SUCCESSFULLY", objectMapper.writeValueAsString(loginDto),
					user, user.getCompany());
			return userDto;

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			try {
				loginDto.setPassword("");
				loggerService.logError(request, "Login User", "FAILED", objectMapper.writeValueAsString(loginDto));
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

		}

	}

	@Override
	public UserDTO createUser(RegisterDTO registerDto, HttpServletRequest request) {

		try {
			Company company = companyRepository.findByKeyCompany(registerDto.getCompanyKey())
					.orElseThrow(() -> new ResourceNotFoundException("Company", "key", registerDto.getCompanyKey()));
			if (userRepository.existsByEmail(registerDto.getEmail()))
				throw new AppGlobalException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
			if (userRepository.existsByPhone(registerDto.getPhone()))
				throw new AppGlobalException(HttpStatus.BAD_REQUEST, "Phone is already exists!.");

			String passwordEncode = passwordEncoder.encode(registerDto.getPassword());
			User user = MapperUtils.mapToEntity(registerDto, User.class);

			user.setPassword(passwordEncode);
			user.setStatus(0);

			List<Role> roles = new ArrayList<>();
			Role userRole = roleRepository.findByName(registerDto.getRole())
					.orElseThrow(() -> new ResourceNotFoundException("Role", "name", registerDto.getName()));
			roles.add(userRole);
			user.setRole(roles);
			user.setCompany(company);
			String otp = otpService.generateAndStoreOtp(user.getEmail());
			userRepository.save(user);

			emailService.sendSimpleEmail(user.getEmail(), "Otp register user", otp);
			UserDTO reg = MapperUtils.mapToEntity(user, UserDTO.class);
			user.getRole().forEach(role -> {
				reg.getRoles().add(role.getName());
			});
			loggerService.logInfor(request, "Register User", "SUCCESSFULLY",
					objectMapper.writeValueAsString(registerDto), user, user.getCompany());
			return reg;

		} catch (JsonProcessingException e) {
			try {
				registerDto.setPassword("");
				loggerService.logError(request, "Register User", "FAILED",
						objectMapper.writeValueAsString(registerDto));
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

		}

	}

	@Override
	public void forgotPassword(String email) throws MessagingException {
		if (email.contains("@")) {
			userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
			String otp = otpService.generateAndStoreOtp(email);
			emailService.sendSimpleEmail(email, "resetPassword", otp);
		}

	}

	@Override
	public Boolean validateOtp(String otp, String key) {
		if (key == null || !key.equals(otp))
			throw new AppGlobalException(HttpStatus.BAD_REQUEST, "Otp is not correct!");

		return true;
	}

	@Override
	public UserDTO getUserById(int id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		UserDTO userDto = MapperUtils.mapToDTO(user, UserDTO.class);
		if (user.getAvatar() != null)
			userDto.setImage(user.getAvatar().getImage());

		user.getRole().forEach(role -> userDto.getRoles().add(role.getName()));
		return userDto;
	}

	@Override
	public void deleteUserById(Integer id, HttpServletRequest request) throws IOException {
		try {
			User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
			user.setStatus(2);
			userRepository.save(user);
			loggerService.logInfor(request, "Delete User", "SUCCESSFULLY", "{\"id\": " + "\"" + id + "\"" + "}");
		} catch (Exception e) {
			loggerService.logError(request, "Delete User", "FAILED", "{\"id\": " + "\"" + id + "\"" + "}");
			throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

		}
	}

	@Override
	public UserDTO validateRegister(ValidateOtpDTO validateDto) {
		String otpCache = otpService.getOtpByEmail(validateDto.getEmail());
		this.validateOtp(validateDto.getOtp(), otpCache);
		// update token
		User user = userRepository.findByEmail(validateDto.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User", "email", validateDto.getEmail()));
		// update status user
		user.setStatus(1);
		userRepository.save(user);
		UserDTO reg = MapperUtils.mapToDTO(user, UserDTO.class);
		user.getRole().forEach(role -> {
			reg.getRoles().add(role.getName());
		});
		otpService.clearCache("otpCache", validateDto.getEmail());
		return reg;
	}

	@Override
	public void handleValidateResetPassword(ValidateOtpDTO validateOtpDto) {
		String otpEmail = otpService.getOtpByEmail(validateOtpDto.getEmail());
		this.validateOtp(otpEmail, validateOtpDto.getOtp());
		otpService.clearCache("otpCache", validateOtpDto.getEmail().toLowerCase());
		otpService.generateAndStoreAnotherData(validateOtpDto.getEmail().toLowerCase());
	}

	@Override
	public void handleResetPassword(ResetPasswordDTO reset) {
		User user = userRepository.findByEmail(reset.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User", "email", reset.getEmail()));
		String otpEmail = otpService.getOtpBySession(user.getEmail().toLowerCase());
		if (otpEmail == null)
			throw new AppGlobalException(HttpStatus.BAD_REQUEST, "Time is expired");

		if (!reset.getPassword().equals(reset.getRetypePassword()))
			throw new AppGlobalException(HttpStatus.BAD_REQUEST, "retype password is not match");

		otpService.clearCache("session", reset.getEmail());
		String passwordEncryt = passwordEncoder.encode(reset.getPassword());
		user.setPassword(passwordEncryt);
		userRepository.save(user);
	}

	@Override
	public UserDTO updateUser(UserUpdateDTO userUpdateDto, HttpServletRequest request) throws IOException {
		try {
			User userCurrent = userRepository.findById(userUpdateDto.getId())
					.orElseThrow(() -> new ResourceNotFoundException("User", "id", userUpdateDto.getId()));

			UserDTO userDto = MapperUtils.mapToDTO(userUpdateDto, UserDTO.class);

			userDto.setId(userCurrent.getId());

			if (userUpdateDto.getPassword() != null)
				userCurrent.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
			if (userUpdateDto.getAddress() != null)
				userCurrent.setAddress(userUpdateDto.getAddress());
			if (userUpdateDto.getName() != null)
				userCurrent.setName(userUpdateDto.getName());
			if (userUpdateDto.getPhone() != null)
				userCurrent.setPhone(userUpdateDto.getPhone());
			if (userUpdateDto.getStatus() != null)
				userCurrent.setStatus(userUpdateDto.getStatus());
			if (userUpdateDto.getGender() != null)
				userCurrent.setGender(userUpdateDto.getGender());
			if (userUpdateDto.getRole() != null) {
				List<Role> roles = new ArrayList<>();
				Role userRole = roleRepository.findByName(userUpdateDto.getRole().toString()).orElseThrow(
						() -> new ResourceNotFoundException("Role", "name", userUpdateDto.getRole().toString()));
				roles.add(userRole);
				userCurrent.setRole(roles);
			}
			if (userUpdateDto.getAvartar() != null) {
				Image image;
				if (userCurrent.getAvatar() != null) {
					image = userCurrent.getAvatar();
					cloudinaryService.removeImageFromCloudinary(userCurrent.getAvatar().getImage(), path);
				} else
					image = new Image();
				String url = cloudinaryService.uploadImage(userUpdateDto.getAvartar(), path, "image");
				image.setImage(url);
				image.setStatus(1);
				image.setUser(userCurrent);
				imageRepository.save(image);
				userCurrent.setAvatar(image);
			}
			if (userUpdateDto.getWallpaper() != null) {
				if (userCurrent.getWallpaper() != null)
					cloudinaryService.removeImageFromCloudinary(userCurrent.getAvatar().getImage(), path);
				String url = cloudinaryService.uploadImage(userUpdateDto.getWallpaper(), path, "image");
				userCurrent.setWallpaper(url);
			}

			userRepository.save(userCurrent);

			UserDTO res = MapperUtils.mapToDTO(userCurrent, UserDTO.class);

			if (userCurrent.getAvatar() != null)
				res.setImage(userCurrent.getAvatar().getImage());
			userCurrent.getRole().forEach(role -> res.getRoles().add(role.getName()));

			loggerService.logInfor(request, "Update User", "SUCCESSFULLY",
					objectMapper.writeValueAsString(userUpdateDto));
			return res;
		} catch (Exception e) {
			loggerService.logError(request, "Update User", "FAILED", objectMapper.writeValueAsString(userUpdateDto));
			throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

		}
	}

	@Override
	public UserDTO getUserByEmail(String email) {
		if (!email.contains("@"))
			throw new AppGlobalException(HttpStatus.BAD_REQUEST, "Email not valid");
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
		UserDTO userDto = MapperUtils.mapToDTO(user, UserDTO.class);
		if (user.getAvatar() != null)
			userDto.setImage(user.getAvatar().getImage());

		user.getRole().forEach(role -> userDto.getRoles().add(role.getName()));
		return userDto;
	}

	@Override
	public void changePassword(ResetPasswordDTO reset, HttpServletRequest request) {
		try {
			User user = userRepository.findByEmail(reset.getEmail())
					.orElseThrow(() -> new ResourceNotFoundException("User", "email", reset.getEmail()));
			if (!passwordEncoder.matches(reset.getOldPassword(), user.getPassword()))
				throw new AppGlobalException(HttpStatus.BAD_REQUEST, "Password old not correct!");

			if (!reset.getPassword().equals(reset.getRetypePassword()))
				throw new AppGlobalException(HttpStatus.BAD_REQUEST, "Password and retype password not matches!");

			user.setPassword(passwordEncoder.encode(reset.getPassword()));

			userRepository.save(user);
			loggerService.logInfor(request, "Update User", "SUCCESSFULLY", objectMapper.writeValueAsString(reset));
		} catch (Exception e) {
			try {
				loggerService.logError(request, "Update User", "FAILED", objectMapper.writeValueAsString(reset));
				throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				throw new AppGlobalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

			}
		}

	}

}

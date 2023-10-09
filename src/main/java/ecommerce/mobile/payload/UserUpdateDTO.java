package ecommerce.mobile.payload;

import org.springframework.web.multipart.MultipartFile;

import ecommerce.mobile.annotation.CheckStatus;
import ecommerce.mobile.constant.RoleField;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

@Schema(description = "UserUpdateDTO Model Information")
public class UserUpdateDTO {
	@Schema(description = "Id User")
	@NotNull
	private Integer id;
	@Schema(description = "Password User")
	private String password;
	@Schema(description = "Name User")
	private String name;
	@Schema(description = "Role User")
	@Enumerated(EnumType.STRING)
	private RoleField role;
	@Schema(description = "Address User")
	private String address;
	@Schema(description = "Gender User (Male or Female)")
	private String gender;
	@Schema(description = "Avatar User")
	private MultipartFile avartar;
	@Schema(description = "Phone User")
	private String phone;
	@Schema(description = "Status User (0,1,2,3)")
	@CheckStatus(allowedValues = { 0, 1, 2, 3 })
	private Integer status;

	@Schema(description = "WallpaperUser")
	private MultipartFile wallpaper;

	public UserUpdateDTO() {
		// TODO Auto-generated constructor stub
		this.status = 1;

	}

	public UserUpdateDTO(@NotNull Integer id, String password, String name, RoleField role, String address,
			String gender, MultipartFile avartar, String phone, Integer status, MultipartFile wallpaper) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.role = role;
		this.address = address;
		this.gender = gender;
		this.avartar = avartar;
		this.phone = phone;
		this.status = status;
		this.wallpaper = wallpaper;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RoleField getRole() {
		return role;
	}

	public void setRole(RoleField role) {
		this.role = role;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public MultipartFile getAvartar() {
		return avartar;
	}

	public void setAvartar(MultipartFile avartar) {
		this.avartar = avartar;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public MultipartFile getWallpaper() {
		return wallpaper;
	}

	public void setWallpaper(MultipartFile wallpaper) {
		this.wallpaper = wallpaper;
	}

}

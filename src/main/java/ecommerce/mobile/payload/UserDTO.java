package ecommerce.mobile.payload;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "UserDTO Model Information")
public class UserDTO {
	@Schema(description = "Id User")
	private int id;
	@Schema(description = "Email User")
	private String email;
	@Schema(description = "Name User")
	private String name;
	@Schema(description = "Address User")
	private String address;
	@Schema(description = "Phone User")
	private String phone;
	@Schema(description = "List Roles User")
	private List<String> roles = new ArrayList<>();
	@Schema(description = "Avatar User")
	private String image;
	@Schema(description = "Id Company")
	private Integer companyId;
	@Schema(description = "Gender User (Male or Female)")
	private String gender;
	@Schema(description = "Wallpaper User")
	private String wallpaper;
	@Schema(description = "Status User (0,1,2)")
	private Integer status;
	@Schema(description = "Created At User")
	private String createdAt;
	@Schema(description = "Udpated At User")
	private String updatedAt;

	public UserDTO() {
		// TODO Auto-generated constructor stub
	}

	public UserDTO(int id, String email, String name, String address, String phone, List<String> roles, String image,
			Integer companyId, String gender, String wallpaper, Integer status, String createdAt, String updatedAt) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.roles = roles;
		this.image = image;
		this.companyId = companyId;
		this.gender = gender;
		this.wallpaper = wallpaper;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getWallpaper() {
		return wallpaper;
	}

	public void setWallpaper(String wallpaper) {
		this.wallpaper = wallpaper;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

}

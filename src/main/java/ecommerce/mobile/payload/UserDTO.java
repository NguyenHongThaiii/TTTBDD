package ecommerce.mobile.payload;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
	private int id;
	private String email;
	private String name;
	private String address;
	private String phone;
	private List<String> roles = new ArrayList<>();
	private String image;
	private Integer companyId;
	private String gender;
	private String wallpaper;
	private Integer status;
	private String createdAt;
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

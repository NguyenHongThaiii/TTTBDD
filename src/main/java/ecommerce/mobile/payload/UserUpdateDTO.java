package ecommerce.mobile.payload;

import org.springframework.web.multipart.MultipartFile;

public class UserUpdateDTO {
	private Integer id;
	private String password;
	private String name;
	private String address;
	private String gender;
	private MultipartFile avartar;
	private String phone;
	private Integer status;
	private MultipartFile wallpaper;

	public UserUpdateDTO() {
		// TODO Auto-generated constructor stub
		this.status = 1;

	}

	public UserUpdateDTO(Integer id, String password, String name, String address, String gender, MultipartFile avartar,
			String phone, Integer status, MultipartFile wallpaper) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
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

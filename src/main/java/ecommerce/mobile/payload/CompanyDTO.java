package ecommerce.mobile.payload;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "CompanyDTO Model Information")
public class CompanyDTO {
	@Schema(description = "Id Company")
	private Integer id;
	@Schema(description = "Status Company (0,1,2)")
	private Integer status;
	@Schema(description = "Created At Company")
	private String createdAt;
	@Schema(description = "Udpated At Company")
	private String updatedAt;
	@Schema(description = "Name Company")
	private String name;
	@Schema(description = "Logo Company")
	private String logo;
	@Schema(description = "Key Company")
	private String keyCompany;
	@Schema(description = "Key Company")
	private String address;
	@Schema(description = "Phone Company")
	private String phone;
	@Schema(description = "Email Company")
	private String email;

	public CompanyDTO() {
		// TODO Auto-generated constructor stub
	}

	public CompanyDTO(Integer id, Integer status, String createdAt, String updatedAt, String name, String logo,
			String keyCompany, String address, String phone, String email) {
		super();
		this.id = id;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.name = name;
		this.logo = logo;
		this.keyCompany = keyCompany;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getKeyCompany() {
		return keyCompany;
	}

	public void setKeyCompany(String keyCompany) {
		this.keyCompany = keyCompany;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

package ecommerce.mobile.payload;

import io.swagger.v3.oas.annotations.media.Schema;

public class CustomerDTO {
	@Schema(description = "Id Customer")
	private Integer id;
	@Schema(description = "Status Customer (0,1,2)")
	private Integer status;
	@Schema(description = "Created At Customer")
	private String createdAt;
	@Schema(description = "Udpated At Customer")
	private String updatedAt;
	@Schema(description = "Name Customer")
	private String name;
	@Schema(description = "Phone Customer")
	private String phone;
	@Schema(description = "Email Customer")
	private String email;
	@Schema(description = "Company Name")
	private String companyName;

	public CustomerDTO() {
		// TODO Auto-generated constructor stub
	}

	public CustomerDTO(Integer id, Integer status, String createdAt, String updatedAt, String name, String phone,
			String email, String companyName) {
		super();
		this.id = id;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.companyName = companyName;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}

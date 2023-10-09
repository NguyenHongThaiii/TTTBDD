package ecommerce.mobile.payload;

import ecommerce.mobile.annotation.CheckStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class CustomerCreateDTO {
	@Schema(description = "Status Customer (0,1,2)")
	@NotNull
	@CheckStatus(allowedValues = { 0, 1, 2 })
	private Integer status;
	@Schema(description = "Name Customer")
	@NotNull
	private String name;
	@Schema(description = "Phone Customer")
	@NotNull
	private String phone;
	@Schema(description = "Email Customer")
	@NotNull
	private String email;
	@Schema(description = "Name Company")
	@NotNull
	private String companyName;

	public CustomerCreateDTO() {
		// TODO Auto-generated constructor stub
	}

	public CustomerCreateDTO(@NotNull Integer status, @NotNull String name, @NotNull String phone,
			@NotNull String email, @NotNull String companyName) {
		super();
		this.status = status;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.companyName = companyName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

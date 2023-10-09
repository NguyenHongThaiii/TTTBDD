package ecommerce.mobile.payload;

import org.springframework.web.multipart.MultipartFile;

import ecommerce.mobile.annotation.CheckStatus;
import ecommerce.mobile.annotation.ValidFile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Company Create DTO Model Information")
public class CompanyCreateDTO {
	@Schema(description = "Name Company")
	@NotNull
	@Size(max = 25, min = 4)
	private String name;
	@NotNull
	@ValidFile()
	@Schema(description = "Logo Company")
	private MultipartFile logo;
	@Schema(description = "Status Company (0,1,2)")
	@CheckStatus(allowedValues = { 0, 1, 2 })
	@NotNull
	private Integer status;
	@Schema(description = "Email Company")
	@NotNull
	@Size(max = 25, min = 4)
	private String email;
	@Schema(description = "Address Company")
	@NotNull
	@Size(max = 50, min = 4)
	private String address;
	@Schema(description = "Phone Company")
	@NotNull
	@Size(max = 13, min = 4)
	private String phone;

	public CompanyCreateDTO() {
		// TODO Auto-generated constructor stub
	}

	public CompanyCreateDTO(@NotNull @Size(max = 25, min = 4) String name, @NotNull MultipartFile logo,
			@NotNull Integer status, @NotNull @Size(max = 25, min = 4) String email,
			@NotNull @Size(max = 50, min = 4) String address, @NotNull @Size(max = 13, min = 4) String phone) {
		super();
		this.name = name;
		this.logo = logo;
		this.status = status;
		this.email = email;
		this.address = address;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MultipartFile getLogo() {
		return logo;
	}

	public void setLogo(MultipartFile logo) {
		this.logo = logo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

}

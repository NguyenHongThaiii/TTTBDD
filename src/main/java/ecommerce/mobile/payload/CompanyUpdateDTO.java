package ecommerce.mobile.payload;

import org.springframework.web.multipart.MultipartFile;

import ecommerce.mobile.annotation.CheckStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "CompanyUpdateDTO Model Information")
public class CompanyUpdateDTO {
	@Schema(description = "Id Company")
	@NotNull
	private Integer id;
	@Schema(description = "Name Company")
	@Size(max = 25, min = 4)
	private String name;
	@Schema(description = "Logo Company")
	private MultipartFile logo;
	@Schema(description = "Status Company (0,1,2)")
	@CheckStatus(allowedValues = { 0, 1, 2 })
	private Integer status;
	@Schema(description = "Email Company")
	@Size(max = 25, min = 4)
	private String email;
	@Schema(description = "Address Company")
	@Size(max = 50, min = 4)
	private String address;
	@Schema(description = "Phone Company")
	@Size(max = 13, min = 4)
	private String phone;

	public CompanyUpdateDTO() {
		// TODO Auto-generated constructor stub
	}

	public CompanyUpdateDTO(@NotNull Integer id, @Size(max = 25, min = 4) String name, MultipartFile logo,
			Integer status, @NotNull @Size(max = 25, min = 4) String email,
			@NotNull @Size(max = 50, min = 4) String address, @NotNull @Size(max = 13, min = 4) String phone) {
		super();
		this.id = id;
		this.name = name;
		this.logo = logo;
		this.status = status;
		this.email = email;
		this.address = address;
		this.phone = phone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

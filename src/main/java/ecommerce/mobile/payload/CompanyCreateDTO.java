package ecommerce.mobile.payload;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;

public class CompanyCreateDTO {
	@NotNull
	private String name;

	@NotNull
	private MultipartFile logo;

	@NotNull
	private Integer status;

	public CompanyCreateDTO() {
		// TODO Auto-generated constructor stub
	}

	public CompanyCreateDTO(@NotNull String name, @NotNull MultipartFile logo, @NotNull Integer status) {
		super();
		this.name = name;
		this.logo = logo;
		this.status = status;
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

}

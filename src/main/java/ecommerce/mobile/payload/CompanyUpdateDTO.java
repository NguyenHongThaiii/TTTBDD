package ecommerce.mobile.payload;

import org.springframework.web.multipart.MultipartFile;

public class CompanyUpdateDTO {
	private Integer id;
	private String name;

	private MultipartFile logo;
	private Integer status;
	public CompanyUpdateDTO() {
		// TODO Auto-generated constructor stub
	}

	public CompanyUpdateDTO(Integer id, String name, MultipartFile logo, Integer status) {
		super();
		this.id = id;
		this.name = name;
		this.logo = logo;
		this.status = status;
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

}

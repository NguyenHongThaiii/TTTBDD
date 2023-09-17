package ecommerce.mobile.payload;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class ProductUpdateDTO {
	private int id;
	private String name;
	@Min(value = 0, message = "status should not be less than 0")
	@Max(value = 2, message = "status should not be greater than 1")
	private Integer status;
	private Integer price;
	private List<MultipartFile> listImageFile = new ArrayList<>();
	private String description;
	private Integer type;

	public ProductUpdateDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProductUpdateDTO(int id, String name,
			@Min(value = 0, message = "status should not be less than 0") @Max(value = 2, message = "status should not be greater than 1") Integer status,
			Integer price, List<MultipartFile> listImageFile, String description, Integer type) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.price = price;
		this.listImageFile = listImageFile;
		this.description = description;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public List<MultipartFile> getListImageFile() {
		return listImageFile;
	}

	public void setListImageFile(List<MultipartFile> listImageFile) {
		this.listImageFile = listImageFile;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}

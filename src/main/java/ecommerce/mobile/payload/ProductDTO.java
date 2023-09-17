package ecommerce.mobile.payload;

import java.util.ArrayList;
import java.util.List;

public class ProductDTO {
	private Integer id;
	private String name;
	private int price;
	private List<String> listImage = new ArrayList<>();
	private String description;
	private Integer type;
	private Integer companyId;
	private Integer status;
	private String createdAt;
	private String updatedAt;
	private Integer stock;

	public ProductDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProductDTO(Integer id, String name, int price, List<String> listImage, String description, Integer type,
			Integer companyId, Integer status, String createdAt, String updatedAt, Integer stock) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.listImage = listImage;
		this.description = description;
		this.type = type;
		this.companyId = companyId;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.stock = stock;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public List<String> getListImage() {
		return listImage;
	}

	public void setListImage(List<String> listImage) {
		this.listImage = listImage;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

}

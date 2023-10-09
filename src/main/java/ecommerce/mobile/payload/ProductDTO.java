package ecommerce.mobile.payload;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "ProductDTO Model Information")
public class ProductDTO {
	@Schema(description = "Id Product")
	private Integer id;
	@Schema(description = "Name Product")
	private String name;
	@Schema(description = "Price Product")
	private int price;
	@Schema(description = "List Iamges Product")
	private List<String> listImage = new ArrayList<>();
	@Schema(description = "Desciption Product")
	private String description;
	@Schema(description = "Type Product")
	private Integer type;
	@Schema(description = "Name Company")
	private String nameCompany;
	@Schema(description = "Status Product (0,1,2)")
	private Integer status;
	@Schema(description = "Created At Product")
	private String createdAt;
	@Schema(description = "Updated At Product")
	private String updatedAt;
	@Schema(description = "Stock Product")
	private Integer stock;

	public ProductDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProductDTO(Integer id, String name, int price, List<String> listImage, String description, Integer type,
			String nameCompany, Integer status, String createdAt, String updatedAt, Integer stock) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.listImage = listImage;
		this.description = description;
		this.type = type;
		this.nameCompany = nameCompany;
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

	public String getNameCompany() {
		return nameCompany;
	}

	public void setNameCompany(String nameCompany) {
		this.nameCompany = nameCompany;
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

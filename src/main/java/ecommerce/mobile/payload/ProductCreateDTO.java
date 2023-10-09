package ecommerce.mobile.payload;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ecommerce.mobile.annotation.CheckStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
@Schema(description = "ProductCreateDTO Model Information")
public class ProductCreateDTO {
	@Schema(description = "Name Product")
	@NotNull
	private String name;
	@Schema(description = "Status Product (0,1,2)")
	@CheckStatus(allowedValues = { 0, 1, 2 })
	private Integer status;
	@Schema(description = "Price Product")
	@NotNull
	private Integer price;
	@Schema(description = "List Images Product")
	@NotEmpty(message = "listImageFile list cannot be empty")
	private List<MultipartFile> listImageFile = new ArrayList<>();
	@Schema(description = "Name Company")
	@NotNull
	private String companyName;
	@Schema(description = "Description Product")
	@NotNull
	private String description;
	@Schema(description = "Type Product")
	@NotNull
	private Integer type;

	public ProductCreateDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProductCreateDTO(@NotNull String name,
			@Min(value = 0, message = "status should not be less than 0") @Max(value = 2, message = "status should not be greater than 1") Integer status,
			@NotNull Integer price,
			@NotEmpty(message = "listImageFile list cannot be empty") List<MultipartFile> listImageFile,
			@NotNull String companyName, @NotNull String description, @NotNull Integer type) {
		super();
		this.name = name;
		this.status = status;
		this.price = price;
		this.listImageFile = listImageFile;
		this.companyName = companyName;
		this.description = description;
		this.type = type;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

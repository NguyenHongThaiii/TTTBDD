package ecommerce.mobile.payload;

import jakarta.validation.constraints.NotNull;

public class ProductDeleteDTO {
	@NotNull
	Integer productId;

	public ProductDeleteDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProductDeleteDTO(@NotNull Integer productId) {
		super();
		this.productId = productId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

}

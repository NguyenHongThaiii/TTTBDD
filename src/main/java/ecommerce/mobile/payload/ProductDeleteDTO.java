package ecommerce.mobile.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
@Schema(description = "ProductDeleteDTO Model Information")
public class ProductDeleteDTO {
	@Schema(description = "Id Product")
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

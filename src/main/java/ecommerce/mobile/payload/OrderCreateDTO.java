package ecommerce.mobile.payload;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
@Schema(description = "OrderCreateDTO Model Information")
public class OrderCreateDTO {
	@Schema(description = "Product Id")
	@NotNull
	private Integer productId;
	@Schema(description = "Quantity Order")
	@NotNull
	private Integer quantity;

	public OrderCreateDTO() {
		// TODO Auto-generated constructor stub
	}

	public OrderCreateDTO(Integer productId, Integer quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}

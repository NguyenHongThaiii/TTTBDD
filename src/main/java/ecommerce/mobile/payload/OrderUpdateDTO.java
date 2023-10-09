package ecommerce.mobile.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
@Schema(description = "OrderUpdateDTO Model Information")
public class OrderUpdateDTO {
	@Schema(description = "Id Order")
	@NotNull
	private Integer id;
	@Schema(description = "Quantity Order")
	@NotNull
	private Integer quantity;

	public OrderUpdateDTO() {
		// TODO Auto-generated constructor stub
	}

	public OrderUpdateDTO(Integer id, Integer quantity) {
		super();
		this.id = id;
		this.quantity = quantity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}

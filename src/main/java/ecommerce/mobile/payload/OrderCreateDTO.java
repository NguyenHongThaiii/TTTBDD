package ecommerce.mobile.payload;

public class OrderCreateDTO {
	private Integer productId;
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

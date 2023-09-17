package ecommerce.mobile.payload;

public class OrderUpdateDTO {
	private Integer id;
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

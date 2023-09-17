package ecommerce.mobile.payload;

public class OrderDTO {
	private Integer id;
	private Integer status;
	private String createdAt;
	private String updatedAt;

	private Integer invoiceId;
	private Integer productId;
	private Integer userId;
	private Integer quantity;
	private String companyName;

	public OrderDTO() {
		// TODO Auto-generated constructor stub
	}

	public OrderDTO(Integer id, Integer status, String createdAt, String updatedAt, Integer invoiceId,
			Integer productId, Integer userId, Integer quantity, String companyName) {
		super();
		this.id = id;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.invoiceId = invoiceId;
		this.productId = productId;
		this.userId = userId;
		this.quantity = quantity;
		this.companyName = companyName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}

package ecommerce.mobile.payload;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "OrderDTO Model Information")
public class OrderDTO {
	@Schema(description = "Id Order")
	private Integer id;
	@Schema(description = "Status Order (0,1,2)")
	private Integer status;
	@Schema(description = "Created AT Order")
	private String createdAt;
	@Schema(description = "Updated At Order")
	private String updatedAt;
	@Schema(description = "Id Invoice ")
	private Integer invoiceId;
	@Schema(description = "Id Product")
	private Integer productId;
	@Schema(description = "Id User")
	private Integer userId;
	@Schema(description = "Id Guest")
	private Integer guestId;
	@Schema(description = "Quantity Order")
	private Integer quantity;
	@Schema(description = "Name Company")
	private String companyName;

	public OrderDTO() {
		// TODO Auto-generated constructor stub
	}

	public OrderDTO(Integer id, Integer status, String createdAt, String updatedAt, Integer invoiceId,
			Integer productId, Integer userId, Integer guestId, Integer quantity, String companyName) {
		super();
		this.id = id;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.invoiceId = invoiceId;
		this.productId = productId;
		this.userId = userId;
		this.guestId = guestId;
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

	public Integer getGuestId() {
		return guestId;
	}

	public void setGuestId(Integer guestId) {
		this.guestId = guestId;
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

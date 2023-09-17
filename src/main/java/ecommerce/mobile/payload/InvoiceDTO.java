package ecommerce.mobile.payload;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDTO {
	private Integer id;
	private Integer status;
	private String createdAt;
	private String updatedAt;
	private Integer quantity;
	private String note;
	private Boolean isPaid;
	private List<OrderDTO> orders = new ArrayList<>();
	private String email;
	private String companyName;
	private String method;

	public InvoiceDTO() {
		// TODO Auto-generated constructor stub
	}

	public InvoiceDTO(Integer id, Integer status, String createdAt, String updatedAt, Integer quantity, String note,
			Boolean isPaid, List<OrderDTO> orders, String email, String companyName, String method) {
		super();
		this.id = id;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.quantity = quantity;
		this.note = note;
		this.isPaid = isPaid;
		this.orders = orders;
		this.email = email;
		this.companyName = companyName;
		this.method = method;
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

	public List<OrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDTO> orders) {
		this.orders = orders;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}

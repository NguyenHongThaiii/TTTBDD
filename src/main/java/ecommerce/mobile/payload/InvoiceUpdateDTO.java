package ecommerce.mobile.payload;

public class InvoiceUpdateDTO {
	private Integer id;
	private Integer status;
	private String note;
	private Boolean isPaid;
	private String listOrders;
	private String method;

	public InvoiceUpdateDTO() {
		// TODO Auto-generated constructor stub
	}

	public InvoiceUpdateDTO(Integer id, Integer status, String note, Boolean isPaid, String listOrders, String method) {
		super();
		this.id = id;
		this.status = status;
		this.note = note;
		this.isPaid = isPaid;
		this.listOrders = listOrders;
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

	public String getListOrders() {
		return listOrders;
	}

	public void setListOrders(String listOrders) {
		this.listOrders = listOrders;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}

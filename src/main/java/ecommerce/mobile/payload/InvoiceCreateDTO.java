package ecommerce.mobile.payload;

public class InvoiceCreateDTO {
	private String email;
	private String note;
	private Boolean isPaid;
	private String listOrders;
	private String method;
	private String companyName;

	public InvoiceCreateDTO() {
		// TODO Auto-generated constructor stub
	}

	public InvoiceCreateDTO(String email, String note, Boolean isPaid, String listOrders, String method,
			String companyName) {
		super();
		this.email = email;
		this.note = note;
		this.isPaid = isPaid;
		this.listOrders = listOrders;
		this.method = method;
		this.companyName = companyName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}

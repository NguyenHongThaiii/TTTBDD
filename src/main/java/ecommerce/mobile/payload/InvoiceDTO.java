package ecommerce.mobile.payload;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "InvoiceDTO Model Information")
public class InvoiceDTO {
	@Schema(description = "Id Invoice")
	private Integer id;
	@Schema(description = "Status Invoice (0,1,2)")
	private Integer status;
	@Schema(description = "Created At Invoice")
	private String createdAt;
	@Schema(description = "Udpated At Invoice")
	private String updatedAt;
	@Schema(description = "Total Quantity Invoice")
	private Integer quantity;
	@Schema(description = "Note Invoice")
	private String note;
	@Schema(description = "Is Paid (true or false)")
	private Boolean isPaid;
	@Schema(description = "List Order DTO")
	private List<OrderDTO> orders = new ArrayList<>();
	@Schema(description = "Email User")
	private String emailUser;
	@Schema(description = "Email Guest")
	private String emailGuest;
	@Schema(description = "Name Company")
	private String companyName;
	@Schema(description = "Method Payment (credit or cash)")
	private String method;
	@Schema(description = "Key Image QR")
	private String key;
	@Schema(description = "Tax Invoice")
	private Float tax;
	@Schema(description = "Total Price")
	private Double totalPrice;
	@Schema(description = "Address Invoice")
	private String address;
	@Schema(description = "Image Invoice")
	private String image;

	public InvoiceDTO() {
		// TODO Auto-generated constructor stub
	}

	public InvoiceDTO(Integer id, Integer status, String createdAt, String updatedAt, Integer quantity, String note,
			Boolean isPaid, List<OrderDTO> orders, String emailUser, String emailGuest, String companyName,
			String method, String key, Float tax, Double totalPrice, String address, String image) {
		super();
		this.id = id;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.quantity = quantity;
		this.note = note;
		this.isPaid = isPaid;
		this.orders = orders;
		this.emailUser = emailUser;
		this.emailGuest = emailGuest;
		this.companyName = companyName;
		this.method = method;
		this.key = key;
		this.tax = tax;
		this.totalPrice = totalPrice;
		this.address = address;
		this.image = image;
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

	public String getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

	public String getEmailGuest() {
		return emailGuest;
	}

	public void setEmailGuest(String emailGuest) {
		this.emailGuest = emailGuest;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Float getTax() {
		return tax;
	}

	public void setTax(Float tax) {
		this.tax = tax;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}

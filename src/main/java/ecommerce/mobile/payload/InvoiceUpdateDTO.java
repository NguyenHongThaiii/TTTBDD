package ecommerce.mobile.payload;

import ecommerce.mobile.annotation.CheckStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "InvoiceUpdateDTO Model Information")
public class InvoiceUpdateDTO {
	@Schema(description = "Id Invoice")
	@NotNull
	private Integer id;
	@Schema(description = "Status Invoice (0,1,2)")
	@CheckStatus(allowedValues = { 0, 1, 2 })
	private Integer status;
	@Schema(description = "Note Invoice")
	private String note;
	@Schema(description = "Is Paid (true or false)")
	private Boolean isPaid;
	@Schema(description = "List Oders Update DTO")
	private String listOrders;
	@Schema(description = "Method Payment (cash or credit)")
	private String method;
	@Schema(description = "Tax Invoice")
	@NotNull
	private Float tax;
	@Schema(description = "Total Price")
	@NotNull
	private Double totalPrice;
	@Schema(description = "Address Invoice")
	private String address;

	public InvoiceUpdateDTO() {
		// TODO Auto-generated constructor stub
	}

	public InvoiceUpdateDTO(@NotNull Integer id, Integer status, String note, Boolean isPaid, String listOrders,
			String method, @NotNull Float tax, @NotNull Double totalPrice, String address) {
		super();
		this.id = id;
		this.status = status;
		this.note = note;
		this.isPaid = isPaid;
		this.listOrders = listOrders;
		this.method = method;
		this.tax = tax;
		this.totalPrice = totalPrice;
		this.address = address;
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

}

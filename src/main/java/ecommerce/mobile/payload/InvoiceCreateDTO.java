package ecommerce.mobile.payload;

import org.springframework.web.multipart.MultipartFile;

import ecommerce.mobile.annotation.ValidFile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Invoice Create DTO Model Information")
public class InvoiceCreateDTO {
	@Schema(description = "Email User")
	@NotNull
	private String emailUser;
	@Schema(description = "Phone Guest")
	@NotNull
	private String phoneGuest;
	@Schema(description = "Note Invoice")
	@NotNull
	private String note;
	@Schema(description = "Is Paid (true or false)")
	@NotNull
	private Boolean isPaid;
	@Schema(description = "List Orders Create DTO (JSON)")
	@NotNull
	private String listOrders;
	@Schema(description = "Method payment (credit or cash)")
	@NotNull
	private String method;
	@Schema(description = "Name Company")
	@NotNull
	private String companyName;
	@Schema(description = "Key QR")
	@NotNull
	private String key;
	@Schema(description = "Tax Invoice")
	@NotNull
	private Float tax;
	@Schema(description = "Total Price")
	@NotNull
	private Double totalPrice;
	@Schema(description = "Address Invoice")
	private String address;
	@Schema(description = "Image Invoice")
	@NotNull
	private MultipartFile image;

	public InvoiceCreateDTO() {
		// TODO Auto-generated constructor stub
	}

	public InvoiceCreateDTO(@NotNull String emailUser, @NotNull String phoneGuest, @NotNull String note,
			@NotNull Boolean isPaid, @NotNull String listOrders, @NotNull String method, @NotNull String companyName,
			@NotNull String key, @NotNull Float tax, @NotNull Double totalPrice, String address, MultipartFile image) {
		super();
		this.emailUser = emailUser;
		this.phoneGuest = phoneGuest;
		this.note = note;
		this.isPaid = isPaid;
		this.listOrders = listOrders;
		this.method = method;
		this.companyName = companyName;
		this.key = key;
		this.tax = tax;
		this.totalPrice = totalPrice;
		this.address = address;
		this.image = image;
	}

	public String getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

	public String getPhoneGuest() {
		return phoneGuest;
	}

	public void setPhoneGuest(String phoneGuest) {
		this.phoneGuest = phoneGuest;
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

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

}

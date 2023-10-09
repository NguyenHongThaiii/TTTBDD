package ecommerce.mobile.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoices")
public class Invoice extends BaseEntity {
	private String note;
	private Boolean isPaid;
	@Column(nullable = false, length = 10)
	private String method;
	@Column(nullable = false, unique = true, name = "qr_key")
	private String key;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "guest_id")
	private User guest;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToOne(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Image qrImage;

	@OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
	private List<Order> orders = new ArrayList<>();

	private Float tax;
	private Double totalPrice;

	public Invoice() {
		// TODO Auto-generated constructor stub
	}

	public Invoice(String note, Boolean isPaid, String method, String key, User guest, User user, Image qrImage,
			List<Order> orders, Float tax, Double totalPrice) {
		super();
		this.note = note;
		this.isPaid = isPaid;
		this.method = method;
		this.key = key;
		this.guest = guest;
		this.user = user;
		this.qrImage = qrImage;
		this.orders = orders;
		this.tax = tax;
		this.totalPrice = totalPrice;
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

	public User getGuest() {
		return guest;
	}

	public void setGuest(User guest) {
		this.guest = guest;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Image getQrImage() {
		return qrImage;
	}

	public void setQrImage(Image qrImage) {
		this.qrImage = qrImage;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
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

}

package ecommerce.mobile.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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
	@JoinColumn(name = "customer_id")
	private Customer customer;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
	private List<Order> orders = new ArrayList<>();

	private Float tax;
	private Double totalPrice;
	private String address;

	@OneToOne(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Image image;

	public Invoice() {
		// TODO Auto-generated constructor stub
	}

	public Invoice(String note, Boolean isPaid, String method, String key, Customer customer, User user,
			List<Order> orders, Float tax, Double totalPrice, String address, Image image) {
		super();
		this.note = note;
		this.isPaid = isPaid;
		this.method = method;
		this.key = key;
		this.customer = customer;
		this.user = user;
		this.orders = orders;
		this.tax = tax;
		this.totalPrice = totalPrice;
		this.address = address;
		this.image = image;
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}

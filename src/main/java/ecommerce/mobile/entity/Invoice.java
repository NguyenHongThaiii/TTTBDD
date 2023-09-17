package ecommerce.mobile.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoices")
public class Invoice extends BaseEntity {
	private String note;
	private Boolean isPaid;
	private String method;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
	private List<Order> orders = new ArrayList<>();

	public Invoice() {
		// TODO Auto-generated constructor stub
	}

	public Invoice(String note, Boolean isPaid, String method, User user, List<Order> orders) {
		super();
		this.note = note;
		this.isPaid = isPaid;
		this.method = method;
		this.user = user;
		this.orders = orders;
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

}

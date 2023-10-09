package ecommerce.mobile.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
	@Column(nullable = false, unique = true)
	private String phone;
	private String name;
	@Column(nullable = false, unique = true)
	private String email;

	public Customer() {
		// TODO Auto-generated constructor stub
	}

	public Customer(String phone, String name, String email) {
		super();
		this.phone = phone;
		this.name = name;
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

package ecommerce.mobile.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "companies")
public class Company extends BaseEntityNotCompany {
	@Column(unique = true, nullable = false, length = 25)
	private String name;
	@OneToOne(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Logo logo;
	@Column(unique = true, nullable = false)
	private String keyCompany;
	private String address;
	@Column(unique = true, nullable = false)
	private String phone;
	@Column(unique = true, nullable = false)
	private String email;

	public Company() {
		// TODO Auto-generated constructor stub
	}

	public Company(String name, Logo logo, String keyCompany, String address, String phone, String email) {
		super();
		this.name = name;
		this.logo = logo;
		this.keyCompany = keyCompany;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Logo getLogo() {
		return logo;
	}

	public void setLogo(Logo logo) {
		this.logo = logo;
	}

	public String getKeyCompany() {
		return keyCompany;
	}

	public void setKeyCompany(String keyCompany) {
		this.keyCompany = keyCompany;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

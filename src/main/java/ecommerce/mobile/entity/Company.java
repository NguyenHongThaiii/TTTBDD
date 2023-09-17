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
	@Column(unique = true, nullable = false)
	private String name;

	@OneToOne(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Logo logo;
	private String keyCompany;

	public Company() {
		// TODO Auto-generated constructor stub
	}

	public Company(String name, Logo logo, String keyCompany) {
		super();
		this.name = name;
		this.logo = logo;
		this.keyCompany = keyCompany;
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

}

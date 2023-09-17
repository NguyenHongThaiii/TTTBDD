package ecommerce.mobile.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "logos")
public class Logo extends BaseEntityNotCompany {

	@OneToOne(mappedBy = "logo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Image logo;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Company company;

	public Logo() {
		// TODO Auto-generated constructor stub
	}

	public Logo(Image logo, Company company) {
		super();
		this.logo = logo;
		this.company = company;
	}

	public Image getLogo() {
		return logo;
	}

	public void setLogo(Image logo) {
		this.logo = logo;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}

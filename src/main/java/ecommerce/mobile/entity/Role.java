package ecommerce.mobile.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role extends BaseEntityNotCompany {
	private String name;

	public Role() {
		// TODO Auto-generated constructor stub
	}

	public Role(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

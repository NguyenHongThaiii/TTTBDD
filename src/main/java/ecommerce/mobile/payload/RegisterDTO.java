package ecommerce.mobile.payload;

import jakarta.validation.constraints.NotNull;

public class RegisterDTO {
	@NotNull
	private String name;

	@NotNull
	private String email;

	@NotNull
	private String password;
	@NotNull
	private String gender;
	@NotNull
	private String phone;

	@NotNull
	private String address;

	@NotNull
	private String role;
	@NotNull
	private String companyKey;

	public RegisterDTO() {
		// TODO Auto-generated constructor stub
	}

	public RegisterDTO(@NotNull String name, @NotNull String email, @NotNull String password, @NotNull String gender,
			@NotNull String phone, @NotNull String address, @NotNull String role, @NotNull String companyKey) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.phone = phone;
		this.address = address;
		this.role = role;
		this.companyKey = companyKey;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCompanyKey() {
		return companyKey;
	}

	public void setCompanyKey(String companyKey) {
		this.companyKey = companyKey;
	}

}

package ecommerce.mobile.payload;

import jakarta.validation.constraints.NotNull;

public class ForgotPasswordDTO {
	
	@NotNull
	private String email;

	public ForgotPasswordDTO(@NotNull String email) {
		super();
		this.email = email;
	}

	public ForgotPasswordDTO() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

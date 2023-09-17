package ecommerce.mobile.payload;

import jakarta.validation.constraints.NotNull;
public class ResetPasswordDTO {
	@NotNull
	private String email;

	@NotNull
	private String password;

	@NotNull
	private String retypePassword;

	public ResetPasswordDTO(@NotNull String email, @NotNull String password, @NotNull String retypePassword) {
		super();
		this.email = email;
		this.password = password;
		this.retypePassword = retypePassword;
	}

	public ResetPasswordDTO() {
		// TODO Auto-generated constructor stub
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

	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}

}

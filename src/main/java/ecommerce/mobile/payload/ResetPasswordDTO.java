package ecommerce.mobile.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "ResetPasswordDTO Model Information")
public class ResetPasswordDTO {
	@Schema(description = "Email User")
	@NotNull
	private String email;

	@Schema(description = "Password User")
	@NotNull
	private String password;
	@Schema(description = "Password Retype")
	@NotNull
	private String retypePassword;

	public ResetPasswordDTO() {
		// TODO Auto-generated constructor stub
	}

	public ResetPasswordDTO(@NotNull String email, @NotNull String password, @NotNull String retypePassword) {
		super();
		this.email = email;
		this.password = password;
		this.retypePassword = retypePassword;
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

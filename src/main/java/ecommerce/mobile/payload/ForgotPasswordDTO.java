package ecommerce.mobile.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
@Schema(description = "ForgotPasswordDTO Model Information")
public class ForgotPasswordDTO {
	@Schema(description = "Email User")
	@NotNull
	@Email
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

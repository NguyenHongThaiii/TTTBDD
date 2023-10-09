package ecommerce.mobile.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(description = "LoginDTO Model Information")
public class LoginDTO {
	@Schema(description = "Email User")
	@NotEmpty(message = "Name should not be null or empty")
	@Size(min = 6, max = 30)
	private String email;

	@Schema(description = "Password User")
	@NotEmpty(message = "Name should not be null or empty")
	@Size(min = 6, max = 20)
	private String password;

	public LoginDTO(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public LoginDTO() {
		super();
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

}

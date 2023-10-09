package ecommerce.mobile.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
@Schema(description = "ValidateOtpDTO Model Information")
public class ValidateOtpDTO {
	@Schema(description = "Email  User")
	@NotNull
	private String email;
	@Schema(description = "Otp Validate")
	@NotNull
	private String otp;

	public ValidateOtpDTO(@NotNull String email, @NotNull String otp) {
		super();

		this.email = email;
		this.otp = otp;
	}

	public ValidateOtpDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

}

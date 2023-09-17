package ecommerce.mobile.payload;

import jakarta.validation.constraints.NotNull;

public class ValidateOtpDTO {

	@NotNull
	private String email;

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

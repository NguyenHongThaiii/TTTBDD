package ecommerce.mobile.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Change Password Model Information")
public class ChangePassworDTO extends ResetPasswordDTO {
	@NotNull
	@Schema(description = "Old Password")
	private String oldPassword;

	public ChangePassworDTO() {
		// TODO Auto-generated constructor stub
	}

	public ChangePassworDTO(@NotNull String oldPassword) {
		super();
		this.oldPassword = oldPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

}

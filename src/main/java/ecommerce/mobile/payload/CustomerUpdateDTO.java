package ecommerce.mobile.payload;

import ecommerce.mobile.annotation.CheckStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class CustomerUpdateDTO {
	@Schema(description = "Id Customer")
	private Integer id;
	@Schema(description = "Status Customer (0,1,2)")
	@CheckStatus(allowedValues = { 0, 1, 2 })
	private Integer status;
	@Schema(description = "Name Customer")
	private String name;
	@Schema(description = "Phone Customer")
	private String phone;
	@Schema(description = "Email Customer")
	private String email;

	public CustomerUpdateDTO() {
		// TODO Auto-generated constructor stub
	}

	public CustomerUpdateDTO(Integer id, Integer status, String name, String phone, String email) {
		super();
		this.id = id;
		this.status = status;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

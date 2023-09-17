package ecommerce.mobile.payload;

public class BaseEntityDTO {
	private Integer status;

	private Long createdAt;

	private Long updatedAt;

	public BaseEntityDTO() {
		super();
		this.setStatus(1);
	}

	public BaseEntityDTO(Integer status, Long createdAt, Long updatedAt) {
		super();
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public Long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}

}

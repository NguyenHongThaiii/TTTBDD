package ecommerce.mobile.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hibernate.annotations.Check;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotNull;

@MappedSuperclass
public class BaseEntityNotCompany {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@Check(constraints = "status IN (0, 1, 2)")
	private Integer status = 1;

	private String createdAt;

	private String updatedAt;

	public BaseEntityNotCompany() {
		// TODO Auto-generated constructor stub
	}

	@PrePersist
	public void prePersist() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy:HH:mm:ss");
		this.createdAt = LocalDateTime.now().format(formatter);
		this.updatedAt = LocalDateTime.now().format(formatter);
	}

	@PreUpdate
	public void preUpdate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy:HH:mm:ss");
		this.updatedAt = LocalDateTime.now().format(formatter);
	}

	public BaseEntityNotCompany(Integer id, @NotNull Integer status, String createdAt, String updatedAt) {
		super();
		this.id = id;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

}

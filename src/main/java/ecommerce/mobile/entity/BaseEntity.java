package ecommerce.mobile.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hibernate.annotations.Check;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotNull;

@MappedSuperclass
public class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@Check(constraints = "status IN (0, 1, 2)")
	private Integer status = 1;

	private String createdAt;

	private String updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	private Company company;

	public BaseEntity() {
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

	public BaseEntity(Integer id, @NotNull Integer status, String createdAt, String updatedAt, Company company) {
		super();
		this.id = id;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.company = company;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}

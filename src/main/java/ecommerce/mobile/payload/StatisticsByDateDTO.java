package ecommerce.mobile.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Statistics By Date DTO Model Information")
public class StatisticsByDateDTO {
	@Schema(description = "Date Created Invoice")
	@NotNull
	private String date;
	@Schema(description = "Company Id")
	@NotNull
	private Integer companyId;

	public StatisticsByDateDTO() {
		// TODO Auto-generated constructor stub
	}

	public StatisticsByDateDTO(@NotNull String date, @NotNull Integer companyId) {
		super();
		this.date = date;
		this.companyId = companyId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

}

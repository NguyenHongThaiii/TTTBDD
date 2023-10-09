package ecommerce.mobile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.mobile.payload.StatisticsByDateDTO;
import ecommerce.mobile.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Statistics REST APIs Invoices")
@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {
	private StatisticsService statisticsSerices;

	public StatisticsController(StatisticsService statisticsSerices) {
		super();
		this.statisticsSerices = statisticsSerices;
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Statistics Invoice By Date")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@PostMapping("/salesByDate")
	public ResponseEntity<Double> statisticsSalesByDate(@Valid @RequestBody StatisticsByDateDTO sbd) {
		Double total = statisticsSerices.statisticsSalesByDate(sbd);
		return ResponseEntity.ok(total);
	}
}

package ecommerce.mobile.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.mobile.payload.LoggerDTO;
import ecommerce.mobile.service.LoggerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/loggers")
@Tag(name = "CRUD REST APIs Loggers")

public class LoggerController {
	private LoggerService loggerService;

	public LoggerController(LoggerService loggerService) {
		super();
		this.loggerService = loggerService;
	}

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Get all loggers")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PreAuthorize("hasAnyRole('ADMIN','MOD')")
	@GetMapping("")
	public ResponseEntity<List<LoggerDTO>> getAllLoggers(@RequestParam(defaultValue = "20") Integer limit,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(required = false) Integer status,
			@RequestParam(required = false) Integer userId, @RequestParam(required = false) String companyName,
			@RequestParam(required = false) String method, @RequestParam(required = false) String message,
			@RequestParam(required = false) String agent, @RequestParam(required = false) String result,
			@RequestParam(required = false) String params, @RequestParam(required = false) String body,
			@RequestParam(required = false) String endpoint, @RequestParam(required = false) String createdAt,
			@RequestParam(required = false) String updatedAt, @RequestParam(required = false) String sortBy) {
		List<LoggerDTO> listLoggerDto = loggerService.getAllLoggers(limit, page, status, method, userId, companyName,
				message, agent, result, params, body, endpoint, createdAt, updatedAt, sortBy);
		return ResponseEntity.ok(listLoggerDto);
	}
}

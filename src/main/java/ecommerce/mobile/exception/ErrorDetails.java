package ecommerce.mobile.exception;

public class ErrorDetails {
	private String message;
	private String details;
	private String timestamp;

	public ErrorDetails(String timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}
}

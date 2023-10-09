package ecommerce.mobile.payload;

public class LoggerCreateDTO {
	private String method;
	private Integer userId;
	private String message;
	private String agent;
	private String result;
	private String params;
	private String body;
	private String endpoint;

	public LoggerCreateDTO() {
		// TODO Auto-generated constructor stub
	}

	public LoggerCreateDTO(String method, Integer userId, String message, String agent, String result, String params,
			String body, String endpoint) {
		super();
		this.method = method;
		this.userId = userId;
		this.message = message;
		this.agent = agent;
		this.result = result;
		this.params = params;
		this.body = body;
		this.endpoint = endpoint;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

}

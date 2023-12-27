package ecommerce.mobile.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "loggers")
public class Logger extends BaseEntity {
	private String method;
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	private String message;
	private String agent;
	private String result;
	@Column(length = 500)
	private String params;
	@Column(length = 500)
	private String body;
	private String endpoint;

	public Logger() {
	}

	public Logger(String method, User user, String message, String agent, String result, String params, String body,
			String endpoint) {
		super();
		this.method = method;
		this.user = user;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

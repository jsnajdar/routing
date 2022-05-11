package cz.iens.routing.exception;

import org.springframework.http.HttpStatus;

public class CountriesClientException extends Exception {

	private static final long serialVersionUID = 1L;

	private final String url;
	private final HttpStatus statusCode;
	
	public CountriesClientException(String message, String url, HttpStatus statusCode) {
		super(message);
		this.url = url;
		this.statusCode = statusCode;
	}

	public String getUrl() {
		return url;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}
	
}

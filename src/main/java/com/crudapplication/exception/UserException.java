package com.crudapplication.exception;

public class UserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UserException(String message, Enum httpStatus) {
		super();
		this.message = message;
		this.httpStatus = httpStatus;
	}

	private String message;
	private Enum httpStatus;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Enum getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(Enum httpStatus) {
		this.httpStatus = httpStatus;
	}

}

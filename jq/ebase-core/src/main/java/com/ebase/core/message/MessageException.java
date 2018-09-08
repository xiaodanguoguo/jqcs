package com.ebase.core.message;

public class MessageException extends Exception {
	private static final long serialVersionUID = 1L;

	public MessageException(String message) {
		super(message);
	}

	public MessageException(Throwable cause) {
		super(cause);
	}

}

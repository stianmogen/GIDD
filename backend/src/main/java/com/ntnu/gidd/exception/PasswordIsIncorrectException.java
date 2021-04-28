package com.ntnu.gidd.exception;

public class PasswordIsIncorrectException extends RuntimeException {

	public static final String DEFAULT_MESSAGE = "Password is incorrect";

	public PasswordIsIncorrectException(String error) {
		super(error);
	}

	public PasswordIsIncorrectException() {
		super(DEFAULT_MESSAGE);
	}
}


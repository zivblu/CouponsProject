package com.example.demo.exceptions;

@SuppressWarnings("serial")
public class CompanyExistException extends RuntimeException {

	public CompanyExistException (String message) {
			super(message);
	}
}

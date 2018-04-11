package com.example.demo.exceptions;

@SuppressWarnings("serial")
public class CompanyNotExistException extends RuntimeException {
	
	public CompanyNotExistException (String message) {
			super(message);
	}

}

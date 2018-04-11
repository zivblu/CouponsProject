package com.example.demo.exceptions;

@SuppressWarnings("serial")
public class CustomerExistException extends RuntimeException {

	public CustomerExistException (String message) {
		super(message);
	}
	
}

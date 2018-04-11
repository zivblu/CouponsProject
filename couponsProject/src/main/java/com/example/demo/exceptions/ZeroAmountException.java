package com.example.demo.exceptions;

@SuppressWarnings("serial")
public class ZeroAmountException extends RuntimeException {

	public ZeroAmountException (String message) {
		super(message);
	}
}

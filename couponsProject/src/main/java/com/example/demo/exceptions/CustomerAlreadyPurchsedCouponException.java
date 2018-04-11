package com.example.demo.exceptions;

@SuppressWarnings("serial")
public class CustomerAlreadyPurchsedCouponException extends RuntimeException {

	public CustomerAlreadyPurchsedCouponException (String message) {
		super(message);
	}
}

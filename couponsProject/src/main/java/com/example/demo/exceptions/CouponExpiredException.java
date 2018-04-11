package com.example.demo.exceptions;

@SuppressWarnings("serial")
public class CouponExpiredException extends RuntimeException {

	public CouponExpiredException (String message) {
		super(message);
}
}

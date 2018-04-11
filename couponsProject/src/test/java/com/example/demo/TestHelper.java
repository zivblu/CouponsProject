package com.example.demo;

import org.junit.Assert;

import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;

public class TestHelper {

	public static void CompareTwoCompanies(Company companyA, Company companyB)
	{
		Assert.assertEquals(companyA.getCompanyName(), (companyB.getCompanyName()));
		Assert.assertEquals(companyA.getPassword(), (companyB.getPassword()));
		Assert.assertEquals(companyA.getEmail(), (companyB.getEmail()));
	}
	
	public static void CompareTwoCustomers(Customer customerA, Customer customerB)
	{
		Assert.assertEquals(customerA.getCustName(), (customerB.getCustName()));
		Assert.assertEquals(customerA.getPassword(), (customerB.getPassword()));
	}
	
	public static void CompareTwoCoupons(Coupon couponA, Coupon couponB)
	{
		Assert.assertEquals(couponA.getTitle(), (couponB.getTitle()));
		Assert.assertEquals(couponA.getStartDate(), (couponB.getStartDate()));
		Assert.assertEquals(couponA.getEndDate(), (couponB.getEndDate()));
		Assert.assertEquals(couponA.getAmount(), (couponB.getAmount()));
		Assert.assertEquals(couponA.getType(), (couponB.getType()));
		Assert.assertEquals(couponA.getMessage(), (couponB.getMessage()));
		Assert.assertEquals(couponA.getImage(), (couponB.getImage()));
	}

}

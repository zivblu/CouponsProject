package com.example.demo.DBDAO;

import java.util.Collection;

import com.example.demo.common.CouponType;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;

/*
 * Interface for customer 
 */
public interface CustomerDAO {

	/*
	 * Creates new customer
	 * @param cust
	 */
	void createCustomer (Customer cust);
	
	/*
	 * Removes a customer
	 * @param cust
	 */
	void removeCustomer (Customer cust);
	
	/*
	 * Updates customer
	 * @param cust
	 */
	void updateCustomer (Customer cust);
	
	/*
	 * Get customer details by customer id
	 * @param id
	 */
	Customer getCustomer(long id);
	
	/*
	 * Get list of all the customers
	 */
	Collection<Customer> getAllCustomers();
	
	/*
	 * Get list of all the coupons
	 */
	Collection <Coupon> getCoupons ();
	
	/*
	 * Customer login
	 * 
	 * @param custName
	 * @param password
	 */
	boolean login (String custName, String password);
	
	/*
	 * Get a customer by name
	 * 
	 * @param custName
	 */
	Customer getCustomerByCustomerName(String custName);
	
	/*
	 * A coupon is added to customer database
	 * 
	 * @param custId
	 * @param coup
	 */
	void purchaseCoupon(long custId, Coupon coup);
	
	/*
	 * Get list of all Purchased coupons by type
	 * 
	 * @param custId
	 * @param type
	 */
	Collection<Coupon> getAllPurchasedCouponsByType(long custId, CouponType type);
	
	/*
	 * Get list of all Purchased coupons by price
	 * 
	 * @param custId
	 * @param minimumPrice
	 * @param maximumPrice
	 */
	Collection<Coupon> getAllPurchasedCouponsByPrice(long custId, double minimumPrice, double maximumPrice);
	
	/*
	 * Get a coupon by title in customer database
	 * 
	 * @param customerLoggedIn
	 * @param title
	 */
	Coupon findCouponInCustomerDB(long customerLoggedIn, String title);
}

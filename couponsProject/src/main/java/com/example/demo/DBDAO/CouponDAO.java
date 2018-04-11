package com.example.demo.DBDAO;

import java.util.Collection;
import java.util.Date;

import com.example.demo.common.CouponType;
import com.example.demo.entities.Coupon;

/*
 * Interface for coupon 
 */
public interface CouponDAO {
	
	/*
	 * Creates new coupon
	 * @param coup
	 */
	void createCoupon (Coupon coup);
	
	/*
	 * Removes a coupon
	 * @param coup
	 */
	void removeCoupon (Coupon coup);
	
	/*
	 * Updates coupon
	 * @param coup
	 */
	void updateCoupon (Coupon coup);
	
	/*
	 * Get coupon details by coupon Id
	 * @param id
	 */
	Coupon getCoupon (long id);
	
	/*
	 * Get list of all the coupons
	 */
	Collection <Coupon> getAllCoupons ();
	
	
	/*
	 * Get list of all coupons by type
	 * 
	 * @param couponType
	 */
	Collection <Coupon> getCouponByType (CouponType couponType);
	
	/*
	 * Get list of all coupons by price
	 * 
	 * @param price
	 */
	Collection<Coupon> getCouponByPrice(double price);
	
	/*
	 * Get list of all coupons by Date
	 * 
	 * @param endDate
	 */
	Collection<Coupon> getCouponByDate(Date endDate);
	
	/*
	 * Get a coupon by title
	 * 
	 * @param title
	 */
	Coupon getCouponByTitle(String title);
	
	/*
	 * Delete all the expired coupon
	 */
	void deleteCouponsThatExpired();
}

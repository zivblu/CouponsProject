package com.example.demo.DBDAO;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.common.CouponType;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;

public interface CustomerRepo extends CrudRepository<Customer, Long> {

	boolean existsBycustName(String custName);

	Customer findCustomerByCustName(String custName);

	@Query("SELECT coup FROM CUSTOMER cust JOIN cust.coupons AS coup WHERE cust.id=:id AND coup.endDate>:date AND coup.amount>=:amount")
	Collection<Coupon> findIfCustomerCanBuyCoupon(@Param("id") long custId, @Param("date") Date date,  @Param("amount") Integer amount);
	
	@Query("SELECT coup FROM CUSTOMER cust JOIN cust.coupons AS coup WHERE cust.id=:id AND coup.type=:couponType")
	Collection<Coupon> findAllPurchasedCouponsByType(@Param("id") long custId, @Param("couponType") CouponType type);

	@Query("SELECT coup FROM CUSTOMER cust JOIN cust.coupons AS coup WHERE cust.id=:id AND coup.price>=:minPrice AND coup.price<=:maxPrice")
	Collection<Coupon> findAllPurchasedCouponsByPrice(@Param("id") long custId, @Param("minPrice") double minimumPrice, @Param("maxPrice") double maximumPrice);
	
	boolean existsByCustNameAndPassword(String custName, String password);

	@Query("SELECT coup FROM CUSTOMER cust JOIN cust.coupons AS coup WHERE cust.id=:id AND coup.title=:title")
	Coupon findCouponInCustomerDB(@Param("id") long custId, @Param("title") String couponTitle);}

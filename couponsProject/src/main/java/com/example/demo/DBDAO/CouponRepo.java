package com.example.demo.DBDAO;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.common.CouponType;
import com.example.demo.entities.Coupon;

public interface CouponRepo extends CrudRepository<Coupon, Long> {

	boolean existsByTitle(String title);

	boolean existsById(long id);

	Coupon findCouponByTitle(String title);

	Collection<Coupon> findCouponById(long id);

	Collection<Coupon> findCouponByType(CouponType couponType);
	
	@Query ("SELECT c FROM COUPON c where c.price < :price")
	Collection<Coupon> findWherePriceLowerThan(@Param("price")double price);

	@Query ("SELECT c FROM COUPON c where c.endDate > :endDate")
	Collection<Coupon> findWhenCouponExpired(@Param("endDate")Date endDate);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM COUPON c WHERE c.endDate<:endDate")
	void deleteCouponsThatExpired(@Param("endDate")Date endDate);
	
}

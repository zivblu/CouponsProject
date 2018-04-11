package com.example.demo.DBDAO;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.common.CouponType;
import com.example.demo.connection.ConnectionPool;
import com.example.demo.connection.DBConnection;
import com.example.demo.entities.Coupon;
import com.example.demo.exceptions.CouponExistException;
import com.example.demo.exceptions.CouponNotExistException;

@Component
public class CouponDBDAO implements CouponDAO{

	@Autowired
	CouponRepo couponRepo;
	

	@Override
	public void createCoupon(Coupon coup) throws CouponExistException {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		if (couponRepo.existsByTitle(coup.getTitle())) 
		throw new CouponExistException ("Coupon Allready Exists");
		else couponRepo.save(coup);
		ConnectionPool.getInstance().returnUserConenction(dbConnection); 

	}

	@Override
	public void removeCoupon(Coupon coup) throws CouponNotExistException {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		if (couponRepo.existsByTitle(coup.getTitle()))
		couponRepo.delete(coup);
		else throw new CouponNotExistException ("Coupon Not Exists");
		ConnectionPool.getInstance().returnUserConenction(dbConnection); 
	}

	@Override
	public void updateCoupon(Coupon coup) throws CouponNotExistException {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		Coupon coupfromdb = couponRepo.findCouponByTitle(coup.getTitle());
		if (coupfromdb != null)
		{
			coupfromdb.setEndDate(coup.getEndDate());
			coupfromdb.setPrice(coup.getPrice());
			couponRepo.save(coup);
			ConnectionPool.getInstance().returnUserConenction(dbConnection); 
		}
		else throw new CouponNotExistException ("Coupon Not Exists");
	}

	@Override
	public Coupon getCoupon(long id) {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		Coupon coupfromdb = couponRepo.findOne(id);
		if (coupfromdb != null)
		{
			return couponRepo.findOne(id);
		}
		ConnectionPool.getInstance().returnUserConenction(dbConnection); 
		return null;
	}
	
	@Override
	public Collection<Coupon> getAllCoupons() {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		Collection<Coupon> listOfCoupons = (Collection<Coupon>) couponRepo.findAll();
		ConnectionPool.getInstance().returnUserConenction(dbConnection); 
		return listOfCoupons;
	}

	@Override
	public Collection<Coupon> getCouponByType(CouponType couponType) {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		Collection<Coupon> listOfCouponsByType = (Collection<Coupon>) couponRepo.findCouponByType(couponType);
		ConnectionPool.getInstance().returnUserConenction(dbConnection); 
		return listOfCouponsByType;
	}

	@Override
	public Collection<Coupon> getCouponByPrice(double price) {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		Collection<Coupon> listOfCouponsByPrice = (Collection<Coupon>) couponRepo.findWherePriceLowerThan(price);
		ConnectionPool.getInstance().returnUserConenction(dbConnection);
		return listOfCouponsByPrice;
	}

	@Override
	public Collection<Coupon> getCouponByDate(Date endDate) {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		Collection<Coupon> listOfCouponsByDate = (Collection<Coupon>) couponRepo.findWhenCouponExpired(endDate);
		ConnectionPool.getInstance().returnUserConenction(dbConnection);
		return listOfCouponsByDate;
	}

	@Override
	public Coupon getCouponByTitle(String title) {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();

		Coupon coupfromdb = couponRepo.findCouponByTitle(title);
		if (coupfromdb != null)
		{
			return couponRepo.findCouponByTitle(title);
		}
		ConnectionPool.getInstance().returnUserConenction(dbConnection);
		 return null;
	}
	
	@Override
	public void deleteCouponsThatExpired() {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		this.couponRepo.deleteCouponsThatExpired(new Date());
		ConnectionPool.getInstance().returnUserConenction(dbConnection);

	}

}

package com.example.demo.facades;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DBDAO.CouponDBDAO;
import com.example.demo.DBDAO.CustomerDBDAO;
import com.example.demo.common.ClientType;
import com.example.demo.common.CouponType;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;
import com.example.demo.exceptions.CouponExpiredException;
import com.example.demo.exceptions.CustomerAlreadyPurchsedCouponException;
import com.example.demo.exceptions.ZeroAmountException;

@Component
public class CustomerFacade implements CouponClientFacade{
	
	@Autowired
	CustomerDBDAO customerDBDAO;
	@Autowired
	CouponDBDAO couponDBDAO;
	
	private long customerLoggedIn;
	
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
		if (customerDBDAO.login(name, password) == true)
			{
				customerLoggedIn = customerDBDAO.getCustomerByCustomerName(name).getId();
				return this;
			}
		else return null;
	}
	public void PurchaseCoupon(Coupon coup)throws CouponExpiredException, ZeroAmountException, CustomerAlreadyPurchsedCouponException
	{
		Collection<Coupon> Customercoupons = getAllPurchasedCoupons();
		Customer customer = customerDBDAO.getCustomer(customerLoggedIn);
		Coupon DBcoupon  = couponDBDAO.getCouponByTitle(coup.getTitle());
		if (DBcoupon != null)
		{
			if (CouponExpired(DBcoupon.getEndDate())) {
				throw new CouponExpiredException("Coupon Has Expired");
			}
			if (DBcoupon.getAmount() == 0) {
				throw new ZeroAmountException("No More Coupons");
			}
		}
			if (getCouponFromCustomerDB(coup.getTitle()) != null) {
				throw new CustomerAlreadyPurchsedCouponException("Coupon Was Purchsed Before");
		}		
		Customercoupons.add(DBcoupon);
		customer.addCoupon(DBcoupon);
		DBcoupon.setAmount(DBcoupon.getAmount() - 1);
		couponDBDAO.updateCoupon(DBcoupon);
		customer.setCoupons(Customercoupons);
		customerDBDAO.updateCustomer(customer);
	}
	public Collection<Coupon> getAllPurchasedCoupons()
	{
		return customerDBDAO.getCustomer(customerLoggedIn).getCoupons();
	}
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type)
	{
		return customerDBDAO.getAllPurchasedCouponsByType(this.customerLoggedIn, type);
	}
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double minimumPrice, double maximumPrice)
	{
		return customerDBDAO.getAllPurchasedCouponsByPrice(this.customerLoggedIn, minimumPrice, maximumPrice);
	}
	public Coupon getCouponFromCustomerDB(String title)
	{
		return customerDBDAO.findCouponInCustomerDB(this.customerLoggedIn, title);
	}
	private boolean CouponExpired(Date endDate) 
	{
		Date now = Calendar.getInstance().getTime();
		return endDate.before(now);
	}
}

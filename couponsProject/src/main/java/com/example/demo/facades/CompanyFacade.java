package com.example.demo.facades;

import java.util.Collection;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DBDAO.CompanyDBDAO;
import com.example.demo.DBDAO.CouponDBDAO;
import com.example.demo.common.ClientType;
import com.example.demo.common.CouponType;
import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.exceptions.CouponExistException;


@Component
public class CompanyFacade  implements CouponClientFacade {

	@Autowired
	CouponDBDAO couponDBDAO;
	@Autowired
	CompanyDBDAO companyDBDAO;
	
	private long companyLoggedIn;

	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
		if (companyDBDAO.login(name, password) == true)
			{
			companyLoggedIn = companyDBDAO.getCompanyByCompanyName(name).getId();
				return this;
			}
		else return null;
	}
	public void CreateCoupon(Coupon newCoupon) throws CouponExistException {
		Company company = companyDBDAO.getCompany(companyLoggedIn);
		Collection<Coupon> coupons = companyDBDAO.getCompany(companyLoggedIn).getCoupons();
		if (couponDBDAO.getCouponByTitle(newCoupon.getTitle()) != null)
		{
			throw new CouponExistException("Coupon Allready Exists ");
		}
		else
		couponDBDAO.createCoupon(newCoupon);
		coupons.add(newCoupon);
		company.addCoupon(newCoupon);
		company.setCoupons(coupons);
		companyDBDAO.updateCompany(company);
		
	}
	public void RemoveCoupon(Coupon coup) 
	{
		couponDBDAO.removeCoupon(coup);
	}
	public void UpdateCoupon(Coupon coup) 
	{
		couponDBDAO.updateCoupon(coup);
	}
	public Coupon GetCoupon(long id) 
	{
		return couponDBDAO.getCoupon(id);
	}
	public Collection<Coupon> getAllCoupons() 
	{
		return couponDBDAO.getAllCoupons();
	}
	public Collection<Coupon> getCouponByType(CouponType couponType) 
	{
		return couponDBDAO.getCouponByType(couponType);
	}
	public Collection<Coupon> getCouponByPrice(double price) 
	{
		return couponDBDAO.getCouponByPrice(price);
	}
	public Collection<Coupon> getCouponByDate(Date endDate) 
	{
		return couponDBDAO.getCouponByDate(endDate);
	}
	public Coupon getCouponByTitle(String title) 
	{
		return couponDBDAO.getCouponByTitle(title);
	}
}
	

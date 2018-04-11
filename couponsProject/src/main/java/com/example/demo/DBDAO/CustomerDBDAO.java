package com.example.demo.DBDAO;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.common.CouponType;
import com.example.demo.connection.ConnectionPool;
import com.example.demo.connection.DBConnection;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;
import com.example.demo.exceptions.CouponNotExistException;
import com.example.demo.exceptions.CustomerExistException;
import com.example.demo.exceptions.CustomerNotExistException;

@Component
public class CustomerDBDAO implements CustomerDAO{

	@Autowired
	CustomerRepo customerRepo;
	@Autowired
	CouponRepo couponRepo;
	
	@Override
	public void createCustomer(Customer cust) throws CustomerExistException {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		if (customerRepo.existsBycustName(cust.getCustName()))
		throw new CustomerExistException ("Customer Allready Exists");
		else customerRepo.save(cust);
		ConnectionPool.getInstance().returnUserConenction(dbConnection); 

	}

	@Override
	public void removeCustomer(Customer cust) throws CustomerNotExistException {
		if (customerRepo.existsBycustName(cust.getCustName()))
		{
			customerRepo.delete(cust);
		}
		else throw new CustomerNotExistException ("Customer not Exists");
	}

	@Override
	public void updateCustomer(Customer cust) throws CustomerNotExistException {
		Customer custfromdb = customerRepo.findCustomerByCustName(cust.getCustName());
		if (custfromdb != null)
		{
			custfromdb.setPassword(cust.getPassword());
			customerRepo.save(cust);
		}
		else throw new CustomerNotExistException ("Customer not Exists");
	}

	@Override
	public Customer getCustomer(long id) {
		Customer custfromdb = customerRepo.findOne(id);
		if (custfromdb != null)
		{
			return customerRepo.findOne(id);
		}
		else return null;
	}

	@Override
	public Collection<Customer> getAllCustomers() {
		Collection<Customer> listOfAllCustomers = (Collection<Customer>) customerRepo.findAll();
		return listOfAllCustomers;
	}

	@Override
	public Collection<Coupon> getCoupons() {
		return (Collection<Coupon>) couponRepo.findAll();
	}

	@Override
	public boolean login(String custName, String password) {
		if (customerRepo.existsByCustNameAndPassword(custName, password))
		{
			System.out.println("login succsesful, welcome " + custName);
			return true;
		}
		else return false;
	}
	
	@Override
	public Customer getCustomerByCustomerName(String custName) {
		Customer custfromdb = customerRepo.findCustomerByCustName(custName);
		if (custfromdb != null)
		{
			return customerRepo.findCustomerByCustName(custName);
		}
		else return null;
	}
	
	@Override
	public void purchaseCoupon(long custId, Coupon coup) throws CouponNotExistException {
		Collection<Coupon> coupfromdb = customerRepo.findIfCustomerCanBuyCoupon(custId,coup.getEndDate(), coup.getAmount());
		if (coupfromdb != null)
		{
			couponRepo.save(coupfromdb);
		}
		else throw new CouponNotExistException ("Coupon Not Exists");
	}
	
	@Override
	public Collection<Coupon> getAllPurchasedCouponsByType(long custId, CouponType type) {
		Collection<Coupon> listOfPurchasedCouponsByType = customerRepo.findAllPurchasedCouponsByType(custId, type);
		return listOfPurchasedCouponsByType;
	}
	
	@Override
	public Collection<Coupon> getAllPurchasedCouponsByPrice(long custId, double minimumPrice, double maximumPrice) {
		Collection<Coupon> listOfPurchasedCouponsByPrice = customerRepo.findAllPurchasedCouponsByPrice(custId, minimumPrice, maximumPrice);
		return listOfPurchasedCouponsByPrice;
	}
	
	@Override
	public Coupon findCouponInCustomerDB(long customerLoggedIn, String title) {
		Coupon custCoupon = customerRepo.findCouponInCustomerDB(customerLoggedIn, title);
		return custCoupon;
	}

}

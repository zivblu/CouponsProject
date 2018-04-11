package com.example.demo.DBDAO;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.connection.ConnectionPool;
import com.example.demo.connection.DBConnection;
import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.exceptions.CompanyExistException;
import com.example.demo.exceptions.CompanyNotExistException;

@Component
public class CompanyDBDAO implements CompanyDAO {

	@Autowired
	CompanyRepo companyRepo;
	@Autowired
	CouponRepo couponRepo;
	
	@Override
	public void createCompany(Company comp) throws CompanyExistException{
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		if (companyRepo.existsBycompanyName(comp.getCompanyName())) 
		throw new CompanyExistException ("Company Allready Exists");
		else companyRepo.save(comp);
		ConnectionPool.getInstance().returnUserConenction(dbConnection); 

	}

	@Override
	public void removeCompany(Company comp) throws CompanyNotExistException {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		if (companyRepo.existsBycompanyName(comp.getCompanyName()))
		companyRepo.delete(comp);		
		else throw new CompanyNotExistException ("Company Not Exists");	
		ConnectionPool.getInstance().returnUserConenction(dbConnection); 
	}

	@Override
	public void updateCompany(Company comp) throws CompanyNotExistException {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		Company compfromdb = companyRepo.findCompanyById(comp.getId());
		if (compfromdb != null)
		{
			compfromdb.setPassword(comp.getPassword());	
			compfromdb.setEmail(comp.getEmail());
			companyRepo.save(comp);
			ConnectionPool.getInstance().returnUserConenction(dbConnection); 
		}
		else throw new CompanyNotExistException ("Company Not Exists");

	}

	@Override
	public Company getCompany(long id) {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		Company compfromdb = companyRepo.findOne(id);
		if (compfromdb != null)
		{
			return companyRepo.findOne(id);
		}
		ConnectionPool.getInstance().returnUserConenction(dbConnection); 
		return null;

	}

	@Override
	public Collection<Company> getAllCompanies() {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		Collection<Company> listOfAllCompanies = (Collection<Company>) companyRepo.findAll();
		ConnectionPool.getInstance().returnUserConenction(dbConnection); 
		return listOfAllCompanies;
	}

	@Override
	public Collection<Coupon> getCoupons() {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		Collection<Coupon> listOfAllCoupons = (Collection<Coupon>) couponRepo.findAll();
		ConnectionPool.getInstance().returnUserConenction(dbConnection); 
		return listOfAllCoupons;
	}

	@Override
	public boolean login(String companyName, String password) {
		if (companyRepo.existsBycompanyNameAndPassword(companyName, password))
		{
			System.out.println("login succsesful, welcome " + companyName);
			return true;
		}
		else return false;
	}
	
	@Override
	public Company getCompanyByCompanyName(String companyName) {
		DBConnection dbConnection = ConnectionPool.getInstance().getConnection();
		Company compfromdb = companyRepo.findCompanyBycompanyName(companyName);
		if (compfromdb != null)
		{
			return companyRepo.findCompanyBycompanyName(companyName);
		}
		ConnectionPool.getInstance().returnUserConenction(dbConnection); 
		return null;
	}
}

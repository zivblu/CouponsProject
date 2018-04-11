package com.example.demo.DBDAO;

import java.util.Collection;

import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;

/*
 * Interface for company 
 */
public interface CompanyDAO {

	/*
	 * Creates new company
	 * @param company
	 */
	void createCompany (Company comp);
	
	/*
	 * Removes a company
	 * @param company
	 */
	void removeCompany (Company comp);
	
	/*
	 * Updates company
	 * @param company
	 */
	void updateCompany (Company comp);
	
	/*
	 * Get Company details by company id
	 * @param id
	 */
	Company getCompany(long id) ;
	
	/*
	 * Get list of all the companies
	 */
	Collection <Company> getAllCompanies ();
	
	/*
	 * Get list of all coupons
	 */
	Collection <Coupon> getCoupons ();
	
	/*
	 * Company login
	 * 
	 * @param companyName
	 * @param password
	 */
	boolean login (String companyName, String password);
	
	/*
	 * Get a company by name
	 * 
	 * @param companyName
	 */
	Company getCompanyByCompanyName(String companyName);
	
	
}

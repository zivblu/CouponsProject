package com.example.demo.facades;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DBDAO.CompanyDBDAO;
import com.example.demo.DBDAO.CouponDBDAO;
import com.example.demo.DBDAO.CustomerDBDAO;
import com.example.demo.common.ClientType;
import com.example.demo.entities.Company;
import com.example.demo.entities.Customer;

@Component
public class AdminFacade implements CouponClientFacade  {

	@Autowired
	CompanyDBDAO companyDBDAO;
	@Autowired
	CustomerDBDAO customerDBDAO;
	@Autowired
	CouponDBDAO couponDBDAO;

	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
		
		if ((name.equals("admin")) && (password.equals("1234")))
			return this;
		
			else return null;
	}
	public void CreateCompany(Company comp) 
	{
		companyDBDAO.createCompany(comp);
	}
	public void RemoveCompany (Company comp)
	{
		companyDBDAO.removeCompany(comp);
	}
	public void UpdateCompany(Company comp)
	{
		companyDBDAO.updateCompany(comp);
	}
	public Company GetCompany(long id)
	{
		return companyDBDAO.getCompany(id);
	}
	public Company GetCompanyByCompanyName(String companyName)
	{
		return companyDBDAO.getCompanyByCompanyName(companyName);
	}
	public Collection<Company> GetAllCompanies ()
	{
		return companyDBDAO.getAllCompanies();
	}
	public void CreateCustomer (Customer cust) 
	{
		customerDBDAO.createCustomer(cust);
	}
	public void RemoveCustomer (Customer cust)
	{
		customerDBDAO.removeCustomer(cust);
	}
	public void UpdateCustomer (Customer cust)
	{
		customerDBDAO.updateCustomer(cust);
	}
	public Customer GetCustomer (long id)
	{
		return customerDBDAO.getCustomer(id);
	}
	public Collection<Customer> GetAllCustomers ()
	{
		return customerDBDAO.getAllCustomers();
	}
	public Customer GetCustomerByCustomerName(String custName)
	{
		return customerDBDAO.getCustomerByCustomerName(custName);
	}
	
}

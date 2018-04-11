package com.example.demo;

import java.util.Collection;
import java.util.Date;


import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.common.ClientType;
import com.example.demo.common.CouponType;
import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;
import com.example.demo.entry.CouponSystem;
import com.example.demo.exceptions.CompanyExistException;
import com.example.demo.exceptions.CompanyNotExistException;
import com.example.demo.exceptions.CouponExistException;
import com.example.demo.exceptions.CouponExpiredException;
import com.example.demo.exceptions.CouponNotExistException;
import com.example.demo.exceptions.CustomerAlreadyPurchsedCouponException;
import com.example.demo.exceptions.CustomerNotExistException;
import com.example.demo.exceptions.ZeroAmountException;
import com.example.demo.exceptions.CustomerExistException;
import com.example.demo.facades.AdminFacade;
import com.example.demo.facades.CompanyFacade;
import com.example.demo.facades.CustomerFacade;

/*
 *   test methods applied
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)

public class CouponsProjectApplicationTests {
	
	@Autowired
	ApplicationContext ctx;
	
	@Autowired
	private CouponSystem couponSystem;

	@Test
	public void contextLoads() {

	}
	
	
/*
 * 	ADMIN FACADE METHODS CHECK
 */
	
	/*
 	 *  test01_adminFacade_createComany() - checks method createCompany
 	 *  create company in database and assert check if company exist
 	 */
	
	//@Ignore
	@Test
	public void test01_adminFacade_createComany()
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company MessaCompany = new Company("Messa","Messa1234","Messa@Gmail.com");
		Admin.CreateCompany(MessaCompany);
		long compFromdbid = MessaCompany.getId();
		Company compare_to_MessaCompany = Admin.GetCompany(compFromdbid);
		TestHelper.CompareTwoCompanies(MessaCompany, compare_to_MessaCompany);
	}
	
	/*
	 *  test02_adminFacade_CompanyExistException - checks exception CompanyExistException
	 *  create company in database and creates the same company again to check if exception was thrown
	 */
	
	//@Ignore
	@Test(expected = CompanyExistException.class)
	public void test02_adminFacade_CompanyExistException() 
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company TnovaCompany = new Company("Tnova","Tnova1234","Tnova@Gmail.com");
		Admin.CreateCompany(TnovaCompany);
		Company AlsoTnovaCompany = new Company("Tnova","Tnova1234","Tnova@Gmail.com");
		Admin.CreateCompany(AlsoTnovaCompany);
	}
	
	/*
	 *  test03_adminFacade_removeCompany() - checks method removeCompany
	 *  create company in database, removes it, and assert check if company was removed
	 */
	
	//@Ignore
	@Test
	public void test03_adminFacade_removeCompany() 
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company YesCompany = new Company("Yes","Yes1234","Yes@Gmail.com");
		Admin.CreateCompany(YesCompany);
		Company compFromdbid = Admin.GetCompanyByCompanyName("Yes");
		long removedCompany = compFromdbid.getId();
		Admin.RemoveCompany(Admin.GetCompany(removedCompany));
		Assert.assertEquals(Admin.GetCompanyByCompanyName("Yes"), null);
	}
	
	/*
	 *  test04_adminFacade_CompanyNotExistException - checks exception CompanyNotExistException
	 *  create company in database, removes it, try to remove the same company again
	 *  to check if exception was thrown
	 */
	
	//@Ignore
	@Test(expected = CompanyNotExistException.class)
	public void test04_adminFacade_CompanyNotExistException() 
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company HotCompany = new Company("Hot","Hot1234","Hot@Gmail.com");
		Admin.CreateCompany(HotCompany);
		Company compFromdbid = Admin.GetCompanyByCompanyName("Hot");
		Admin.RemoveCompany(compFromdbid);
		Admin.RemoveCompany(compFromdbid);
	}
	
	/*
	 *  test05_adminFacade_updateCompany - checks method updateCompany
	 *  create company in database, updated the company email and password
	 *  and assert check if the email was updated
	 */
	
	//@Ignore
	@Test
	public void test05_adminFacade_updateCompany() 
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company TidharCompany = new Company("Tidhar","Tidhar1234","Tidhar@Gmail.com");
		Admin.CreateCompany(TidharCompany);
		Company compFromdbid = Admin.GetCompanyByCompanyName("Tidhar");
		long updatedCompany = compFromdbid.getId();
		Company TidharCompanyForUpdate = Admin.GetCompany(updatedCompany);
		TidharCompanyForUpdate.setPassword("Tidhar12345");
		TidharCompanyForUpdate.setEmail("Tidhar@Outlook.org.il");
		Admin.UpdateCompany(TidharCompanyForUpdate);
		Assert.assertEquals(Admin.GetCompanyByCompanyName("Tidhar").getEmail(), TidharCompanyForUpdate.getEmail());
	}
	
	/*
	 *  test06_adminFacade_getCompany - checks method getCompany
	 *  create company in database and assert check if the company exist by its id
	 */
	
	//@Ignore
	@Test
	public void test06_adminFacade_getCompany() 
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company GetCompany = new Company("Get","Get1234","Get@Gmail.com");
		Admin.CreateCompany(GetCompany);
		Company compFromdbid = Admin.GetCompanyByCompanyName("Get");
		long companyProperties = compFromdbid.getId();
		Company gotCompany = Admin.GetCompany(companyProperties);
		TestHelper.CompareTwoCompanies(GetCompany, gotCompany);
	}
	
	/*
	 *  test07_adminFacade_getAllCompanies - checks method getAllCompanies
	 *  create 3 companies in database and assert check if the numbers of companies created
	 *  equal to actual number of companies in database
	 */
	
	//@Ignore
	@Test
	public void test07_adminFacade_getAllCompanies() 
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company MacabbiCompany = new Company("Macabi","Macabi1234","Macabi@Gmail.com");
		Admin.CreateCompany(MacabbiCompany);
		Company MegaSportCompany = new Company("MegaSport","MegaSport1234","MegaSport@Gmail.com");
		Admin.CreateCompany(MegaSportCompany);
		Company LametayelCompany = new Company("Lametayel","Lametayel1234","Lametayel@Gmail.com");
		Admin.CreateCompany(LametayelCompany);
		TestHelper.CompareTwoCompanies(MacabbiCompany, Admin.GetCompanyByCompanyName("Macabi"));
		TestHelper.CompareTwoCompanies(MegaSportCompany, Admin.GetCompanyByCompanyName("MegaSport"));
		TestHelper.CompareTwoCompanies(LametayelCompany, Admin.GetCompanyByCompanyName("Lametayel"));
		Collection<Company> companyList = Admin.GetAllCompanies();
		Assert.assertEquals(companyList.size(), 3);
	}
	
	/*
	 *  test08_adminFacade_createCustomer - checks method createCustomer
	 *  create customer in database and assert check if customer exist
	 */
	
	//@Ignore
	@Test
	public void test08_adminFacade_createCustomer() 
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Customer ZivCustomer = new Customer("Ziv", "Ziv1234");
		Admin.CreateCustomer(ZivCustomer);
		long custFromdbid = ZivCustomer.getId();
		Customer compare_to_ZivCustomer = Admin.GetCustomer(custFromdbid);
		TestHelper.CompareTwoCustomers(ZivCustomer, compare_to_ZivCustomer);
	}
	
	/*
 	 *  test09_adminFacade_CustomerExistException - checks exception CustomerExistException
	 *  create customer in database and creates the same customer again to check if exception was thrown
	 */
	
	//@Ignore
	@Test(expected = CustomerExistException.class)
	public void test09_adminFacade_CustomerExistException() 
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Customer BennyCustomer = new Customer("Benny", "Benny1234");
		Admin.CreateCustomer(BennyCustomer);
		Customer AlsoBennyCustomerCustomer = new Customer("Benny", "Benny1234");
		Admin.CreateCustomer(AlsoBennyCustomerCustomer);
	}
	
	/*
	 *  test10_adminFacade_removeCustomer - checks method removeCustomer
	 *  create customer in database, removes it, and assert check if company was removed
	 */
	
	//@Ignore
	@Test
	public void test10_adminFacade_removeCustomer() 
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Customer DanCustomer = new Customer("Dan", "Dan1234");
		Admin.CreateCustomer(DanCustomer);
		Customer custFromdbid = Admin.GetCustomerByCustomerName("Dan");
		long removedCustomer = custFromdbid.getId();
		Admin.RemoveCustomer(Admin.GetCustomer(removedCustomer));
		Assert.assertEquals(Admin.GetCustomerByCustomerName("Dan"), null);
	}
	
	/*
 	 *  test11_adminFacade_CustomerNotExistExceptionn - checks exception CustomerNotExistException
	 *  create customer in database, removes it, try to remove the same customer again
	 *  to check if exception was thrown
	 */
	
	//@Ignore
	@Test(expected = CustomerNotExistException.class)
	public void test11_adminFacade_CustomerNotExistException() 
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Customer BeethovenCustomer = new Customer("Beethoven", "Beethoven1234");
		Admin.CreateCustomer(BeethovenCustomer);
		Customer custFromdbid = Admin.GetCustomerByCustomerName("Beethoven");
		Admin.RemoveCustomer(custFromdbid);
		Admin.RemoveCustomer(custFromdbid);
	}
	
	/*
	 *  test12_adminFacade_updateCustomer - checks method updateCustomer
	 *  create customer in database, updated the customer password
	 *  and assert check if the password was updated
	 */
	
	//@Ignore
	@Test
	public void test12_adminFacade_updateCustomer() 
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Customer MozartCustomer = new Customer("Mozart", "Mozart1234");
		Admin.CreateCustomer(MozartCustomer);
		Customer custFromdbid = Admin.GetCustomerByCustomerName("Mozart");
		long updatedCustomer = custFromdbid.getId();
		Customer MozartCustomerForUpdate = Admin.GetCustomer(updatedCustomer);
		MozartCustomerForUpdate.setPassword("1234Mozart");
		Admin.UpdateCustomer(MozartCustomerForUpdate);
		Assert.assertEquals(Admin.GetCustomerByCustomerName("Mozart").getPassword(), MozartCustomerForUpdate.getPassword());
	}
	
	/*
	 *  test13_adminFacade_getCustomer - checks method getCustomer
	 *  create customer in database and assert check if the customer exist by its id
	 */
	
	//@Ignore
	@Test
	public void test13_adminFacade_getCustomer() 
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Customer BachCustomer = new Customer("Bach", "Bach1234");
		Admin.CreateCustomer(BachCustomer);
		Customer custFromdbid = Admin.GetCustomerByCustomerName("Bach");
		long customerProperties = custFromdbid.getId();
		Customer gotCustomer = Admin.GetCustomer(customerProperties);
		TestHelper.CompareTwoCustomers(BachCustomer, gotCustomer);
	}
	
	/*
	 *  test14_adminFacade_getAllCustomers - checks method getAllCustomers
	 *  create 5 customers in database and assert check if the numbers of customers created
	 *  equal to actual number of customers in database
	 */
	
	//@Ignore
	@Test
	public void test14_adminFacade_getAllCustomers() 
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Customer OrenCustomer = new Customer("Oren", "Oren1234");
		Admin.CreateCustomer(OrenCustomer);
		Customer YuvalCustomer = new Customer("Yuval", "Yuval1234");
		Admin.CreateCustomer(YuvalCustomer);
		Customer NogaCustomer = new Customer("Noga", "Noga1234");
		Admin.CreateCustomer(NogaCustomer);
		Customer InbarCustomer = new Customer("Inbar", "Inbar1234");
		Admin.CreateCustomer(InbarCustomer);
		Customer TalCustomer = new Customer("Tal", "Tal1234");
		Admin.CreateCustomer(TalCustomer);
		TestHelper.CompareTwoCustomers(OrenCustomer, Admin.GetCustomerByCustomerName("Oren"));
		TestHelper.CompareTwoCustomers(YuvalCustomer, Admin.GetCustomerByCustomerName("Yuval"));
		TestHelper.CompareTwoCustomers(NogaCustomer, Admin.GetCustomerByCustomerName("Noga"));
		TestHelper.CompareTwoCustomers(InbarCustomer, Admin.GetCustomerByCustomerName("Inbar"));
		TestHelper.CompareTwoCustomers(TalCustomer, Admin.GetCustomerByCustomerName("Tal"));
		Collection<Customer> customerList = Admin.GetAllCustomers();
		Assert.assertEquals(customerList.size(), 5);
	}
	
/*
 * 	COMPANY FACADE METHODS CHECK
 */
	
	/*
	 *  test15_companyFacade_createCoupon - checks method createCoupon
	 *  create coupon in database and assert check if coupon exist
	 */
	
	//@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void test15_companyFacade_createCoupon() 
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company MessaCompany = new Company("Messa","Messa1234","Messa@Gmail.com");
		Admin.CreateCompany(MessaCompany);
		CompanyFacade Messa = (CompanyFacade) couponSystem.login("Messa", "Messa1234", ClientType.COMPANY);
		Coupon DinnerCoupon = new Coupon("Dinner", new Date(2017-1900, 2, 3), new Date(2019-1900, 6, 7), 5, CouponType.RESTURANS, "Dinner For 150₪", 150, "Image");
		Messa.CreateCoupon(DinnerCoupon);
		long couponFromdbid = DinnerCoupon.getId();
		Coupon compare_to_DinnerCoupon = Messa.GetCoupon(couponFromdbid);
		TestHelper.CompareTwoCoupons(DinnerCoupon, compare_to_DinnerCoupon);
	}
	
	/*
 	 *  test11_adminFacade_CustomerNotExistExceptionn - checks exception CustomerNotExistException
	 *  create coupon in database and creates the same coupon again to check if exception was thrown
	 */
	
	//@Ignore
	@SuppressWarnings("deprecation")
	@Test(expected = CouponExistException.class)
	public void test16_companyFacade_CouponExistException() 
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company YesCompany = new Company("Yes","Yes1234","Yes@Gmail.com");
		Admin.CreateCompany(YesCompany);
		CompanyFacade yes = (CompanyFacade) couponSystem.login("Yes", "Yes1234", ClientType.COMPANY);
		Coupon MadeUpCoupon = new Coupon("Sport", new Date(2017-1900, 4, 13), new Date(2019-1900, 3, 7), 3, CouponType.ELECTRICITY, "Sport Channels for 50₪", 50, "Image");
		yes.CreateCoupon(MadeUpCoupon);
		Coupon AlsoMadeUpCoupon = new Coupon("Sport", new Date(2017-1900, 4, 13), new Date(2019-1900, 3, 7), 3, CouponType.ELECTRICITY, "Sport Channels for 50₪", 50, "Image");
		yes.CreateCoupon(AlsoMadeUpCoupon);
	}
	
	/*
	 *  test17_companyFacade_removeCoupon - checks method removeCoupon
	 *  create coupon in database, removes it, and assert check if coupon was removed
	 */
	
	//@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void test17_companyFacade_removeCoupon() 
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company BBBCompany = new Company("BBB","BBB1234","BBB@Gmail.com");
		Admin.CreateCompany(BBBCompany);
		CompanyFacade BBB = (CompanyFacade) couponSystem.login("BBB", "BBB1234", ClientType.COMPANY);
		Coupon DrinksCoupon = new Coupon("1+1 on Drinks", new Date(2017-1900, 1, 23), new Date(2019-1900, 3, 15), 5, CouponType.RESTURANS, "30₪ EntryFee", 30, "Image");
		BBB.CreateCoupon(DrinksCoupon);
		Coupon couponFromdbid = BBB.getCouponByTitle("1+1 on Drinks");
		long removedCoupon = couponFromdbid.getId();
		BBB.RemoveCoupon(BBB.GetCoupon(removedCoupon));
		Assert.assertEquals(BBB.getCouponByTitle("1+1 on Drinks"), null);
	}
	
	/*
 	 *  test18_companyFacade_CouponNotExistException - checks exception CouponNotExistException
	 *  create coupon in database, removes it, try to remove the same coupon again
	 *  to check if exception was thrown
	 */
	
	//@Ignore
	@SuppressWarnings("deprecation")
	@Test(expected = CouponNotExistException.class)
	public void test18_companyFacade_CouponNotExistException()
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company TempleBarCompany = new Company("TempleBar","TempleBar1234","TempleBar@Gmail.com");
		Admin.CreateCompany(TempleBarCompany);
		CompanyFacade TempleBar = (CompanyFacade) couponSystem.login("TempleBar", "TempleBar1234", ClientType.COMPANY);
		Coupon FreeDrinksCoupon = new Coupon("Drinks For Free", new Date(2017-1900, 6, 22), new Date(2019-1900, 9, 2), 5, CouponType.RESTURANS, "70₪ EntryFee", 70, "Image");
		TempleBar.CreateCoupon(FreeDrinksCoupon);
		Coupon couponFromdbid = TempleBar.getCouponByTitle("Drinks For Free");
		TempleBar.RemoveCoupon(couponFromdbid);
		TempleBar.RemoveCoupon(couponFromdbid);
	}
	
	/*
	 *  test19_companyFacade_updateCoupon - checks method updateCoupon
	 *  create coupon in database, updated the coupon price
	 *  and assert check if the price was updated
	 */
	
	//@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void test19_companyFacade_updateCoupon() 
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company AngusCompany = new Company("Angus","Angus1234","Angus@Gmail.com");
		Admin.CreateCompany(AngusCompany);
		CompanyFacade Angus = (CompanyFacade) couponSystem.login("Angus", "Angus1234", ClientType.COMPANY);
		Coupon SteakCoupon = new Coupon("Steak", new Date(2017-1900, 2, 17), new Date(2019-1900, 1, 27), 10, CouponType.FOOD, "500g Steak for 45₪", 45, "Image");
		Angus.CreateCoupon(SteakCoupon);
		Coupon couponFromdbid = Angus.getCouponByTitle("Steak");
		long updatedCoupon = couponFromdbid.getId();
		Coupon SteakCouponForUpdate = Angus.GetCoupon(updatedCoupon);
		SteakCouponForUpdate.setPrice(55);
		Angus.UpdateCoupon(SteakCouponForUpdate);
		Assert.assertEquals(55, Angus.getCouponByTitle("Steak").getPrice(), 0);
	}
	
	/*
	 *  test20_companyFacade_getCoupon - checks method getCoupon
	 *  create coupon in database and assert check if the coupon exist by its id
	 */
	
	//@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void test20_companyFacade_getCoupon()
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company ElalCompany = new Company("Elal","Elal1234","Elal@Gmail.com");
		Admin.CreateCompany(ElalCompany);
		CompanyFacade Elal = (CompanyFacade) couponSystem.login("Elal", "Elal1234", ClientType.COMPANY);
		Coupon ParisFlightCoupon = new Coupon("Flight To Paris", new Date(2017-1900, 8, 3), new Date(2019-1900, 5, 13), 5, CouponType.TRAVELLING, "299₪ Flight to Paris", 299, "Image");
		Elal.CreateCoupon(ParisFlightCoupon);
		Coupon couponFromdbid = Elal.getCouponByTitle("Flight To Paris");
		long couponProperties = couponFromdbid.getId();
		Coupon gotCoupon = Elal.GetCoupon(couponProperties);
		TestHelper.CompareTwoCoupons(ParisFlightCoupon, gotCoupon);
	}
	
	/*
	 *  test21_companyFacade_getAllCoupons - checks method getAllCoupons
	 *  create 2 coupons in database and assert check if the numbers of coupons created
	 *  equal to actual number of coupons in database
	 */
	
	//@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void test21_companyFacade_getAllCoupons()
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company ALMCompany = new Company("ALM","ALM1234","ALM@Gmail.com");
		Admin.CreateCompany(ALMCompany);
		CompanyFacade ALM = (CompanyFacade) couponSystem.login("ALM", "ALM1234", ClientType.COMPANY);
		Coupon HeadphonesCoupon = new Coupon("Headphones", new Date(2017-1900, 3, 19), new Date(2019-1900, 4, 27), 10, CouponType.ELECTRICITY, "Pay 50₪ On Selected Headphones", 50, "Image");
		ALM.CreateCoupon(HeadphonesCoupon);
		Coupon TvCoupon = new Coupon("Lg Tv", new Date(2017-1900, 6, 12), new Date(2019-1900, 2, 3), 23, CouponType.ELECTRICITY, "9999₪ on 78 Lg Tv", 9999, "Image");
		ALM.CreateCoupon(TvCoupon);
		TestHelper.CompareTwoCoupons(HeadphonesCoupon, ALM.getCouponByTitle("Headphones"));
		TestHelper.CompareTwoCoupons(TvCoupon, ALM.getCouponByTitle("Lg Tv"));
		Collection<Coupon> couponList = ALM.getAllCoupons();
		Assert.assertEquals(couponList.size(), 2);
	}
	
	/*
	 *  test22_companyFacade_getAllCouponByType - checks method getAllCouponByType
	 *  create 3 coupons in database and assert check if the numbers of coupons created by type 
	 *  equal to actual number of coupons in database
	 */
	
	//@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void test22_companyFacade_getAllCouponByType()
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company IdigitalCompany = new Company("Idigital","Idigital1234","Idigital@Gmail.com");
		Admin.CreateCompany(IdigitalCompany);
		CompanyFacade Idigital = (CompanyFacade) couponSystem.login("Idigital", "Idigital1234", ClientType.COMPANY);
		Coupon AirpodsCoupon = new Coupon("Airpods", new Date(2017-1900, 7, 3), new Date(2019-1900, 6, 5), 15, CouponType.ELECTRICITY, "Airpods For 599₪", 599, "Image");
		Idigital.CreateCoupon(AirpodsCoupon);
		Coupon BeatsCoupon = new Coupon("Headphones", new Date(2017-1900, 2, 24), new Date(2019-1900, 7, 10), 20, CouponType.ELECTRICITY, "Beats Headphones For 300₪", 300, "Image");
		Idigital.CreateCoupon(BeatsCoupon);
		Coupon BreakfastCoupon = new Coupon("Breakfast", new Date(2017-1900, 5, 13), new Date(2019-1900, 8, 25), 10, CouponType.FOOD, "Breakfast For 30₪", 30, "Image");
		Idigital.CreateCoupon(BreakfastCoupon);
		Collection<Coupon> couponList = Idigital.getCouponByType(CouponType.ELECTRICITY);
		Assert.assertEquals(couponList.size(), 2);
	}
	
	/*
	 *  test23_companyFacade_getAllCouponByPrice - checks method getAllCouponByPrice
	 *  create 3 coupons in database and assert check if the numbers of coupons created by price 
	 *  equal to actual number of coupons in database
	 */
	
	//@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void test23_companyFacade_getAllCouponByPrice()
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company GrouponCompany = new Company("Groupon","Groupon1234","Groupon@Gmail.com");
		Admin.CreateCompany(GrouponCompany);
		CompanyFacade Groupon = (CompanyFacade) couponSystem.login("Groupon", "Groupon1234", ClientType.COMPANY);
		Coupon DysonCoupon = new Coupon("Dyson₪", new Date(2017-1900, 1, 21), new Date(2019-1900, 4, 13), 15, CouponType.ELECTRICITY, "Dyson V10 For 700₪", 700, "Image");
		Groupon.CreateCoupon(DysonCoupon);
		Coupon Ps4ProCoupon = new Coupon("Ps4Pro", new Date(2017-1900, 5, 4), new Date(2019-1900, 5, 2), 20, CouponType.ELECTRICITY, "Ps4Pro For 1500₪", 1500, "Image");
		Groupon.CreateCoupon(Ps4ProCoupon);
		Coupon DinnerCoupon = new Coupon("Dinner", new Date(2017-1900, 9, 13), new Date(2019-1900, 6, 25), 10, CouponType.FOOD, "Dinner for 50₪", 50, "Image");
		Groupon.CreateCoupon(DinnerCoupon);
		Collection<Coupon> couponList = Groupon.getCouponByPrice(750);
		Assert.assertEquals(couponList.size(), 2);
	}
	
	/*
	 *  test24_companyFacade_getCouponByDate - checks method getCouponByDate
	 *  create 2 coupons in database and assert check if the numbers of coupons created by date 
	 *  equal to actual number of coupons in database
	 */
	
	//@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void test24_companyFacade_getCouponByDate()
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company GrouponCompany = new Company("Groupon","Groupon1234","Groupon@Gmail.com");
		Admin.CreateCompany(GrouponCompany);
		CompanyFacade Groupon = (CompanyFacade) couponSystem.login("Groupon", "Groupon1234", ClientType.COMPANY);
		Coupon DysonCoupon = new Coupon("Dyson₪", new Date(2017-1900, 1, 21), new Date(2018-1900, 4, 13), 15, CouponType.ELECTRICITY, "Dyson V10 For 700₪", 700, "Image");
		Groupon.CreateCoupon(DysonCoupon);
		Coupon Ps4ProCoupon = new Coupon("Ps4Pro", new Date(2018-1900, 7, 4), new Date(2019-1900, 5, 2), 20, CouponType.ELECTRICITY, "Ps4Pro For 1500₪", 1500, "Image");
		Groupon.CreateCoupon(Ps4ProCoupon);
		Collection<Coupon> couponList = Groupon.getCouponByDate(new Date(2018-1900, 6, 14));
		Assert.assertEquals(couponList.size(), 1);
	}
	
/*
 * 	CUSTOMER FACADE METHODS CHECK
 */
	
	/*
	 *  test25_customerFacade_purchaseCoupon - checks method purchaseCoupon
	 *  create 2 coupon and 1 customer in database, customer purchase 1 coupon 
	 *  and assert check if coupon title purchased equals actual coupon title in database
	 */
	
	//@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void test25_customerFacade_purchaseCoupon()
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company AllSaleCompany = new Company("AllSale","AllSale1234","Yes@Gmail.com");
		Admin.CreateCompany(AllSaleCompany);
		Customer KobiCustomer = new Customer("Kobi", "Kobi1234");
		Admin.CreateCustomer(KobiCustomer);
		Coupon DishwasherCoupon = new Coupon("Dishwasher", new Date(2017-1900, 2, 3), new Date(2019-1900, 4, 29), 14, CouponType.ELECTRICITY, "Bosch Dishwasher For 800₪", 800, "Image");
		Coupon Ps4SlimCoupon = new Coupon("Ps4Slim", new Date(2017-1900, 3, 6), new Date(2019-1900, 11, 14), 26, CouponType.ELECTRICITY, "Ps4Slim For 799₪", 799, "Image");
		CompanyFacade AllSale = (CompanyFacade) couponSystem.login("AllSale", "AllSale1234", ClientType.COMPANY);
		AllSale.CreateCoupon(DishwasherCoupon);
		AllSale.CreateCoupon(Ps4SlimCoupon);
		CustomerFacade Kobi = (CustomerFacade) couponSystem.login("Kobi", "Kobi1234", ClientType.CUSTOMER);
		Kobi.PurchaseCoupon(DishwasherCoupon);
		Assert.assertEquals(DishwasherCoupon.getTitle(),AllSale.getCouponByTitle("Dishwasher").getTitle());
	}
	
	/*
	 *  test26_customerFacade_getAllPurchasedCoupons - checks method getAllPurchasedCoupons
	 *  create 3 coupons and 1 customer in database, and assert check if the numbers of coupons purchased
	 *  equal to actual number of coupons Purchased
	 */
	
	//@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void test26_customerFacade_getAllPurchasedCoupons()
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company RoladinCompany = new Company("Roladin","Roladin1234","Roladin@Gmail.com");
		Company KspCompany = new Company("Ksp","Ksp1234","Ksp@Gmail.com");
		Admin.CreateCompany(KspCompany);
		Admin.CreateCompany(RoladinCompany);
		Customer InbarCustomer = new Customer("Inbar", "Inbar1234");
		Admin.CreateCustomer(InbarCustomer);
		Coupon CafeCoupon = new Coupon("Cafe", new Date(2017-1900, 1, 5), new Date(2019-1900, 5, 24), 15, CouponType.RESTURANS, "Cafe For 4₪", 4, "Image");
		Coupon PasteryCoupon = new Coupon("Pastery", new Date(2017-1900, 3, 21), new Date(2019-1900, 6, 5), 3, CouponType.RESTURANS, "Pastery For 7₪", 7, "Image");
		Coupon MouseCoupon = new Coupon("Mouse", new Date(2017-1900, 3, 6), new Date(2019-1900, 11, 14), 5, CouponType.ELECTRICITY, "Razer Mouse For 250₪", 250, "Image");
		CompanyFacade Roladin = (CompanyFacade) couponSystem.login("Roladin", "Roladin1234", ClientType.COMPANY);
		Roladin.CreateCoupon(CafeCoupon);
		Roladin.CreateCoupon(PasteryCoupon);
		CompanyFacade Ksp = (CompanyFacade) couponSystem.login("Ksp", "Ksp1234", ClientType.COMPANY);
		Ksp.CreateCoupon(MouseCoupon);
		CustomerFacade Inbar = (CustomerFacade) couponSystem.login("Inbar", "Inbar1234", ClientType.CUSTOMER);
		Inbar.PurchaseCoupon(CafeCoupon);
		Inbar.PurchaseCoupon(MouseCoupon);
		Collection<Coupon> couponList = Inbar.getAllPurchasedCoupons();
		Assert.assertEquals(couponList.size(), 2);
	}
	
	/*
	 *  test27_customerFacade_getAllPurchasedCouponsByType - checks method getAllPurchasedCouponsByType
	 *  create 3 coupons and 1 customer in database, and assert check if the numbers of coupons purchased by type
	 *  equal to actual number of coupons Purchased by type
	 */
	
	//@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void test27_customerFacade_getAllPurchasedCouponsByType()
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company LametayelCompany = new Company("Lametayel","Lametayel1234","Lametayel@Gmail.com");
		Admin.CreateCompany(LametayelCompany);
		Customer YoavCustomer = new Customer("Yoav", "Yoav1234");
		Admin.CreateCustomer(YoavCustomer);
		Coupon TentCoupon = new Coupon("Tent", new Date(2017-1900, 2, 3), new Date(2019-1900, 4, 29), 15, CouponType.CAMPING, "Coleman Tent For 450₪", 450, "Image");
		Coupon SleepingbagCoupon = new Coupon("Sleepingbag", new Date(2017-1900, 3, 6), new Date(2019-1900, 11, 14), 20, CouponType.CAMPING, "Camptown Sleepingbag For 150₪", 150, "Image");
		Coupon FlashlightCoupon = new Coupon("Flashlight", new Date(2017-1900, 3, 21), new Date(2019-1900, 6, 5), 20, CouponType.ELECTRICITY, "Maglite FlashlightCoupon for 70₪", 70, "Image");
		CompanyFacade Lametayel = (CompanyFacade) couponSystem.login("Lametayel", "Lametayel1234", ClientType.COMPANY);
		Lametayel.CreateCoupon(SleepingbagCoupon);
		Lametayel.CreateCoupon(TentCoupon);
		Lametayel.CreateCoupon(FlashlightCoupon);
		CustomerFacade Yoav = (CustomerFacade) couponSystem.login("Yoav", "Yoav1234", ClientType.CUSTOMER);
		Yoav.PurchaseCoupon(TentCoupon);
		Yoav.PurchaseCoupon(SleepingbagCoupon);
		Assert.assertEquals(Yoav.getAllPurchasedCouponsByType(CouponType.CAMPING).size(), 2);
	}
	
	/*
	 *  test28_customerFacade_getAllPurchasedCouponsByPrice - checks method getAllPurchasedCouponsByPrice
	 *  create 3 coupons and 1 customer in database, and assert check if the numbers of coupons purchased by price
	 *  equal to actual number of coupons Purchased by price
	 */
	
	//@Ignore
	@SuppressWarnings("deprecation")
	@Test
	public void test28_customerFacade_getAllPurchasedCouponsByPrice()
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company LametayelCompany = new Company("Lametayel","Lametayel1234","Lametayel@Gmail.com");
		Admin.CreateCompany(LametayelCompany);
		Customer YoavCustomer = new Customer("Yoav", "Yoav1234");
		Admin.CreateCustomer(YoavCustomer);
		Coupon TentCoupon = new Coupon("Tent", new Date(2017-1900, 2, 3), new Date(2019-1900, 4, 29), 15, CouponType.CAMPING, "Coleman Tent For 450₪", 450, "Image");
		Coupon SleepingbagCoupon = new Coupon("Sleepingbag", new Date(2017-1900, 3, 6), new Date(2019-1900, 11, 14), 20, CouponType.CAMPING, "Camptown Sleepingbag For 150₪", 150, "Image");
		Coupon FlashlightCoupon = new Coupon("Flashlight", new Date(2017-1900, 3, 21), new Date(2019-1900, 6, 5), 20, CouponType.ELECTRICITY, "Maglite FlashlightCoupon for 70₪", 70, "Image");
		CompanyFacade Lametayel = (CompanyFacade) couponSystem.login("Lametayel", "Lametayel1234", ClientType.COMPANY);
		Lametayel.CreateCoupon(SleepingbagCoupon);
		Lametayel.CreateCoupon(TentCoupon);
		Lametayel.CreateCoupon(FlashlightCoupon);
		CustomerFacade Yoav = (CustomerFacade) couponSystem.login("Yoav", "Yoav1234", ClientType.CUSTOMER);
		Yoav.PurchaseCoupon(FlashlightCoupon);
		Yoav.PurchaseCoupon(SleepingbagCoupon);
		Assert.assertEquals(Yoav.getAllPurchasedCouponsByPrice(0, 170).size(), 2);
	}
	
	/*
 	 *  test29_customerFacade_CouponExpiredException - checks exception CouponExpiredException
	 *  create coupon in database with expired date, try to purchase coupon
	 *  to check if exception was thrown
	 */
	
	//@Ignore
	@SuppressWarnings("deprecation")
	@Test(expected = CouponExpiredException.class)
	public void test29_customerFacade_CouponExpiredException()
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company RikoshetCompany = new Company("Rikoshet","Rikoshetl1234","Rikoshet@Gmail.com");
		Admin.CreateCompany(RikoshetCompany);
		Customer RonCustomer = new Customer("Ron", "Ron1234");
		Admin.CreateCustomer(RonCustomer);
		Coupon SandalsCoupon = new Coupon("Sandals", new Date(2017-1900, 2, 3), new Date(2017-1900, 4, 29), 40, CouponType.CAMPING, "Sandals For 100₪", 100, "Image");
		CompanyFacade Rikoshet = (CompanyFacade) couponSystem.login("Rikoshet", "Rikoshetl1234", ClientType.COMPANY);
		Rikoshet.CreateCoupon(SandalsCoupon);
		CustomerFacade Ron = (CustomerFacade) couponSystem.login("Ron", "Ron1234", ClientType.CUSTOMER);
		Ron.PurchaseCoupon(SandalsCoupon);
	}
	
	/*
 	 *  test30_customerFacade_CustomerAlreadyPurchsedCouponException - checks exception CustomerAlreadyPurchsedCouponException
	 *  create coupon in database, purchase coupon and try to purchase coupon again
	 *  to check if exception was thrown
	 */
	
	//@Ignore
	@SuppressWarnings("deprecation")
	@Test(expected = CustomerAlreadyPurchsedCouponException.class)
	public void test30_customerFacade_CustomerAlreadyPurchsedCouponException()
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company RikoshetCompany = new Company("Rikoshet","Rikoshetl1234","Rikoshet@Gmail.com");
		Admin.CreateCompany(RikoshetCompany);
		Customer TamarCustomer = new Customer("Tamar", "Tamar1234");
		Admin.CreateCustomer(TamarCustomer);
		Coupon ShoesCoupon = new Coupon("Shoes", new Date(2017-1900, 2, 3), new Date(2019-1900, 6, 5), 27, CouponType.CAMPING, "Osolo Shoes For 600₪", 600, "Image");
		CompanyFacade Rikoshet = (CompanyFacade) couponSystem.login("Rikoshet", "Rikoshetl1234", ClientType.COMPANY);
		Rikoshet.CreateCoupon(ShoesCoupon);
		CustomerFacade Tamar = (CustomerFacade) couponSystem.login("Tamar", "Tamar1234", ClientType.CUSTOMER);
		Tamar.PurchaseCoupon(ShoesCoupon);
		Tamar.PurchaseCoupon(ShoesCoupon);
	}
	
	/*
 	 *  test31_customerFacade_ZeroAmountException - checks exception ZeroAmountException
	 *  create coupon in database with zero amount, try to purchase coupon
	 *  to check if exception was thrown
	 */
	
	//@Ignore
	@SuppressWarnings("deprecation")
	@Test(expected = ZeroAmountException.class)
	public void test31_customerFacade_ZeroAmountException()
	{
		AdminFacade Admin =(AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company RikoshetCompany = new Company("Rikoshet","Rikoshetl1234","Rikoshet@Gmail.com");
		Admin.CreateCompany(RikoshetCompany);
		Customer BenCustomer = new Customer("Ben", "Ben1234");
		Admin.CreateCustomer(BenCustomer);
		Coupon ShirtCoupon = new Coupon("Shirt", new Date(2017-1900, 2, 3), new Date(2019-1900, 6, 5), 0, CouponType.CAMPING, "NorthFace Shirt For 150₪", 150, "Image");
		CompanyFacade Rikoshet = (CompanyFacade) couponSystem.login("Rikoshet", "Rikoshetl1234", ClientType.COMPANY);
		Rikoshet.CreateCoupon(ShirtCoupon);
		CustomerFacade Ben = (CustomerFacade) couponSystem.login("Ben", "Ben1234", ClientType.CUSTOMER);
		Ben.PurchaseCoupon(ShirtCoupon);
	}
		
}


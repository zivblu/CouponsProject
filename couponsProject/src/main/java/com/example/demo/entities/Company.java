package com.example.demo.entities;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


@Entity(name="COMPANY")
public class Company {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name="COMP_NAME")
	private String companyName;
	
	@Column(name="PASSWORD")
	private String password;

	@Column(name="EMAIL")
	private String email;

	/*
	 * 1. OneToMany association between Company to Coupon,
	 *    creates new column in Table "COUPON" named "company_id"
	 * 
	 * 2. Fetchtype.EAGER - lets the data of any company to be loaded with his coupons
	 *
	 * 3. CascadeType.All - lets company to do operations 
	 *    (PERSIST, REMOVE, REFRESH, MERGE, DETACH) on Coupon
	 */
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="company_id")
	private Collection<Coupon> coupons;

	/*
	 * default constructor
	 */
	
	@SuppressWarnings("unused")
	private Company()
	{
		super();
	}
	
	/*
	 *  constructor
	 */
	
	public Company(String companyName, String password, String email) {
		super();
		this.companyName = companyName;
		this.password = password;
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	public void addCoupon(Coupon coupon) {
		this.coupons.add(coupon);
	}

	public void removeCoupon(Coupon coupon) {
		this.coupons.remove(coupon);
	}
	
	@Override
	public String toString() {
		return "Company [id=" + id + ", companyName=" + companyName + ", password=" + password + ", email=" + email
				+ ", coupons=" + this.coupons + "]";
	}
}

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity(name="CUSTOMER")
public class Customer {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name="CUST_NAME")
	private String custName;
	
	@Column(name="PASSWORD")
	private String password;

	/*
	 * 1. ManyToMany association between Customer to Coupon,
	 *    creates new Table (customer_coupon) in database with two columns
	 *    named (coupon_id) and (customer_id)
	 * 
	 * 2. Fetchtype.EAGER - lets the data of any customer to be loaded with his coupons
	 *
	 * 3. Cascade Options: DETACH - detaches customer, 
	 * REFRESH - refresh customer and MERGE - performs persist(insert) & update
	 * 
	 */
	
	@ManyToMany(fetch=FetchType.EAGER, cascade = {CascadeType.DETACH , CascadeType.MERGE , CascadeType.REFRESH})
	@JoinTable(name = "customer_coupon", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "coupon_id"))
	private Collection<Coupon> coupons;
	
	/*
	 * default constructor
	 */	
	
	@SuppressWarnings("unused")
	private Customer()
	{
		super();
	}
	
	/*
	 * constructor
	 */	
	
	public Customer(String custName, String password) {
		super();
		this.custName = custName;
		this.password = password;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", password=" + password + ", coupons=" + coupons+ "]";
	}
}

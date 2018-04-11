package com.example.demo.entities;

import java.util.Collection;
import java.util.Date;

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

import com.example.demo.common.CouponType;

@Entity(name="COUPON")
public class Coupon {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name="TITLE")
	private String title;
	
	@Column(name="START_DATE")
	private Date startDate;

	@Column(name="END_DATE")
	private Date endDate;

	@Column(name="AMOUNT")
	private Integer amount;

	@Column(name="COUPON_TYPE")
	private CouponType type;
	
	@Column(name="MESSAGE")
	private String message;

	@Column(name="PRICE")
	private double price;

	@Column(name="IMAGE")
	private String image;
	
	/*
	 * 1. ManyToMany association between Coupon to Customer,
	 *    creates new Table (customer_coupon) in database with two columns
	 *    named (coupon_id) and (customer_id)
	 * 
	 * 2. Fetchtype.EAGER - lets the data of any customer to be loaded with his coupons
	 *
	 * 3. Cascade Options: DETACH - detaches customer, REFRESH - refresh customer   
	 */
	
	@ManyToMany(fetch=FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(name = "customer_coupon", joinColumns = @JoinColumn(name = "coupon_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private Collection<Customer> customers;
	
	/*
	 * default constructor
	 */
	
	@SuppressWarnings("unused")
	private Coupon()
	{
		super();
	}
	
	/*
	 * constructor
	 */
	
	public Coupon(String title, Date startDate, Date endDate, Integer amount, CouponType type, String message,
			double price, String image) {
		super();
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public CouponType getType() {
		return type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + ", customers=" + customers.size() + "]";
	}
}

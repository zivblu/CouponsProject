package com.example.demo.thread;

import org.springframework.context.ApplicationContext;
import com.example.demo.DBDAO.CouponDBDAO;

public class DailyCouponTask implements Runnable {
	
	private CouponDBDAO couponDBDAO;

	private boolean quit;

	public DailyCouponTask(ApplicationContext ctx) {
		this.couponDBDAO = ctx.getBean(CouponDBDAO.class);
		this.quit = false;
	}

	/*
	 * run the Daily Coupon Expiration Task deletes Coupons which there endDate is
	 * expired
	 */
	@Override
	public void run() {
			try {
				deleteExpiredCoupons();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	public synchronized void deleteExpiredCoupons() throws InterruptedException {
		while(!quit) 
		{
		couponDBDAO.deleteCouponsThatExpired();
		Thread.sleep(1000 * 60 * 60 * 24);
		}
	}
	public void stopTask() {
			quit = true;
			Thread.currentThread().interrupt();
	}
}
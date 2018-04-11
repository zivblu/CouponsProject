package com.example.demo.entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.example.demo.common.ClientType;
import com.example.demo.connection.ConnectionPool;
import com.example.demo.facades.AdminFacade;
import com.example.demo.facades.CompanyFacade;
import com.example.demo.facades.CouponClientFacade;
import com.example.demo.facades.CustomerFacade;
import com.example.demo.thread.DailyCouponTask;

/*
 *  CouponSystem is entry point for login and initiate the Daily Coupon task
 */

@Component
@Scope("singleton")
public class CouponSystem {

	@Autowired
	AdminFacade adminFacade;
	@Autowired
	CompanyFacade companyFacade;
	@Autowired
	CustomerFacade customerFacade;
	@Autowired
	ApplicationContext ctx;
	
	private boolean DailyThreadTaskIgnite = true;

	public CouponSystem()
	{
		super();
	}
	
	/*
	 *  Method returns Facade and invokes the Daily Coupon Task
	 */

	public CouponClientFacade login(String name ,String password, ClientType clientType){
		
		if (this.DailyThreadTaskIgnite) 
		{
			DailyCouponTask dailyThread = new DailyCouponTask(ctx);
			Thread invokeDailyThreadTask = new Thread(dailyThread);
			invokeDailyThreadTask.start();
			this.DailyThreadTaskIgnite = false;
		}
		
		switch (clientType) {
		case ADMIN:
			return adminFacade.login(name, password, clientType);
		case COMPANY:
			return companyFacade.login(name, password, clientType);
		case CUSTOMER:
			return customerFacade.login(name, password, clientType);
		}
		return null;
	}
	
	/*
	 *  method that stops the daily Coupon Task also terminates all connection 
	 *  and the currently running program immediately
	 */
	
	public void shutdown() {
		boolean terminate = ConnectionPool.getInstance().terminateDailyTaskAndCloseConnectionPool();
		if (terminate) {
			System.out.println("stoped daily task and closed connection pool");
			System.exit(0);
		}
	
	}
}
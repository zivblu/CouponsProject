package com.example.demo.facades;

import com.example.demo.common.ClientType;

public interface CouponClientFacade {
	
	CouponClientFacade login(String name, String password, ClientType clientType);

}

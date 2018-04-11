package com.example.demo.connection;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.exceptions.SystemShutttingDownException;

public class ConnectionPool {

	private static ConnectionPool _INSTANCE = null;
	private List<DBConnection> connections;
	private static final int MAX_CONNECTIONS = 200 ;
	private boolean close;

	public static synchronized ConnectionPool getInstance() 
	{
		if (_INSTANCE == null) 
		{
			_INSTANCE = new ConnectionPool();
		}
		return _INSTANCE;
	}
	
	private ConnectionPool() {
		this.connections = new ArrayList<>();
		this.close = false;
		for (int i = 0; i < MAX_CONNECTIONS; i++) 
		{
			this.connections.add(new DBConnection());
		}		
	} 	
	
    public synchronized DBConnection getConnection() {
        while (this.connections.isEmpty() && !close) 
        	try{          
            	this.wait();
        	} catch (InterruptedException e) {
				e.printStackTrace();
			}
        if(close)
        	throw new SystemShutttingDownException("The system is shutting down");
        DBConnection Userconnection = this.connections.get(0);
        this.connections.remove(0);        
        return Userconnection;
    }
    
    public synchronized void returnUserConenction(DBConnection connection)
    {
        this.connections.add(connection);
        this.notify(); 
    }

    public boolean terminateDailyTaskAndCloseConnectionPool() {
    	this.close = true;
    	try 
    	{
			Thread.sleep(1000 * 30);
		} 
    	catch (InterruptedException e) 
    	{			
			e.printStackTrace();
		}
    	return this.connections.removeAll(connections);
    }

}

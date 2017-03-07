package com.ca.g11n.gDashboard;

import java.util.ResourceBundle;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationInitializer implements ServletContextListener {

// implementation of ServletContextListener

	@Override
	public void contextInitialized( ServletContextEvent event ) {
		ResourceBundle applicationResourceBundle = ResourceManager.getApplicationResourceBundle();
		String productName = applicationResourceBundle.getString( "product.name" );
		
		System.out.println( "@Initializing " + productName + "..." );
		DataCache.getInstance().startThread();
	}
	@Override
	public void contextDestroyed( ServletContextEvent event ) {
		ResourceBundle applicationResourceBundle = ResourceManager.getApplicationResourceBundle();
		String productName = applicationResourceBundle.getString( "product.name" );
		
		System.out.println( "@Stopping " + productName + "..." );
		DataCache.getInstance().stopThread();
	}

}

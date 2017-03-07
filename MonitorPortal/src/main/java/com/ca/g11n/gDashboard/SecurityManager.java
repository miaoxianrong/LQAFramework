package com.ca.g11n.gDashboard;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SecurityManager {

// private fields

	// Singleton
	private static final SecurityManager m_securityManager = new SecurityManager();
	
	private final List<String> m_systemAdministratorList;

// constructor

	private SecurityManager() {
		ResourceBundle applicationResourceBundle = ResourceManager.getApplicationResourceBundle();
		String systemAdministrators = applicationResourceBundle.getString( "systemAdministrators" );
		m_systemAdministratorList = Arrays.asList( systemAdministrators.split( "," ) );
	}


// public methods

	public static SecurityManager getInstance() {
		return m_securityManager;
	}
	
	public boolean isSystemAdministrator( String userName ) {
		return m_systemAdministratorList.contains( userName );
	}


// for testing

	public static void main( String[] args ) {
		SecurityManager securityManager = SecurityManager.getInstance();
		System.out.println( securityManager.isSystemAdministrator( "furto01" ) );
		System.out.println( securityManager.isSystemAdministrator( "furto02" ) );
		System.out.println( securityManager.isSystemAdministrator( "iijku01" ) );
		System.out.println( securityManager.isSystemAdministrator( "iijku02" ) );
		System.out.println( securityManager.isSystemAdministrator( "hossh01" ) );
		System.out.println( securityManager.isSystemAdministrator( "hossh02" ) );
	}

}

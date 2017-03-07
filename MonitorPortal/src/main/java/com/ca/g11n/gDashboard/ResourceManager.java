package com.ca.g11n.gDashboard;

import java.util.ResourceBundle;

public class ResourceManager {

// private fields

	private static ResourceBundle m_applicationResourceBundle = ResourceBundle.getBundle( "Application" );
	private static ResourceBundle m_messageResourceBundle = ResourceBundle.getBundle( "MessageResources" );


// public methods

	public static ResourceBundle getApplicationResourceBundle() {
		return m_applicationResourceBundle;
	}
	public static ResourceBundle getMessageResourceBundle() {
		return m_messageResourceBundle;
	}
	
}

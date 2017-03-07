package com.ca.g11n.gDashboard;

import java.util.HashMap;
import java.util.Map;

public enum Tab {

// constants

	ANNOUNCEMENT( "Announcement", false ),
	EXECUTIVE( "Executive", true ),
	PM( "PM", true ),
	TRANSLATOR( "Translator", true ),
	QA( "QA", true ),
	ADMINISTRATION( "Administration", true );


// private fields

	private final String m_tabName;
	private final boolean m_loginRequired;
	private Page m_defaultPage = null;
	private static final Map<String, Tab> m_idTabMap = new HashMap<String, Tab>();
	static {
		for( Tab tab : values() ) {
			m_idTabMap.put( tab.getId(), tab );
		}
	}


// constructor

	private Tab( String tabName, boolean loginRequired ) {
		m_tabName = tabName;
		m_loginRequired = loginRequired;
	}


// public methods

	public String getId() {
		return toString();
	}
	public String getTabName() {
		return m_tabName;
	}
	public boolean getLoginRequired() {
		return m_loginRequired;
	}
	public Page getDefaultPage() {
		return m_defaultPage;
	}
	
	public static Tab fromId( String id ) {
		return m_idTabMap.get( id );
	}


// package-private methods

	void setDefaultPage( Page page ) {
		m_defaultPage = page;
	}

}

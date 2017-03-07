package com.ca.g11n.gDashboard;

import java.util.HashMap;
import java.util.Map;

public enum Page {

// constants

	ANNOUNCEMENT_TOP( "Announcement Top", "announcement/announcement_top.jsp", Tab.ANNOUNCEMENT, true, "" ),
	EXECUTIVE_TOP( "Executive Top", "executive/executive_top.jsp", Tab.EXECUTIVE, true, "" ),
	PM_TOP( "PM Top", "pm/pm_top.jsp", Tab.PM, true, "" ),
	TRANSLATOR_TOP( "Translator Top", "translator/translator_top.jsp", Tab.TRANSLATOR, true, "" ),
	QA_TOP( "QA Top", "qa/qa_top.jsp", Tab.QA, true, "" ),
	ADMINISTRATION_TOP( "Administration Top", "administration/administration_top.jsp", Tab.ADMINISTRATION, true, "" ),
	ADMINITRATION_DUMMY( "Administration Dummy", "administration/administration_dummy.jsp", Tab.ADMINISTRATION, false, "" );


// private fields

	private final String m_name;
	private final String m_jspPage;
	private final Tab m_tab;
	private final String m_breadcrumb;
	private static final Map<String, Page> m_idPageMap = new HashMap<String, Page>();
	static {
		for( Page page: values() ) {
			m_idPageMap.put( page.getId(), page );
		}
	}


// constructor

	private Page( String name, String jspPage, Tab tab, boolean tabDefaultPage, String breadcrumb ) {
		m_name = name;
		m_jspPage = jspPage;
		m_tab = tab;
		m_breadcrumb = breadcrumb;
		if( tabDefaultPage ) {
			tab.setDefaultPage( this );
		}
	}


// public methods

	public String getId() {
		return toString();
	}
	public String getName() {
		return m_name;
	}
	public String getJspPage() {
		return m_jspPage;
	}
	public Tab getTab() {
		return m_tab;
	}
	public String getBreadcrumb() {
		return m_breadcrumb;
	}
	
	public static Page fromId( String id ) {
		return m_idPageMap.get( id );
	}

}

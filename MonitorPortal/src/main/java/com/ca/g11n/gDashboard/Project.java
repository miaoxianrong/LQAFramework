package com.ca.g11n.gDashboard;

import java.util.List;

public class Project {

// private fields

	private final String m_id;
	private final String m_name;
	private final String m_pmName;
	private List<Language> m_languageList;


// constructor

	public Project( String id, String name, String pmName, List<Language> languageList ) {
		m_id = id;
		m_name = name;
		m_pmName = pmName;
		m_languageList = languageList;
	}


// public methods

	public String getId() {
		return m_id;
	}
	public String getName() {
		return m_name;
	}
	public String getPmName() {
		return m_pmName;
	}
	public List<Language> getLanguageList() {
		return m_languageList;
	}
	public void setLanguageList( List<Language> languageList ) {
		m_languageList = languageList;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append( '(' );
		builder.append( m_id ).append( ", " );
		builder.append( m_name ).append( ", " );
		builder.append( m_pmName ).append( ", " );
		builder.append( '{' );
		for( Language language : m_languageList ) {
			builder.append( language.toString() ).append( ", " );
		}
		builder.append( '}' );
		builder.append( ')' );
		
		return builder.toString();
	}
	
}

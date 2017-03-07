package com.ca.g11n.gDashboard;

/*
 * This class will be used to create Project list and their supporting Language list.
 */
public class ProjectAndLanguage {

// private fields

	private final String m_projectId;
	private final String m_projectName;
	private final String m_pmName;
	private final String m_languageCode;
	private final String m_languageName;


// constructor

	ProjectAndLanguage(
		String projectId,
		String projectName,
		String pmName,
		String languageCode,
		String languageName
	) {
		m_projectId = projectId;
		m_projectName = projectName;
		m_pmName = pmName;
		m_languageCode = languageCode;
		m_languageName = languageName;
	}


// public methods

	public String getProjectId() {
		return m_projectId;
	}
	public String getProjectName() {
		return m_projectName;
	}
	public String getPmName() {
		return m_pmName;
	}
	public String getLanguageCode() {
		return m_languageCode;
	}
	public String getLanguageName() {
		return m_languageName;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append( '(' );
		builder.append( m_projectId ).append( ", " );
		builder.append( m_projectName ).append( ", " );
		builder.append( m_pmName ).append( ", " );
		builder.append( m_languageCode ).append( ", " );
		builder.append( m_languageName );
		builder.append( ')' );
		
		return builder.toString();
	}

}

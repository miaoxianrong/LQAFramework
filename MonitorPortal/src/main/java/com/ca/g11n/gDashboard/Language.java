package com.ca.g11n.gDashboard;

import java.util.HashMap;
import java.util.Map;

public class Language {

// private fields

	private static final Map<String, Language> m_languageCodeLanguageMap = new HashMap<String, Language>();
	private final String m_code;
	private final String m_name;


// constructor

	/*
	 * Constructor cannot be called from outside.
	 * Call Language.createLanguage() instead.
	 */
	private Language( String code, String name ) {
		m_code = code;
		m_name = name;
	}


// public methods

	public static Language createLanguage( String code, String name ) {
		Language language = m_languageCodeLanguageMap.get( code );
		if( language != null ) {
			if( !language.getName().equals( name ) ) {
				language = new Language( code, name );
				m_languageCodeLanguageMap.put( code, language );
			}
		} else {
			language = new Language( code, name );
			m_languageCodeLanguageMap.put( code, language );
		}
		return language;
	}
	public static Language getLanguageFromLanguageCode( String code ) {
		return m_languageCodeLanguageMap.get( code );
	}
	public String getCode() {
		return m_code;
	}
	public String getName() {
		return m_name;
	}
	
	@Override
	public String toString() {
		return m_name;
	}

}

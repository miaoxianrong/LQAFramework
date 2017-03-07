package com.ca.g11n.gDashboard;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class DataCache {

// private fields

	// Singleton
	private static DataCache m_instance = new DataCache();
	
	private Thread m_dataCacheThread = new DataCacheThread();
	private boolean m_threadRunning = false;
	
	private List<Language> m_languageList = Collections.emptyList();
	private List<Project> m_projectList = Collections.emptyList();


// constructor

	private DataCache() {}


// public methods

	public static DataCache getInstance() {
		return m_instance;
	}
	
	public synchronized List<Language> getLanguageList() {
		return m_languageList;
	}
	public synchronized List<Project> getProjectList() {
		return m_projectList;
	}
	
	public void startThread() {
		m_threadRunning = true;
		m_dataCacheThread.start();
	}
	public void stopThread() {
		m_threadRunning = false;
		m_dataCacheThread.interrupt();
	}


// private methods

	private synchronized boolean isThreadRunning() {
		return m_threadRunning;
	}
	
	private synchronized void setLanguageList( List<Language> languageList ) {
		m_languageList = languageList;
	}
	private synchronized void setProjectList( List<Project> projectList ) {
		m_projectList = projectList;
	}


// inner class

	class DataCacheThread extends Thread {
	
	// override
	
		@Override
		public void run() {
			ClarityDbAccess clarityDbAccess = DbAccess.getClarityDbAccess();
			
			ResourceBundle applicationResourceBundle = ResourceManager.getApplicationResourceBundle();
			long dbDataFetchInterval = Long.valueOf( applicationResourceBundle.getString( "dbData.fetch.interval" ) ).longValue();
			
			while( isThreadRunning() ) {
				try {
					if( !isThreadRunning() ) return;
					List<Language> languageList = clarityDbAccess.getLanguageList();
					if( !isThreadRunning() ) return;
					setLanguageList( languageList );
System.out.println( "@Language list updated");
					
					if( !isThreadRunning() ) return;
					List<ProjectAndLanguage> projectAndLanguageList = clarityDbAccess.getProjectAndLanguageList();
					List<Project> projectList = Utility.convertProjectAndLanguageListToProjectList( projectAndLanguageList );
					if( !isThreadRunning() ) return;
					setProjectList( projectList );
System.out.println( "@Project list updated");
				} catch( SQLException ex ) {
					ex.printStackTrace();
				}
				
				try {
					Thread.sleep( dbDataFetchInterval );
				} catch( InterruptedException ex ) {
//					ex.printStackTrace();
				}
			}
		}
	
	}

}

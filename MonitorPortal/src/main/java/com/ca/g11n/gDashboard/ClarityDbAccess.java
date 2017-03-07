package com.ca.g11n.gDashboard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClarityDbAccess {

// private fields

	private Connection m_connection = null;


// constructor

	ClarityDbAccess() {
		try {
			initJDBCDriver();
		} catch( ClassNotFoundException ex ) {
			ex.printStackTrace();
			throw new Error( "@Failed to find JDBC driver: " + ex.toString() );
		}
		try {
			connect();
		} catch( SQLException ex ) {
			ex.printStackTrace();
			throw new Error( "@Failed to connect to DB server: " + ex.toString() );
		}
	}


// public methods

	public List<Language> getLanguageList() throws SQLException {
		try {
			return call_getLanguageList();
		} catch( SQLException ex ) {
			connect();
			return call_getLanguageList();
		}
	}
	public List<ProjectAndLanguage> getProjectAndLanguageList() throws SQLException {
		try {
			return call_getProjectAndLanguageList();
		} catch( SQLException ex ) {
			connect();
			return call_getProjectAndLanguageList();
		}
	}


// private methods

	private void initJDBCDriver() throws ClassNotFoundException {
		ResourceBundle applicationResourceBundle = ResourceManager.getApplicationResourceBundle();
		String dbDriver = applicationResourceBundle.getString( "db1.driver" );
		
		Class.forName( dbDriver );
	}
	private void connect() throws SQLException {
		ResourceBundle applicationResourceBundle = ResourceManager.getApplicationResourceBundle();
		String dbHost = applicationResourceBundle.getString( "db1.host" );
		String dbUserName = applicationResourceBundle.getString( "db1.username" );
		String dbPassword = applicationResourceBundle.getString( "db1.password" );
		String dbUrl1 = applicationResourceBundle.getString( "db1.url1" );
		String dbUrl2 = applicationResourceBundle.getString( "db1.url2" );
		
		m_connection = DriverManager.getConnection( dbUrl1 + dbHost + dbUrl2, dbUserName, dbPassword );
	}
	
	// actual DB access
	private List<Language> call_getLanguageList() throws SQLException {
		List<Language> languageList = new ArrayList<Language>();
		
		ResourceBundle applicationResourceBundle = ResourceManager.getApplicationResourceBundle();
		String sql = applicationResourceBundle.getString( "sql.1.1" );
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = m_connection.prepareStatement( sql );
			resultSet = preparedStatement.executeQuery();
			while( resultSet.next() ) {
				String code = resultSet.getString( 1 );
				String name = resultSet.getString( 2 );
				languageList.add( Language.createLanguage( code, name ) );
			}
		} finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				} catch( SQLException ex ) {}
			}
			if( preparedStatement != null ) {
				try {
					preparedStatement.close();
				} catch( SQLException ex ) {}
			}
		}
		
		return languageList;
	}
	private List<ProjectAndLanguage> call_getProjectAndLanguageList() throws SQLException {
		List<ProjectAndLanguage> projectAndLanguageList = new ArrayList<ProjectAndLanguage>();
		
		ResourceBundle applicationResourceBundle = ResourceManager.getApplicationResourceBundle();
		String sql = applicationResourceBundle.getString( "sql.1.2" );
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = m_connection.prepareStatement( sql );
			resultSet = preparedStatement.executeQuery();
			while( resultSet.next() ) {
				String projectId = resultSet.getString( 1 );
				String projectName = resultSet.getString( 2 );
				String pmName = resultSet.getString( 3 );
				String languageCode = resultSet.getString( 4 );
				String languageName = resultSet.getString( 5 );
				projectAndLanguageList.add( new ProjectAndLanguage( projectId, projectName, pmName, languageCode, languageName ) );
			}
		} finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				} catch( SQLException ex ) {}
			}
			if( preparedStatement != null ) {
				try {
					preparedStatement.close();
				} catch( SQLException ex ) {}
			}
		}
		
		return projectAndLanguageList;
	}


// for testing

	public static void main( String[] args ) throws SQLException {
		long s0 = System.nanoTime();
		ClarityDbAccess clarityDbAccess = DbAccess.getClarityDbAccess();
		long s1 = System.nanoTime();
System.out.println( ( ( s1 - s0 ) / 1000000 ) + "ms" );
		List<Language> languageList = clarityDbAccess.getLanguageList();
		long s2 = System.nanoTime();
System.out.println( ( ( s2 - s1 ) / 1000000 ) + "ms" );
		for( Language language : languageList ) {
			System.out.println( language );
		}
		long s3 = System.nanoTime();
System.out.println( ( ( s3 - s2 ) / 1000000 ) + "ms" );
		List<ProjectAndLanguage> projectAndLanguageList = clarityDbAccess.getProjectAndLanguageList();
		long s4 = System.nanoTime();
System.out.println( ( ( s4 - s3 ) / 1000000 ) + "ms" );
		List<Project> projectList = Utility.convertProjectAndLanguageListToProjectList( projectAndLanguageList );
		long s5 = System.nanoTime();
		System.out.println( ( ( s5 - s4 ) / 1000000 ) + "ms" );
		for( Project project : projectList ) {
			System.out.println( project );
		}
	}

}

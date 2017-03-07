package com.ca.g11n.gDashboard;

public enum Role {

// constants

	USER( 0, "User" ),
	MANAGEMENT( 1, "Management" ),
	PM( 2, "PM" ),
	ENGINEER( 3, "Engineer" ),
	TRANSLATOR( 4, "Translator" ),
	QA( 5, "QA" ),
	DEVELOPMENT( 6, "Development" ),
	ADMINISTRATOR( 7, "Administrator" );


// private fields

	private final int m_id;
	private final String m_description;


// constructor

	private Role( int id, String description ) {
		m_id = id;
		m_description = description;
	}


// public methods

	public int getId() {
		return m_id;
	}
	public String getDescription() {
		return m_description;
	}

	public static Role fromId( int id ) {
		for( Role role : values() ) {
			if( role.getId() == id ) {
				return role;
			}
		}
		return null;
	}


// for testing

	public static void main( String[] args ) {
		for( Role role : values() ) {
			System.out.println( role.getId() + ", " + role.getDescription() + ", " + role.toString() );
		}
	}

}
